package com.jpcchaves.softreaming.repositories;

import com.jpcchaves.softreaming.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Boolean existsByName(String name);

    @Override
    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByReleaseDate(Pageable pageable,
                                  String releaseDate);

    Page<Movie> findByNameContainingIgnoreCase(Pageable pageable,
                                               String name);

    Page<Movie> findByNameContainingIgnoreCaseAndReleaseDate(Pageable pageable,
                                                             String name,
                                                             String releaseDate);

    List<Movie> findTop10ByOrderByRatings_RatingDesc();

    Page<Movie> findByRatings_RatingGreaterThanEqual(Pageable pageable,
                                                     Double rating);

    Page<Movie> findByReleaseDateBetween(String startDate,
                                         String endDate,
                                         Pageable pageable);
}
