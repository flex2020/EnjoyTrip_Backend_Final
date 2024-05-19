package com.ssafy.enjoytrip.member.model;

import com.ssafy.enjoytrip.security.TokenInfo;

public interface MemberService {
    void signup(MemberDto memberDto) throws Exception;
    TokenInfo signin(SigninRequestDto signinRequestDto) throws Exception;
    void signout() throws Exception;
}
