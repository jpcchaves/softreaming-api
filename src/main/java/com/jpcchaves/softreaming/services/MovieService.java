package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRatingDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;

import java.util.List;

public interface MovieService {
    MovieResponseDto create(MovieRequestDto requestDto);

    List<MovieResponseDto> getAll();

    MovieResponseDto getById(Long id);

    MovieResponseDto update(MovieRequestDto requestDto, Long id);

    void delete(Long id);

    ApiMessageResponseDto updateMovieRating(Long id, MovieRatingDto movieRatingDto);
}
