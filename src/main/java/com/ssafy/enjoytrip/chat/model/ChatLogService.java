package com.ssafy.enjoytrip.chat.model;

import java.util.List;

public interface ChatLogService {
	List<ChatLogDto> loadChatLog(String matchId);
	void saveChatLog(ChatLogDto dto);
}
