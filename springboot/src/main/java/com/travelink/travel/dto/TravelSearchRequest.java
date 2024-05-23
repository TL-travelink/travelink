package com.travelink.travel.dto;

public class TravelSearchRequest {

    private int areaCode;
    private int gugun;
    private int contentTypeId;
    private String keyword;

    private TravelSearchRequest() {
    }

    public TravelSearchRequest(int areaCode, int gugun, int contentTypeId, String keyword) {
        this.areaCode = areaCode;
        this.gugun = gugun;
        this.contentTypeId = contentTypeId;
        this.keyword = keyword;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public int getGugun() {
        return gugun;
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    public String getKeyword() {
        return keyword;
    }
}
