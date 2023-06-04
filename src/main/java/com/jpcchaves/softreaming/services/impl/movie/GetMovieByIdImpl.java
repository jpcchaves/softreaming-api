package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;
import com.jpcchaves.softreaming.services.usecases.movie.GetMovieByIdUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtilsMethods;
import org.springframework.stereotype.Service;

@Service
public class GetMovieByIdImpl implements GetMovieByIdUseCase {

    private final MovieUtilsMethods movieUtils;

    public GetMovieByIdImpl(MovieUtilsMethods movieUtils) {
        this.movieUtils = movieUtils;
    }

    @Override
    public MovieResponseDto getById(Long id) {
        Movie movie = movieUtils.getMovie(id);

        return movieUtils.buildMovieResponseDto(movie);
    }
}
