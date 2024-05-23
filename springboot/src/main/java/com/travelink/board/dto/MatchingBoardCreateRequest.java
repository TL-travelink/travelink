package com.travelink.board.dto;

import java.util.List;

public class MatchingBoardCreateRequest extends BoardCreateRequest {

    private String category;

    public MatchingBoardCreateRequest(Integer boardId, String memberId, String title, String content,
        String status, BoardFileInfoDto fileInfo, String category) {
        super(boardId, memberId, title, content, status, fileInfo);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return super.toString() + "MatchingBoardCreateRequest{" +
            "category='" + category + '\'' +
            '}';
    }
}
