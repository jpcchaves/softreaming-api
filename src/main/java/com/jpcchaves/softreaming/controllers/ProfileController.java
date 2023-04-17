package com.jpcchaves.softreaming.controllers;

import com.jpcchaves.softreaming.payload.dtos.profile.ProfileDto;
import com.jpcchaves.softreaming.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profileDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(profileDto));
    }

    @GetMapping
    public ResponseEntity<List<ProfileDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }
}
