package com.mthien.yumble.service;

import com.mthien.yumble.entity.Enum.UserStatus;
import com.mthien.yumble.entity.InvalidatedToken;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.UserMapper;
import com.mthien.yumble.payload.request.auth.*;
import com.mthien.yumble.payload.response.auth.AuthResponse;
import com.mthien.yumble.payload.response.auth.IntrospectResponse;
import com.mthien.yumble.repository.InvalidatedTokenRepo;
import com.mthien.yumble.repository.UserRepo;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @Value("${jwt.validDuration}")
    protected long VALID_DURATION;
    @Value("${jwt.refreshableDuration}")
    protected long REFRESHABLE_DURATION;
    @Value("${app.base-url}")
    protected String BASE_URL;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final JavaMailSenderImpl mailSender;
    private final InvalidatedTokenRepo invalidatedTokenRepo;
    private final SpringTemplateEngine springTemplateEngine;
    private final PremiumService premiumService;

    public void register(RegisterRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Users users = userMapper.register(request);
        if (userRepo.findByEmail(users.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        userRepo.save(users);
    }

    public AuthResponse login(LoginRequest request) {
        Users users = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
        if (users.getStatus().equals(UserStatus.UNVERIFIED)) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_VERIFIED);
        }
        if (!passwordEncoder.matches(request.getPassword(), users.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        var token = generateToken(users);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public void logout(LogoutRequest request) {
        revokeToken(request.getToken());
    }

    public void resetPassword(String token, String newPassword, String confirmPassword) {
        Users users = extractUserInformation(token);
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
        if (newPassword == null || !newPassword.matches(passwordPattern)) {
            throw new AppException(ErrorCode.INVALID_PASSWORD_FORMAT); // Tạo mã lỗi phù hợp
        }
        if (!newPassword.equals(confirmPassword)) {

            throw new AppException(ErrorCode.INVALID_RE_PASSWORD);
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        users.setPassword(encodedPassword);
        userRepo.save(users);
    }

    public void sendVerificationEmail(String email) {
        try {
            Users users = userRepo.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
            if (users.getStatus().equals(UserStatus.UNVERIFIED)) throw new AppException(ErrorCode.ACCOUNT_VERIFIED);
            String url = BASE_URL + "/auth/verify?token=" + generateToken(users);
            String subject = "Xác thực tài khoản Yumble";
            //Add SpringTemplateEngine vào message của email
            Context context = new Context();
            context.setVariable("name", users.getName());
            context.setVariable("url", url);
            String message = springTemplateEngine.process("EmailVerification", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendForgotPasswordEmail(String email) {
        try {
            Users users = userRepo.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
            if (users.getStatus().equals(UserStatus.UNVERIFIED)) throw new AppException(ErrorCode.ACCOUNT_NOT_VERIFIED);
            String url = BASE_URL + "/auth/reset-password?token=" + generateToken(users);
            String subject = "Quên mật khẩu tài khoản Yumble";

            Context context = new Context();
            context.setVariable("name", users.getName());
            context.setVariable("url", url);
            String message = springTemplateEngine.process("EmailForgotPassword", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void verifyAccount(String token) {
        Users users = extractUserInformation(token);
        premiumService.initPremium(users.getId());
        users.setStatus(UserStatus.VERIFIED);
        userRepo.save(users);
    }

    public AuthResponse refreshToken(RefreshRequest request) throws ParseException {
        //Kiểm tra hiệu lực của token
        var signedJWT = verifiedToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepo.save(invalidatedToken);

        var userId = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepo.findById(userId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        var token = generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    private String generateToken(Users users) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getId())
                .issuer("MThien")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())                //Đặt id cho token
                .claim("scope", users.getRole().toString())   //Claim role của người dùng vào token để phân quyền
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Không thể tạo token", e);
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifiedToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .isValid(isValid)
                .build();
    }

    public SignedJWT verifiedToken(String token, boolean isRefresh) {
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = (isRefresh)
                    ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.HOURS).toEpochMilli())
                    : signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);
            if (!(verified && expiryTime.after(new Date()))) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            if (invalidatedTokenRepo.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            return signedJWT;
        } catch (JOSEException | ParseException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private Users extractUserInformation(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            String userId = signedJWT.getJWTClaimsSet().getSubject();
            if (!signedJWT.verify(verifier)) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
            return userRepo.findById(userId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        } catch (JOSEException | ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public void revokeToken(String token) {
        try {
            var signToken = verifiedToken(token, true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepo.save(invalidatedToken);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
