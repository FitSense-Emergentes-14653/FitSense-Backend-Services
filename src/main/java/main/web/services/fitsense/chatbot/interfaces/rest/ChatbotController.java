package main.web.services.fitsense.chatbot.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import main.web.services.fitsense.chatbot.domain.model.ChatMessage;
import main.web.services.fitsense.chatbot.domain.model.ChatSession;
import main.web.services.fitsense.chatbot.domain.services.ChatbotService;
import main.web.services.fitsense.chatbot.interfaces.rest.resources.ChatMessageResource;
import main.web.services.fitsense.chatbot.interfaces.rest.resources.EndChatSessionRequest;
import main.web.services.fitsense.chatbot.interfaces.rest.resources.SendChatMessageRequest;
import main.web.services.fitsense.chatbot.interfaces.rest.resources.StartChatSessionResource;
import main.web.services.fitsense.chatbot.interfaces.rest.transform.ChatbotResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for chatbot operations
 * Exposes endpoints to interact with ChatBox-AI service
 */
@RestController
@RequestMapping("/api/v1/chatbot")
@Tag(name = "Chatbot", description = "AI-powered fitness chatbot endpoints")
@Slf4j
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * Start a new chat session for a user
     */
    @PostMapping("/users/{userId}/sessions")
    @Operation(summary = "Start chat session", description = "Initialize a new chat session for the authenticated user")
    public ResponseEntity<StartChatSessionResource> startSession(@PathVariable Long userId) {
        log.info("REST request to start chat session for user: {}", userId);
        
        try {
            ChatSession session = chatbotService.startSession(userId);
            
            if (session.isActive()) {
                return ResponseEntity.ok(ChatbotResourceAssembler.toResourceFromSession(session));
            } else {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(ChatbotResourceAssembler.toResourceFromSession(session));
            }
        } catch (Exception e) {
            log.error("Error starting chat session for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Send a message to the chatbot
     */
    @PostMapping("/users/{userId}/messages")
    @Operation(summary = "Send message", description = "Send a message to the chatbot and receive a response")
    public ResponseEntity<ChatMessageResource> sendMessage(
            @PathVariable Long userId,
            @RequestBody SendChatMessageRequest request) {
        
        log.info("REST request to send message for user: {}", userId);
        
        try {
            boolean forcePlan = request.forcePlan() != null ? request.forcePlan() : false;
            
            ChatMessage message = chatbotService.sendMessage(
                    userId, 
                    request.sessionId(), 
                    request.message(), 
                    forcePlan
            );
            
            return ResponseEntity.ok(ChatbotResourceAssembler.toResourceFromMessage(message));
        } catch (Exception e) {
            log.error("Error sending message for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * End a chat session
     */
    @PostMapping("/users/{userId}/sessions/end")
    @Operation(summary = "End chat session", description = "End the current chat session and save conversation summary")
    public ResponseEntity<Map<String, String>> endSession(
            @PathVariable Long userId,
            @RequestBody EndChatSessionRequest request) {
        
        log.info("REST request to end chat session for user: {}", userId);
        
        try {
            chatbotService.endSession(userId, request.sessionId());
            return ResponseEntity.ok(Map.of("status", "Session ended successfully"));
        } catch (Exception e) {
            log.error("Error ending session for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to end session"));
        }
    }

    /**
     * Check chatbot service availability
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the chatbot service is available")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        log.info("REST request to check chatbot health");
        
        try {
            boolean available = chatbotService.isServiceAvailable();
            return ResponseEntity.ok(Map.of(
                    "service", "ChatBox-AI",
                    "available", available,
                    "status", available ? "UP" : "DOWN"
            ));
        } catch (Exception e) {
            log.error("Error checking chatbot health", e);
            return ResponseEntity.ok(Map.of(
                    "service", "ChatBox-AI",
                    "available", false,
                    "status", "DOWN"
            ));
        }
    }
}
