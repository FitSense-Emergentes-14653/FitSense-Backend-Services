package main.web.services.fitsense.chatbot.application.internal;

import lombok.extern.slf4j.Slf4j;
import main.web.services.fitsense.chatbot.domain.model.ChatMessage;
import main.web.services.fitsense.chatbot.domain.model.ChatSession;
import main.web.services.fitsense.chatbot.domain.services.ChatbotService;
import main.web.services.fitsense.chatbot.infrastructure.client.ChatBoxClient;
import main.web.services.fitsense.chatbot.infrastructure.client.dto.EndSessionResponse;
import main.web.services.fitsense.chatbot.infrastructure.client.dto.SendMessageResponse;
import main.web.services.fitsense.chatbot.infrastructure.client.dto.StartSessionResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Application service implementation for chatbot integration
 * Acts as orchestrator between FitSense backend and ChatBox-AI service
 */
@Service
@Slf4j
public class ChatbotServiceImpl implements ChatbotService {

    private final ChatBoxClient chatBoxClient;

    public ChatbotServiceImpl(ChatBoxClient chatBoxClient) {
        this.chatBoxClient = chatBoxClient;
    }

    @Override
    public ChatSession startSession(Long userId) {
        log.info("Starting chat session for user: {}", userId);
        
        // Generate session ID based on userId
        String sessionId = "fs-" + userId;
        
        try {
            StartSessionResponse response = chatBoxClient.startSession(userId, sessionId);
            
            if (response != null && response.isOk()) {
                return new ChatSession(userId, response.getSessionId(), true);
            } else {
                log.warn("Failed to start session for user: {}", userId);
                return new ChatSession(userId, null, false);
            }
        } catch (Exception e) {
            log.error("Error starting session for user: {}", userId, e);
            return new ChatSession(userId, null, false);
        }
    }

    @Override
    public ChatMessage sendMessage(Long userId, String sessionId, String message, boolean forcePlan) {
        log.info("Sending message for user: {}, session: {}", userId, sessionId);
        
        try {
            SendMessageResponse response = chatBoxClient.sendMessage(
                    userId, 
                    sessionId, 
                    message, 
                    forcePlan
            );
            
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setUserId(userId);
            chatMessage.setSessionId(sessionId);
            chatMessage.setMessage(message);
            
            if (response != null) {
                chatMessage.setReply(response.getReply());
                chatMessage.setCanChange(response.getCanChange() != null ? response.getCanChange() : false);
                chatMessage.setGeneratedPlan(response.getGeneratedPlan() != null ? response.getGeneratedPlan() : false);
                chatMessage.setDaysSinceLastPlan(response.getDaysSinceLastPlan() != null ? response.getDaysSinceLastPlan() : 0);
            } else {
                chatMessage.setReply("Error: No se pudo obtener respuesta del servicio de chat.");
            }
            
            return chatMessage;
        } catch (Exception e) {
            log.error("Error sending message for user: {}", userId, e);
            
            ChatMessage errorMessage = new ChatMessage();
            errorMessage.setUserId(userId);
            errorMessage.setSessionId(sessionId);
            errorMessage.setMessage(message);
            errorMessage.setReply("Lo siento, ocurrió un error al procesar tu mensaje. Por favor, intenta nuevamente.");
            errorMessage.setCanChange(false);
            errorMessage.setGeneratedPlan(false);
            errorMessage.setDaysSinceLastPlan(0);
            
            return errorMessage;
        }
    }

    @Override
    public void endSession(Long userId, String sessionId) {
        log.info("Ending chat session for user: {}, session: {}", userId, sessionId);
        
        try {
            EndSessionResponse response = chatBoxClient.endSession(userId, sessionId);
            
            if (response != null && response.isOk()) {
                log.info("Session ended successfully for user: {}", userId);
            } else {
                log.warn("Failed to end session for user: {}", userId);
            }
        } catch (Exception e) {
            log.error("Error ending session for user: {}", userId, e);
        }
    }

    @Override
    public boolean isServiceAvailable() {
        try {
            Map<String, Object> health = chatBoxClient.healthCheck();
            return health != null && Boolean.TRUE.equals(health.get("ok"));
        } catch (Exception e) {
            log.error("ChatBox-AI service is unavailable", e);
            return false;
        }
    }
}
