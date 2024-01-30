package com.example.demo.services;

import com.example.demo.models.Story;
import org.json.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.file.Paths;

@Service
public class AudioService {

    private final WebClient webClient;
    private final FileService fileService;

    public AudioService(WebClient webClient, FileService fileService) {
        this.webClient = webClient;
        this.fileService = fileService;
    }

    public Mono<String> textToSpeechToFile(String text, String voice, Story story) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("input", text);
        requestBody.put("voice", voice);
        requestBody.put("model", "tts-1");

        String audioFilePath = fileService.generateAudioPath(story.getId() + ".mp3");

        return webClient.post()
                .uri("/audio/speech")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody.toString()))
                .retrieve()
                .bodyToMono(DataBuffer.class)
                .flatMap(dataBuffer -> fileService.saveFile(Paths.get(audioFilePath), Mono.just(dataBuffer), "https://galacticstories.com/audios/"))
                .doOnError(error -> System.err.println("Error during text-to-speech conversion: " + error.getMessage()));
    }
}
