package com.ssafy.enjoytrip.gpt.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.follow.controller.FollowController;
import com.ssafy.enjoytrip.follow.model.FollowService;
import com.ssafy.enjoytrip.gpt.model.ChatGPTResponse;
import com.ssafy.enjoytrip.gpt.model.ChatGPTService;
import com.ssafy.enjoytrip.gpt.model.ModelResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gpt")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
@Slf4j
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/generate/nickname")
    public ResponseEntity<ChatGPTResponse> generateNickname() throws Exception {
        String prompt = "아래 조건에 맞는 닉네임 생성해\n" +
                        "- 닉네임은 [] 안에 담겨야해\n" +
                        "- 닉네임은 A B 로 이루어져있고 A는 형용사, B는 명사야. A, B가 서로 관련없는 단어여도 좋아.\n" +
                        "- 답변은 닉네임만 대답하면 돼. 예를 들어서 [단단한 호랑이] 이렇게.";
        
        log.info(prompt);
        
        String nickname = chatGPTService.generateNickname(prompt);
        ChatGPTResponse response = new ChatGPTResponse(nickname);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/models")
    public ResponseEntity<List<ModelResponse>> getAvailableModels() throws Exception {
        return ResponseEntity.ok(chatGPTService.getAvailableModels());
    }
}
