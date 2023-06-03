package com.jpcchaves.softreaming.services.usecases.actor;

import com.jpcchaves.softreaming.payload.dtos.actor.ActorDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsPaginatedDto;
import org.springframework.data.domain.Pageable;


public interface ActorsService {
    ActorDto create(ActorDto actorDto);

    ActorsPaginatedDto getAll(Pageable pageable);

    ActorDto getById(Long id);

    ActorDto update(ActorDto actorDto,
                    Long id);

    void delete(Long id);
}
