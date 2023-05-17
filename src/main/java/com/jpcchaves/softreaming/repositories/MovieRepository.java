package com.jpcchaves.softreaming.repositories;

import com.jpcchaves.softreaming.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Boolean existsByName(String name);

    @Override
    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByReleaseDate(Pageable pageable, String releaseDate);
}
