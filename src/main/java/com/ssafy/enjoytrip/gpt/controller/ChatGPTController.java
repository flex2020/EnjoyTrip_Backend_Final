package com.ssafy.enjoytrip.gpt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.follow.controller.FollowController;
import com.ssafy.enjoytrip.follow.model.FollowService;
import com.ssafy.enjoytrip.gpt.model.ChatGPTResponse;
import com.ssafy.enjoytrip.gpt.model.ChatGPTService;
import com.ssafy.enjoytrip.gpt.model.ModelResponse;
import com.ssafy.enjoytrip.match.model.MatchDto;
import com.ssafy.enjoytrip.match.model.MatchService;
import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.model.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/gpt")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@Slf4j
public class ChatGPTController {

    private final ChatGPTService chatGPTService;
    private final MatchService matchService;
    private final MemberService memberService;
    
    @PostMapping("/generate/nickname")
    public ResponseEntity<ChatGPTResponse> generateNickname() throws Exception {
        String prompt = "아래 조건에 맞는 닉네임 생성해\n" +
                        "- 닉네임은 [] 안에 담겨야해\n" +
                        "- 닉네임은 A B 로 이루어져있고 A는 형용사, B는 명사야. A, B가 서로 관련없는 단어여도 좋아.\n" +
                        "- 답변은 닉네임만 대답하면 돼. 예를 들어서 [단단한 호랑이] 이렇게.";
        
        log.info(prompt);
        
        String nickname = chatGPTService.generateAnswer(prompt);
        ChatGPTResponse response = new ChatGPTResponse(nickname);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/generate/attraction-recommend")
    public ResponseEntity<?> generateAttractionRecommend(@RequestBody Map<String, Object> map) throws Exception {
    	String attraction = (String) map.get("attraction");
    	String prompt = "대한민국의 관광지 '" + attraction + "'에 대해 알려줘.\r\n"
		    			+ "- 근처 다른 여행지를 포함하면 더 좋은 답변이야.\r\n"
		    			+ "- 다른 여행지는 ''(작은따옴표)로 감싸서 답변해.\r\n"
		    			+ "- 말투는 ~~합니다. 또는 ~~ 습니다. 체를 사용해.\r\n"
		    			+ "- 답변은 70자이내로.";
    	log.info(prompt);
    	String answer = chatGPTService.generateAnswer(prompt);
    	return ResponseEntity.ok(answer);
    }
    
    @PostMapping("/generate/match-recommend")
    public ResponseEntity<?> generateMathchRecommend(@RequestBody Map<String, Object> map) throws Exception {
    	List<MatchDto> matchList = matchService.getMatchesNotFinished();
    	StringBuilder matchData = new StringBuilder();
    	for (MatchDto m : matchList) {
    		matchData.append("[게시물 ID: ")
    			.append(m.getMatchId())
    			.append(", 성별제한: ")
    			.append(m.getGenderType().equals("0") ? "성별무관" : m.getGenderType().equals("1") ? "남자만" : "여자만")
    			.append(", 작성자가 원하는 사람의 설명: ")
    			.append(m.getContent())
    			.append("],");
    	}
    	String email = (String) map.get("email");
    	MemberDto member = memberService.findByEmail(email);
    	
    	StringBuilder prompt = new StringBuilder();
    	prompt.append("다음 게시물의 정보를 보고 아래 조건을 가장 잘 만족하는 게시글을 최대 5개 선택해.\n")
    		.append("먼저 게시물의 조건은 아래와 같아.\n")
    		.append("조건1. 내 성격과 잘 맞아야해. 그 지표로 내 MBTI는 '").append(member.getMbti()).append("'야.\n")
    		.append("조건2. 내 나이를 고려해서 선택해. 내 생년월일은 '").append(member.getBirthday()).append("'이야.\n")
    		.append("조건3. 내 성별을 고려해서 남자라면 (성별무관, 남자만), 여자라면 (성별무관, 여자만)인 게시물을 선택해. 내 성별은 '").append(member.getGender().equals("0") ? "여자야." : "남자'야.\n")
    		.append("조건4. 내 자기소개를 고려해서 선택해. 내 자기소개는 '").append(member.getIntro()).append("'야.\n")
    		.append("다음은 답변에 관한 조건이야.\n")
    		.append("조건1. 답변은 50자 이내로 해.\n")
    		.append("조건2. 답변은 무조건 [9,63,31,37,85]의 형태로 대답해. 나에게 맞는 게시물ID를 [게시물ID,게시물ID,게시물ID,게시물ID,게시물ID] 형태로만 출력해.\n")
    		.append("조건3. 콤마(,) 사이에 절대 공백 넣지마.\n")
    		.append("조건4. [] 안의 내용을 제외하고 절대 다른 말 하지마.\n")
    		.append("조건3. 답변은 게시물의 Id 순서로 정렬하지말고 나와 잘 맞다고 생각하는 순서로 정렬해. 이게 중요해.\n")
    		.append("이제 게시물 정보를 줄게. 게시물 정보는 다음과 같아.\n")
    		.append(matchData.toString());
    	System.out.println(prompt);
    	String answer = chatGPTService.generateAnswer(prompt.toString());
    	//String answer = "[99,89,94,102,103]";
    	System.out.println("answer = " + answer);
		List<String> numberList = new ArrayList<>();
		 
		// 숫자만 추출하기 위한 정규 표현식
		String regex = "\\d+";
		 
		// 정규 표현식에 따라 숫자만 추출
		Pattern pattern = java.util.regex.Pattern.compile(regex);
		Matcher matcher = pattern.matcher(answer);
		 
		while (matcher.find()) {
		    numberList.add(matcher.group());
		}
		System.out.println(numberList);
		List<MatchDto> filteredList = matchList.stream()
	            .filter(match -> numberList.contains(match.getMatchId()))
	            .collect(Collectors.toList());
		System.out.println(filteredList.size());
    	return ResponseEntity.ok(filteredList);
    }
    
    @GetMapping("/models")
    public ResponseEntity<List<ModelResponse>> getAvailableModels() throws Exception {
        return ResponseEntity.ok(chatGPTService.getAvailableModels());
    }
}
