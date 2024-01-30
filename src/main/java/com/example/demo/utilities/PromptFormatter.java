package com.example.demo.utilities;

import com.example.demo.dto.StoryDTO;
import org.springframework.stereotype.Component;

@Component
public class PromptFormatter {

    public String formatPrompt(StoryDTO storyRequest) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Create a story");

        if (storyRequest.getWordCount() > 0) {
            prompt.append(" with a maximum of ").append(storyRequest.getWordCount()).append(" words");
        }

        if (storyRequest.getTheme() != null && !storyRequest.getTheme().isEmpty()) {
            prompt.append(", themed around ").append(storyRequest.getTheme());
        }

        if (storyRequest.getCharacters() != null && !storyRequest.getCharacters().isEmpty()) {
            prompt.append(", featuring characters ").append(storyRequest.getCharacters());
        }

        if (storyRequest.getLanguage() != null && !storyRequest.getLanguage().isEmpty()) {
            prompt.append(", in ").append(storyRequest.getLanguage());
        }

        prompt.append(". Please ensure the story is engaging and creative.");

        prompt.append(" Avoid using violence, explicit language, or any form of discrimination.");

        return prompt.toString();
    }
}
