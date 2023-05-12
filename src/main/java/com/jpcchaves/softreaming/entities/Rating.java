package com.jpcchaves.softreaming.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ratigs")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    private Integer ratingsAmount;

    @OneToOne(mappedBy = "ratings")
    private Movie movie;

    public Rating() {
    }

    public Rating(Long id, Double rating, Integer ratingsAmount, Movie movie) {
        this.id = id;
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
