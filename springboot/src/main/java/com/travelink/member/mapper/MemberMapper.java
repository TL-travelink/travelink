package com.travelink.member.mapper;

import com.travelink.member.dto.BestMemberResponse;
import com.travelink.member.dto.FollowInfoDto;
import com.travelink.member.dto.MemberDto;
import com.travelink.member.dto.MemberInfoDto;
import com.travelink.member.dto.MemberInfoResponse;
import com.travelink.member.dto.NewFollowDto;
import com.travelink.member.dto.ProfileDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void createInfo(MemberInfoDto memberInfoDto);

    MemberInfoResponse getInfo(String loginId);

    void updateInfo(MemberInfoDto memberInfoDto);

    void deleteInfo(String loginId);

    void addFollowing(FollowInfoDto followInfoDto);

    List<ProfileDto> getFollowing(String loginId);

    MemberDto getMember(String loginId);

    List<ProfileDto> getFollower(String loginId);

    List<ProfileDto> search(String memberId);

    int findFollowing(FollowInfoDto followInfoDto);

    void deleteFollowing(FollowInfoDto followInfoDto);

    List<NewFollowDto> findMembers(String memberId);

    List<BestMemberResponse> getBest3Member();
}
