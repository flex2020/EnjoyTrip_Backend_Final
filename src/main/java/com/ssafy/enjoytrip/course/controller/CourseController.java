package com.ssafy.enjoytrip.course.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.course.model.CourseService;
import com.ssafy.enjoytrip.course.model.TravelDestinationDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
public class CourseController {
	private final CourseService courseService;
	
	@PutMapping("")
	public ResponseEntity<?> updateCourseDest(@RequestBody List<TravelDestinationDto> list) {
		System.out.println(list);
		courseService.updateCourseDest(list);
		return ResponseEntity.ok("수정 완료");
	}
}
