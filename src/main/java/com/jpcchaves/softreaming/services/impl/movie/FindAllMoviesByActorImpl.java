package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Actor;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import com.jpcchaves.softreaming.repositories.ActorRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.FindAllMoviesByActorUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllMoviesByActorImpl implements FindAllMoviesByActorUseCase {
    private final MovieUtils movieUtils;
    private final MovieRepository repository;
    private final ActorRepository actorRepository;

    public FindAllMoviesByActorImpl(MovieUtils movieUtils,
                                    MovieRepository repository,
                                    ActorRepository actorRepository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
        this.actorRepository = actorRepository;
    }

    @Override
    public MovieResponsePaginatedDto<?> findAllMoviesByActor(Pageable pageable,
                                                             Long actorId) {
        Actor actor = actorRepository
                .findById(actorId)
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada com o ID informado: " + actorId));

        Page<Movie> movies = repository.findAllByActors(pageable, actor);
        List<MovieByBestRatedDto> movieByBestRatedDto = movieUtils.buildMovieListResponse(movies.getContent());

        return movieUtils.buildMovieResponsePaginatedDto(movieByBestRatedDto, movies);
    }
}
