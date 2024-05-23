package com.travelink.member.dto;

public class MemberInfoDto {

    private String memberId;
    private int height;
    private String gender;
    private int age;
    private ImageInfoDto image;

    private MemberInfoDto() {
    }

    private MemberInfoDto(final String memberId,
        final int height,
        final String gender,
        final int age,
        final ImageInfoDto image) {
        this.memberId = memberId;
        this.height = height;
        this.gender = gender;
        this.age = age;
        this.image = image;
    }

    public static MemberInfoDto of(final String memberId) {
        return new MemberInfoDto(memberId, 0, "", 0, null);
    }

    public static MemberInfoDto of(final String memberId,
        final MemberInfoRequest memberInfoRequest,
        ImageInfoDto image) {
        return new MemberInfoDto(memberId,
            memberInfoRequest.getHeight(),
            memberInfoRequest.getGender(),
            memberInfoRequest.getAge(),
            image);
    }

    public String getMemberId() {
        return memberId;
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
