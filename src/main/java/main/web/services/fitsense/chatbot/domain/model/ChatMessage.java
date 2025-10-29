package main.web.services.fitsense.chatbot.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a chat message sent to or received from ChatBox-AI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private Long userId;
    private String sessionId;
    private String message;
    private String reply;
    private boolean canChange;
    private boolean generatedPlan;
    private int daysSinceLastPlan;
}
