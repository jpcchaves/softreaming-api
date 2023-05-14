package com.jpcchaves.softreaming.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@Entity
@Table
public class LineRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rate;
    private UserDetails user;
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

    public LineRating(Long id, Double rate, UserDetails user, Rating rating, Date createdAt) {
        this.id = id;
        this.rate = rate;
        this.user = user;
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

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
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
