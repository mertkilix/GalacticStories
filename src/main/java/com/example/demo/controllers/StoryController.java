package com.example.demo.controllers;

import com.example.demo.dto.StoryDTO;
import com.example.demo.services.StoryService;
import com.example.demo.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")

public class StoryController {

    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping("/story")
    public Mono<ResponseEntity<StoryResponse>> createStory(@RequestBody StoryDTO storyRequest) {
        return storyService.createStory(storyRequest)
                .map(response -> ResponseEntity.ok().body(response))
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new StoryResponse("Error: " + e.getMessage()))));
    }


}
