package com.ssafy.enjoytrip.chat.model;

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
	private String profileImage;
	private String timestamp;
	private int matchId;
	private int idx;
	
	@Builder
	public ChatDto(String type, int userIdx, String username, String content, String profileImage, String timestamp,
			int matchId, int idx) {
		this.type = type;
		this.userIdx = userIdx;
		this.username = username;
		this.content = content;
		this.profileImage = profileImage;
		this.timestamp = timestamp;
		this.matchId = matchId;
		this.idx = idx;
	}

}
