package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.FilterMovieByReleaseDateBetweenUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterMovieByReleaseDateBetweenImpl implements FilterMovieByReleaseDateBetweenUseCase {
    private final MovieRepository repository;
    private final MovieUtils movieUtils;

    public FilterMovieByReleaseDateBetweenImpl(MovieRepository repository,
                                               MovieUtils movieUtils) {
        this.repository = repository;
        this.movieUtils = movieUtils;
    }

    @Override
    public MovieResponsePaginatedDto<?> filterByReleaseDateBetween(String startDate,
                                                                   String endDate,
                                                                   Pageable pageable) {
        Page<Movie> moviePage = repository.findByReleaseDateBetween(startDate, endDate, pageable);
        List<MovieByBestRatedDto> movieByBestRatedDto = movieUtils.buildMovieListResponse(moviePage.getContent());

        return movieUtils.buildMovieResponsePaginatedDto(movieByBestRatedDto, moviePage);
    }
}

