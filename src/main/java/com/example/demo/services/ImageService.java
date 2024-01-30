package com.example.demo.services;

import com.example.demo.models.Story;
import com.example.demo.utilities.APIUtility;
import org.json.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final APIUtility apiUtility;
    private final FileService fileService;

    public ImageService(APIUtility apiUtility, FileService fileService) {
        this.apiUtility = apiUtility;
        this.fileService = fileService;
    }

    public Mono<String> generateImage(String storyText, Story story) {
        String prompt = "Create an image for story without any kind of text on image: " + storyText;
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "dall-e-3");
        requestBody.put("prompt", prompt);
        requestBody.put("size", "1024x1024");
        requestBody.put("quality", "standard");
        requestBody.put("n", 1);

        String filename = story.getId() + ".png";
        String imagePath = fileService.generateImagePath(filename);

        return apiUtility.makeApiCall("/images/generations", requestBody, jsonResponse -> {
                    String imageUrl = jsonResponse.getJSONArray("data").getJSONObject(0).getString("url");
                    return downloadAndSaveImage(imageUrl, Paths.get(imagePath))
                            .thenReturn(imageUrl); // Return the original OpenAI URL
                })
                .doOnError(e -> System.err.println("Error during image generation: " + e.getMessage()));
    }

    private Mono<Void> downloadAndSaveImage(String imageUrl, Path destinationPath) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(imageUrl))
                .build();

        return Mono.fromCallable(() -> client.send(request, HttpResponse.BodyHandlers.ofInputStream()))
                .flatMap(response -> DataBufferUtils.join(DataBufferUtils.readInputStream(() -> response.body(), new DefaultDataBufferFactory(), 4096)))
                .flatMap(dataBuffer -> fileService.saveFile(destinationPath, Mono.just(dataBuffer), "https://galacticstories.com/storyimages/"))
                .then(); // Complete the Mono after the file is saved
    }

}
