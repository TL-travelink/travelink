package com.travelink.board.dto;

import java.util.List;

public class MatchingBoardDto extends BoardDto {

    private String category;

    public MatchingBoardDto(Integer boardId, String memberId, String title, String content,
        String status, int hit, int likeCount, int reportCount, String createAt,
        BoardFileInfoDto fileInfo, List<CommentDto> commentList, String category) {
        super(boardId, memberId, title, content, status, hit, likeCount, reportCount, createAt,
            fileInfo, commentList);
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
        return super.toString() + "MatchingBoardDto{" +
            ", category='" + category + '\'' +
            '}';
    }
}
