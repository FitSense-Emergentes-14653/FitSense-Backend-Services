package main.web.services.fitsense.chatbot.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import main.web.services.fitsense.chatbot.infrastructure.client.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ChatBoxClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public ChatBoxClient(
            RestTemplate restTemplate,
            @Value("${chatbox.url}") String baseUrl,
            @Value("${chatbox.apiKey:}") String apiKey) {

        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl != null && baseUrl.endsWith("/")
                ? baseUrl.substring(0, baseUrl.length() - 1)
                : baseUrl;
        this.apiKey = apiKey;
    }

    public StartSessionResponse startSession(Long userId, String sessionId) {
        log.info("Starting chat session for userId: {}", userId);

        String endpoint = baseUrl + "/session/start";
        HttpHeaders headers = createHeaders();

        StartSessionRequest request = new StartSessionRequest(userId, sessionId);
        HttpEntity<StartSessionRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<StartSessionResponse> response = restTemplate.postForEntity(
                    endpoint,
                    entity,
                    StartSessionResponse.class
            );

            log.info("Session started successfully: {}", response.getBody());
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error starting chat session: {}", e.getMessage());
            throw e;
        }
    }

    public SendMessageResponse sendMessage(Long userId, String sessionId, String message, Boolean forcePlan) {
        log.info("Sending message for userId: {}, sessionId: {}", userId, sessionId);

        String endpoint = baseUrl + "/chat/send";
        HttpHeaders headers = createHeaders();

        SendMessageRequest request = new SendMessageRequest(userId, sessionId, message, forcePlan);
        HttpEntity<SendMessageRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<SendMessageResponse> response = restTemplate.postForEntity(
                    endpoint,
                    entity,
                    SendMessageResponse.class
            );

            log.info("Message sent successfully");
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error sending message: {}", e.getMessage());
            throw e;
        }
    }

    public EndSessionResponse endSession(Long userId, String sessionId) {
        log.info("Ending chat session for userId: {}, sessionId: {}", userId, sessionId);

        String endpoint = baseUrl + "/session/end";
        HttpHeaders headers = createHeaders();

        EndSessionRequest request = new EndSessionRequest(userId, sessionId);
        HttpEntity<EndSessionRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<EndSessionResponse> response = restTemplate.postForEntity(
                    endpoint,
                    entity,
                    EndSessionResponse.class
            );

            log.info("Session ended successfully");
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error ending session: {}", e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> healthCheck() {
        log.info("Checking ChatBox-AI health");

        String endpoint = baseUrl + "/health";
        HttpHeaders headers = createHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            @SuppressWarnings("unchecked")
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.GET,
                    entity,
                    (Class<Map<String, Object>>) (Class<?>) Map.class
            );

            return response.getBody();
        } catch (RestClientException e) {
            log.error("Health check failed: {}", e.getMessage());
            throw e;
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (apiKey != null && !apiKey.isEmpty()) {
            headers.set("x-api-key", apiKey);
        }
        return headers;
    }
}
