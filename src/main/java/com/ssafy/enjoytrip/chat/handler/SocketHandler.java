package com.ssafy.enjoytrip.chat.handler;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.ssafy.enjoytrip.chat.user.ChatDto;
import com.ssafy.enjoytrip.chat.user.NetworkService;
import com.ssafy.enjoytrip.chat.user.User;

@Component
public class SocketHandler extends TextWebSocketHandler {
	private AtomicInteger idx = null;
	private ObjectMapper objectMapper;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
	@Autowired
	private NetworkService networkService;
	
	public SocketHandler() {
		super();
		new HashMap<String, User>();
		this.objectMapper = JsonMapper.builder()
			    .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
			    .build();
		this.idx = new AtomicInteger(0);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		int curIdx = idx.incrementAndGet();
		User user = User.builder()
				.session(session)
				.userIdx(curIdx)
				.username(null)
				.matchId(-1)
				.build();
		networkService.addSession(user);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws Exception {
		super.handleTextMessage(session, msg);
		int curIdx = idx.incrementAndGet();
        String timestamp = LocalDateTime.now().format(formatter);
		// ChatDTO 객체에 유저 메시지 맵핑
		ChatDto chat = objectMapper.reader().readValue(msg.getPayload(), ChatDto.class);
		
		// 메시지 타입에 따라 분기
		System.out.println(chat.toString());
		// signin : 채팅방 입장
		if (chat.getType().equals("signin")) {
			System.out.println("---- SIGNIN PROCEDURE ----");
			// 해당 세션에 대해 유저 정보 등록
			User newUser = networkService.getUser(session.getId());
			newUser.setUsername(chat.getUsername());
			newUser.setMatchId(chat.getMatchId());
			
			networkService.addUser(session.getId(), newUser);
			
			ChatDto chatDto = ChatDto.builder()
					.type("notice")
					.content(chat.getUsername() + "님이 입장했습니다.")
					.userIdx(newUser.getUserIdx())
					.idx(curIdx)
					.matchId(chat.getMatchId())
					.timestamp(timestamp)
					.build();
			System.out.println(":: WILL SEND :: " + chatDto.toString());
			
			networkService.sendBroadcast(chatDto, chat.getMatchId());
			
			String state = networkService.getListState(newUser.getMatchId());
			if (state != null) {
				ChatDto stateChatDto = ChatDto.builder()
						.type("path")
						.content(state)
						.idx(idx.incrementAndGet())
						.matchId(newUser.getMatchId())
						.userIdx(newUser.getUserIdx())
						.timestamp(timestamp)
						.build();
				networkService.sendUnicast(session.getId(), stateChatDto);
			}
			
		}
		// chat : 채팅
		else if (chat.getType().equals("chat")) {
			User curUser = networkService.getUser(session.getId());
			ChatDto chatDto = ChatDto.builder()
			        .type("chat")
			        .content(chat.getContent())
			        .username(curUser.getUsername())
			        .idx(curIdx)
			        .matchId(curUser.getMatchId())
			        .userIdx(curUser.getUserIdx())
			        .timestamp(timestamp)
			        .build();

			networkService.sendBroadcast(chatDto, chat.getMatchId());
			
		}
		// gpt-answer: gpt 답변
		else if (chat.getType().equals("gpt-answer")) {
			User curUser = networkService.getUser(session.getId());
			ChatDto chatDto = ChatDto.builder()
			        .type("gpt-answer")
			        .content(chat.getContent())
			        .username("Chat-GPT")
			        .idx(curIdx)
			        .matchId(curUser.getMatchId())
			        .userIdx(curUser.getUserIdx())
			        .timestamp(timestamp)
			        .build();

			networkService.sendBroadcast(chatDto, chat.getMatchId());
			
		}
		// add-tab : 탭 추가
		else if (chat.getType().equals("add-tab")) {
			User curUser = networkService.getUser(session.getId());
			ChatDto chatDto = ChatDto.builder()
			        .type("add-tab")
			        .content(chat.getContent())
			        .username(curUser.getUsername())
			        .idx(curIdx)
			        .matchId(curUser.getMatchId())
			        .userIdx(curUser.getUserIdx())
			        .timestamp(timestamp)
			        .build();
			networkService.sendBroadcastExcept(chatDto, chat.getMatchId());
		}
		// remove-tab : 탭 삭제
		else if (chat.getType().equals("remove-tab")) {
			User curUser = networkService.getUser(session.getId());
			ChatDto chatDto = ChatDto.builder()
			        .type("remove-tab")
			        .content(chat.getContent())
			        .username(curUser.getUsername())
			        .idx(curIdx)
			        .matchId(curUser.getMatchId())
			        .userIdx(curUser.getUserIdx())
			        .timestamp(timestamp)
			        .build();
			networkService.sendBroadcastExcept(chatDto, chat.getMatchId());
		}
		// update-tab : 여행 코스 수정
		else if (chat.getType().equals("update-tab")) {
			int matchId = chat.getMatchId();
			ChatDto chatDto = ChatDto.builder()
					.type("update-tab")
					.content(chat.getContent())
					.idx(curIdx)
					.matchId(matchId)
					.userIdx(networkService.getUser(session.getId()).getUserIdx())
					.timestamp(timestamp)
					.build();
			
			//networkService.setListState(matchId, chatDto.getContent());
			System.out.println(chatDto.getContent().toString());
			//networkService.sendBroadcast(chatDto, matchId);
			networkService.sendBroadcastExcept(chatDto, matchId);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		String timestamp = LocalDateTime.now().format(formatter);
		int curIdx = idx.incrementAndGet();
		User removedUser = networkService.getUser(session.getId());
		ChatDto chatDto = ChatDto.builder()
				.type("notice")
				.content(removedUser.getUsername() + "님이 퇴장했습니다.")
				.userIdx(removedUser.getUserIdx())
				.idx(curIdx)
				.matchId(removedUser.getMatchId())
				.timestamp(timestamp)
				.build();

		System.out.println(":: WILL SEND :: " + chatDto.toString());
		int matchId = removedUser.getMatchId();
		
		networkService.removeUser(session.getId());
		networkService.sendBroadcast(chatDto, matchId);
	}
	
}