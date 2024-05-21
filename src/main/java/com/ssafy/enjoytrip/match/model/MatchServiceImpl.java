package com.ssafy.enjoytrip.match.model;

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
		
		List<MatchDto> list = matchMapper.getFindMatches(param);
		
		int totalArticleCount = matchMapper.getTotalMatchCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		MatchListDto matchListDto = new MatchListDto();
		matchListDto.setMatches(list);
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
	
}
