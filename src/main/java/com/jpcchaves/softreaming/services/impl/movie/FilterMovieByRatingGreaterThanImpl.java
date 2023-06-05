package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.FilterMovieByRatingGreaterThanUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterMovieByRatingGreaterThanImpl implements FilterMovieByRatingGreaterThanUseCase {

    private final MovieUtils movieUtils;
    private final MovieRepository repository;

    public FilterMovieByRatingGreaterThanImpl(MovieUtils movieUtils,
                                              MovieRepository repository) {
        this.movieUtils = movieUtils;
        this.repository = repository;
    }

    @Override
    public MovieResponsePaginatedDto<?> filterByRatingGreaterThan(Pageable pageable,
                                                                  Double rating) {
        Page<Movie> movies = repository.findByRatings_RatingGreaterThanEqual(pageable, rating);
        List<MovieByBestRatedDto> movieByBestRatedDto = movieUtils.buildMovieListResponse(movies.getContent());

        return movieUtils.buildMovieResponsePaginatedDto(movieByBestRatedDto, movies);
    }
}
