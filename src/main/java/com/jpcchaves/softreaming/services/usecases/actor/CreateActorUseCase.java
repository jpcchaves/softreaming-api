package com.jpcchaves.softreaming.services.usecases.actor;

import com.jpcchaves.softreaming.payload.dtos.actor.ActorDto;

public interface CreateActorUseCase {
    ActorDto create(ActorDto actorDto);
}
