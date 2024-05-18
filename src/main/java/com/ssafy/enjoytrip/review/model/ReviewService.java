package com.ssafy.enjoytrip.review.model;

import java.util.Map;

public interface ReviewService {
	void writeReview(ReviewDto reviewDto) throws Exception;

	ReviewListDto listReview(Map<String, String> map) throws Exception;

	void updateHit(int viewId) throws Exception;

	ReviewViewDto getReview(int viewId) throws Exception;
}
