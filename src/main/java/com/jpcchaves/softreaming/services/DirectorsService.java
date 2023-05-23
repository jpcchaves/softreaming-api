package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;

import java.util.List;

public interface DirectorsService {
    ApiMessageResponseDto create(DirectorDto directorDto);

    List<DirectorDto> getAll();

    DirectorDto getById(Long id);

    ApiMessageResponseDto update(Long id,
                                 DirectorDto directorDto);
}
