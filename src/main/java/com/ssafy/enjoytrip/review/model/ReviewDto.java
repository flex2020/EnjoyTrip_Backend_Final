package com.ssafy.enjoytrip.review.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewDto {
	private int reviewId;
	private int matchId;
	private int memberId;
	private String reviewTitle;
	private int scope;
	private String previewContent;
	private String content;
	private int hit;
	private String registerTime;
	private int deleted;
	private int likeCount;
	private String firstImage;
	
	private String loginMemberId;
}
