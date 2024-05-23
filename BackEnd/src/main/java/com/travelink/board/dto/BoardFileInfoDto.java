package com.travelink.board.dto;

public class BoardFileInfoDto {

    private int id;
    private int boardId;
    private String saveFolder;
    private String originalFile;
    private String saveFile;

    public BoardFileInfoDto() {

    }

    public BoardFileInfoDto(int boardId, String saveFolder, int id, String originalFile,
        String saveFile) {
        this.id = id;
        this.boardId = boardId;
        this.saveFolder = saveFolder;
        this.originalFile = originalFile;
        this.saveFile = saveFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoard_id() {
        return boardId;
    }

    public void setBoard_id(int boardId) {
        this.boardId = boardId;
    }

    public String getSaveFolder() {
        return saveFolder;
    }


    public void setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
    }


    public String getOriginalFile() {
        return originalFile;
    }


    public void setOriginalFile(String originalFile) {
        this.originalFile = originalFile;
    }

    public String getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
    }

    @Override
    public String toString() {
        return "BoardFileInfoDto{" +
            "saveFolder='" + saveFolder + '\'' +
            ", originalFile='" + originalFile + '\'' +
            ", saveFile='" + saveFile + '\'' +
            '}';
    }
}
