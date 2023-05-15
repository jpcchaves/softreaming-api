package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRatingDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseMinDto;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;

import java.util.List;

public interface MovieService {
    MovieResponseDto create(MovieRequestDto requestDto);

    List<MovieResponseMinDto> getAll();

    MovieResponseDto getById(Long id);

    MovieResponseDto update(MovieRequestDto requestDto, Long id);

    void delete(Long id);

    ApiMessageResponseDto updateMovieRating(Long id, MovieRatingDto movieRatingDto);

    RatingDto getMovieRating(Long movieId);
}
