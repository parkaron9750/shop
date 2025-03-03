package com.web.backend.config;

import com.web.backend.handler.WebMvcHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfig {

    private WebMvcHandler webMvcHandler;

    @Bean
    public WebMvcHandler getWebMvcHandler() {
        return new WebMvcHandler();
    }
}
