package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.CategoryRequestDto;

public interface RemoveMovieCategoryUseCase {
    ApiMessageResponseDto removeCategory(Long id,
                                         CategoryRequestDto categoryRequestDto);
}
