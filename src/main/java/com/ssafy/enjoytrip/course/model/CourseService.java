package com.ssafy.enjoytrip.course.model;

import java.util.List;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

public interface CourseService {
	void updateCourseDest(List<TravelDestinationDto> list);
	List<CourseDto> getMemberCourseList(String memberId);
	List<AttractionDto> getCourse(String courseId);
}
