package com.jpcchaves.softreaming.services.impl.actor;

import com.jpcchaves.softreaming.entities.Actor;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorDto;
import com.jpcchaves.softreaming.repositories.ActorRepository;
import com.jpcchaves.softreaming.services.usecases.actor.CreateActorUseCase;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.stereotype.Service;

@Service
public class CreateActorsUseCaseImpl implements CreateActorUseCase {
    private final ActorRepository repository;
    private final MapperUtils mapper;

    public CreateActorsUseCaseImpl(ActorRepository actorRepository,
                                   MapperUtils mapper) {
        this.repository = actorRepository;
        this.mapper = mapper;
    }

    @Override
    public ActorDto create(ActorDto actorDto) {
        Actor actor = mapper.parseObject(actorDto, Actor.class);
        Actor actorSaved = repository.save(actor);
        return mapper.parseObject(actorSaved, ActorDto.class);
    }
}
