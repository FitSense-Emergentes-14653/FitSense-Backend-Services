package main.web.services.fitsense.chatbot.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a chat session with the ChatBox-AI service
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSession {
    private Long userId;
    private String sessionId;
    private boolean active;
}
