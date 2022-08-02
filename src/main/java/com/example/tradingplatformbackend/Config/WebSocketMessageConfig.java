package com.example.tradingplatformbackend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry){
        // endpoint to start handshake for new socket connection with server
        registry.addEndpoint("/chat-example").withSockJS();
        registry.addEndpoint("/trading-platform-stream").withSockJS();
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry){
        // prefix for controller endpoints for the client to hit
        registry.setApplicationDestinationPrefixes("/app");
        // where the messages get sent -> the controller's @SendTo
        registry.enableSimpleBroker("/stream");
    }
}
