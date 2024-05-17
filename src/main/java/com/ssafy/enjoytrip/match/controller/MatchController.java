package com.ssafy.enjoytrip.match.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.match.model.MatchDto;
import com.ssafy.enjoytrip.match.model.MatchService;
import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;
import com.ssafy.enjoytrip.trip.model.service.TripService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/match")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
public class MatchController {
	private final MatchService matchService;
	
	@GetMapping("/{matchId}")
	public ResponseEntity<?> getMatchCourse(@PathVariable("matchId") String matchId) {
		List<AttractionDto> result = matchService.match(matchId);
		return ResponseEntity.ok(result);
	}
}
