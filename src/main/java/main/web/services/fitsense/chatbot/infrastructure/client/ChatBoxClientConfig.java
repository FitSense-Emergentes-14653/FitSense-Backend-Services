package main.web.services.fitsense.chatbot.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for ChatBox-AI HTTP client
 */
@Configuration
public class ChatBoxClientConfig {

    @Value("${chatbox.connectTimeoutMs:2000}")
    private int connectTimeout;

    @Value("${chatbox.readTimeoutMs:15000}")
    private int readTimeout;

    @Bean(name = "chatBoxRestTemplate")
    public RestTemplate chatBoxRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return new RestTemplate(factory);
    }
}
