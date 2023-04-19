package com.jpcchaves.softreaming.controllers;

import com.jpcchaves.softreaming.payload.dtos.movie.MovieDto;
import com.jpcchaves.softreaming.services.impl.MovieServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieServiceImpl service;

    public MovieController(MovieServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MovieDto> create(@RequestBody MovieDto movieDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(movieDto));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable("id") Long id, @RequestBody MovieDto movieDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(movieDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
