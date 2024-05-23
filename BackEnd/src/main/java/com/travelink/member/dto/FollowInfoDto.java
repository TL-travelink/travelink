package com.travelink.member.dto;

public class FollowInfoDto {

    private String following; //나를 기준
    private String follower;

    private FollowInfoDto() {
    }

    public FollowInfoDto(String following, String follower) {
        this.following = following;
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public String getFollower() {
        return follower;
    }
}
