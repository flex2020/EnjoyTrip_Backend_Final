package com.ssafy.enjoytrip.email.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.enjoytrip.course.model.CourseService;
import com.ssafy.enjoytrip.email.model.EmailService;
import com.ssafy.enjoytrip.email.model.EmailVerifyDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@Slf4j
public class EmailController {

	private final EmailService emailService;

    @GetMapping("/signup/auth/send")
    public ResponseEntity<String> sendSingupEmailAuth(@RequestParam String email) {
        try {
            emailService.sendSignupAuthEmail(email);
            return ResponseEntity.ok("이메일 인증 메일이 전송되었습니다.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송에 실패했습니다.");
        }
    }
    
    @PostMapping("/signup/verify")
    public ResponseEntity<String> verifySignupEmailCode(@RequestBody EmailVerifyDto emailVerifyDto) throws Exception {
        boolean isVerified = emailService.verifyEmailCode(emailVerifyDto);
        if (isVerified) {
            return ResponseEntity.ok("이메일 인증에 성공했습니다.");
        } else {
            return ResponseEntity.status(400).body("유효하지 않거나 만료된 인증 코드입니다.");
        }
    }
    
    @GetMapping("/findpassword/auth/send")
    public ResponseEntity<String> sendFindpasswordEmailAuth(@RequestParam String email) {
        try {
        	emailService.sendPasswordResetEmail(email);
            return ResponseEntity.ok("비밀번호 재설정 메일이 전송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("이메일 전송에 실패했습니다.");
        }
    }
    
    @PostMapping("/findpassword/verify")
    public ResponseEntity<Boolean> verifyFindpasswordEmailCode(@RequestBody EmailVerifyDto emailVerifyDto) throws Exception {
    	log.info(emailVerifyDto.toString());
        boolean isVerified = emailService.verifyEmailCode(emailVerifyDto);
        if (isVerified) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}