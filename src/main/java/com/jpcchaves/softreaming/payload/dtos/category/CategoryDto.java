package com.jpcchaves.softreaming.payload.dtos.category;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;

import java.util.Set;

public class CategoryDto {
    private Long id;
    private String category;
    @JsonIgnore
    private Set<MovieResponseDto> movies;

    public CategoryDto() {
    }

    public CategoryDto(Long id,
                       String category,
                       Set<MovieResponseDto> movies) {
        this.id = id;
        this.category = category;
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<MovieResponseDto> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieResponseDto> movies) {
        this.movies = movies;
    }
}
