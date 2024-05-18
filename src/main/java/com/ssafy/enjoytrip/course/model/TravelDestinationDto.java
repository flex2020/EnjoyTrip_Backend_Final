package com.ssafy.enjoytrip.course.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TravelDestinationDto {
	private String travelDestinationId;
	private String courseId;
	private String attractionId;
	private String order;
}
