package com.ssafy.enjoytrip.chat.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatDto {
	private String type;
	private int userIdx;
	private String username;
	private String content;
	private String timestamp;
	private int matchId;
	private int idx;
	
	@Builder
	public ChatDto(String type, int userIdx, String username, String content, String timestamp, int matchId, int idx) {
		this.type = type;
		this.userIdx = userIdx;
		this.username = username;
		this.content = content;
		this.timestamp = timestamp;
		this.matchId = matchId;
		this.idx = idx;
	}
}
