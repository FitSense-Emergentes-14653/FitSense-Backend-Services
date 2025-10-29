package main.web.services.fitsense.chatbot.interfaces.rest.resources;

public record ChatMessageResource(
        String message,
        String reply,
        String sessionId,
        boolean canChange,
        boolean generatedPlan,
        int daysSinceLastPlan
) {
}
