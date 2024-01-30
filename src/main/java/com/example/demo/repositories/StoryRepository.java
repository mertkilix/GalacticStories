package com.example.demo.repositories;

import com.example.demo.models.Story;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StoryRepository extends ReactiveCrudRepository<Story, Long> {
    // Additional query methods if needed
}
