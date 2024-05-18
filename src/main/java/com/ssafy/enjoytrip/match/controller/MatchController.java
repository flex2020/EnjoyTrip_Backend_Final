package com.ssafy.enjoytrip.match.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
		MatchDto matchInfo = matchService.matchDetail(matchId);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("courseItem", result);
		resultMap.put("matchInfo", matchInfo);
		return ResponseEntity.ok(resultMap);
	}
	
	@GetMapping("/matches")
	public ResponseEntity<List<MatchDto>> getMatches() throws Exception {
		return new ResponseEntity<List<MatchDto>>(matchService.getMatches(), HttpStatus.OK);
	}
	
	@GetMapping("/member-matches/{memberId}")
	public ResponseEntity<?> getMatchesByMember(@PathVariable("memberId") String memberId) {
		List<MatchDto> matchList = matchService.getMatchesByMember(memberId);
		return ResponseEntity.ok(matchList);
	}
	
	@DeleteMapping("/member-matches/{memberId}/{matchId}")
	public ResponseEntity<?> removeMatchOfMember(
			@PathVariable("memberId") String memberId
			, @PathVariable("matchId") String matchId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("memberId", memberId);
		paramMap.put("matchId", matchId);
		matchService.removeMatchOfMember(paramMap);
		return ResponseEntity.ok("나가기 완료");
	}
}
