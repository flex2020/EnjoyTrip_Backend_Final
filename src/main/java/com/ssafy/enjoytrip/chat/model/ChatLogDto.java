package com.ssafy.enjoytrip.chat.model;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatLogDto {
	private String chatLogId;
	private String matchId;
	private String sender;
	private String type;
	private String content;
	private String sendTime;
	
	@Builder
	public ChatLogDto(String chatLogId, String matchId, String sender, String type, String content, String sendTime) {
		super();
		this.chatLogId = chatLogId;
		this.matchId = matchId;
		this.sender = sender;
		this.type = type;
		this.content = content;
		this.sendTime = sendTime;
	}
}
