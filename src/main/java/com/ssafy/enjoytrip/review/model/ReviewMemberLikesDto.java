package com.ssafy.enjoytrip.review.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewMemberLikesDto {
	private String reviewMemberLikeId;
	private String reviewId;
	private String memberId;
}
