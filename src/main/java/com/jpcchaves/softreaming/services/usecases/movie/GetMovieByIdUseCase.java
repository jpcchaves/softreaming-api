package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;

public interface GetMovieByIdUseCase {
    MovieResponseDto getById(Long id);
}
