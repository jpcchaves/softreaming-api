package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsIdsDtos;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.RemoveMovieDirectorUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RemoveMovieDirectorImpl implements RemoveMovieDirectorUseCase {
    private final MovieUtils movieUtils;
    private final MovieRepository repository;

    public RemoveMovieDirectorImpl(MovieUtils movieUtils,
                                   MovieRepository repository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
    }

    @Override
    public ApiMessageResponseDto removeDirector(Long id,
                                                DirectorsIdsDtos directorsIdsDtos) {
        Movie movie = movieUtils.getMovie(id);

        for (Long directorId : directorsIdsDtos.getDirectorsIds()) {
            movie.getDirectors().removeIf(director -> Objects.equals(director.getId(), directorId));
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Diretor(es) removido(s) com sucesso");
    }
}
