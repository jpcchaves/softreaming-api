package com.jpcchaves.softreaming.repositories;

import com.jpcchaves.softreaming.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName,
                                                                      String lastName);
}
