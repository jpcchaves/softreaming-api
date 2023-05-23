package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsPaginatedDto;
import org.springframework.data.domain.Pageable;

public interface DirectorsService {
    DirectorsPaginatedDto getAll(Pageable pageable);

    ApiMessageResponseDto create(DirectorDto directorDto);

    DirectorDto getById(Long id);

    ApiMessageResponseDto update(Long id,
                                 DirectorDto directorDto);
}
