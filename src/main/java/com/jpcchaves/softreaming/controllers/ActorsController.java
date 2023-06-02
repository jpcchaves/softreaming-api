package com.jpcchaves.softreaming.controllers;

import com.jpcchaves.softreaming.payload.dtos.actor.ActorDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsPaginatedDto;
import com.jpcchaves.softreaming.services.ActorsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorsController {
    private final ActorsService service;

    public ActorsController(ActorsService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Creates a new actor",
            description = "Creates a new actor by passing a JSON representation of the actor",
            tags = {"Actor"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ActorDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ActorDto> create(@RequestBody ActorDto actorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(actorDto));
    }

    @GetMapping
    @Operation(summary = "Gets an actors list",
            description = "Gets a list of all actors",
            tags = {"Actor"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ActorsPaginatedDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ActorsPaginatedDto> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets an actor by ID",
            description = "Gets an actor by passing a category ID",
            tags = {"Actor"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ActorDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ActorDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates an actor",
            description = "Updates an actor by passing an actor ID and it's JSON representation",
            tags = {"Actor"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ActorDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ActorDto> update(@PathVariable("id") Long id,
                                           @RequestBody ActorDto actorDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(actorDto, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletes an actor",
            description = "Deletes an actor by passing an actor ID",
            tags = {"Actor"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
