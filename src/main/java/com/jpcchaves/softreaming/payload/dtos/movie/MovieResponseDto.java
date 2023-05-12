package com.jpcchaves.softreaming.payload.dtos.movie;

import com.jpcchaves.softreaming.entities.Category;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MovieResponseDto {
    private Long id;
    private String name;
    private String description;
    private String duration;
    private String releaseDate;
    private String movieUrl;
    private String posterUrl;
    private Date createdAt;
    private Double rating = 0.0;
    private Set<Category> categories = new HashSet<>();

    public MovieResponseDto() {
    }

    public MovieResponseDto(Long id,
                            String name,
                            String description,
                            String duration,
                            String releaseDate,
                            String movieUrl,
                            String posterUrl,
                            Date createdAt,
                            Double rating,
                            Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.movieUrl = movieUrl;
        this.posterUrl = posterUrl;
        this.createdAt = createdAt;
        this.rating = rating;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}