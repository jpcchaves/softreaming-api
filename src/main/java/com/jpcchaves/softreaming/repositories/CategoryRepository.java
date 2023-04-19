package com.jpcchaves.softreaming.repositories;

import com.jpcchaves.softreaming.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategory(String category);
}
