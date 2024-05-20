package com.ssafy.enjoytrip.gpt.model;

import java.util.List;

public interface ChatGPTService {
	public String generateNickname(String prompt) throws Exception;
	List<ModelResponse> getAvailableModels() throws Exception;
}
