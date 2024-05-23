package com.travelink.board.dto;

public class CommentDto {
    private int id;
    private int boardId;
    private String memberId;
    private String content;
    private String createAt;

    public CommentDto() {

    }
    public CommentDto(int id, int boardId, String memberId, String content, String createAt) {
        this.id = id;
        this.boardId = boardId;
        this.memberId = memberId;
        this.content = content;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
            "boardId=" + boardId +
            ", memberId='" + memberId + '\'' +
            ", content='" + content + '\'' +
            ", createAt='" + createAt + '\'' +
            '}';
    }
}
