package com.jpcchaves.softreaming.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ratings")
public class Rating implements Serializable {
    private static final long serialVersionUID = -9212065549437355252L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private Double rating;
    private Integer ratingsAmount;

    @OneToMany(
            mappedBy = "rating",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<LineRating> lineRatings = new ArrayList<>();

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
                  Movie movie
    ) {
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
        this.movie = movie;
    }

    public Rating(Long id, Double rating, Integer ratingsAmount, Movie movie) {
        this.id = id;
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
        this.movie = movie;
    }

    public Rating(Long id, Double rating, Integer ratingsAmount, Date createdAt, Movie movie) {
        this.id = id;
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
        this.createdAt = createdAt;
        this.movie = movie;
    }

    public Rating(Long id,
                  Double rating,
                  Integer ratingsAmount,
                  List<LineRating> lineRatings,
                  Date createdAt,
                  Movie movie) {
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<LineRating> getLineRatings() {
        return lineRatings;
    }

    public void setLineRatings(List<LineRating> lineRatings) {
        this.lineRatings = lineRatings;
    }
}
