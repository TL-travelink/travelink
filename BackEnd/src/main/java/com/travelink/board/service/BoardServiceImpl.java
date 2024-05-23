package com.travelink.board.service;

import com.travelink.board.dto.*;
import com.travelink.board.mapper.BoardMapper;

import com.travelink.member.dto.MemberDto;
import com.travelink.member.dto.UpdateBoardLikeDto;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {

    @Value("${file.path}")
    private String uploadPath;

    @Value("${file.path.upload-images}")
    private String uploadImagePath;

    @Value("${file.path.upload-files}")
    private String uploadFilePath;

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    @Transactional
    public void createBoard(BoardCreateRequest boardCreateRequest) {
        boardMapper.createBoard(boardCreateRequest);
        boardMapper.registerFile(boardCreateRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardDto> listBoard() throws Exception{
        List<BoardDto> boardList = boardMapper.listBoard();

        for (BoardDto boardDto : boardList) {
            encodingImage(boardDto.getFileInfo());
        }

        return boardList;
    }

    @Override
    @Transactional(readOnly = true)
    public BoardDto getBoard(int boardId) throws Exception{
        BoardDto boardDto = boardMapper.getBoard(boardId);
        encodingImage(boardDto.getFileInfo());
        return boardDto;
    }

    @Override
    @Transactional
    public void modifyBoard(BoardModifyRequest boardModifyRequest) {
        boardMapper.modifyBoard(boardModifyRequest);
    }

    @Override
    @Transactional
    public void deleteBoard(int boardId) {
        boardMapper.deleteBoard(boardId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchingBoardDto> listMatchingBoard() {
        List<MatchingBoardDto> matchingBoardList = boardMapper.listMatchingBoard();
        return matchingBoardList;
    }

    @Override
    @Transactional(readOnly = true)
    public MatchingBoardDto getMatchingBoard(int boardId) {
        MatchingBoardDto matchingBoardDto = boardMapper.getMatchingBoard(boardId);
        return matchingBoardDto;
    }

    @Override
    @Transactional
    public void createBoardWithMatchingBoard(MatchingBoardCreateRequest matchingBoardCreateRequest) {
        boardMapper.createBoard(matchingBoardCreateRequest);
        System.out.println(matchingBoardCreateRequest.getBoardId());
        boardMapper.createMatchingBoard(matchingBoardCreateRequest);
    }

    @Override
    @Transactional
    public void modifyMatchingBoard(MatchingBoardModifyRequest matchingBoardModifyRequest) {
        boardMapper.modifyBoard(matchingBoardModifyRequest);
        boardMapper.modifyMatchingBoard(matchingBoardModifyRequest);
    }

    @Override
    @Transactional
    public void deleteMatchingBoard(int boardId) {
        boardMapper.deleteMatchingBoard(boardId);
    }

    //    @Override
//    public void deleteBoard(String memberId, int boardId) {
//        // todo 보드 아이디로 보드 객체 가져와서 보드에 박혀있는 작성자 id랑 로그인한 id랑 비교로직 작성하고
//        // 다르면 예외던지면됨 -> try catch X
//    }

    @Override
    @Transactional
    public void clickLike(LikeDto likeDto) {
        int count = likeCount(likeDto);
        System.out.println("좋아요 개수 : " + count);
        int status = 0;
        if(count == 0){
            boardMapper.insertLike(likeDto);
            status = 1;
        }else{
            boardMapper.deleteLike(likeDto);
            status = -1;
        }
        UpdateBoardLikeDto updateBoardLikeDto = new UpdateBoardLikeDto(likeDto.getBoardId(), status);
        boardMapper.updateBoardLike(updateBoardLikeDto);
    }


    @Override
    @Transactional(readOnly = true)
    public int likeCount(LikeDto likeDto){
        return boardMapper.likeCount(likeDto);
    }

    @Override
    public void createComment(CommentDto commentDto) {
        boardMapper.createComment(commentDto);
    }

    @Override
    public void deleteComment(int commentId) {
        boardMapper.deleteComment(commentId);
    }

    @Override
    public void modifyComment(CommentModifyRequest commentModifyRequest) {
        boardMapper.modifyComment(commentModifyRequest);
    }

    @Override
    public void encodingImage(BoardFileInfoDto boardFileInfoDto) throws IOException {
        if(boardFileInfoDto.getSaveFile()!=null) {
            String filePath = uploadPath + File.separator + "board" +File.separator + boardFileInfoDto.getSaveFile();
            byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
            String base64EncodedString = Base64.getEncoder().encodeToString(bytes); //인코딩
            boardFileInfoDto.setSaveFile(base64EncodedString);//thumbnail에 인코딩 정보 넣어주기
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardDto> getHotBoard() throws IOException{
        List<BoardDto> boardList = boardMapper.getHotBoard();

        for (BoardDto boardDto : boardList) {
            encodingImage(boardDto.getFileInfo());
        }

        return boardList;
    }

}
