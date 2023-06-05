package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsIdsDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.RemoveMovieActorUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RemoveMovieActorImpl implements RemoveMovieActorUseCase {
    private final MovieUtils movieUtils;
    private final MovieRepository repository;

    public RemoveMovieActorImpl(MovieUtils movieUtils,
                                MovieRepository repository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
    }

    @Override
    public ApiMessageResponseDto removeActor(Long id,
                                             ActorsIdsDto actorsIds) {
        Movie movie = movieUtils.getMovie(id);

        for (Long actorId : actorsIds.getActorsIds()) {
            movie.getActors().removeIf(actor -> Objects.equals(actor.getId(), actorId));
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Ator(es) removido(s) com sucesso");
    }
}
