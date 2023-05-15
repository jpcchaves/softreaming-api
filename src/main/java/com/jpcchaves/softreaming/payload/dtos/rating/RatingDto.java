package com.jpcchaves.softreaming.payload.dtos.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RatingDto {
    private Long id;
    private Double rating;
    private Integer ratingsAmount;
    private List<LineRatingDto> lineRatings = new ArrayList<>();
    private Date createdAt;
    @JsonIgnore
    private MovieResponseDto movie;

    public RatingDto() {
    }

    public RatingDto(Long id, Double rating, Integer ratingsAmount, List<LineRatingDto> lineRatings, Date createdAt, MovieResponseDto movie) {
        this.id = id;
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
        this.lineRatings = lineRatings;
        this.createdAt = createdAt;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRatingsAmount() {
        return ratingsAmount;
    }

    public void setRatingsAmount(Integer ratingsAmount) {
        this.ratingsAmount = ratingsAmount;
    }

    public List<LineRatingDto> getLineRatings() {
        return lineRatings;
    }

    public void setLineRatings(List<LineRatingDto> lineRatings) {
        this.lineRatings = lineRatings;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public MovieResponseDto getMovie() {
        return movie;
    }

    public void setMovie(MovieResponseDto movie) {
        this.movie = movie;
    }
}
