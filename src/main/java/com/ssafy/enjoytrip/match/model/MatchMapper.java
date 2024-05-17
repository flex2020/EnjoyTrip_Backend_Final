package com.ssafy.enjoytrip.match.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

@Mapper
public interface MatchMapper {
	List<AttractionDto> match(String matchId);
}
