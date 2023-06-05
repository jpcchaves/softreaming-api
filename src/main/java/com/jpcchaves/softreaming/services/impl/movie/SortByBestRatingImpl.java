package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.SortByBestRatingUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortByBestRatingImpl implements SortByBestRatingUseCase {

    private final MovieRepository repository;
    private final MovieUtils movieUtils;

    public SortByBestRatingImpl(MovieRepository repository,
                                MovieUtils movieUtils) {
        this.repository = repository;
        this.movieUtils = movieUtils;
    }

    @Override
    public List<MovieByBestRatedDto> sortByBestRating() {
        List<Movie> movieList = repository.findTop10ByOrderByRatings_RatingDesc();

        return movieUtils.buildMovieListResponse(movieList);
    }
}
