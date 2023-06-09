package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsPaginatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseMinDto;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface DirectorsService {
    DirectorsPaginatedDto getAll(Pageable pageable);

    ApiMessageResponseDto create(DirectorDto directorDto);

    DirectorDto getById(Long id);

    ApiMessageResponseDto update(Long id,
                                 DirectorDto directorDto);

    Set<MovieResponseMinDto> getAllMoviesByDirector(Long id);
}
