package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRatingDto;

public interface AddMovieRatingUseCase {
    ApiMessageResponseDto addMovieRating(Long id,
                                         MovieRatingDto movieRatingDto);
}
