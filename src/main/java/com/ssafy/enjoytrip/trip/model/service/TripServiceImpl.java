package com.ssafy.enjoytrip.trip.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;
import com.ssafy.enjoytrip.trip.model.dto.GugunDto;
import com.ssafy.enjoytrip.trip.model.dto.SidoDto;
import com.ssafy.enjoytrip.trip.model.mapper.TripMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
	private final TripMapper tripMapper;
	private static final int PAGE_SIZE = 5;
	@Override
	public List<SidoDto> sidoList() {
		return tripMapper.sidoList();
	}

	@Override
	public List<GugunDto> gugunList(String sidoCode) {
		return tripMapper.gugunList(sidoCode);
	}

	@Override
	public List<AttractionDto> searchAttractions(Map<String, Object> paramMap) {
		int page = (int) paramMap.get("page");
		paramMap.put("offset", (page - 1) * PAGE_SIZE);
		paramMap.put("pageSize", PAGE_SIZE);
		return tripMapper.searchAttractions(paramMap);
	}

	@Override
	public int totalPages(Map<String, Object> paramMap) {
		return tripMapper.totalPages(paramMap);
	}

}
