package com.ssafy.enjoytrip.review.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewAddDto {
	private ReviewDto review;
	private List<Integer> fileIds;
}
