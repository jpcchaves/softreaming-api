package com.jpcchaves.softreaming.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@EntityListeners(AuditingEntityListener.class)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String duration;
    @Column(nullable = false)
    private String releaseDate;
    @Column(nullable = false)
    private String movieUrl;
    @Column(nullable = false)
    private String posterUrl;
    @CreatedDate
    private Date createdAt;
    private Double rating;
    private Integer ratingsAmount;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH
    )
    @JoinTable(
            name = "movies_categories",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    public Movie() {
    }

    public Movie(Long id,
                 String name,
                 String description,
                 String duration,
                 String releaseDate,
                 String movieUrl,
                 String posterUrl,
                 Date createdAt,
                 Double rating,
                 Integer ratingsAmount,
                 Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.movieUrl = movieUrl;
        this.posterUrl = posterUrl;
        this.createdAt = createdAt;
        this.rating = rating;
        this.ratingsAmount = ratingsAmount;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
