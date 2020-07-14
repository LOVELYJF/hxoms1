package com.hxoms.modules.publicity.taskSupervise.entity;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/7
 */
public class FileInfo {
    //文件名
    private String fileName;
    //文件数据数组
    private byte[] fileDataByte;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileDataByte() {
        return fileDataByte;
    }

    public void setFileDataByte(byte[] fileDataByte) {
        this.fileDataByte = fileDataByte;
    }
}
