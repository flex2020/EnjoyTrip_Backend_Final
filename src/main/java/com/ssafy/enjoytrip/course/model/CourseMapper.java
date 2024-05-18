package com.ssafy.enjoytrip.course.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {
	void removeCourseDestByCourseId(String courseId);
	void addCourseDestinations(List<TravelDestinationDto> list);
}
