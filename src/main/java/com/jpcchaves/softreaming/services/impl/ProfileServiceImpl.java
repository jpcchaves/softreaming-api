package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Profile;
import com.jpcchaves.softreaming.entities.User;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.payload.dtos.profile.ProfileDto;
import com.jpcchaves.softreaming.repositories.ProfileRepository;
import com.jpcchaves.softreaming.services.ProfileService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
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

        verifyProfilesAmount(user.getId());

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
    public ProfileDto getById(Long id) {
        User user = securityContextService.getCurrentLoggedUser();

        Profile profile = repository
                .findByUser_IdAndId(user.getId(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um perfil com os dados informados!"));

        ProfileDto profileDto = mapper.parseObject(profile, ProfileDto.class);

        return profileDto;
    }

    @Override
    public ProfileDto update(ProfileDto profileDto, Long id) {
        User user = securityContextService.getCurrentLoggedUser();
        Profile profile = repository.findByUser_IdAndId(user.getId(), id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um perfil com os dados informados!"));

        profile.setName(profileDto.getName());
        profile.setImgUrl(profileDto.getImgUrl());

        Profile updatedProfile = repository.save(profile);

        ProfileDto updatedProfileDto = mapper.parseObject(updatedProfile, ProfileDto.class);

        return updatedProfileDto;
    }

    @Override
    public void delete(Long id) {
        getById(id);

        repository.deleteById(id);
    }

    private void verifyProfilesAmount(Long userId) {
        if (repository.countAllByUser_Id(userId) >= 4) {
            throw new BadRequestException("O usuário atingiu o limite máximo de perfis cadastrados!");
        }
    }
}
