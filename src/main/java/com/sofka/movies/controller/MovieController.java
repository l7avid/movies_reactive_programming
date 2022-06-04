package com.sofka.movies.controller;

import com.sofka.movies.DTO.MovieDTO;
import com.sofka.movies.usecases.GetMoviesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public RouterFunction<ServerResponse> allMovies(GetMoviesUseCase moviesUseCase){
        return route(GET("/movies"), request -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(moviesUseCase.getMovies(), MovieDTO.class)));
    }
}
