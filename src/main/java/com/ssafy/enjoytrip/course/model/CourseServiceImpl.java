package com.ssafy.enjoytrip.course.model;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
