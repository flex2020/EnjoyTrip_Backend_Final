package com.ssafy.enjoytrip.course.model;

import java.util.List;

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
		if (list == null || list.isEmpty()) return;
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
}
