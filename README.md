# Galactic Stories Backend

## Overview
Galactic Stories is a backend service built with Java Spring Boot, designed to support a web and mobile application that allows users to create interactive stories. This service interacts with the OpenAI's ChatGPT API to generate story text, and offers functionalities for text-to-speech conversion and image generation related to the stories.

- [Click to go Front End repository](https://github.com/mertkilix/GalacticStoriesUI)

 <img src="https://github.com/mertkilix/GalacticStoriesUI/assets/71731982/79a78e04-572d-4224-8ee1-12867c383923" width="100">

<img src="https://github.com/mertkilix/GalacticStoriesUI/assets/71731982/a487efb7-f4ae-4ebf-a133-e9e9be913148" width="100">

<img src="https://github.com/mertkilix/GalacticStoriesUI/assets/71731982/dbf71a78-45b6-4776-8f70-b7d19c532773" width="100">

<img src="https://github.com/mertkilix/GalacticStoriesUI/assets/71731982/689c375d-b4eb-4cca-9439-727956ffdda5" width="100">


## Features
- **Story Generation**: Leverages OpenAI's ChatGPT API to create engaging and dynamic story text based on user input.
- **Text-to-Speech**: Converts the generated story text into speech using a custom voice, enhancing the user experience.
- **Image Generation**: Utilizes advanced algorithms to generate images that are relevant to the story content.
- **WebSockets**: Provides real-time communication channels for a responsive user experience.
- **RESTful API**: Offers a well-defined interface for front-end applications to interact with the backend.
- **PostgreSQL Integration**: Uses R2DBC with PostgreSQL for reactive data handling and persistence.
- **Spring Boot WebFlux**: For building asynchronous, non-blocking API.
- **Swagger UI**: Documentation of API endpoints for easy testing and integration.

## Technologies Used
- Java Spring Boot
- PostgreSQL
- Spring Data R2DBC
- Spring Boot WebFlux
- OpenAI API
- JSON
- Swagger UI

## Running the Application
1. **Clone the Repository**: `git clone (https://github.com/mertkilix/GalacticStories.git)`
2. **Set up PostgreSQL**: Ensure PostgreSQL is installed and configured.
3. **Configure Application Properties**: Set your database and OpenAI API credentials in `application.properties`.
4. **Build the Project**: Run `mvn clean install` to build the project.
5. **Run the Application**: Execute `java -jar target/GalacticStories.jar`.

## API Documentation
Access the Swagger UI for the API documentation and testing at [(https://www.galacticstories.com/app/swagger-ui/index.html)](https://www.galacticstories.com/app/swagger-ui/index.html)`.


