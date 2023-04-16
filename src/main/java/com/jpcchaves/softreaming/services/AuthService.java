package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.payload.dtos.auth.*;

public interface AuthService {
    JwtAuthResponseDto login(LoginDto loginDto);

    RegisterResponseDto register(RegisterRequestDto registerDto);

    UpdateUserResponseDto update(UpdateUserRequestDto updateUserDto, Long id);
}
