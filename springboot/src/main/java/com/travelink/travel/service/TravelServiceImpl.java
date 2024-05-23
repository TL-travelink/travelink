package com.travelink.travel.service;

import com.travelink.member.dto.FollowInfoDto;
import com.travelink.travel.dto.Favorite;
import com.travelink.travel.dto.FavoriteInfoRequest;
import com.travelink.travel.dto.FavoriteListResponse;
import com.travelink.travel.dto.ScheduleRequest;
import com.travelink.travel.dto.TravelInfoRequest;
import com.travelink.travel.dto.TravelSearchRequest;
import com.travelink.travel.dto.TravelSearchResponse;
import com.travelink.travel.mapper.TravelMapper;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelMapper travelMapper;

    public TravelServiceImpl(final TravelMapper travelMapper) {
        this.travelMapper = travelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getGugun(final Integer areaCode) {
        return travelMapper.getGugun(areaCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TravelSearchResponse> search(final TravelSearchRequest travelSearchRequest) {
        return travelMapper.search(travelSearchRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public String getDetail(int contentId) {
        return travelMapper.getDetail(contentId);
    }

    @Override
    @Transactional
    public void addInfo(TravelInfoRequest travelInfoRequest) {
        travelMapper.addInfo(travelInfoRequest);
    }

    @Override
    @Transactional
    public void addSchedule(String memberId, ScheduleRequest scheduleRequest) {
        scheduleRequest.setMemberId(memberId);
        travelMapper.addSchedule(scheduleRequest);
        travelMapper.addScheduleWithTravels(scheduleRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteListResponse> getMyFavoriteList(String memberId) {
        return travelMapper.getMyFavoriteList(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteListResponse> getFavoriteList(String memberId, String id) {
        FollowInfoDto followInfoDto = new FollowInfoDto(memberId, id);

        List<FavoriteListResponse> favoriteList = travelMapper.getFavoriteList(followInfoDto);

        if (travelMapper.option1(followInfoDto) == 1 && travelMapper.option2(followInfoDto) == 1) {
            favoriteList.addAll(travelMapper.getFavoriteMutual(followInfoDto));
        }


        return favoriteList;
    }

    @Override
    @Transactional
    public Favorite getFavorite(String boardId) {
        // boardId로 기본 정보 가져오기
        // 이후에 지도 정보 list로 넘버 가져오기
        // 그다음에 지도 마커 찍기위해 정보가져오기

        FavoriteInfoRequest favoriteInfo = travelMapper.getFavorite(boardId);

        List<Integer> travels = travelMapper.getTravelsId(boardId);


        List<TravelSearchResponse> responseList = new ArrayList<>();

        for (Integer t : travels) {
            responseList.add(travelMapper.getTravelInfo(t));
        }
        Favorite favorite = new Favorite();

        favorite.setTitle(favoriteInfo.getTitle());
        favorite.setStartAt(favoriteInfo.getStartAt());
        favorite.setEndAt(favoriteInfo.getEndAt());
        favorite.setMemberId(favoriteInfo.getMemberId());
        favorite.setTravels(responseList);

        return favorite;
    }
}
