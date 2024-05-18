package com.ssafy.enjoytrip.review.model;

import java.util.Map;

import com.ssafy.enjoytrip.match.model.MatchDto;

public interface ReviewService {
	void writeReview(ReviewDto reviewDto) throws Exception;

	ReviewListDto listReview(Map<String, String> map) throws Exception;

	void updateHit(int viewId) throws Exception;

	ReviewViewDto getReviewView(int viewId) throws Exception;

	ReviewDto getReviewUpdate(int viewId) throws Exception;

	void updateReview(ReviewDto reviewDto) throws Exception;

	void deleteReview(int viewId) throws Exception;
}
