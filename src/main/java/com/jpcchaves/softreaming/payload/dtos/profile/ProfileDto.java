package com.jpcchaves.softreaming.payload.dtos.profile;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class ProfileDto {
    private Long id;
    @NotBlank(message = "O nome do perfil é obrigatório!")
    private String name;
    @NotBlank(message = "A imagem do perfil é obrigatória!")
    private String imgUrl;
    private Date createdAt;

    public ProfileDto() {
    }

    public ProfileDto(Long id, String name, String imgUrl, Date createdAt) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
