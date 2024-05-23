package com.travelink.member.dto;

public class ProfileDto {

    private String proImg;
    private String id;

    private ProfileDto() {
    }

    public ProfileDto(String proImg, String id) {
        this.proImg = proImg;
        this.id = id;
    }

    public String getProImg() {
        return proImg;
    }

    public String getId() {
        return id;
    }

    public void setProImg(String proImg) {
        this.proImg = proImg;
    }

    public void setId(String id) {
        this.id = id;
    }
}
