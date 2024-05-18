package com.ssafy.enjoytrip.match.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
	private final MatchMapper matchMapper;
	@Override
	public List<AttractionDto> match(String matchId) {
		return matchMapper.match(matchId);
	}
	@Override
	public MatchDto matchDetail(String matchId) {
		return matchMapper.matchDetail(matchId);
	}
	@Override
	public List<MatchDto> getMatches() {
		return matchMapper.getMatches();
	}
	@Override
	public List<MatchDto> getMatchesByMember(String memberId) {
		return matchMapper.getMatchesByMember(memberId);
	}
	@Override
	public void removeMatchOfMember(Map<String, Object> map) {
		matchMapper.removeMatchOfMember(map);
	}
	
}
