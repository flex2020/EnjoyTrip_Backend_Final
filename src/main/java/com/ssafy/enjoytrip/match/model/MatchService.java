package com.ssafy.enjoytrip.match.model;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

public interface MatchService {
	List<AttractionDto> match(String matchId);
	MatchDto matchDetail(String matchId);
	List<MatchDto> getMatches();
	List<MatchDto> getMatchesByMember(String memberId);
	void removeMatchOfMember(Map<String, Object> map);
	MatchListDto getFindMatches(Map<String, Object> map);
	void writeMatch(MatchDto matchDto);
	void writeHashtag(HashtagDto hashtagDto);
	HashtagDto isDuplicateHashtag(HashtagDto hashtagDto);
	void mappingHashtag(Map<String, Object> map);
	void mappingFile(MatchDto matchDto);
	MatchDto getFindMatch(int matchId);
	String countMembersByMatchId(int matchId);
	List<String> getHashtags(int matchId);
	void postMatchesByMember(Map<String, Object> map);
}
