package com.jpcchaves.softreaming.repositories;

import com.jpcchaves.softreaming.entities.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    @Override
    Page<Actor> findAll(Pageable pageable);
}
