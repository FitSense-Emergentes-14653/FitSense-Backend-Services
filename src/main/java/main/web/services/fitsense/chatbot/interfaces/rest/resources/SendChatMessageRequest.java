package main.web.services.fitsense.chatbot.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SendChatMessageRequest(
        @JsonProperty("message") String message,
        @JsonProperty("sessionId") String sessionId,
        @JsonProperty("forcePlan") Boolean forcePlan
) {
}
