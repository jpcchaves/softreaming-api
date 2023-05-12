package com.jpcchaves.softreaming.payload.dtos.movie;

public class MovieRatingDto {
    private Double rating;

    public MovieRatingDto() {
    }

    public MovieRatingDto(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
