package com.ssafy.enjoytrip.follow.model;

import com.ssafy.enjoytrip.email.model.EmailVerifyDto;

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
public class FollowCountRequestDto {
	private String memberId;
}
