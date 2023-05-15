package com.jpcchaves.softreaming.payload.dtos.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpcchaves.softreaming.entities.Rating;

import java.util.Date;

public class LineRatingDto {
    private Long id;
    private Double rate;
    private Long userId;
    @JsonIgnore
    private Rating rating;
    private Date createdAt;

    public LineRatingDto() {
    }

    public LineRatingDto(Long id, Double rate, Long userId, Rating rating, Date createdAt) {
        this.id = id;
        this.rate = rate;
        this.userId = userId;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
