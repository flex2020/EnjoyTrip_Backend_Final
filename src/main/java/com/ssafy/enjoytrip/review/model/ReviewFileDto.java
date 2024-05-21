package com.ssafy.enjoytrip.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFileDto {
	private String reviewFilesId;
	private String reviewId;
	private String fileId;
}
