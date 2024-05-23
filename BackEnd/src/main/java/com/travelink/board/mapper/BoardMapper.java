package com.travelink.board.mapper;

import com.travelink.board.dto.*;

import com.travelink.member.dto.UpdateBoardLikeDto;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    void createBoard(BoardCreateRequest boardCreateRequest);

    List<BoardDto> listBoard();

    BoardDto getBoard(int boardId);

    void modifyBoard(BoardModifyRequest boardModifyRequest);

    void deleteBoard(int boardId);

    List<MatchingBoardDto> listMatchingBoard();

    MatchingBoardDto getMatchingBoard(int boardId);

    void createMatchingBoard(MatchingBoardCreateRequest matchingBoardCreateRequest);

    void modifyMatchingBoard(MatchingBoardModifyRequest matchingBoardModifyRequest);

    void deleteMatchingBoard(int boardId);

    void insertLike(LikeDto likeDto);

    void deleteLike(LikeDto likeDto);

    int likeCount(LikeDto likeDto);

    void updateBoardLike(UpdateBoardLikeDto updateBoardLikeDto);

    void createComment(CommentDto commentDto);

    void deleteComment(int commentId);

    void modifyComment(CommentModifyRequest commentModifyRequest);

    void registerFile(BoardCreateRequest boardCreateRequest);

    void selectFileInfoByBoardId(int boardId);
    void selectCommentsByBoardId(int boardId);
    List<BoardDto> getHotBoard();
}
