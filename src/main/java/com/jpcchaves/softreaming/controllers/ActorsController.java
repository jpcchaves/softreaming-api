package com.jpcchaves.softreaming.controllers;

import com.jpcchaves.softreaming.payload.dtos.actor.ActorDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsPaginatedDto;
import com.jpcchaves.softreaming.services.ActorsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorsController {
    private final ActorsService service;

    public ActorsController(ActorsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ActorDto> create(@RequestBody ActorDto actorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(actorDto));
    }

    @GetMapping
    public ResponseEntity<ActorsPaginatedDto> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDto> update(@PathVariable("id") Long id,
                                           @RequestBody ActorDto actorDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(actorDto, id));
    }
}
