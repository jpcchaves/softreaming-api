package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsIdsDtos;
import com.jpcchaves.softreaming.payload.dtos.movie.*;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    ApiMessageResponseDto create(MovieRequestDto requestDto);

    MovieResponsePaginatedDto<?> getAll(Pageable pageable);

    MovieResponseDto getById(Long id);

    MovieResponseDto update(MovieRequestDto requestDto,
                            Long id);

    void delete(Long id);

    ApiMessageResponseDto addMovieRating(Long id,
                                         MovieRatingDto movieRatingDto);

    ApiMessageResponseDto updateRating(Long id,
                                       Long ratingId,
                                       MovieRatingDto movieRatingDto);

    RatingDto getMovieRating(Long movieId);

    MovieResponsePaginatedDto<?> filterBy(Pageable pageable,
                                          String releaseDate,
                                          String name);

    List<MovieByBestRatedDto> sortByBestRating();

    ApiMessageResponseDto addCategory(Long id,
                                      CategoryRequestDto categoryRequestDto);


    ApiMessageResponseDto removeCategory(Long id,
                                         CategoryRequestDto categoryRequestDto);

    ApiMessageResponseDto addDirector(Long id,
                                      List<DirectorDto> directorDtos);

    ApiMessageResponseDto removeDirector(Long id,
                                         DirectorsIdsDtos directorsIdsDtos);

    MovieResponsePaginatedDto<?> filterByRatingGreaterThan(Pageable pageable,
                                                           Double rating);

    MovieResponsePaginatedDto<?> filterByReleaseDateBetween(String startDate,
                                                            String endDate,
                                                            Pageable pageable);
}
