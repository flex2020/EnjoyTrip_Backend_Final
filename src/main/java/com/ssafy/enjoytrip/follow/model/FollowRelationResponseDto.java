package com.ssafy.enjoytrip.follow.model;

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
public class FollowRelationResponseDto {
	// 본인 : 0, 팔로우 : 1, 팔로워 : 2, 맞팔 : 3
	private int relation;
}
