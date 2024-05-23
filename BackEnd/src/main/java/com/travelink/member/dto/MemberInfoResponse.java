package com.travelink.member.dto;

public class MemberInfoResponse {

    private int height;
    private String gender;
    private int age;
    private ImageInfoDto image;

    private MemberInfoResponse() {
    }

    private MemberInfoResponse(final int height, final String gender, final int age, final ImageInfoDto image) {
        this.height = height;
        this.gender = gender;
        this.age = age;
        this.image = image;
    }

    public static MemberInfoResponse of(final int height, final String gender, final int age, final ImageInfoDto image){
        return new MemberInfoResponse(height, gender, age, image);
    }

    public int getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public ImageInfoDto getImage() {
        return image;
    }
}
