package com.jpcchaves.softreaming.payload.dtos.movie;

public class MovieByBestRatedDto {
    private MovieResponseMinDto movie;
    private Double rating;
    private Integer ratingsAmount;

    public MovieByBestRatedDto() {
    }

    public MovieByBestRatedDto(MovieResponseMinDto movie,
                               Double rating,
                               Integer ratingsAmount) {
        this.movie = movie;
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
    }

    public MovieResponseMinDto getMovie() {
        return movie;
    }

    public void setMovie(MovieResponseMinDto movie) {
        this.movie = movie;
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
}
