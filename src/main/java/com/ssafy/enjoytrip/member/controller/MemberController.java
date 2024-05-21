package com.ssafy.enjoytrip.member.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.file.model.FileDto;
import com.ssafy.enjoytrip.file.model.FileService;
import com.ssafy.enjoytrip.match.model.MatchService;
import com.ssafy.enjoytrip.member.model.FindRequestDto;
import com.ssafy.enjoytrip.member.model.KakaoMemberInfo;
import com.ssafy.enjoytrip.member.model.MemberDeleteRequestDto;
import com.ssafy.enjoytrip.member.model.MemberDeleteRequestDto;
import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.model.MemberInfoResponseDto;
import com.ssafy.enjoytrip.member.model.MemberService;
import com.ssafy.enjoytrip.member.model.MemberUpdateRequestDto;
import com.ssafy.enjoytrip.member.model.OAuth2Response;
import com.ssafy.enjoytrip.member.model.PasswordUpdateRequestDto;
import com.ssafy.enjoytrip.member.model.SigninRequestDto;
import com.ssafy.enjoytrip.security.JwtProvider;
import com.ssafy.enjoytrip.security.TokenInfo;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	private final MemberService memberService;
	private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
	
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
    
    @PostMapping("/findpassword")
    public ResponseEntity<String> find(@RequestBody FindRequestDto findRequestDto) {
    	try {
    		if(memberService.find(findRequestDto)) {
    			return ResponseEntity.ok("User find successfully");
    		}
    		return ResponseEntity.ok("");
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error occurred during user find");
    	} 
    }
    
    @PostMapping("/updatepassword")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto) {
        try {
            memberService.updatePassword(passwordUpdateRequestDto);
            return ResponseEntity.ok("비밀번호가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 업데이트에 실패했습니다. 다시 시도해주세요.");
        }
    }
    
    @PostMapping("/info")
    public ResponseEntity<MemberInfoResponseDto> updatePassword(@RequestBody Map<String, String> map) {
        try {
            MemberInfoResponseDto member = memberService.getMemberInfo(map.get("memberId"));
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
//    @PutMapping("/update")
//    public String updateMember(@RequestBody MemberDto memberDto) {
//        try {
//            memberService.updateMember(memberDto);
//            return "회원 정보가 성공적으로 수정되었습니다.";
//        } catch (Exception e) {
//            return "회원 정보 수정에 실패했습니다: " + e.getMessage();
//        }
//    }
    
    @PutMapping("/update")
    public ResponseEntity<String> updateMember(@RequestPart("data") String memberDtoString,
                                               @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) throws Exception {
        MemberDto memberDto = objectMapper.readValue(memberDtoString, MemberDto.class);
        log.info(memberDto.toString());
        try {
            memberService.updateMember(memberDto, profileImage);
            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("회원 정보 수정에 실패했습니다. 다시 시도해주세요.");
        }
    }
    
    // 프로필 가져오기
    @PostMapping("/profile")
    public ResponseEntity<String> getMemberProfileImage(@RequestBody Map<String, String> map) {
        try {
            String profileImagePath = memberService.getMemberProfileImage(Integer.parseInt(map.get("memberId")));
            return ResponseEntity.ok(profileImagePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("프로필 이미지 조회에 실패했습니다.");
        }
    }
    
    // 회원탈퇴
    @PostMapping("/withdraw")
    public void withdrawMember(@RequestBody MemberDeleteRequestDto memberDeleteRequestDto) throws Exception {
        memberService.withdrawMember(memberDeleteRequestDto.getMemberId());
    }
    
    @PostMapping("/oauth2/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestBody KakaoMemberInfo userInfo) throws Exception {
        MemberDto member = memberService.findByEmail(userInfo.getEmail());
        if (member == null) {
            // 새로운 사용자
            return ResponseEntity.ok(new OAuth2Response(true, userInfo.getEmail(), userInfo.getNickname()));
        } else {
            // 기존 사용자
            TokenInfo token = memberService.signin(member);
            return ResponseEntity.ok(token.getAccessToken());
        }
    }
    
    
}
