package com.ssafy.enjoytrip.course.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDto {
	private String courseId;
	private String memberId;
	private String courseName;
}
