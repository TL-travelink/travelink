package com.travelink.member.dto;

import java.util.List;

public class ProfilesDto {

    List<ProfileDto> profileDtos;

    public ProfilesDto(List<ProfileDto> profileDtos) {
        this.profileDtos = profileDtos;
    }

    public List<ProfileDto> getProfileDtos() {
        return profileDtos;
    }
}
