package com.ssafy.enjoytrip.course.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

@Mapper
public interface CourseMapper {
	void removeCourseDestByCourseId(String courseId);
	void addCourseDestinations(List<TravelDestinationDto> list);
	List<CourseDto> getMemberCourseList(String memberId);
	List<AttractionDto> getCourse(String courseId);
	void addCourse(CourseDto dto);
	void updateCourse(CourseDto course);
	void removeCourseByCourseId(String courseId);
}
