package com.travelink.travel.dto;

public class TravelDetailResponse {

    private String detail;

    private TravelDetailResponse() {
    }

    private TravelDetailResponse(final String detail) {
        this.detail = detail;
    }

    public static TravelDetailResponse from(final String detail){
        return new TravelDetailResponse(detail);
    }

    public String getDetail() {
        return detail;
    }
}
