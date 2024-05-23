package com.travelink.board.controller;

import com.travelink.auth.support.JwtLogin;
import com.travelink.board.dto.*;
import com.travelink.board.service.BoardService;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BoardController {

    @Value("${file.path}")
    private String uploadPath;

    @Value("${file.path.upload-images}")
    private String uploadImagePath;

    @Value("${file.path.upload-files}")
    private String uploadFilePath;

    private final BoardService boardService;

    public BoardController(final BoardService boardService) {
        this.boardService = boardService;
    }

    // board
    @GetMapping("/board")
    public ResponseEntity<List<BoardDto>> readAll() throws Exception {
        List<BoardDto> resultList = boardService.listBoard();
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/hot-board")
    public ResponseEntity<List<BoardDto>> readHot() throws Exception {
        List<BoardDto> resultList = boardService.getHotBoard();
        System.out.println(resultList.size());
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardDto> read(@PathVariable int boardId) throws Exception {
        BoardDto result = boardService.getBoard(boardId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/board")
    public ResponseEntity<Void> create(BoardCreateRequest boardCreateRequest, @RequestPart("upfile") MultipartFile file, @JwtLogin String loginId)
        throws Exception {
        if(!file.isEmpty()) {
                    String saveFolder = uploadPath + File.separator + "board";
                    File folder = new File(saveFolder);
                    if (!folder.exists())
                        folder.mkdirs();
                    BoardFileInfoDto fileInfoDto = new BoardFileInfoDto();

                    String originalFileName = file.getOriginalFilename();
                    if (!originalFileName.isEmpty()) {
                        String saveFileName = UUID.randomUUID().toString()
                            + originalFileName.substring(originalFileName.lastIndexOf('.'));
                        fileInfoDto.setSaveFolder("board");
                        fileInfoDto.setOriginalFile(originalFileName);
                        fileInfoDto.setSaveFile(saveFileName);
                file.transferTo(new File(folder, saveFileName));
            }
            boardCreateRequest.setFileInfo(fileInfoDto);
        }
        boardCreateRequest.setMemberId(loginId);
        boardService.createBoard(boardCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/board/{boardId}")
    public ResponseEntity<Void> modify(BoardModifyRequest boardModifyRequest) {
        boardService.modifyBoard(boardModifyRequest);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable int boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    // match
    @GetMapping("/match")
    public ResponseEntity<List<MatchingBoardDto>> readMatchingBoardAll() {
        List<MatchingBoardDto> resultList = boardService.listMatchingBoard();
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/match/{boardId}")
    public ResponseEntity<MatchingBoardDto> readMatchingBoard(@PathVariable int boardId) {
        MatchingBoardDto result = boardService.getMatchingBoard(boardId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/match")
    public ResponseEntity<Void> createMatchingBoard(
        MatchingBoardCreateRequest matchingBoardCreateRequest, @JwtLogin String loginId) {
        matchingBoardCreateRequest.setMemberId(loginId);
        boardService.createBoardWithMatchingBoard(matchingBoardCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/match/{boardId}")
    public ResponseEntity<Void> modifyMatchingBoard(
        MatchingBoardModifyRequest matchingBoardModifyRequest) {
        boardService.modifyMatchingBoard(matchingBoardModifyRequest);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/match/{boardId}")
    public ResponseEntity<Void> deleteMatchingBoard(@PathVariable int boardId) {
        boardService.deleteMatchingBoard(boardId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    // travel
    @GetMapping("/travel")
    public ResponseEntity<?> readTravelBoard() {
        return ResponseEntity.ok("travel");
    }

    // like
    @PostMapping("/like/{boardId}")
    public ResponseEntity<Void> like(LikeDto likeDto, @JwtLogin String loginId) {
        likeDto.setMemberId(loginId);
        boardService.clickLike(likeDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // comment
    @PostMapping("/comment/{boardId}")
    public ResponseEntity<Void> commentBoard(CommentDto commentDto, @JwtLogin String loginId) {
        commentDto.setMemberId(loginId);
        boardService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        boardService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Void> modifyComment(CommentModifyRequest commentModifyRequest) {
        boardService.modifyComment(commentModifyRequest);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

}
