package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;

public interface CreateMovieUseCase {
    ApiMessageResponseDto create(MovieRequestDto requestDto);
}
