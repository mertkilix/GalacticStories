package com.example.demo.services;

import com.example.demo.dto.StoryDTO;
import com.example.demo.utilities.APIUtility;
import com.example.demo.utilities.PromptFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StoryTextGenerator {

    private final APIUtility apiUtility;
    private final PromptFormatter promptFormatter;

    public StoryTextGenerator(APIUtility apiUtility, PromptFormatter promptFormatter) {
        this.apiUtility = apiUtility;
        this.promptFormatter = promptFormatter;
    }

    public Mono<String> generateStoryText(StoryDTO storyRequest) {
        JSONObject requestBody = createRequestBody(storyRequest);

        return apiUtility.makeApiCall("/chat/completions", requestBody, this::extractStoryFromResponse);
    }

    private JSONObject createRequestBody(StoryDTO storyRequest) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("stream", false);
        JSONArray messages = new JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", promptFormatter.formatPrompt(storyRequest)));
        requestBody.put("messages", messages);
        return requestBody;
    }




    private Mono<String> extractStoryFromResponse(JSONObject response) {
        return Mono.just(response.getJSONArray("choices").getJSONObject(0).optJSONObject("message").optString("content", ""));
    }
}
