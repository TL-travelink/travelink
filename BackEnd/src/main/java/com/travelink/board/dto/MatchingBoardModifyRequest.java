package com.travelink.board.dto;

public class MatchingBoardModifyRequest extends BoardModifyRequest {

    private String category;

    public MatchingBoardModifyRequest() {

    }

    public MatchingBoardModifyRequest(int boardId, String title, String content, String category) {
        super(boardId, title, content);
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
        return super.toString() + "MatchingBoardModifyRequest{" +
            "category='" + category + '\'' +
            '}';
    }
}
