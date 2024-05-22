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
	List<MatchListItemDto> getFindMatches(Map<String, Object> param);
	int getTotalMatchCount(Map<String, Object> param);
	void writeMatch(MatchDto matchDto);
	void writeHashtag(HashtagDto hashtagDto);
	HashtagDto isDuplicateHashtag(HashtagDto hashtagDto);
	void mappingHashtag(Map<String, Object> map);
	void mappingFile(MatchDto matchDto);
	MatchDto getFindMatch(int matchId);
	String countMembersByMatchId(int matchId);
	List<String> getHashtags(int matchId);
	void postMatchesByMember(Map<String, Object> map);
	List<MatchDto> getMatchesNotFinished(); 
	void updateMatchHit(int matchId);
	void updateMatch(MatchDto matchDto);
	void deleteMappingHashtag(String matchId);
	void deleteMatchImage(int fileId);
	void deleteMatch(int matchId);
	List<MatchDto> getFindMatchByMemberId(Map<String, Object> map);
	List<MateDto> getMatesByMatch(int matchId);
}
