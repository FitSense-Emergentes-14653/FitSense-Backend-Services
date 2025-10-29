package main.web.services.fitsense.chatbot.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendMessageRequest {
    private Long userId;
    private String sessionId;
    private String message;
    private Boolean forcePlan;
}
