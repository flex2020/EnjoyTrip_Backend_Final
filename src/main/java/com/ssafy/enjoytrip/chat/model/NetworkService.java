package com.ssafy.enjoytrip.chat.model;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Service
public class NetworkService {
	
	private static ConcurrentHashMap<String, User> sessionMap;
	private static ConcurrentHashMap<Integer, AtomicInteger> userCountByMatchIdMap;
	private static ConcurrentHashMap<Integer, String> curListStateByMatchIdMap;
	private static ObjectMapper objectMapper;
	
	public NetworkService() {
		super();
		// SessionID와 User 객체를 key:value로 저장
		sessionMap = new ConcurrentHashMap<String, User>();
		userCountByMatchIdMap = new ConcurrentHashMap<Integer, AtomicInteger>();
		curListStateByMatchIdMap = new ConcurrentHashMap<Integer, String>();
		objectMapper = JsonMapper.builder()
			    .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
			    .build();
	}
	
	public User getUser(String sessionID) {
		return sessionMap.get(sessionID);
	}
	
	public void setListState(int matchId, String state) {
		curListStateByMatchIdMap.put(matchId, state);
	}
	
	public String getListState(int matchId) {
		return curListStateByMatchIdMap.get(matchId);
	}

	public boolean addUser(String sessionID, User user) {
		sessionMap.put(sessionID, user);
		
		int matchId = user.getMatchId();
		
		if (userCountByMatchIdMap.putIfAbsent(matchId, new AtomicInteger(1)) != null) {
			userCountByMatchIdMap.get(matchId).incrementAndGet();
		}
		return true;
	}
	
	public void sendUnicast(String sessionID, ChatDto chatDto) {
		TextMessage tm;
		try {
			tm = new TextMessage(objectMapper.writer().writeValueAsString(chatDto));
			sessionMap.get(sessionID).getSession().sendMessage(tm);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBroadcast(ChatDto chatDto, int matchId) {
		TextMessage tm;
		try {
			tm = new TextMessage(objectMapper.writer().writeValueAsString(chatDto));
			for (ConcurrentHashMap.Entry<String, User> entry : sessionMap.entrySet()) {
				if (entry.getValue().getMatchId() == matchId) {
					System.out.println("send to in " + entry.getValue().getMatchId() + " user " + entry.getValue().getUsername());
					try {
					entry.getValue().getSession().sendMessage(tm);
					} catch (Exception e) {}
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendBroadcastExcept(ChatDto chatDto, int matchId) {
		TextMessage tm;
		try {
			tm = new TextMessage(objectMapper.writer().writeValueAsString(chatDto));
			for (ConcurrentHashMap.Entry<String, User> entry : sessionMap.entrySet()) {
				if (entry.getValue().getMatchId() == matchId) {
					if (entry.getValue().getUserIdx() == chatDto.getUserIdx()) continue;
					System.out.println("send to in " + entry.getValue().getMatchId() + " user " + entry.getValue().getUsername());
					try {
						entry.getValue().getSession().sendMessage(tm);
					} catch (Exception e) {}
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean removeUser(String sessionID) {
		int matchId = getUser(sessionID).getMatchId();
		System.out.println(matchId);
		if (userCountByMatchIdMap.get(matchId).decrementAndGet() == 0) {
			curListStateByMatchIdMap.remove(matchId);
		}
		
		sessionMap.remove(sessionID);
		return true;
	}

	public void addSession(User user) {
		sessionMap.put(user.getSession().getId(), user);
	}

}