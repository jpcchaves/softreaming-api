package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import org.springframework.data.domain.Pageable;

public interface FilterMovieByReleaseDateBetweenUseCase {
    MovieResponsePaginatedDto<?> filterByReleaseDateBetween(String startDate,
                                                            String endDate,
                                                            Pageable pageable);
}
