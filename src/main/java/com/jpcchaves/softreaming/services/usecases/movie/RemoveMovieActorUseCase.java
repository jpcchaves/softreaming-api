package com.jpcchaves.softreaming.services.usecases.movie;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsIdsDto;

public interface RemoveMovieActorUseCase {
    ApiMessageResponseDto removeActor(Long id,
                                      ActorsIdsDto actorsIds);
}
