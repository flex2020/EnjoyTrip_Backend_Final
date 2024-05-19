package com.ssafy.enjoytrip.security;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.model.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberDto member = null;
		try {
			member = memberMapper.findByEmail(email);
		} catch (SQLException e) {
			log.error("SQLException has been occurred. email = " + email);
			e.printStackTrace();
		}
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "입력된 정보와 일치하는 사용자가 없습니다.");
        }
        return new CustomUserDetails(member);
    }
}
