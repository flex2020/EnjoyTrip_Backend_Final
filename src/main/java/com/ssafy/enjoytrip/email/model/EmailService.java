package com.ssafy.enjoytrip.email.model;

import org.springframework.transaction.annotation.Transactional;

public interface EmailService {
    @Transactional
    void sendSignupAuthEmail(String to) throws Exception;

    @Transactional
    void sendPasswordResetEmail(String to) throws Exception;
    
    boolean verifyEmailCode(EmailVerifyDto emailVerifyDto) throws Exception;
}
