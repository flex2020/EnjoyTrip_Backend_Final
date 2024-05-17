package com.ssafy.enjoytrip.trip.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.trip.model.dto.GugunDto;
import com.ssafy.enjoytrip.trip.model.dto.SidoDto;
import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

public interface TripService {
	List<SidoDto> sidoList();
	List<GugunDto> gugunList(String sidoCode);
	List<AttractionDto> searchAttractions(Map<String, Object> paramMap);
	int totalPages(Map<String, Object> paramMap);
}
