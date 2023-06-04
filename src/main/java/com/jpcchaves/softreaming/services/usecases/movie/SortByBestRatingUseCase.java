package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;

import java.util.List;

public interface SortByBestRatingUseCase {
    List<MovieByBestRatedDto> sortByBestRating();
}
