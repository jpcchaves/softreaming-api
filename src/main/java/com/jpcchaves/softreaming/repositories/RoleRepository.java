package com.jpcchaves.softreaming.repositories;

import com.jpcchaves.softreaming.entities.Role;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Id> {
    Optional<Role> findByName(String name);
}
