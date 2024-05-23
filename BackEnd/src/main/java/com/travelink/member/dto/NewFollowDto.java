package com.travelink.member.dto;

public class NewFollowDto {

    private String proImg;
    private String id;
    private int age;
    private String gender;

    public NewFollowDto(String proImg, String id, int age, String gender) {
        this.proImg = proImg;
        this.id = id;
        this.age = age;
        this.gender = gender;
    }

    public String getProImg() {
        return proImg;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setProImg(String proImg) {
        this.proImg = proImg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
