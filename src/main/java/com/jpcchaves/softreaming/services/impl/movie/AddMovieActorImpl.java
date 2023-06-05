package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Actor;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsIdsDto;
import com.jpcchaves.softreaming.repositories.ActorRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.AddMovieActorUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddMovieActorImpl implements AddMovieActorUseCase {

    private final MovieUtils movieUtils;
    private final MovieRepository repository;
    private final ActorRepository actorRepository;

    public AddMovieActorImpl(MovieUtils movieUtils,
                             MovieRepository repository,
                             ActorRepository actorRepository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
        this.actorRepository = actorRepository;
    }

    @Override
    public ApiMessageResponseDto addActor(Long id,
                                          ActorsIdsDto actorsIds) {
        Movie movie = movieUtils.getMovie(id);

        List<Actor> actors = actorRepository.findAllById(actorsIds.getActorsIds());

        for (Actor actor : actors) {
            movie.getActors().add(actor);
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Ator(es) adicionado(s) com sucesso");
    }
}
