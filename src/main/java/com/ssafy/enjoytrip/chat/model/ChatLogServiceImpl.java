package com.ssafy.enjoytrip.chat.model;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatLogServiceImpl implements ChatLogService {
	private final ChatLogMapper chatLogMapper;
	
	@Override
	public List<ChatLogDto> loadChatLog(String matchId) {
		return chatLogMapper.loadChatLog(matchId);
	}

	@Override
	public void saveChatLog(ChatLogDto dto) {
		chatLogMapper.saveChatLog(dto);
	}

}
