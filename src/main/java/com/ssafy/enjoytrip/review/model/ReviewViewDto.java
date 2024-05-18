package com.ssafy.enjoytrip.review.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewViewDto {
	private String reviewId;
	private String matchId;
	private String memberId;
	private String reviewTitle;
	private String scope;
	private String content;
	private String hit;
	private String registerTime;
	private int deleted;
	private String likeCount;
	private String memberName;
	private String travelStartDate;
	private String travelEndDate;
}
