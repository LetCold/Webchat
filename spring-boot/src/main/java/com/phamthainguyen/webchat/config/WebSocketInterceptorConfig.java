package com.phamthainguyen.webchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.phamthainguyen.webchat.component.WebSocketInterceptor;

@Configuration
public class WebSocketInterceptorConfig {

    @Bean
    public WebSocketInterceptor webSocketInterceptor() {
        return new WebSocketInterceptor();
    }
}