package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Profile;
import com.jpcchaves.softreaming.entities.User;
import com.jpcchaves.softreaming.payload.dtos.profile.ProfileDto;
import com.jpcchaves.softreaming.repositories.ProfileRepository;
import com.jpcchaves.softreaming.services.ProfileService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;
    private final MapperUtils mapper;
    private SecurityContextServiceImpl securityContextService;

    public ProfileServiceImpl(ProfileRepository repository,
                              MapperUtils mapper,
                              SecurityContextServiceImpl securityContextService) {
        this.repository = repository;
        this.mapper = mapper;
        this.securityContextService = securityContextService;
    }

    @Override
    public ProfileDto create(ProfileDto profileDto) {
        Profile profile = mapper.parseObject(profileDto, Profile.class);
        User user = securityContextService.getCurrentLoggedUser();

        profile.setUser(user);

        Profile savedProfile = repository.save(profile);
        return mapper.parseObject(savedProfile, ProfileDto.class);
    }

    @Override
    public List<ProfileDto> getAll() {
        User user = securityContextService.getCurrentLoggedUser();
        List<Profile> profiles = repository.findAllByUser_Id(user.getId());
        List<ProfileDto> profileDtoList = mapper.parseListObjects(profiles, ProfileDto.class);
        return profileDtoList;
    }

    @Override
    public ProfileDto getById() {
        return null;
    }

    @Override
    public ProfileDto update(ProfileDto profileDto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
