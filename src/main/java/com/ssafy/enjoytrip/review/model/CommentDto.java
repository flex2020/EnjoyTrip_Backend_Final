package com.ssafy.enjoytrip.review.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentDto {
	private String commentId;
	private String reviewId;
	private String memberId;
	private String content;
	private String commentGroup;
	private String depth;
	private String registerTime;
	private int deleted;
	private String memberName;
}
