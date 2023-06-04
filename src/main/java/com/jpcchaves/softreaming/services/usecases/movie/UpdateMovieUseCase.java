package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;

public interface UpdateMovieUseCase {
    MovieResponseDto update(MovieRequestDto requestDto,
                            Long id);
}
