package com.jpcchaves.softreaming.payload.dtos.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MovieResponseDto {
    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private String duration;
    private String releaseDate;
    private String movieUrl;
    private String posterUrl;
    @JsonIgnore
    private Date createdAt;
    private Set<String> categories = new HashSet<>();
    private Set<DirectorDto> directors = new HashSet<>();
    private Set<ActorDto> actors = new HashSet<>();
    private RatingDto ratings;

    public MovieResponseDto() {
    }

    public MovieResponseDto(Long id,
                            String name,
                            String shortDescription,
                            String longDescription,
                            String duration,
                            String releaseDate,
                            String movieUrl,
                            String posterUrl,
                            Date createdAt,
                            Set<String> categories,
                            Set<DirectorDto> directors,
                            Set<ActorDto> actors,
                            RatingDto ratings) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.movieUrl = movieUrl;
        this.posterUrl = posterUrl;
        this.createdAt = createdAt;
        this.categories = categories;
        this.directors = directors;
        this.actors = actors;
        this.ratings = ratings;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public Set<ActorDto> getActors() {
        return actors;
    }

    public void setActors(Set<ActorDto> actors) {
        this.actors = actors;
    }

    public RatingDto getRatings() {
        return ratings;
    }

    public void setRatings(RatingDto ratings) {
        this.ratings = ratings;
    }

    public Set<DirectorDto> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<DirectorDto> directors) {
        this.directors = directors;
    }
}
