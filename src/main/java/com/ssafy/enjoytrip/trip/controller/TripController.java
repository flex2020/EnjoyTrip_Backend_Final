package com.ssafy.enjoytrip.trip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.trip.model.dto.GugunDto;
import com.ssafy.enjoytrip.trip.model.dto.SidoDto;
import com.ssafy.enjoytrip.trip.model.service.TripService;
import com.ssafy.enjoytrip.util.PaginationUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trip")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
public class TripController {
	private final TripService tripService;
	
	@GetMapping("/sido")
	public ResponseEntity<?> getSidoList() throws Exception {
		return ResponseEntity.ok(tripService.sidoList());
	}

	@GetMapping("/gugun")
	public ResponseEntity<?> getGugunList(@RequestParam("sido") String sidoCode) throws Exception {
		return ResponseEntity.ok(tripService.gugunList(sidoCode));
	}

	@GetMapping("/attractions")
	public ResponseEntity<?> searchAttractions(
			@RequestParam(name = "sido", required = false, defaultValue="") String sidoCode
			, @RequestParam(name = "gugun", required = false, defaultValue="") String gugunCode
			, @RequestParam(name = "keyword", required = false, defaultValue="") String keyword
			, @RequestParam(name = "page",  required = false, defaultValue = "1") int page)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("sidoCode", sidoCode);
		paramMap.put("gugunCode", gugunCode);
		paramMap.put("keyword", keyword);
		paramMap.put("page", page);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("data", tripService.searchAttractions(paramMap));
		int totalPages = tripService.totalPages(paramMap);
		resultMap.put("page", page);
		resultMap.put("totalPages", totalPages);
		resultMap.put("pagination", PaginationUtil.getPages(page, totalPages));
		return ResponseEntity.ok(resultMap);
	}

}
