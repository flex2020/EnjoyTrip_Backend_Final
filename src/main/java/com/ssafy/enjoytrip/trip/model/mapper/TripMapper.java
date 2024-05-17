package com.ssafy.enjoytrip.trip.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;
import com.ssafy.enjoytrip.trip.model.dto.GugunDto;
import com.ssafy.enjoytrip.trip.model.dto.SidoDto;

@Mapper
public interface TripMapper {
	List<SidoDto> sidoList();
	List<GugunDto> gugunList(String sidoCode);
	List<AttractionDto> searchAttractions(Map<String, Object> paramMap);
	int totalPages(Map<String, Object> paramMap);
}
