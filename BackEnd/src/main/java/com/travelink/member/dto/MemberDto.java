package com.travelink.member.dto;

public class MemberDto {

    private String proImg;
    private String id;
    private String name;
    private String address;
    private int point;
    private int grade;

    public MemberDto(String proImg, String id, String name, String address, int point, int grade) {
        this.proImg = proImg;
        this.id = id;
        this.name = name;
        this.address = address;
        this.point = point;
        this.grade = grade;
    }

    public void setProImg(String proImg) {
        this.proImg = proImg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getProImg() {
        return proImg;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPoint() {
        return point;
    }

    public int getGrade() {
        return grade;
    }
}
