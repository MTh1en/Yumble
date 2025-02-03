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
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final JavaMailSenderImpl mailSender;
    private final InvalidatedTokenRepo invalidatedTokenRepo;

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

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            var signToken = verifiedToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();                    //Lấy id của token
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();      //Lấy  thời gian hết hết hạn của token
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepo.save(invalidatedToken);
        }catch (AppException exception){
            log.info("Token đã hết hạn");
        }
    }

    public void sendVerificationEmail(String email) {
        try {
            Users users = userRepo.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
            String token = generateToken(users);
            String url = "http://localhost:8081/auth/verify?token=" + token;
            String subject = "Xác thực tài khoản Yumble";
            String message = "<html>" +
                    "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;'>" +
                    "<table align='center' width='600' style='border-collapse: collapse; background-color: #ffffff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
                    "    <tr>" +
                    "        <td style='padding: 20px; text-align: center; background-color: #4CAF50; color: #ffffff;'>" +
                    "            <h2 style='margin: 0;'>Xác thực tài khoản của bạn</h2>" +
                    "        </td>" +
                    "    </tr>" +
                    "    <tr>" +
                    "        <td style='padding: 20px; font-size: 16px; color: #333333;'>" +
                    "            <p>Chào <strong>" + users.getName() + "</strong>,</p>" +
                    "            <p>Cảm ơn bạn đã đăng ký tài khoản với chúng tôi! Để hoàn tất quá trình đăng ký, vui lòng nhấn vào nút bên dưới để xác thực tài khoản của bạn:</p>" +
                    "            <p style='text-align: center; margin: 20px 0;'>" +
                    "                <a href='" + url + "' style='background-color: #4CAF50; color: white; padding: 15px 25px; text-decoration: none; font-size: 16px; border-radius: 5px; display: inline-block;'>Xác thực tài khoản</a>" +
                    "            </p>" +
                    "            <p>Nếu bạn không đăng ký tài khoản này, vui lòng bỏ qua email này.</p>" +
                    "        </td>" +
                    "    </tr>" +
                    "    <tr>" +
                    "        <td style='padding: 20px; text-align: center; font-size: 14px; color: #555555; background-color: #f4f4f4;'>" +
                    "            <p style='margin: 0;'>Trân trọng,<br><strong>YUMBLE</strong></p>" +
                    "        </td>" +
                    "    </tr>" +
                    "</table>" +
                    "</body>" +
                    "</html>";
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void verifyAccount(String token) {
        Users users = validateToken(token);
        users.setStatus(UserStatus.VERIFIED);
        userRepo.save(users);
    }

    public AuthResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
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
                .expirationTime(new Date(Instant.now().plus(5, ChronoUnit.SECONDS).toEpochMilli()))
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

    private SignedJWT verifiedToken(String token, boolean isRefresh) throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
        : signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidatedTokenRepo.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    private Users validateToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            String userId = signedJWT.getJWTClaimsSet().getSubject();
            if (!signedJWT.verify(verifier)) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
            return userRepo.findById(userId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
