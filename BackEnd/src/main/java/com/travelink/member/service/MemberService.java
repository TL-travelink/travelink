package com.travelink.member.service;

import com.travelink.member.dto.BestMemberResponse;
import com.travelink.member.dto.FollowListDto;
import com.travelink.member.dto.MemberDto;
import com.travelink.member.dto.MemberInfoRequest;
import com.travelink.member.dto.MemberInfoResponse;

import com.travelink.member.dto.NewFollowDto;
import com.travelink.member.dto.ProfileDto;
import java.io.IOException;
import java.util.List;

public interface MemberService {
    void createInfo(String loginId, MemberInfoRequest memberInfoRequest) throws IOException;

    MemberInfoResponse getInfo(String loginId);

    void updateInfo(String loginId, MemberInfoRequest memberInfoRequest) throws IOException;

    void deleteInfo(String loginId) throws IOException;

    void addFollowing(String loginId, String following);

    FollowListDto getFollowing(String loginId) throws IOException;

    MemberDto getMember(String loginId);

    FollowListDto getFollower(String loginId) throws IOException;

    List<ProfileDto> search(String memberId);

    List<NewFollowDto> findMembers(String memberId) throws IOException;

    String encodingImage(String img) throws IOException;

    List<BestMemberResponse> getBest3Member() throws IOException;
}
