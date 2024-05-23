package com.travelink.travel.dto;

import java.util.Date;
import java.util.List;

public class Favorite {

    private String title;
    private Date startAt;
    private Date endAt;
    private String memberId;
    private List<TravelSearchResponse> travels;

    public Favorite() {
    }

    public Favorite(String title, Date startAt, Date endAt, String memberId,
        List<TravelSearchResponse> travels) {
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.memberId = memberId;
        this.travels = travels;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public void setMemberId(String memberId) {
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

    public List<TravelSearchResponse> getTravels() {
        return travels;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setTravels(List<TravelSearchResponse> travels) {
        this.travels = travels;
    }

    @Override
    public String toString() {
        return "Favorite{" +
            "title='" + title + '\'' +
            ", startAt=" + startAt +
            ", endAt=" + endAt +
            ", memberId='" + memberId + '\'' +
            ", travels=" + travels +
            '}';
    }
}
