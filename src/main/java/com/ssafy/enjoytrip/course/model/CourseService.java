package com.ssafy.enjoytrip.course.model;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

public interface CourseService {
	void updateCourseDest(List<TravelDestinationDto> list);
	List<CourseDto> getMemberCourseList(String memberId);
	List<AttractionDto> getCourse(String courseId);
	void addCourse(CourseAddDto dto);
	void updateCourse(CourseAddDto dto);
	void removeCourseByCourseId(String courseId);
}
