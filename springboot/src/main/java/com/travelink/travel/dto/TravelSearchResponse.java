package com.travelink.travel.dto;

import java.math.BigDecimal;

public class TravelSearchResponse {

    private int contentId; // pk
    private int contentType;
    private String title;
    private String addr1;
    private String addr2;
    private String firstImage;
    private BigDecimal mapY;
    private BigDecimal mapX;

    private TravelSearchResponse() {
    }

    private TravelSearchResponse(final int contentId,
                                 final int contentType,
                                 final String title,
                                 final String addr1,
                                 final String addr2,
                                 final String firstImage,
                                 final BigDecimal mapY,
                                 final BigDecimal mapX) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.title = title;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.firstImage = firstImage;
        this.mapY = mapY;
        this.mapX = mapX;
    }

    public static TravelSearchResponse of(final int contentId,
                                          final int contentType,
                                          final String title,
                                          final String addr1,
                                          final String addr2,
                                          final String firstImage,
                                          final BigDecimal mapY,
                                          final BigDecimal mapX) {
        return new TravelSearchResponse(contentId, contentType, title, addr1, addr2, firstImage, mapY, mapX);
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
}
