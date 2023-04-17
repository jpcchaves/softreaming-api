package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.entities.Profile;
import com.jpcchaves.softreaming.payload.dtos.profile.ProfileDto;

import java.util.List;

public interface ProfileService {
    ProfileDto create(ProfileDto profileDto);
    List<ProfileDto> getAll();
    ProfileDto getById();
    ProfileDto update(ProfileDto profileDto, Long id);
    void delete(Long id);
}
