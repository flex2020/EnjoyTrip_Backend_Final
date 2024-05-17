package com.ssafy.enjoytrip.match.model;

import java.util.List;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

public interface MatchService {
	List<AttractionDto> match(String matchId);
}
