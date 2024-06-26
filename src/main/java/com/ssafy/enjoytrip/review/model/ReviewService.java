package com.ssafy.enjoytrip.review.model;

import java.util.List;
import java.util.Map;

public interface ReviewService {
	void writeReview(ReviewAddDto reviewDto) throws Exception;

	ReviewListDto listReview(Map<String, String> map) throws Exception;

	void updateHit(int viewId) throws Exception;

	ReviewViewDto getReviewView(int viewId) throws Exception;

	ReviewDto getReviewUpdate(int viewId) throws Exception;

	void updateReview(ReviewAddDto reviewDto) throws Exception;

	void deleteReview(int viewId) throws Exception;

	void updateLikeCount(Map<String,Object> res) throws Exception;

	ReviewMemberLikesDto selectLikeCount(ReviewMemberLikesDto dto) throws Exception;

	void insertLikeCount(ReviewMemberLikesDto dto) throws Exception;

	void deleteLikeCount(ReviewMemberLikesDto dto) throws Exception;

	Integer getRelation(Map<String, String> map) throws Exception;

	List<ReviewDto> getRecentlyReview(Map<String, String> map) throws Exception;

	List<ReviewDto> getFollowReview(Map<String, String> map) throws Exception;
	
	List<ReviewDto> getLikedReviews(Map<String, String> map) throws Exception;
	
	//댓글
	void writeComment(CommentDto dto) throws Exception;

	void setCommentGroup(String commentId) throws Exception;

	List<CommentDto> commentList(int viewId) throws Exception;

	void writeReply(CommentDto dto) throws Exception;

	String getReplyParentName(String commentId) throws Exception;

	void updateComment(CommentDto dto) throws Exception;

	void deleteComment(int commentId) throws Exception;
	
	// 게시글 개수 가져오기
	int getReviewCount(int memberId) throws Exception;
}
