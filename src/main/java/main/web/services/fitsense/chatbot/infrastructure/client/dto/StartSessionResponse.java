package main.web.services.fitsense.chatbot.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartSessionResponse {
    private boolean ok;
    private String sessionId;
}
