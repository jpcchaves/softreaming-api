package com.jpcchaves.softreaming.config.repositories;

import com.jpcchaves.softreaming.config.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
}
