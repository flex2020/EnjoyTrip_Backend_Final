package com.ssafy.enjoytrip.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class MemberInfoResponseDto {
	private String email;
	private String memberName;
	private String nickname;
	private String mbti;
	private String gender;
	private String intro;
	private String valuedCount;
	private String score;
	private String phoneNumber;
}
