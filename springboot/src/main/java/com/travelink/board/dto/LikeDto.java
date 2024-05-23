package com.travelink.board.dto;

public class LikeDto {
    private String memberId;
    private int boardId;

    public LikeDto(String memberId, int boardId) {
        this.memberId = memberId;
        this.boardId = boardId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    @Override
    public String toString() {
        return "LikeDto{" +
            ", memberId='" + memberId + '\'' +
            ", boardId=" + boardId +
            '}';
    }
}
