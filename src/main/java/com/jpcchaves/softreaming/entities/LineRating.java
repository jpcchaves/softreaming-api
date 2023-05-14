package com.jpcchaves.softreaming.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class LineRating implements Serializable {
    private static final long serialVersionUID = -6819849785007650200L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rate;
    private Long userId;
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "rating_id",
            nullable = false
    )
    @JsonBackReference
    private Rating rating;
    @CreatedDate
    private Date createdAt;

    public LineRating() {
    }

    public LineRating(Long id, Double rate, Long userId, Rating rating, Date createdAt) {
        this.id = id;
        this.rate = rate;
        this.userId = userId;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public LineRating(Double rate, Long userId) {
        this.rate = rate;
        this.userId = userId;
    }

    public LineRating(Double rate,
                      Long userId,
                      Rating rating) {
        this.rate = rate;
        this.userId = userId;
        this.rating = rating;
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
