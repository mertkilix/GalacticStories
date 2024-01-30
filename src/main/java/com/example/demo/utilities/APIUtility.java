package com.example.demo.utilities;

import com.example.demo.dto.StoryDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class APIUtility {

    private final WebClient webClient;
    private static final String TEXT_GENERATOR_API_MODEL = "gpt-3.5-turbo";

    @Value("${openai.api.key}")
    private String apiKey;

    public APIUtility(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getTextGeneratorApiModel() {
        return TEXT_GENERATOR_API_MODEL;
    }

    public Mono<String> makeApiCall(String uri, JSONObject requestBody, Function<JSONObject, Mono<String>> responseHandler) {
        System.out.println("Making API call to: " + uri);
        System.out.println("Request body: " + requestBody.toString(4)); // Pretty print JSON

        return webClient.post()
                .uri(uri)
                .header("Authorization", "Bearer " + apiKey) // Include API key in the header
                .contentType(MediaType.APPLICATION_JSON) // Set content type
                .bodyValue(requestBody.toString())
                .retrieve()
                .onStatus(status -> status.value() >= 400, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.err.println("Error Status Code: " + response.statusCode().toString());
                                    System.err.println("Error Response Body: " + errorBody);
                                    return Mono.error(new RuntimeException("API Error: " + errorBody));
                                }))
                .bodyToMono(String.class)
                .flatMap(responseBody -> {
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    return responseHandler.apply(jsonResponse);
                })
                .doOnError(e -> logError("API call to " + uri + " failed", e));
    }

    public void logError(String message, Throwable e) {
        System.err.println(message);
        e.printStackTrace(); // For detailed error logging, consider using a logging framework like SLF4J
    }

}
