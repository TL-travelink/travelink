package com.travelink.member.dto;

import org.springframework.web.multipart.MultipartFile;

public class MemberInfoRequest {

    private int height;
    private String gender;
    private int age;
    private MultipartFile image;

    public MemberInfoRequest() {
    }

    public MemberInfoRequest(int height, String gender, int age, MultipartFile image) {
        this.height = height;
        this.gender = gender;
        this.age = age;
        this.image = image;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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

    public MultipartFile getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "MemberInfoRequest{" +
            "height=" + height +
            ", gender='" + gender + '\'' +
            ", age=" + age +
            ", image=" + image +
            '}';
    }
}
