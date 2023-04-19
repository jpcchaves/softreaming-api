package com.jpcchaves.softreaming.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String category;

    @ManyToMany(
            mappedBy = "categories",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Set<Movie> movies;

    public Category() {
    }

    public Category(Long id,
                    String category,
                    Set<Movie> movies) {
        this.id = id;
        this.category = category;
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
