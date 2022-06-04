package com.sofka.movies.usecases;

import com.sofka.movies.DTO.MovieDTO;
import com.sofka.movies.mapper.MovieMapper;
import com.sofka.movies.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PostMovieUseCase {

    private final MovieRepository repository;
    private final MovieMapper mapper;

    public PostMovieUseCase(MovieRepository repository, MovieMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    //Method to validate existing attributes. Non null attributes -> return true, null attributes return false
    private boolean validateAttributes(MovieDTO movieDTO){
        return movieDTO.getMovieName() != null && movieDTO.getSynopsis() != null;
    }

    private Mono<MovieDTO> validateMovieDTO(MovieDTO movieDTO){
        //return Mono.just(movieDTO)
        // .filter(movieDTO1 -> this.validateAttributes(movieDTO1)); same as below but using arrow function
        return Mono.just(movieDTO)
                .filter(this::validateAttributes)
                .switchIfEmpty(Mono.error(() -> new Exception("Missing attributes")));
    }

    public Mono<MovieDTO> postMovieDTO(MovieDTO movieDTO){
        return validateMovieDTO(movieDTO)
                .flatMap(movieDTO1 -> repository.save(mapper.toMovie(movieDTO1)))
                //.map(movie -> mapper.toMovieDTO(movie)); same as below but using arrow function
                .map(mapper::toMovieDTO);
    }
}
