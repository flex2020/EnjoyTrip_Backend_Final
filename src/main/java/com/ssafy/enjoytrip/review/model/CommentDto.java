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
public class CommentDto implements Comparable<CommentDto> {
	private String commentId;
	private String reviewId;
	private String memberId;
	private String content;
	private String commentGroup;
	private String depth;
	private String registerTime;
	private int deleted;
	private String memberName;
	private String replyParentName;
	
	@Override
	public int compareTo(CommentDto o) {
		if (Integer.parseInt(commentGroup) != Integer.parseInt(o.commentGroup)) return Integer.parseInt(commentGroup) - Integer.parseInt(o.commentGroup);
		if (Integer.parseInt(depth) != Integer.parseInt(o.depth)) return Integer.parseInt(depth) - Integer.parseInt(o.depth);
		return Integer.parseInt(commentId) - Integer.parseInt(o.commentId);
	}
}
