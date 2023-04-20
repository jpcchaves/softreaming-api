package com.jpcchaves.softreaming.services;

import java.util.List;

public interface ICrudService<DtoRequest, DtoResponse> {
    DtoResponse create(DtoRequest requestDto);

    List<DtoResponse> getAll();

    DtoResponse getById(Long id);

    DtoResponse update(DtoRequest requestDto, Long id);

    void delete(Long id);
}
