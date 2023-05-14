package com.jpcchaves.softreaming.repositories;

import com.jpcchaves.softreaming.entities.LineRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRatingRepository extends JpaRepository<LineRating, Long> {
}
