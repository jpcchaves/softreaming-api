package com.jpcchaves.softreaming.payload.dtos.movie;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieRequestDto {
    private Long id;
    private List<Long> categoriesIds;
    private Set<Long> directorsIds = new HashSet<>();
    private Set<Long> actorsIds = new HashSet<>();
    private String name;
    private String shortDescription;
    private String longDescription;
    private String duration;
    private String releaseDate;
    private String movieUrl;
    private String posterUrl;
    private Date createdAt;

    public MovieRequestDto() {
    }

    public MovieRequestDto(Long id,
                           List<Long> categoriesIds,
                           Set<Long> directorsIds,
                           Set<Long> actorsIds,
                           String name,
                           String shortDescription,
                           String longDescription,
                           String duration,
                           String releaseDate,
                           String movieUrl,
                           String posterUrl,
                           Date createdAt) {
        this.id = id;
        this.categoriesIds = categoriesIds;
        this.directorsIds = directorsIds;
        this.actorsIds = actorsIds;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.movieUrl = movieUrl;
        this.posterUrl = posterUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(List<Long> categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public Set<Long> getDirectorsIds() {
        return directorsIds;
    }

    public void setDirectorsIds(Set<Long> directorsIds) {
        this.directorsIds = directorsIds;
    }

    public Set<Long> getActorsIds() {
        return actorsIds;
    }

    public void setActorsIds(Set<Long> actorsIds) {
        this.actorsIds = actorsIds;
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
}
