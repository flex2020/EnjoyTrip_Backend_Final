package com.ssafy.enjoytrip.course.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.course.model.CourseAddDto;
import com.ssafy.enjoytrip.course.model.CourseDto;
import com.ssafy.enjoytrip.course.model.CourseService;
import com.ssafy.enjoytrip.course.model.TravelDestinationDto;
import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
public class CourseController {
	private final CourseService courseService;
	
	@PutMapping("")
	public ResponseEntity<?> updateCourseDest(@RequestBody List<TravelDestinationDto> list) {
		System.out.println(list);
		courseService.updateCourseDest(list);
		return ResponseEntity.ok("수정 완료");
	}
	
	@GetMapping("/member-course/{memberId}")
	public ResponseEntity<?> getMemberCourseList(@PathVariable("memberId") String memberId) {
		List<CourseDto> courseList = courseService.getMemberCourseList(memberId);
		
		return ResponseEntity.ok(courseList);
	}
	
	@GetMapping("/{courseId}")
	public ResponseEntity<?> getCourse(@PathVariable("courseId") String courseId) {
		List<AttractionDto> result = courseService.getCourse(courseId);
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("")
	public ResponseEntity<?> addCourse(@RequestBody CourseAddDto dto) {
		System.out.println(dto);
		if (dto.getCourseId() == -1) {
			// 새로 저장
			courseService.addCourse(dto);
		}
		else {
			// 기존 정보 수정
			courseService.updateCourse(dto);
		}
		
		return ResponseEntity.ok("저장 완료");
	}
	
	@DeleteMapping("/{courseId}")
	public ResponseEntity<?> removeCourse(@PathVariable("courseId") String courseId) {
		courseService.removeCourseByCourseId(courseId);
		return ResponseEntity.ok("삭제 완료");
	}
}
