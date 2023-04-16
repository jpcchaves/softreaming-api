package com.jpcchaves.softreaming.payload.dtos.auth;

public class UpdateUserResponseDto {
    private String message;

    public UpdateUserResponseDto() {
    }

    public UpdateUserResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
