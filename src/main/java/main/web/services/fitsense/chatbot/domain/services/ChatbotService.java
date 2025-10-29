package main.web.services.fitsense.chatbot.domain.services;

import main.web.services.fitsense.chatbot.domain.model.ChatMessage;
import main.web.services.fitsense.chatbot.domain.model.ChatSession;

/**
 * Domain service interface for chatbot operations
 */
public interface ChatbotService {
    
    /**
     * Start a new chat session
     */
    ChatSession startSession(Long userId);
    
    /**
     * Send a message to the chatbot
     */
    ChatMessage sendMessage(Long userId, String sessionId, String message, boolean forcePlan);
    
    /**
     * End a chat session
     */
    void endSession(Long userId, String sessionId);
    
    /**
     * Check if chatbot service is available
     */
    boolean isServiceAvailable();
}
