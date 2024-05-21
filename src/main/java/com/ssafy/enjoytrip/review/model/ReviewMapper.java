package com.ssafy.enjoytrip.review.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.match.model.MatchDto;

@Mapper
public interface ReviewMapper {
	void writeReview(ReviewDto reviewDto) throws SQLException;
	
	void mappingFiles(List<ReviewFileDto> dto) throws SQLException;
	
	List<ReviewDto> listReview(Map<String, Object> param) throws SQLException;

	int getTotalReviewCount(Map<String, Object> param) throws SQLException;

	void updateHit(int viewId) throws SQLException;

	ReviewViewDto getReviewView(int viewId) throws SQLException;

	ReviewDto getReviewUpdate(int viewId) throws SQLException;

	void updateReview(ReviewDto reviewDto) throws SQLException;

	void deleteReview(int viewId) throws SQLException;

	void updateLikeCount(Map<String,Object> res) throws SQLException;

	ReviewMemberLikesDto selectLikeCount(ReviewMemberLikesDto dto) throws SQLException;

	void insertLikeCount(ReviewMemberLikesDto dto) throws SQLException;

	void deleteLikeCount(ReviewMemberLikesDto dto) throws SQLException;

	Integer getRelation(Map<String, String> map) throws SQLException;

	List<ReviewDto> getRecentlyReview(Map<String, String> map) throws SQLException;

	List<ReviewDto> getFollowReview(Map<String, String> map) throws SQLException;

	void writeComment(CommentDto dto) throws SQLException;

	void setCommentGroup(String commentId) throws SQLException;

	List<CommentDto> commentList(int viewId) throws SQLException;

	void writeReply(CommentDto dto) throws SQLException;

	String getReplyParentName(String commentId) throws SQLException;

	void updateComment(CommentDto dto) throws SQLException;

	void deleteComment(int commentId) throws SQLException;

}
