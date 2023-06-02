package com.jpcchaves.softreaming.controllers;

import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorsIdsDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsIdsDtos;
import com.jpcchaves.softreaming.payload.dtos.movie.*;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;
import com.jpcchaves.softreaming.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@SecurityRequirement(name = "Bearer Authentication")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
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
    public ResponseEntity<ApiMessageResponseDto> create(@RequestBody MovieRequestDto movieDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(movieDto));
    }

    @Operation(summary = "Gets a movies list",
            description = "Gets a list of all movies",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = MovieResponsePaginatedDto.class))
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping
    public ResponseEntity<MovieResponsePaginatedDto<?>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(pageable));
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
    public ResponseEntity<MovieResponseDto> update(@PathVariable("id") Long id,
                                                   @RequestBody MovieRequestDto movieDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(movieDto,
                id));
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

    @Operation(summary = "Filter movies",
            description = "Filters movies by passing a movie name or/and release date. If you dont pass any parameters, a list of movies will be returned",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = MovieResponsePaginatedDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/filter")
    public ResponseEntity<MovieResponsePaginatedDto<?>> filterMoviesByReleaseDate(
            @RequestParam(value = "releaseDate", required = false) String releaseDate,
            @RequestParam(value = "name", required = false) String name,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.filterBy(pageable,
                releaseDate,
                name));
    }

    @Operation(summary = "Adds a movie category",
            description = "Adds a movie category by passing a movie ID and a List with the categories IDs",
            tags = {"Movie - Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ApiMessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/{id}/add-category")
    public ResponseEntity<ApiMessageResponseDto> addCategory(@PathVariable("id") Long id,
                                                             @RequestBody CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.addCategory(id,
                categoryRequestDto));
    }

    @Operation(summary = "Removes a movie category",
            description = "Removes a movie category by passing a movie ID and a List with the categories IDs",
            tags = {"Movie - Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ApiMessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/{id}/remove-category")
    public ResponseEntity<ApiMessageResponseDto> removeCategory(@PathVariable("id") Long id,
                                                                @RequestBody CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.removeCategory(id,
                categoryRequestDto));
    }

    @Operation(summary = "Adds a movie director",
            description = "Adds a movie director by passing a movie ID and a JSON representation of the Director",
            tags = {"Movie - Director"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ApiMessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/{id}/add-director")
    public ResponseEntity<ApiMessageResponseDto> addDirector(@PathVariable("id") Long id,
                                                             @RequestBody DirectorsIdsDtos directorDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.addDirector(id,
                directorDto));
    }

    @Operation(summary = "Removes a movie director",
            description = "Removes a movie director by passing a movie ID and a JSON representation of the Director",
            tags = {"Movie - Director"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ApiMessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/{id}/remove-director")
    public ResponseEntity<ApiMessageResponseDto> removeDirector(@PathVariable("id") Long id,
                                                                @RequestBody DirectorsIdsDtos directorsIdsDtos) {
        return ResponseEntity.status(HttpStatus.OK).body(service.removeDirector(id,
                directorsIdsDtos));
    }

    @Operation(summary = "Adds a movie actor",
            description = "Adds a movie actor by passing a movie ID and a JSON representation of the Actor",
            tags = {"Movie - Actor"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ApiMessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/{id}/add-actor")
    public ResponseEntity<ApiMessageResponseDto> addActor(@PathVariable("id") Long id,
                                                          @RequestBody ActorsIdsDto actorsIds) {
        return ResponseEntity.status(HttpStatus.OK).body(service.addActor(id,
                actorsIds));
    }

    @Operation(summary = "Removes a movie actor",
            description = "Removes a movie actor by passing a movie ID and a JSON representation of the Actor",
            tags = {"Movie - Actor"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ApiMessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/{id}/remove-actor")
    public ResponseEntity<ApiMessageResponseDto> removeActor(@PathVariable("id") Long id,
                                                             @RequestBody ActorsIdsDto actorsIds) {
        return ResponseEntity.status(HttpStatus.OK).body(service.removeActor(id,
                actorsIds));
    }

    @Operation(summary = "Gets the 10 best rated movies",
            description = "Gets a list of the 10 best rated movies",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = MovieByBestRatedDto.class))
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/best-rated")
    public ResponseEntity<List<MovieByBestRatedDto>> filterByBestRated() {
        return ResponseEntity.status(HttpStatus.OK).body(service.sortByBestRating());
    }

    @Operation(summary = "Gets movie list based on the rating parameter",
            description = "By passing a rating as a url parameter, you will get all movies that have the same rating or greater than the specified",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = MovieResponsePaginatedDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/filter/rating")
    public ResponseEntity<MovieResponsePaginatedDto<?>> filterByRatingGreaterThan(@RequestParam(value = "rating") Double rating,
                                                                                  Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.filterByRatingGreaterThan(pageable, rating));
    }

    @Operation(summary = "Gets movie list based on a period of time",
            description = "By passing a start date and end date as a url parameters, you will get all movies with the release date between them",
            tags = {"Movie"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = MovieResponsePaginatedDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/filter/release-date")
    public ResponseEntity<MovieResponsePaginatedDto<?>> filterByReleaseDateBetween(@RequestParam(value = "startDate") String startDate,
                                                                                   @RequestParam(value = "endDate") String endDate,
                                                                                   Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.filterByReleaseDateBetween(startDate, endDate, pageable));
    }

    @Operation(summary = "Adds a movie rating",
            description = "Adds a movie rating by passing a movie ID and a JSON representation of the rating",
            tags = {"Movie - Rating"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ApiMessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/{id}/rating")
    public ResponseEntity<ApiMessageResponseDto> addRating(@PathVariable("id") Long id,
                                                           @Valid @RequestBody MovieRatingDto movieRatingDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.addMovieRating(id,
                movieRatingDto));
    }

    @Operation(summary = "Gets a rating list of a movie",
            description = "Gets a list of all rating of a movie by passing a movie ID",
            tags = {"Movie - Rating"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = RatingDto.class))
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/{id}/rating")
    public ResponseEntity<RatingDto> getMovieRating(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getMovieRating(id));
    }

    @Operation(summary = "Updates a movie rating",
            description = "Updates a movie rating by passing a movie ID and a JSON representation of the rating",
            tags = {"Movie - Rating"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ApiMessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/{id}/rating/{ratingId}")
    public ResponseEntity<ApiMessageResponseDto> updateRating(@PathVariable("id") Long id,
                                                              @PathVariable("ratingId") Long ratingId,
                                                              @Valid @RequestBody MovieRatingDto movieRatingDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateRating(id,
                ratingId,
                movieRatingDto));
    }

    @GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Gets a movies list",
            description = "Gets a list of movies by category by passing a category ID",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = MovieResponsePaginatedDto.class))
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<MovieResponsePaginatedDto<?>> getAllMoviesByCategory(@PathVariable("categoryId") Long categoryId,
                                                                               Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllMoviesByCategory(pageable, categoryId));
    }
}
