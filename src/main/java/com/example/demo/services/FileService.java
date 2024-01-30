package com.example.demo.services;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.*;

@Service
public class FileService {

    private final String imageDirectoryPath = "/var/www/html/storyimages/";
    private final String audioDirectoryPath = "/var/www/html/audios/";

    public FileService() {
        initializeDirectories();
    }

    private void initializeDirectories() {
        try {
            Files.createDirectories(Paths.get(imageDirectoryPath));
            Files.createDirectories(Paths.get(audioDirectoryPath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create necessary directories", e);
        }
    }

    public Mono<String> saveFile(Path path, Mono<DataBuffer> fileContentMono, String publicUrlPrefix) {
        return fileContentMono.flatMap(dataBuffer -> {
            try (WritableByteChannel channel = Channels.newChannel(Files.newOutputStream(path, StandardOpenOption.CREATE_NEW))) {
                channel.write(dataBuffer.asByteBuffer());
                return Mono.just(publicUrlPrefix + path.getFileName().toString());
            } catch (IOException e) {
                return Mono.error(new RuntimeException("Failed to write file: " + path, e));
            }
        });
    }

    public String generateImagePath(String filename) {
        return imageDirectoryPath + filename;
    }

    public String generateAudioPath(String filename) {
        return audioDirectoryPath + filename;
    }
}
