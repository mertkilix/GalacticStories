package com.example.demo.controllers;


public class StoryResponse {
    private String story;
    private String audioUrl;
    private String imageUrl;


    private String errorMessage;

    public StoryResponse(String story, String audioUrl, String imageUrl) {
        this.story = story;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
    }

    public StoryResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        // Initialize other fields as appropriate
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
