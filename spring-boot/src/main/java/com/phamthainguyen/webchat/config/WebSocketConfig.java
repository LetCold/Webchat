package com.phamthainguyen.webchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.phamthainguyen.webchat.component.MessageWebSocketHandler;
import com.phamthainguyen.webchat.component.WebSocketInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler(), "/websocket").addInterceptors(webSocketInterceptor).setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler messageHandler() {
        return new MessageWebSocketHandler();
    }
}
