package com.travelink.board.dto;

import java.util.List;

public class BoardDto {

    private Integer boardId;
    private String memberId;
    private String title;
    private String content;
    private String status;
    private int hit;
    private int likeCount;
    private int reportCount;
    private String createAt;
    private BoardFileInfoDto fileInfo;
    private List<CommentDto> commentList;

    public BoardDto() {
    }

    public BoardDto(Integer boardId, String memberId, String title, String content, String status,
                    int hit, int likeCount, int reportCount, String createAt,
                    BoardFileInfoDto fileInfo, List<CommentDto> commentList) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.hit = hit;
        this.likeCount = likeCount;
        this.reportCount = reportCount;
        this.createAt = createAt;
        this.fileInfo = fileInfo;
        this.commentList = commentList;
    }

    public int getBoardId() {
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

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }


    public BoardFileInfoDto getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(BoardFileInfoDto fileInfo) {
        this.fileInfo = fileInfo;
    }

    public List<CommentDto> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDto> commentList) {
        this.commentList = commentList;
    }

}
