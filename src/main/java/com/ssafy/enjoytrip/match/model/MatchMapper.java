package com.ssafy.enjoytrip.match.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

@Mapper
public interface MatchMapper {
	List<AttractionDto> match(String matchId);
	MatchDto matchDetail(String matchId);
	List<MatchDto> getMatches();
	List<MatchDto> getMatchesByMember(String memberId);
	void removeMatchOfMember(Map<String, Object> map);
}
