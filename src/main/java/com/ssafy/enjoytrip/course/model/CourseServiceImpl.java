package com.ssafy.enjoytrip.course.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
	private final CourseMapper courseMapper;
	
	@Override
	@Transactional
	public void updateCourseDest(List<TravelDestinationDto> list) {
		courseMapper.removeCourseDestByCourseId(list.get(0).getCourseId());
		courseMapper.addCourseDestinations(list);
	}

	@Override
	public List<CourseDto> getMemberCourseList(String memberId) {
		return courseMapper.getMemberCourseList(memberId);
	}

	@Override
	public List<AttractionDto> getCourse(String courseId) {
		return courseMapper.getCourse(courseId);
	}

	@Override
	@Transactional
	public void addCourse(CourseAddDto dto) {
		CourseDto course = new CourseDto();
		course.setCourseName(dto.getCourseName());
		course.setMemberId(dto.getMemberId() + "");
		courseMapper.addCourse(course);
		List<TravelDestinationDto> plan = dto.getPlan();
		for (TravelDestinationDto item : plan) {
			item.setCourseId(course.getCourseId());
		}
		courseMapper.addCourseDestinations(plan);
	}

	@Override
	@Transactional
	public void updateCourse(CourseAddDto dto) {
		CourseDto course = new CourseDto();
		course.setCourseId(dto.getCourseId() + "");
		course.setCourseName(dto.getCourseName());
		
		courseMapper.updateCourse(course);
		courseMapper.removeCourseDestByCourseId(dto.getPlan().get(0).getCourseId());
		courseMapper.addCourseDestinations(dto.getPlan());
	}

	@Override
	@Transactional
	public void removeCourseByCourseId(String courseId) {
		courseMapper.removeCourseDestByCourseId(courseId);
		courseMapper.removeCourseByCourseId(courseId);
	}
	
	
}
