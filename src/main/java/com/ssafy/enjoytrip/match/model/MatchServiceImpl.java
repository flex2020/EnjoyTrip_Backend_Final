package com.ssafy.enjoytrip.match.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.review.model.ReviewDto;
import com.ssafy.enjoytrip.review.model.ReviewListDto;
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
	@Override
	public MatchListDto getFindMatches(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("keyword", map.get("keyword") == null ? "" : map.get("keyword"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : (String) map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "9" : (String) map.get("spp"));
		int sortKey = Integer.parseInt(map.get("sortKey") == null ? "0" : (String) map.get("sortKey"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		param.put("sortKey", sortKey);
		
		List<MatchListItemDto> list = matchMapper.getFindMatches(param);
		List<Map<String, Object>> result = new ArrayList<>();
		for (int i=0; i<list.size(); i++) {
			Map<String, Object> temp = new HashMap<>();
			if (list.get(i).getHashtagNames() != null) temp.put("hashtags", Arrays.asList(list.get(i).getHashtagNames().split(",")));
			temp.put("matchId", list.get(i).getMatchId());
			temp.put("matchTitle", list.get(i).getMatchTitle());
			temp.put("filePath", list.get(i).getFilePath());
			temp.put("hit", list.get(i).getHit());
			result.add(temp);
		}
		
		int totalArticleCount = matchMapper.getTotalMatchCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		MatchListDto matchListDto = new MatchListDto();
		matchListDto.setMatches(result);
		matchListDto.setCurrentPage(currentPage);
		matchListDto.setTotalPageCount(totalPageCount);

//		System.out.println(start + " " + sizePerPage);
//		System.out.println(list.toString());
		
		return matchListDto;
	}
	
	@Override
	public void writeMatch(MatchDto matchDto) {
		matchMapper.writeMatch(matchDto);
	}
	@Override
	public void writeHashtag(HashtagDto hashtagDto) {
		matchMapper.writeHashtag(hashtagDto);
	}
	@Override
	public HashtagDto isDuplicateHashtag(HashtagDto hashtagDto) {
		// TODO Auto-generated method stub
		return matchMapper.isDuplicateHashtag(hashtagDto);
	}
	@Override
	public void mappingHashtag(Map<String, Object> map) {
		// TODO Auto-generated method stub
		matchMapper.mappingHashtag(map);
	}
	@Override
	public void deleteMappingHashtag(String matchId) {
		// TODO Auto-generated method stub
		matchMapper.deleteMappingHashtag(matchId);
	}
	@Override
	public void mappingFile(MatchDto matchDto) {
		// TODO Auto-generated method stub
		matchMapper.mappingFile(matchDto);
	}
	@Override
	public MatchDto getFindMatch(int matchId) {
		// TODO Auto-generated method stub
		return matchMapper.getFindMatch(matchId);
	}
	@Override
	public String countMembersByMatchId(int matchId) {
		// TODO Auto-generated method stub
		return matchMapper.countMembersByMatchId(matchId);
	}
	@Override
	public List<String> getHashtags(int matchId) {
		// TODO Auto-generated method stub
		return matchMapper.getHashtags(matchId);
	}
	@Override
	public void postMatchesByMember(Map<String, Object> map) {
		// TODO Auto-generated method stub
		matchMapper.postMatchesByMember(map);
	}
	@Override
	public List<MatchDto> getMatchesNotFinished() {
		return matchMapper.getMatchesNotFinished();
	}
  
	public void updateMatchHit(int matchId) {
		// TODO Auto-generated method stub
		matchMapper.updateMatchHit(matchId);
	}
	@Override
	public void updateMatch(MatchDto matchDto) {
		// TODO Auto-generated method stub
		matchMapper.updateMatch(matchDto);
	}
	@Override
	public void deleteMatchImage(int fileId) {
		// TODO Auto-generated method stub
		matchMapper.deleteMatchImage(fileId);
	}
	@Override
	public void deleteMatch(int matchId) {
		// TODO Auto-generated method stub
		matchMapper.deleteMatch(matchId);
	}
	@Override
	public List<MatchDto> getFindMatchByMemberId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return matchMapper.getFindMatchByMemberId(map);
	}
	@Override
	public List<MateDto> getMatesByMatch(int matchId) {
		// TODO Auto-generated method stub
		return matchMapper.getMatesByMatch(matchId);
	}
	
}
