package com.sofka.movies.usecases;

import com.sofka.movies.collection.Movie;
import com.sofka.movies.repository.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteMovieUseCase {

    private final MovieRepository repository;

    public DeleteMovieUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    private Mono<Movie> findMovie(String id){
        return repository.findById(id)
                .switchIfEmpty(Mono.error(() -> new Exception("Movie not found")));
    }

    public Mono<Void> deleteMovie(String id){
        return findMovie(id)
                .flatMap(movie -> repository.deleteById(movie.getId()));
    }
}
