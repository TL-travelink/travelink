package com.travelink.member.dto;

import java.util.List;

public class FollowListDto {

    private List<ProfileDto> profiles;

    public FollowListDto(List<ProfileDto> profiles) {
        this.profiles = profiles;
    }

    public List<ProfileDto> getProfiles() {
        return profiles;
    }
}
