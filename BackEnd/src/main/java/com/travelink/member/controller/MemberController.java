package com.travelink.member.controller;

import com.travelink.auth.support.JwtLogin;
import com.travelink.member.dto.BestMemberResponse;
import com.travelink.member.dto.FollowListDto;
import com.travelink.member.dto.MemberDto;
import com.travelink.member.dto.MemberInfoRequest;
import com.travelink.member.dto.MemberInfoResponse;
import com.travelink.member.dto.NewFollowDto;
import com.travelink.member.dto.ProfileDto;
import com.travelink.member.dto.ProfilesDto;
import com.travelink.member.service.MemberService;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/info")
    public ResponseEntity<Void> createInfo(@JwtLogin final String loginId,
                                           @ModelAttribute final MemberInfoRequest memberInfoRequest) throws IOException {
        memberService.createInfo(loginId, memberInfoRequest);
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponse> getInfo(@JwtLogin final String loginId) {
        MemberInfoResponse result = memberService.getInfo(loginId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/info")
    public ResponseEntity<Void> updateInfo(@JwtLogin final String loginId,
                                           @ModelAttribute final MemberInfoRequest memberInfoRequest) throws IOException {
        memberService.updateInfo(loginId, memberInfoRequest);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/info")
    public ResponseEntity<Void> updateInfo(@JwtLogin final String loginId) throws IOException {
        memberService.deleteInfo(loginId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PostMapping("/follow/{following}")
    public ResponseEntity<Void> addFollowing(@JwtLogin final String loginId,
                                             @PathVariable("following") final String following) {
        memberService.addFollowing(loginId, following);
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }

    @GetMapping("/following")
    public ResponseEntity<FollowListDto> getFollowing(@JwtLogin final String loginId)
        throws IOException {
        FollowListDto followListDto = memberService.getFollowing(loginId);
        return ResponseEntity.ok(followListDto);
    }

    @GetMapping("/follower")
    public ResponseEntity<FollowListDto> getFollower(@JwtLogin final String loginId)
        throws IOException {
        FollowListDto followListDto = memberService.getFollower(loginId);
        return ResponseEntity.ok(followListDto);
    }

    @GetMapping
    public ResponseEntity<MemberDto> getMember(@JwtLogin final String loginId) {
        MemberDto memberDto = memberService.getMember(loginId);
        return ResponseEntity.ok(memberDto);
    }

    @GetMapping("/search/{memberId}")
    public ResponseEntity<ProfilesDto> searchWithMemberId(@PathVariable("memberId") final String memberId) {
        List<ProfileDto> members = memberService.search(memberId);
        return ResponseEntity.ok(new ProfilesDto(members));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewFollowDto>> findMembers(@JwtLogin final String memberId)
        throws IOException {
        List<NewFollowDto> newFollowDtos = memberService.findMembers(memberId);
        return ResponseEntity.ok(newFollowDtos);
    }

    @GetMapping("/best")
    public ResponseEntity<List<BestMemberResponse>> getBest3Member() throws IOException {
        List<BestMemberResponse> best = memberService.getBest3Member();
        return ResponseEntity.ok(best);
    }
}
