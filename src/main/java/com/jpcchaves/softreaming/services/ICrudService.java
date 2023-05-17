package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseMinDto;

import java.util.List;
import java.util.Set;

public interface ICrudService<DtoRequest, DtoResponse> {
    DtoResponse create(DtoRequest requestDto);

    List<DtoResponse> getAll();

    DtoResponse getById(Long id);

    DtoResponse update(DtoRequest requestDto, Long id);

    void delete(Long id);

    Set<MovieResponseMinDto> getAllMoviesByCategoryId(Long categoryId);
}
