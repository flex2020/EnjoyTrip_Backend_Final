package com.ssafy.enjoytrip.gpt.model;

import java.util.List;

public interface ChatGPTService {
	List<ModelResponse> getAvailableModels() throws Exception;
	String generateAnswer(String prompt) throws Exception;
}
