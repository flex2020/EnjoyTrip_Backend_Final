package com.ssafy.enjoytrip.review.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
	void writeReview(ReviewDto reviewDto) throws SQLException;

	List<ReviewDto> listReview(Map<String, Object> param) throws SQLException;

	int getTotalReviewCount(Map<String, Object> param) throws SQLException;

	void updateHit(int viewId) throws SQLException;

	ReviewViewDto getReviewView(int viewId) throws SQLException;

	ReviewDto getReviewUpdate(int viewId) throws SQLException;

	void updateReview(ReviewDto reviewDto) throws SQLException;

	void deleteReview(int viewId) throws SQLException;
}
