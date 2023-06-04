package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsIdsDtos;

public interface RemoveMovieDirectorUseCase {
    ApiMessageResponseDto removeDirector(Long id,
                                         DirectorsIdsDtos directorsIdsDtos);
}
