package com.jpcchaves.softreaming.payload.dtos.profile;

import jakarta.validation.constraints.NotBlank;

public class ProfileDto {
    @NotBlank(message = "O nome do perfil é obrigatório!")
    private String name;
    @NotBlank(message = "A imagem do perfil é obrigatória!")
    private String imgUrl;

    public ProfileDto() {
    }

    public ProfileDto(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
