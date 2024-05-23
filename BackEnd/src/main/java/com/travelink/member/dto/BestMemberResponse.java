package com.travelink.member.dto;

public class BestMemberResponse {

    private String proImg;
    private String id;
    private int point;
    private String name;
    private String gender;

    public BestMemberResponse(String proImg, String id, int point, String name, String gender) {
        this.proImg = proImg;
        this.id = id;
        this.point = point;
        this.name = name;
        this.gender = gender;
    }

    public String getProImg() {
        return proImg;
    }

    public String getId() {
        return id;
    }

    public int getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public void setProImg(String proImg) {
        this.proImg = proImg;
    }
}
