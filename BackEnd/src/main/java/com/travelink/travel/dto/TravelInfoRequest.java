package com.travelink.travel.dto;

import java.math.BigDecimal;

public class TravelInfoRequest {
    private int contentId;
    private int contentType;
    private String title;
    private String addr1;
    private String addr2;
    private String firstImage;
    private BigDecimal mapY;
    private BigDecimal mapX;

    public TravelInfoRequest(int contentId, int contentType, String title, String addr1, String addr2, String firstImage, BigDecimal mapY, BigDecimal mapX) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.title = title;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.firstImage = firstImage;
        this.mapY = mapY;
        this.mapX = mapX;
    }

    public int getContentId() {
        return contentId;
    }

    public int getContentType() {
        return contentType;
    }

    public String getTitle() {
        return title;
    }

    public String getAddr1() {
        return addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public BigDecimal getMapY() {
        return mapY;
    }

    public BigDecimal getMapX() {
        return mapX;
    }

    @Override
    public String toString() {
        return "TravelInfoRequest{" +
            "contentId=" + contentId +
            ", contentType=" + contentType +
            ", title='" + title + '\'' +
            ", addr1='" + addr1 + '\'' +
            ", addr2='" + addr2 + '\'' +
            ", firstImage='" + firstImage + '\'' +
            ", mapY=" + mapY +
            ", mapX=" + mapX +
            '}';
    }
}
