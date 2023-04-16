package com.jpcchaves.softreaming.config.payload.dtos.profile;

public class ProfileDto {
    private String name;
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
