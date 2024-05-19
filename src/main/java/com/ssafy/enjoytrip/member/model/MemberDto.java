package com.ssafy.enjoytrip.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class MemberDto {
	private String memberId;
	private String email;
	private String memberName;
	private String birthday;
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
