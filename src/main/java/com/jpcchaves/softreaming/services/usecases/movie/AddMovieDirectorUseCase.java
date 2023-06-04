package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsIdsDtos;

public interface AddMovieDirectorUseCase {
    ApiMessageResponseDto addDirector(Long id,
                                      DirectorsIdsDtos directorsIdsDtos);
}
