package com.ssafy.enjoytrip.member.model;

import java.sql.SQLException;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.enjoytrip.file.model.FileDto;
import com.ssafy.enjoytrip.file.model.FileService;
import com.ssafy.enjoytrip.security.JwtProvider;
import com.ssafy.enjoytrip.security.TokenInfo;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final FileService fileService;
    private final ServletContext servletContext;
    
	@Override
	public void signup(MemberDto memberDto) throws Exception {
		// TODO Auto-generated method stub
		memberDto.setPassword(hashPassword(memberDto.getPassword()));
		memberMapper.insertMember(memberDto);
	}
	
	@Override
    public TokenInfo signin(SigninRequestDto signinRequestDto) throws SQLException {
        // 1. ID와 암호화된 PW를 기반으로 Authentication 객체 생성
		// 이 때 authentication 은 인증 여부를 확인하는 authenticated 값이 false 로 설정되어있음.
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(signinRequestDto.getEmail(), signinRequestDto.getPassword());

        // 2. 실제 검증 과정 (사용자 비밀번호 확인)
        // authenticate 함수가 실행되면, CustomUserDetailsService 에서 구현한 loadUserByUsername 함수가 자동으로 실행 됨.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 다른데서 사용시
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); 이캐 갖다쓰면 된다.
        
        MemberDto member = memberMapper.findByEmail(signinRequestDto.getEmail());
        if (member == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "입력된 정보와 일치하는 사용자가 없습니다.");
        
        // 3. 인증 정보를 기반으로 JWT 생성 및 반환
        return jwtProvider.generateToken(authentication, member);
    }
	
	@Override
    public TokenInfo signin(MemberDto memberRequest) throws SQLException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberRequest.getEmail(), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        MemberDto member = memberMapper.findByEmail(memberRequest.getEmail());
        if (member == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "입력된 정보와 일치하는 사용자가 없습니다.");
        
        // 3. 인증 정보를 기반으로 JWT 생성 및 반환
        return jwtProvider.generateToken(authentication, member);
    }
	
	@Override
	public void signout() throws Exception {
		// TODO Auto-generated method stub
		SecurityContextHolder.clearContext();
		// 그 외 로그아웃 로직 처리(블랙리스트)
	}
	
	private String hashPassword(String password) {
		log.info("해싱 : " + passwordEncoder.encode(password));
		return passwordEncoder.encode(password);
	}

	@Override
	public boolean find(FindRequestDto findRequestDto) throws Exception {
		MemberDto member = memberMapper.findByEmailAndName(findRequestDto.getEmail(), findRequestDto.getName());
		if(member!=null) return true;
		return false;
	}

	@Override
	public void updatePassword(PasswordUpdateRequestDto passwordUpdateRequestDto) throws Exception {
		passwordUpdateRequestDto.setNewPassword(hashPassword(passwordUpdateRequestDto.getNewPassword()));
		memberMapper.updatePassword(passwordUpdateRequestDto);
	}

	@Override
	public MemberInfoResponseDto getMemberInfo(String memberId) throws Exception {
		MemberDto member = memberMapper.findById(memberId);
		log.info(member.toString());
		return MemberInfoResponseDto.builder()
				.email(member.getEmail())
				.memberName(member.getMemberName())
				.nickname(member.getNickname())
				.mbti(member.getMbti())
				.gender(member.getGender())
				.intro(member.getIntro())
				.valuedCount(member.getValuedCount())
				.score(member.getScore())
				.phoneNumber(member.getPhoneNumber())
				.build();
	}

//    @Override
//    public void updateMember(MemberDto memberDto) throws Exception {
//        memberMapper.updateMember(memberDto);
//    }
    
    @Override
    @Transactional
    public void updateMember(MemberDto memberDto, MultipartFile profileImage) throws Exception {
        memberMapper.updateMember(memberDto);

        if (profileImage != null) {
            FileDto fileDto = fileService.uploadFile(profileImage, servletContext);
            int memberId = Integer.parseInt(memberDto.getMemberId());
            Integer oldFileId = memberMapper.getMemberProfileImage(memberId);
            if (oldFileId != null) {
                memberMapper.deleteMemberProfileImage(memberId);
            }
            memberMapper.insertMemberProfileImage(memberId, Integer.parseInt(fileDto.getFileId()));
        }
    }
    
    @Override
    public void withdrawMember(int memberId) throws Exception {
        memberMapper.updateDeleteMember(memberId);
    }
    
    
    @Override
    public String getMemberProfileImage(int memberId) throws Exception {
        return memberMapper.findProfileImagePathByMemberId(memberId);
    }
    
    @Override
    public MemberDto findByEmail(String email) throws Exception {
        return memberMapper.findByEmail(email);
    }
}
