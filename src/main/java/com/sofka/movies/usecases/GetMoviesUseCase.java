package com.sofka.movies.usecases;

import com.sofka.movies.DTO.MovieDTO;
import com.sofka.movies.mapper.MovieMapper;
import com.sofka.movies.repository.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetMoviesUseCase {

    private final MovieRepository repository;
    private final MovieMapper mapper;

    public GetMoviesUseCase(MovieRepository repository, MovieMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Flux<MovieDTO> getMovies(){
        // return repository.findAll().map(mapper::toMovieDTO) -> same as below but using method reference
        return repository.findAll().map(movie -> mapper.toMovieDTO(movie));
    }
}
