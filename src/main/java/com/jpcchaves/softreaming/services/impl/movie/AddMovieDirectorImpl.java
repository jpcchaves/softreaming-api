package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Director;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsIdsDtos;
import com.jpcchaves.softreaming.repositories.DirectorRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.AddMovieDirectorUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddMovieDirectorImpl implements AddMovieDirectorUseCase {
    private final MovieUtils movieUtils;
    private final MovieRepository repository;
    private final DirectorRepository directorRepository;

    public AddMovieDirectorImpl(MovieUtils movieUtils,
                                MovieRepository repository,
                                DirectorRepository directorRepository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
        this.directorRepository = directorRepository;
    }

    @Override
    public ApiMessageResponseDto addDirector(Long id,
                                             DirectorsIdsDtos directorsIdsDtos) {
        Movie movie = movieUtils.getMovie(id);

        List<Director> directors = directorRepository.findAllById(directorsIdsDtos.getDirectorsIds());

        for (Director director : directors) {
            movie.getDirectors().add(director);
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Diretor(es) adicionado(s) com sucesso");
    }
}
