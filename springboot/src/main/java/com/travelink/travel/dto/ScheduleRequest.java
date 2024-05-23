package com.travelink.travel.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ScheduleRequest {

    private String title;
    private Date startDate;
    private Date endDate;
    private List<Integer> travels;
    private int generatedId; // 새로 추가한 필드
    private String memberId;
    private String visibility;

    public ScheduleRequest(String title, Date startDate, Date endDate, List<Integer> travels,
        String visibility) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.travels = travels;
        this.visibility = visibility;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<Integer> getTravels() {
        return travels;
    }

    public int getGeneratedId() {
        return generatedId;
    }

    public String getVisibility() {
        return visibility;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTravels(List<Integer> travels) {
        this.travels = travels;
    }

    public void setGeneratedId(int generatedId) {
        this.generatedId = generatedId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "ScheduleRequest{" +
            "title='" + title + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", travels=" + travels +
            ", generatedId=" + generatedId +
            ", memberId='" + memberId + '\'' +
            ", visibility='" + visibility + '\'' +
            '}';
    }
}
