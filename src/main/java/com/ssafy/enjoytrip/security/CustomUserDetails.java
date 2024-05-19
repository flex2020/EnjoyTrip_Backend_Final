package com.ssafy.enjoytrip.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.enjoytrip.member.model.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {
    private final MemberDto member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 권한 설정, 필요시 커스텀 권한 설정 가능
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 추가적인 사용자 정보 접근자 메서드
    public String getMemberName() {
        return member.getMemberName();
    }

    public String getBirthday() {
        return member.getBirthday();
    }

    public String getNickname() {
        return member.getNickname();
    }

    public String getMbti() {
        return member.getMbti();
    }

    public String getGender() {
        return member.getGender();
    }

    public String getIntro() {
        return member.getIntro();
    }

    public String getPhoneNumber() {
        return member.getPhoneNumber();
    }

    public String getValuedCount() {
        return member.getValuedCount();
    }

    public String getScore() {
        return member.getScore();
    }

    public String getEmailVerifyCode() {
        return member.getEmailVerifyCode();
    }

    public int getDeleted() {
        return member.getDeleted();
    }
}
