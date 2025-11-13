package main.web.services.fitsense.chatbot.infrastructure.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import main.web.services.fitsense.chatbot.infrastructure.client.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP client for ChatBox-AI service with circuit breaker and retry patterns
 */
@Component
@Slf4j
public class ChatBoxClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public ChatBoxClient(
            @Qualifier("chatBoxRestTemplate") RestTemplate restTemplate,
            @Value("https://chatbox-ai-production-6ead.up.railway.app") String baseUrl,
            @Value("${chatbox.apiKey:}") String apiKey) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.apiKey = apiKey;
    }

    /**
     * Start or resume a chat session
     */
    @CircuitBreaker(name = "chatbox", fallbackMethod = "startSessionFallback")
    @Retry(name = "chatbox")
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

    /**
     * Send a message to the chatbot
     */
    @CircuitBreaker(name = "chatbox", fallbackMethod = "sendMessageFallback")
    @Retry(name = "chatbox")
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

    /**
     * End a chat session
     */
    @CircuitBreaker(name = "chatbox", fallbackMethod = "endSessionFallback")
    @Retry(name = "chatbox")
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

    /**
     * Check health of ChatBox-AI service
     */
    @CircuitBreaker(name = "chatbox", fallbackMethod = "healthCheckFallback")
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
                    (Class<Map<String, Object>>)(Class<?>)Map.class
            );
            
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Health check failed: {}", e.getMessage());
            throw e;
        }
    }

    // Fallback methods
    
    private StartSessionResponse startSessionFallback(Long userId, String sessionId, Exception e) {
        log.warn("Fallback: startSession failed for userId: {}, reason: {}", userId, e.getMessage());
        StartSessionResponse fallback = new StartSessionResponse();
        fallback.setOk(false);
        fallback.setSessionId(null);
        return fallback;
    }

    private SendMessageResponse sendMessageFallback(Long userId, String sessionId, String message, Boolean forcePlan, Exception e) {
        log.warn("Fallback: sendMessage failed for userId: {}, reason: {}", userId, e.getMessage());
        SendMessageResponse fallback = new SendMessageResponse();
        fallback.setReply("Lo siento, el servicio de chat no está disponible en este momento. Por favor, intenta más tarde.");
        fallback.setCanChange(false);
        fallback.setGeneratedPlan(false);
        fallback.setDaysSinceLastPlan(0);
        return fallback;
    }

    private EndSessionResponse endSessionFallback(Long userId, String sessionId, Exception e) {
        log.warn("Fallback: endSession failed for userId: {}, reason: {}", userId, e.getMessage());
        EndSessionResponse fallback = new EndSessionResponse();
        fallback.setOk(false);
        fallback.setSaved(false);
        return fallback;
    }

    private Map<String, Object> healthCheckFallback(Exception e) {
        log.warn("Fallback: health check failed, reason: {}", e.getMessage());
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("ok", false);
        fallback.put("error", "ChatBox-AI service unavailable");
        return fallback;
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
