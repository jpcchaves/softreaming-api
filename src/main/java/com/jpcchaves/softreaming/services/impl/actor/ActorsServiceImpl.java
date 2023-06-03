package com.jpcchaves.softreaming.services.impl.actor;

import com.jpcchaves.softreaming.entities.Actor;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsPaginatedDto;
import com.jpcchaves.softreaming.repositories.ActorRepository;
import com.jpcchaves.softreaming.services.usecases.actor.ActorsService;
import com.jpcchaves.softreaming.services.usecases.actor.CreateActorUseCase;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ActorsServiceImpl implements ActorsService {
    private final ActorRepository repository;
    private final MapperUtils mapper;
    private final CreateActorUseCase createActorUseCase;

    public ActorsServiceImpl(ActorRepository repository,
                             MapperUtils mapper,
                             CreateActorUseCase createActorUseCase) {
        this.repository = repository;
        this.mapper = mapper;
        this.createActorUseCase = createActorUseCase;
    }

    @Override
    public ActorDto create(ActorDto actorDto) {
        return createActorUseCase.create(actorDto);
    }

    @Override
    public ActorsPaginatedDto getAll(Pageable pageable) {
        Page<Actor> actorPage = repository.findAll(pageable);
        return buildActorsPaginatedDto(actorPage);
    }

    @Override
    public ActorDto getById(Long id) {
        Actor actor = getActor(id);
        return mapper.parseObject(actor, ActorDto.class);
    }

    @Override
    public ActorDto update(ActorDto actorDto,
                           Long id) {
        Actor actor = getActor(id);
        actor.setFirstName(actorDto.getFirstName());
        actor.setLastName(actorDto.getLastName());
        Actor updatedActor = repository.save(actor);
        return mapper.parseObject(updatedActor, ActorDto.class);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    private ActorsPaginatedDto buildActorsPaginatedDto(Page<Actor> actorPage) {
        ActorsPaginatedDto actorsPaginatedDto = new ActorsPaginatedDto();
        actorsPaginatedDto.setContent(mapper.parseListObjects(actorPage.getContent(), ActorDto.class));
        actorsPaginatedDto.setPageNo(actorPage.getNumber());
        actorsPaginatedDto.setPageSize(actorPage.getSize());
        actorsPaginatedDto.setTotalElements(actorPage.getTotalElements());
        actorsPaginatedDto.setLast(actorPage.isLast());
        actorsPaginatedDto.setTotalPages(actorPage.getTotalPages());
        return actorsPaginatedDto;
    }

    private Actor getActor(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ator não encontrado com o id: " + id));
    }
}
