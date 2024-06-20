package com.ssafy.enjoytrip.chat.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatLogMapper {
	List<ChatLogDto> loadChatLog(String matchId);
	void saveChatLog(ChatLogDto dto);
}
