package com.travelink.member.dto;

public class UpdateBoardLikeDto {
    private int boardId;
    private int status;

    public UpdateBoardLikeDto(int boardId, int status) {
        this.boardId = boardId;
        this.status = status;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
