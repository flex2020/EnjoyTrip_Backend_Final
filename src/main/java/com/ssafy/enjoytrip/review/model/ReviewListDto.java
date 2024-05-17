package com.ssafy.enjoytrip.review.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewListDto {
	private List<ReviewDto> reviews;
	private int currentPage;
	private int totalPageCount;
}
