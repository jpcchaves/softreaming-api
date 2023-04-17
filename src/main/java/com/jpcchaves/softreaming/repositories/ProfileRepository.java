package com.jpcchaves.softreaming.repositories;

import com.jpcchaves.softreaming.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findAllByUser_Id(Long userId);
    Optional<Profile> findByUser_IdAndId(Long userId, Long id);
}
