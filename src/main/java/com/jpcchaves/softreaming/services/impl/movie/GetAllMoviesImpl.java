package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.GetAllMoviesUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtilsMethods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMoviesImpl implements GetAllMoviesUseCase {

    private final MovieRepository repository;
    private final MovieUtilsMethods movieUtils;

    public GetAllMoviesImpl(MovieRepository repository,
                            MovieUtilsMethods movieUtils) {
        this.repository = repository;
        this.movieUtils = movieUtils;
    }

    @Override
    public MovieResponsePaginatedDto<?> getAll(Pageable pageable) {
        Page<Movie> moviesPage = repository.findAll(pageable);
        List<MovieByBestRatedDto> movieByBestRatedDto = movieUtils.buildMovieListResponse(moviesPage.getContent());

        return movieUtils.buildMovieResponsePaginatedDto(movieByBestRatedDto, moviesPage);
    }
}
