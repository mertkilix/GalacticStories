package com.example.demo.services;

import com.example.demo.controllers.StoryResponse;
import com.example.demo.dto.StoryDTO;
import com.example.demo.models.Story;
import com.example.demo.repositories.StoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StoryService {

    private final StoryTextGenerator storyTextGenerator;
    private final AudioService audioService;
    private final ImageService imageService;
    private final StoryRepository storyRepository;

    // Updated constructor with StoryRepository parameter
    public StoryService(StoryTextGenerator storyTextGenerator, AudioService audioService, ImageService imageService, StoryRepository storyRepository) {
        this.storyTextGenerator = storyTextGenerator;
        this.audioService = audioService;
        this.imageService = imageService;
        this.storyRepository = storyRepository;
    }
    private Story createStoryEntity(StoryDTO storyRequest, String storyText) {
        Story story = new Story();
        story.setLanguage(storyRequest.getLanguage());
        story.setVoice(storyRequest.getVoice());
        story.setTheme(storyRequest.getTheme());
        story.setCharacters(storyRequest.getCharacters());
        story.setStory(storyText); // Set the generated story text
        story.setTitle(storyRequest.getTitle());

        return story;
    }

    public Mono<StoryResponse> createStory(StoryDTO storyRequest) {
        System.out.println("Story creation started for: " + storyRequest);

        return storyTextGenerator.generateStoryText(storyRequest)
                .flatMap(storyText -> {
                    if (storyText.isEmpty()) {
                        System.err.println("Story text is empty.");
                        return Mono.error(new RuntimeException("Story text generation failed."));
                    }

                    Story story = createStoryEntity(storyRequest, storyText);
                    return storyRepository.save(story)
                            .flatMap(savedStory -> {
                                Mono<String> audioUrlMono = audioService.textToSpeechToFile(storyText, storyRequest.getVoice(), savedStory);
                                Mono<String> imageUrlMono = imageService.generateImage(storyText, savedStory);

                                return Mono.zip(audioUrlMono, imageUrlMono, Mono.just(storyText))
                                        .map(tuple -> {
                                            System.out.println("Story, audio and image generated.");
                                            return new StoryResponse(tuple.getT3(), tuple.getT1(), tuple.getT2());
                                        });
                            })
                            .doOnError(error -> System.err.println("Error in story creation: " + error.getMessage()));
                });
    }
}
