package com.jpcchaves.softreaming.payload.dtos.category;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpcchaves.softreaming.entities.Movie;

import java.util.Set;

public class CategoryDto {
    private Long id;
    private String category;
    @JsonIgnore
    private Set<Movie> movies;

    public CategoryDto() {
    }

    public CategoryDto(Long id,
                       String category,
                       Set<Movie> movies) {
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

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
