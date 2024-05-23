package com.travelink.travel.service;

import com.travelink.travel.dto.Favorite;
import com.travelink.travel.dto.FavoriteListResponse;
import com.travelink.travel.dto.ScheduleRequest;
import com.travelink.travel.dto.TravelInfoRequest;
import com.travelink.travel.dto.TravelSearchRequest;
import com.travelink.travel.dto.TravelSearchResponse;

import java.util.List;

public interface TravelService {
    List<String> getGugun(Integer areaCode);

    List<TravelSearchResponse> search(TravelSearchRequest travelSearchRequest);

    String getDetail(int contentId);

    void addInfo(TravelInfoRequest travelInfoRequest);

    void addSchedule(String memberId, ScheduleRequest scheduleRequest);

    List<FavoriteListResponse> getMyFavoriteList(String memberId);

    List<FavoriteListResponse> getFavoriteList(String memberId, String id);

    Favorite getFavorite(String boardId);
}
