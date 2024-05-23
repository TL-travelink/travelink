package com.travelink.board.dto;

import java.util.List;

public class BoardCreateRequest {

    private Integer boardId;
    private String memberId;
    private String title;
    private String content;
    private String status;
    private BoardFileInfoDto fileInfo;

    public BoardCreateRequest(Integer boardId, String memberId, String title, String content,
        String status, BoardFileInfoDto fileInfo) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.fileInfo = fileInfo;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BoardFileInfoDto getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(BoardFileInfoDto fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public String toString() {
        return "BoardCreateRequest{" +
            "boardId=" + boardId +
            ", memberId='" + memberId + '\'' +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", status='" + status + '\'' +
            ", fileInfo=" + fileInfo +
            '}';
    }
}
