package main.web.services.fitsense.chatbot.interfaces.rest.transform;

import main.web.services.fitsense.chatbot.domain.model.ChatMessage;
import main.web.services.fitsense.chatbot.domain.model.ChatSession;
import main.web.services.fitsense.chatbot.interfaces.rest.resources.ChatMessageResource;
import main.web.services.fitsense.chatbot.interfaces.rest.resources.StartChatSessionResource;

/**
 * Assembler to transform domain models to REST resources
 */
public class ChatbotResourceAssembler {

    public static StartChatSessionResource toResourceFromSession(ChatSession session) {
        return new StartChatSessionResource(
                session.getSessionId(),
                session.isActive()
        );
    }

    public static ChatMessageResource toResourceFromMessage(ChatMessage message) {
        return new ChatMessageResource(
                message.getMessage(),
                message.getReply(),
                message.getSessionId(),
                message.isCanChange(),
                message.isGeneratedPlan(),
                message.getDaysSinceLastPlan()
        );
    }
}
