package com.sofka.movies.mapper;

import com.sofka.movies.DTO.MovieDTO;
import com.sofka.movies.collection.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;

@Component
@EnableWebFlux
public class MovieMapper {

    private final ModelMapper mapper;

    public MovieMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public MovieDTO toMovieDTO(Movie movie){
        return mapper.map(movie, MovieDTO.class);
    }

    public Movie toMovie(MovieDTO movieDTO){
        return mapper.map(movieDTO, Movie.class);
    }
}
