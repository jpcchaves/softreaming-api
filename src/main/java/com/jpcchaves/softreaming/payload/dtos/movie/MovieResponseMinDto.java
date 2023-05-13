package com.jpcchaves.softreaming.payload.dtos.movie;

public class MovieResponseMinDto {
    private Long id;
    private String name;
    private String shortDescription;
    private String duration;
    private String releaseDate;
    private String posterUrl;

    public MovieResponseMinDto() {
    }

    public MovieResponseMinDto(Long id, String name, String shortDescription, String duration, String releaseDate, String posterUrl) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}

