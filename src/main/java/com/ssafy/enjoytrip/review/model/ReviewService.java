package com.ssafy.enjoytrip.review.model;

import java.util.List;
import java.util.Map;

public interface ReviewService {
	void writeReview(ReviewDto reviewDto) throws Exception;

	ReviewListDto listReview(Map<String, String> map) throws Exception;

	void updateHit(int viewId) throws Exception;

	ReviewViewDto getReviewView(int viewId) throws Exception;

	ReviewDto getReviewUpdate(int viewId) throws Exception;

	void updateReview(ReviewDto reviewDto) throws Exception;

	void deleteReview(int viewId) throws Exception;

	void updateLikeCount(Map<String,Object> res) throws Exception;

	ReviewMemberLikesDto selectLikeCount(int viewId) throws Exception;

	void insertLikeCount(int viewId) throws Exception;

	void deleteLikeCount(int viewId) throws Exception;

	
	//댓글
	void writeComment(CommentDto dto) throws Exception;

	void setCommentGroup(String commentId) throws Exception;

	List<CommentDto> commentList(int viewId) throws Exception;

	void writeReply(CommentDto dto) throws Exception;
}
