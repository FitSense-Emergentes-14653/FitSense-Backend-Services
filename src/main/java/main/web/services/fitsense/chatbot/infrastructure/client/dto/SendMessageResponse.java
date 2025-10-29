package main.web.services.fitsense.chatbot.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageResponse {
    private String reply;
    private Boolean canChange;
    private Boolean generatedPlan;
    private Integer daysSinceLastPlan;
}
