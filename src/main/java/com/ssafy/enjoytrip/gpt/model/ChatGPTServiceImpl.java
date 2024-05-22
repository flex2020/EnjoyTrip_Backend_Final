package com.ssafy.enjoytrip.gpt.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.enjoytrip.config.ChatGPTConfig;
import com.ssafy.enjoytrip.follow.model.FollowMapper;
import com.ssafy.enjoytrip.member.model.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatGPTServiceImpl implements ChatGPTService {
	
    private final ChatGPTConfig chatGPTConfig;

    private final RestTemplate restTemplate;

    @Override
    public String generateAnswer(String prompt) throws Exception {
        String apiUrl = chatGPTConfig.getApiUrl();
        String apiKey = chatGPTConfig.getApiKey();
        
        log.debug(apiKey + " " + apiUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o");
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                apiUrl + "/v1/chat/completions",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        Map<String, Object> responseBody = responseEntity.getBody();
        
        if (responseBody != null && responseBody.containsKey("choices")) {
            Map<String, Object> choice = ((List<Map<String, Object>>) responseBody.get("choices")).get(0);
            Map<String, Object> message = (Map<String, Object>) choice.get("message");
            return ((String) message.get("content")).trim();
        }

        return "Nickname generation failed";
    }

    @Override
    public List<ModelResponse> getAvailableModels() throws Exception {
        String apiUrl = chatGPTConfig.getApiUrl();
        String apiKey = chatGPTConfig.getApiKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                apiUrl + "/v1/models",
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        Map<String, Object> responseBody = responseEntity.getBody();

        List<ModelResponse> models = new ArrayList();
        if (responseBody != null && responseBody.containsKey("data")) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get("data");
            for (Map<String, Object> model : data) {
                String id = (String) model.get("id");
                models.add(new ModelResponse(id));
            }
        }

        return models;
    }
}
