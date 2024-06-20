package com.ssafy.enjoytrip.chat.handler;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.chat.model.ChatLogDto;
import com.ssafy.enjoytrip.chat.model.ChatLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {
	private final ChatLogService chatLogService;
	
	@GetMapping("/api/chat/{matchId}")
	public ResponseEntity<?> loadChatLog(@PathVariable("matchId") String matchId) {
		List<ChatLogDto> logs = chatLogService.loadChatLog(matchId);
		return ResponseEntity.ok(logs);
	}
}
