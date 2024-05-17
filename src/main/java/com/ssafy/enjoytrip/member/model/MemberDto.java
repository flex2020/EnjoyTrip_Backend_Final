package com.ssafy.enjoytrip.member.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {
	private String memberId;
	private String email;
	private String memberName;
	private String birthDay;
	private String password;
	private String nickname;
	private String mbti;
	private String gender;
	private String intro;
	private String phoneNumber;
	private String valuedCount;
	private String score;
	private String emailVerifyCode;
	private int deleted;
}
