package com.ssafy.enjoytrip.chat.user;

import org.springframework.web.socket.WebSocketSession;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
	private WebSocketSession session;
	private int userIdx;
	private String username;
	private int matchId;
}
