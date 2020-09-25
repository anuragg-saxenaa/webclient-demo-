package com.arrayindex.webclientdemo.service;


import com.arrayindex.webclientdemo.domain.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ToDoService {

    @Autowired
    WebClient webClient;

    public Mono<ToDo> getToDoById(String id) {
        return webClient.get()
                .uri("/todos/{id}", id)
                .retrieve()
                .bodyToMono(ToDo.class);
    }

    public Flux<ToDo> getToDos() {
        return webClient.get()
                .uri("/todos")
                .retrieve()
                .bodyToFlux(ToDo.class);
    }
}
