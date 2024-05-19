package com.ssafy.enjoytrip.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.match.model.MatchService;
import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.model.MemberService;
import com.ssafy.enjoytrip.member.model.SigninRequestDto;
import com.ssafy.enjoytrip.security.TokenInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	private final MemberService memberService;
	
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberDto memberDto) throws Exception {
    	log.info(memberDto.toString());
        memberService.signup(memberDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody SigninRequestDto signinRequestDto) throws Exception {
    	log.info(signinRequestDto.toString());
        TokenInfo token = memberService.signin(signinRequestDto);
        return ResponseEntity.ok(token.getAccessToken());
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signout() throws Exception {
        memberService.signout();
        return ResponseEntity.ok("User logout successfully");
    }
}
