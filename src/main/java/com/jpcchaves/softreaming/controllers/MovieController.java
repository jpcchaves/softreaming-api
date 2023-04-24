package com.jpcchaves.softreaming.controllers;

import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;
import com.jpcchaves.softreaming.services.impl.MovieServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@SecurityRequirement(name = "Bearer Authentication")
public class MovieController {

    private final MovieServiceImpl service;

    public MovieController(MovieServiceImpl service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new movie",
            description = "Creates a new movie by passing a JSON representation of the movie",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = MovieResponseDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping
    public ResponseEntity<MovieResponseDto> create(@RequestBody MovieRequestDto movieDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(movieDto));
    }

    @Operation(summary = "Gets a movies list",
            description = "Gets a list of all movies",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = MovieResponseDto.class))
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @Operation(summary = "Gets a movie by ID",
            description = "Gets a movie by passing a movie ID",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = MovieResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates a movie",
            description = "Updates a movie by passing a movie ID and it's JSON representation",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = MovieResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> update(@PathVariable("id") Long id, @RequestBody MovieRequestDto movieDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(movieDto, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletes a movie",
            description = "Deletes a movie by passing a movie ID",
            tags = {"Movie"},
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
