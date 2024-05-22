package com.ssafy.enjoytrip.match.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.match.model.HashtagDto;
import com.ssafy.enjoytrip.match.model.MatchDto;
import com.ssafy.enjoytrip.match.model.MatchListDto;
import com.ssafy.enjoytrip.match.model.MatchService;
import com.ssafy.enjoytrip.match.model.MateDto;
import com.ssafy.enjoytrip.trip.model.dto.AttractionDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/match")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
public class MatchController {
	private final MatchService matchService;
	
	@GetMapping("/{matchId}")
	public ResponseEntity<?> getMatchCourse(@PathVariable("matchId") String matchId) {
		List<AttractionDto> result = matchService.match(matchId);
		MatchDto matchInfo = matchService.matchDetail(matchId);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("courseItem", result);
		resultMap.put("matchInfo", matchInfo);
		return ResponseEntity.ok(resultMap);
	}
	
	@GetMapping("/matches")
	public ResponseEntity<List<MatchDto>> getMatches() throws Exception {
		return new ResponseEntity<List<MatchDto>>(matchService.getMatches(), HttpStatus.OK);
	}
	
	@GetMapping("/find/matches")
	public ResponseEntity<Map<String,Object>> getFindMatches(@RequestParam Map<String, Object> map) throws Exception {
		MatchListDto matchListDto = matchService.getFindMatches(map);
		
		List<MatchDto> matchDto = matchService.getFindMatchByMemberId(map);
		for (int i = 0; i < matchDto.size(); i++) {
			matchDto.get(i).setNowPeople(matchService.countMembersByMatchId(Integer.parseInt(matchDto.get(i).getMatchId())));
			matchDto.get(i).setHashtags(matchService.getHashtags(Integer.parseInt(matchDto.get(i).getMatchId())));
		}
		Map<String,Object> res = new HashMap();
		res.put("msg", "조회완료");
		res.put("resdata", matchListDto);
		res.put("matchByMemberId", matchDto);

		
		return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
	}
	
	@GetMapping("/find/{matchId}")
	public ResponseEntity<Map<String,Object>> getFindMatch(@PathVariable("matchId") int matchId) throws Exception {
		MatchDto matchDto = matchService.getFindMatch(matchId);
		matchDto.setNowPeople(matchService.countMembersByMatchId(matchId));
		matchDto.setHashtags(matchService.getHashtags(matchId));
		matchService.updateMatchHit(matchId);
		
		Map<String,Object> res = new HashMap();
		res.put("msg", "조회완료");
		res.put("resdata", matchDto);
		return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
	}
	
	@GetMapping("/member-matches/{memberId}")
	public ResponseEntity<?> getMatchesByMember(@PathVariable("memberId") String memberId) {
		List<MatchDto> matchList = matchService.getMatchesByMember(memberId);
		return ResponseEntity.ok(matchList);
	}
	
	@GetMapping("/match-mates/{matchId}")
	public ResponseEntity<?> getMatesByMatch(@PathVariable("matchId") int matchId) {
		List<MateDto> matchList = matchService.getMatesByMatch(matchId);
		return ResponseEntity.ok(matchList);
	}
	
	@PostMapping("/member-matches")
	public ResponseEntity<?> postMatchesByMember(@RequestParam Map<String, Object> map) {
		System.out.println(map);
		matchService.postMatchesByMember(map);
		return ResponseEntity.ok("채팅방 입장 완료");
	}
	
	@DeleteMapping("/member-matches/{memberId}/{matchId}")
	public ResponseEntity<?> removeMatchOfMember(
			@PathVariable("memberId") String memberId
			, @PathVariable("matchId") String matchId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("memberId", memberId);
		paramMap.put("matchId", matchId);
		matchService.removeMatchOfMember(paramMap);
		return ResponseEntity.ok("나가기 완료");
	}
	
	@PostMapping
	public ResponseEntity<MatchDto> writeMatch(
			@RequestBody MatchDto matchDto) {
		matchService.writeMatch(matchDto);
		matchService.mappingFile(matchDto);
		
		List<String> hashtagList = matchDto.getHashtags(); 
		HashtagDto hashtagDto = new HashtagDto();
		for (int i = 0; i < hashtagList.size(); i++) {
			hashtagDto.setHashtagName(hashtagList.get(i));
			HashtagDto isDuplicateHashtag = matchService.isDuplicateHashtag(hashtagDto);
			Map<String, Object> map = new HashMap<>();
			map.put("matchId", matchDto.getMatchId());
			if (isDuplicateHashtag == null) {
				matchService.writeHashtag(hashtagDto);
				map.put("hashtagId", hashtagDto.getHashtagId());
			} else {
				map.put("hashtagId", isDuplicateHashtag.getHashtagId());
			}
			matchService.mappingHashtag(map);
		}
		
		return ResponseEntity.ok(matchDto);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateMatch(
			@RequestBody MatchDto matchDto) {
		matchService.updateMatch(matchDto);
		
		// 기존 해시태그 매핑 삭제
		matchService.deleteMappingHashtag(matchDto.getMatchId());
		// 새로운 해시태그 업데이트
		List<String> hashtagList = matchDto.getHashtags();
		HashtagDto hashtagDto = new HashtagDto();
		for (int i = 0; i < hashtagList.size(); i++) {
			hashtagDto.setHashtagName(hashtagList.get(i));
			HashtagDto isDuplicateHashtag = matchService.isDuplicateHashtag(hashtagDto);
			Map<String, Object> map = new HashMap<>();
			map.put("matchId", matchDto.getMatchId());
			if (isDuplicateHashtag == null) {
				matchService.writeHashtag(hashtagDto);
				map.put("hashtagId", hashtagDto.getHashtagId());
			} else {
				map.put("hashtagId", isDuplicateHashtag.getHashtagId());
			}
			matchService.mappingHashtag(map);
		}
		System.out.println(matchDto.getMatchId());
		// fileId null이 아니면
		String matchId = matchDto.getMatchId();
		String fileId = matchDto.getFileId();
		if (matchId != null) {
			// 기존 사진 삭제
			matchService.deleteMatchImage(Integer.parseInt(matchId));
			// 새로운 삭제 업로드
			matchService.mappingFile(matchDto);
		}
		return ResponseEntity.ok("수정 완료");
	}
	
	@DeleteMapping("/{matchid}")
	public ResponseEntity<String> deleteMatch(@PathVariable("matchid") int matchId) throws Exception {
		matchService.deleteMatch(matchId);
		return ResponseEntity.ok("삭제 완료");
	}
	
	@GetMapping("/profile/{matchId}")
	public ResponseEntity<?> getMatchProfile(@PathVariable("matchId") String matchId) throws Exception {
		String profilePath = matchService.getMatchProfile(matchId);
		return ResponseEntity.ok(profilePath);
	}
}
