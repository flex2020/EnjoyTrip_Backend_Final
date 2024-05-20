package com.ssafy.enjoytrip.email.model;

import com.ssafy.enjoytrip.member.model.MemberDto;

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
public class EmailVerifyDto {
	private String email;
	private String code;
}
