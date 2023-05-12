package com.jpcchaves.softreaming.payload.dtos.movie;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public class MovieRatingDto {
    @DecimalMin(value = "0.1", message = "A avaliação deve ser maior do que zero")
    @DecimalMax(value = "5.0", message = "A avaliação deve ser menor do que cinco")
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
