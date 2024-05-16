package com.ssafy.enjoytrip.review.model;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
	void writeReview(ReviewDto reviewDto) throws SQLException;
}
