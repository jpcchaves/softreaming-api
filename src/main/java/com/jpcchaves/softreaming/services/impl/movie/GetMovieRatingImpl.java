package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.entities.Rating;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;
import com.jpcchaves.softreaming.services.usecases.movie.GetMovieRatingUseCase;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

@Service
public class GetMovieRatingImpl implements GetMovieRatingUseCase {
    private final MapperUtils mapper;
    private final MovieUtils movieUtils;

    public GetMovieRatingImpl(MapperUtils mapper,
                              MovieUtils movieUtils) {
        this.mapper = mapper;
        this.movieUtils = movieUtils;
    }

    @Override
    public RatingDto getMovieRating(Long movieId) {
        Movie movie = movieUtils.getMovie(movieId);
        Rating movieRating = movie.getRatings();

        return mapper.parseObject(movieRating,
                RatingDto.class);
    }
}
