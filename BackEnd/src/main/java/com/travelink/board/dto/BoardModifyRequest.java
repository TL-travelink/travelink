package com.travelink.board.dto;

public class BoardModifyRequest {

    private int boardId;
    private String title;
    private String content;

    public BoardModifyRequest() {

    }

    public BoardModifyRequest(int boardId, String title, String content) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BoardModifyRequest{" +
            "boardId=" + boardId +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            '}';
    }
}
