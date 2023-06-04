package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;

public interface GetMovieRatingUseCase {
    RatingDto getMovieRating(Long movieId);
}
