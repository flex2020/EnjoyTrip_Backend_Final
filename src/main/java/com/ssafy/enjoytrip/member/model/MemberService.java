package com.ssafy.enjoytrip.member.model;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.file.model.FileDto;
import com.ssafy.enjoytrip.security.TokenInfo;

public interface MemberService {
    void signup(MemberDto memberDto) throws Exception;
    TokenInfo signin(SigninRequestDto signinRequestDto) throws Exception;
    void signout() throws Exception;
    boolean find(FindRequestDto findRequestDto) throws Exception;
    void updatePassword(PasswordUpdateRequestDto passwordUpdateRequestDto) throws Exception;
    MemberInfoResponseDto getMemberInfo(String email) throws Exception;
//    void updateMember(MemberDto memberDto) throws Exception;
    void updateMember(MemberDto memberDto, MultipartFile profileImage) throws Exception;
    void withdrawMember(int memberId) throws Exception;
    String getMemberProfileImage(int memberId) throws Exception;
}
