package com.jpcchaves.softreaming.controllers;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsPaginatedDto;
import com.jpcchaves.softreaming.services.DirectorsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/directors")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
public class DirectorsController {

    private final DirectorsService service;

    public DirectorsController(DirectorsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<DirectorsPaginatedDto> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ApiMessageResponseDto> create(@RequestBody DirectorDto directorDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(directorDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiMessageResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody DirectorDto directorDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, directorDto));
    }
}
