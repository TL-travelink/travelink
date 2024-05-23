package com.travelink.travel.dto;

import java.util.Date;

public class FavoriteListResponse {

    private int id;
    private String title;
    private Date startAt;
    private Date endAt;
    private String author;
    private String visibility;

    public FavoriteListResponse(int id, String title, Date startAt, Date endAt, String author, String visibility) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.author = author;
        this.visibility = visibility;
    }

    public int getId() {
        return id;
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

    public String getAuthor() {
        return author;
    }

    public String getVisibility() {
        return visibility;
    }

    @Override
    public String toString() {
        return "FavoriteListResponse{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", startAt=" + startAt +
            ", endAt=" + endAt +
            ", author='" + author + '\'' +
            '}';
    }
}
