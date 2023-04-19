package com.jpcchaves.softreaming.services;

import java.util.List;

public interface ICrudService<DTO> {
    DTO create (DTO requestDto);
    List<DTO> getAll();
    DTO getById (Long id);
    DTO update (DTO requestDto, Long id);
    void delete (Long id);
}
