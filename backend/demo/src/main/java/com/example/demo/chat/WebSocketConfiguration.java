package com.example.demo.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.example.demo.UrlMapping.APPLICATION_SOCKET_PREFIX;
import static com.example.demo.UrlMapping.STOMP_WEB_SOCKET_ENDPOINT;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(STOMP_WEB_SOCKET_ENDPOINT)
                .setAllowedOrigins("http://localhost:8091")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user"); //where the client reads messages from
        registry.setApplicationDestinationPrefixes(APPLICATION_SOCKET_PREFIX); //where the server receives messages
        registry.setUserDestinationPrefix("/user");
    }

}
