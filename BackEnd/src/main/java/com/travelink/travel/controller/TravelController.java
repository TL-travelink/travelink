package com.travelink.travel.controller;

import com.travelink.auth.support.JwtLogin;
import com.travelink.travel.dto.Favorite;
import com.travelink.travel.dto.FavoriteListResponse;
import com.travelink.travel.dto.GugunResponse;
import com.travelink.travel.dto.ScheduleRequest;
import com.travelink.travel.dto.TravelDetailResponse;
import com.travelink.travel.dto.TravelInfoRequest;
import com.travelink.travel.dto.TravelSearchRequest;
import com.travelink.travel.dto.TravelSearchResponse;
import com.travelink.travel.dto.TravelSearchResponses;
import com.travelink.travel.service.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travel")
public class TravelController {

    private final TravelService travelService;

    public TravelController(final TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping("/guguns")
    public ResponseEntity<GugunResponse> getGugun(@RequestParam final Integer areaCode) {
        List<String> guguns = travelService.getGugun(areaCode);
        return ResponseEntity.ok(GugunResponse.from(guguns));
    }

    @GetMapping("/search")
    public ResponseEntity<TravelSearchResponses> search(
        final TravelSearchRequest travelSearchRequest) {
        List<TravelSearchResponse> result = travelService.search(travelSearchRequest);
        return ResponseEntity.ok(TravelSearchResponses.from(result));
    }

    @GetMapping("/detail/{contentId}")
    public ResponseEntity<TravelDetailResponse> getDetail(
        @PathVariable("contentId") final int contentId) {
        String result = travelService.getDetail(contentId);
        return ResponseEntity.ok(TravelDetailResponse.from(result));
    }

    @PostMapping
    public ResponseEntity<Void> addInfo(@RequestBody final TravelInfoRequest travelInfoRequest) {
        travelService.addInfo(travelInfoRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/schedule")
    public ResponseEntity<Void> addSchedule(@JwtLogin final String memberId,
        @RequestBody final ScheduleRequest scheduleRequest) {
        System.out.println(scheduleRequest);
        travelService.addSchedule(memberId, scheduleRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/favorite")
    public ResponseEntity<List<FavoriteListResponse>> getMyFavoriteList(
        @JwtLogin final String memberId) {
        List<FavoriteListResponse> list = travelService.getMyFavoriteList(memberId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/favorite/{memberId}")
    public ResponseEntity<List<FavoriteListResponse>> getFavoriteList(
        @JwtLogin final String LoginId, @PathVariable("memberId") String memberId) {
        List<FavoriteListResponse> list = travelService.getFavoriteList(LoginId, memberId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/favorite/info/{boardId}")
    public ResponseEntity<Favorite> getFavorite(@PathVariable("boardId") String boardId) {
        Favorite favorite = travelService.getFavorite(boardId);
        return ResponseEntity.ok(favorite);
    }
}
