package com.ssafy.enjoytrip.course.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CourseAddDto {
	private int courseId;
	private String courseName;
	private int memberId;
	private List<TravelDestinationDto> plan;
}
