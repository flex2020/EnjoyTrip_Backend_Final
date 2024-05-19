package com.ssafy.enjoytrip.email.model;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.enjoytrip.course.model.CourseMapper;
import com.ssafy.enjoytrip.util.RandomKeyGenerator;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final EmailMapper emailMapper;
    
    @Value("${admin.id}")
    private String senderEmail;
    @Value("${admin.sender}")
    private String sender;

    @Override
    public void sendSignupAuthEmail(String email) throws Exception {
        String authKey = RandomKeyGenerator.createKey(6); // 6자리 랜덤 인증 키 생성
        String time = "5";

        emailMapper.insertEmailVerification(email, authKey, time); // 데이터베이스에 이메일과 인증 키 저장

        MimeMessage message = createEmailAuthMessage(email, authKey);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            log.error("메일 전송에 실패했습니다.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송에 실패했습니다.");
        }
    }

    @Override
    public void sendPasswordResetEmail(String email) throws Exception {
        String verifyCode = RandomKeyGenerator.createKey(6); // 6자리 랜덤 인증 코드 생성
        String time = "60";

        emailMapper.insertEmailVerification(email, verifyCode, time); // 데이터베이스에 이메일과 인증 키 저장

        MimeMessage message = createPasswordResetMessage(email, verifyCode);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            log.error("메일 전송에 실패했습니다.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송에 실패했습니다.");
        }
    }

    private MimeMessage createEmailAuthMessage(String to, String authKey) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(to);
            helper.setSubject("[EnjoyTrip] 이메일 인증 코드");
            String msg = "<div style='font-family: Arial, sans-serif; font-size: 16px; color: #333;'>";
            msg += "<p>아래 인증 코드를 회원가입 창에 입력해주세요.<p>";
            msg += "<p>감사합니다.<p>";
            msg += "<br>";
            msg += "<div style='text-align: center; padding: 20px; background-color: #f8f8f8; border: 1px solid #ddd; border-radius: 8px; display: inline-block;'>";
            msg += "<div style='font-size: 24px; font-weight: bold; color: #555;'>";
            msg += "CODE: <span style='color: #ff5722;'>" + authKey + "</span>";
            msg += "</div></div><br/>";
            msg += "<p style='color: #999;'>This code will expire in 5 minutes.</p>";
            msg += "</div>";
            helper.setText(msg, true);
            helper.setFrom(new InternetAddress(senderEmail, sender));
        } catch (Exception e) {
            log.error("메일의 내용을 설정하는 도중 오류가 발생했습니다.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "메일의 내용을 설정하는 도중 오류가 발생했습니다.");
        }
        return message;
    }

    private MimeMessage createPasswordResetMessage(String to, String verifyCode) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(to);
            helper.setSubject("[EnjoyTrip] 비밀번호 재설정 인증 코드");
            String msg = "<div style='font-family: Arial, sans-serif; font-size: 16px; color: #333;'>";
            msg += "<p>아래 인증 코드를 비밀번호 재설정 창에 입력해주세요.<p>";
            msg += "<p>감사합니다.<p>";
            msg += "<br>";
            msg += "<div style='text-align: center; padding: 20px; background-color: #f8f8f8; border: 1px solid #ddd; border-radius: 8px; display: inline-block;'>";
            msg += "<div style='font-size: 24px; font-weight: bold; color: #555;'>";
            msg += "CODE: <span style='color: #ff5722;'>" + verifyCode + "</span>";
            msg += "</div></div><br/>";
            msg += "<p style='color: #999;'>This code will expire in 1 hour.</p>";
            msg += "</div>";
            helper.setText(msg, true);
            helper.setFrom(new InternetAddress(senderEmail, sender));
        } catch (Exception e) {
            log.error("메일의 내용을 설정하는 도중 오류가 발생했습니다.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "메일의 내용을 설정하는 도중 오류가 발생했습니다.");
        }
        return message;
    }

    @Override
    @Transactional
    public boolean verifyEmailCode(EmailVerifyDto emailVerifyDto) {
        // 먼저 만료된 코드를 삭제
        emailMapper.deleteExpiredCodes();

        // 이메일과 코드가 일치하는지 확인
        String storedCode = emailMapper.getEmailVerificationCode(emailVerifyDto.getEmail());
        if (storedCode != null && storedCode.equals(emailVerifyDto.getCode())) {
            return true;
        }
        return false;
    }
}
