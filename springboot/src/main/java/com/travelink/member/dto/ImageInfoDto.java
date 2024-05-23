package com.travelink.member.dto;

public class ImageInfoDto {

    private String saveFolder;
    private String originalFile;
    private String saveFile;

    private ImageInfoDto() {
    }

    public ImageInfoDto(String saveFolder, String originalFile, String saveFile) {
        this.saveFolder = saveFolder;
        this.originalFile = originalFile;
        this.saveFile = saveFile;
    }

    public void setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
    }

    public void setOriginalFile(String originalFile) {
        this.originalFile = originalFile;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
    }

    public String getSaveFolder() {
        return saveFolder;
    }
    public String getOriginalFile() {
        return originalFile;
    }

    public String getSaveFile() {
        return saveFile;
    }
}
