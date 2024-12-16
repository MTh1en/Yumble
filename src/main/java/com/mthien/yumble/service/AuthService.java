package com.mthien.yumble.service;

import com.mthien.yumble.entity.Enum.UserStatus;
import com.mthien.yumble.entity.Users;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.UserMapper;
import com.mthien.yumble.payload.request.auth.LoginRequest;
import com.mthien.yumble.payload.request.auth.RegisterRequest;
import com.mthien.yumble.payload.response.auth.AuthResponse;
import com.mthien.yumble.repository.UserRepo;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthService {
    private final JavaMailSenderImpl mailSender;
    @Value("${jwt.signerKey}")
    protected String signerKey;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public AuthService(UserMapper userMapper, UserRepo userRepo, JavaMailSenderImpl mailSender) {
        this.userMapper = userMapper;
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

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

    public void sendVerificationEmail(String email) {
        try {
            Users users = userRepo.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
            String token = generateToken(users);
            String url = "http://localhost:8080/auth/verify?token=" + token;
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

    private String generateToken(Users users) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getId().toString())
                .issuer("MThien")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Không thể tạo token", e);
            throw new RuntimeException(e);
        }
    }

    private Users validateToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            MACVerifier verifier = new MACVerifier(signerKey.getBytes());
            if (!jwsObject.verify(verifier)) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
            JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            String userId = claimsSet.getSubject();
            return userRepo.findById(Integer.parseInt(userId)).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
