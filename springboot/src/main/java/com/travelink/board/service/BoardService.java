package com.travelink.board.service;

import com.travelink.board.dto.*;

import com.travelink.member.dto.MemberDto;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BoardService {
    void createBoard(BoardCreateRequest boardCreateRequest);

    List<BoardDto> listBoard() throws Exception;

    BoardDto getBoard(int boardId) throws Exception;

    void modifyBoard(BoardModifyRequest boardModifyRequest);

    void deleteBoard(int boardId);

    //    void deleteBoard(String memberId, int boardId);

    List<MatchingBoardDto> listMatchingBoard();

    MatchingBoardDto getMatchingBoard(int boardId);

    void createBoardWithMatchingBoard(MatchingBoardCreateRequest matchingBoardCreateRequest);

    void modifyMatchingBoard(MatchingBoardModifyRequest matchingBoardModifyRequest);

    void deleteMatchingBoard(int boardId);

    void clickLike(LikeDto likeDto);

    int likeCount(LikeDto likeDto);

    void createComment(CommentDto commentDto);

    void deleteComment(int commentId);

    void modifyComment(CommentModifyRequest commentModifyRequest);

    void encodingImage(BoardFileInfoDto boardFileInfoDto) throws IOException;

    List<BoardDto> getHotBoard() throws IOException;
}
