package com.jpcchaves.softreaming.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private Double rating;
    private Integer ratingsAmount;
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    @CreatedDate
    private Date createdAt;

    @OneToOne(mappedBy = "ratings")
    @JsonIgnore
    private Movie movie;

    public Rating() {
    }

    public Rating(Double rating,
                  Integer ratingsAmount,
                  Movie movie,
                  Long userId
    ) {
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
        this.movie = movie;
        this.userId = userId;
    }

    public Rating(Long id, Double rating, Integer ratingsAmount, Movie movie) {
        this.id = id;
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
        this.movie = movie;
    }

    public Rating(Long id, Double rating, Integer ratingsAmount, Long userId, Date createdAt, Movie movie) {
        this.id = id;
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
        this.userId = userId;
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
