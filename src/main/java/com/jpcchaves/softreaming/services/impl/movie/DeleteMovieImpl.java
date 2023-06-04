package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.DeleteMovieUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

@Service
public class DeleteMovieImpl implements DeleteMovieUseCase {

    private final MovieUtils movieUtils;
    private final MovieRepository repository;

    public DeleteMovieImpl(MovieUtils movieUtils,
                           MovieRepository repository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
    }

    @Override
    public void delete(Long id) {
        movieUtils.getMovie(id);
        repository.deleteById(id);
    }
}
