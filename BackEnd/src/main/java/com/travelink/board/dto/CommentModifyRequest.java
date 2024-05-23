package com.travelink.board.dto;

public class CommentModifyRequest {
    private int commentId;
    private String content;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentModifyRequest{" +
            "commentId=" + commentId +
            ", content='" + content + '\'' +
            '}';
    }

    public CommentModifyRequest(int commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
