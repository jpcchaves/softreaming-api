package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import org.springframework.data.domain.Pageable;

public interface GetAllMoviesUseCase {
    MovieResponsePaginatedDto<?> getAll(Pageable pageable);
}
