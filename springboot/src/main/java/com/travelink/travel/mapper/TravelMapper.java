package com.travelink.travel.mapper;

import com.travelink.member.dto.FollowInfoDto;
import com.travelink.travel.dto.Favorite;
import com.travelink.travel.dto.FavoriteInfoRequest;
import com.travelink.travel.dto.FavoriteListResponse;
import com.travelink.travel.dto.ScheduleRequest;
import com.travelink.travel.dto.TravelInfoRequest;
import com.travelink.travel.dto.TravelSearchRequest;
import com.travelink.travel.dto.TravelSearchResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TravelMapper {

    List<String> getGugun(Integer areaCode);

    List<TravelSearchResponse> search(TravelSearchRequest travelSearchRequest);

    String getDetail(int contentId);

    void addInfo(TravelInfoRequest travelInfoRequest);

    void addSchedule(ScheduleRequest scheduleRequest);

    void addScheduleWithTravels(ScheduleRequest scheduleRequest);

    List<FavoriteListResponse> getMyFavoriteList(String memberId);

    List<FavoriteListResponse> getFavoriteList(FollowInfoDto followInfoDto);

    int option1(FollowInfoDto followInfoDto);

    int option2(FollowInfoDto followInfoDto);

    List<FavoriteListResponse> getFavoriteMutual(FollowInfoDto followInfoDto);

    FavoriteInfoRequest getFavorite(String boardId);

    List<Integer> getTravelsId(String boardId);

    TravelSearchResponse getTravelInfo(Integer contentId);
}
