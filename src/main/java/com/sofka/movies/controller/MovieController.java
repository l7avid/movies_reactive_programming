package com.sofka.movies.controller;

import com.sofka.movies.DTO.MovieDTO;
import com.sofka.movies.usecases.GetMoviesUseCase;
import com.sofka.movies.usecases.PostMovieUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MovieController {

    @Bean
    public RouterFunction<ServerResponse> allMovies(GetMoviesUseCase getUseCase){
        return route(GET("/movies"), request -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(getUseCase.getMovies(), MovieDTO.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> createMovie(PostMovieUseCase postUseCase){
        return route(POST("/create/movie").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(MovieDTO.class)
                        //.flatMap(movieDTO -> postUseCase.postMovie(movieDTO)) same as below but using arrow function
                        .flatMap(postUseCase::postMovie)
                        .flatMap(movieDTO -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(movieDTO))
                        .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }
}
