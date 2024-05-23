package com.travelink.travel.dto;

import java.util.Date;

public class FavoriteInfoRequest {

    private String title;
    private Date startAt;
    private Date endAt;
    private String memberId;

    public FavoriteInfoRequest(String title, Date startAt, Date endAt, String memberId) {
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartAt() {
        return startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public String getMemberId() {
        return memberId;
    }
}
