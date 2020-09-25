package com.arrayindex.webclientdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient webclient() {

        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                //.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
                .build();
        return webClient;
    }

}
