package com.ssafy.enjoytrip.match.model;

import java.sql.SQLException;
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
	List<MatchDto> getFindMatches(Map<String, Object> param);
	int getTotalMatchCount(Map<String, Object> param);
}
