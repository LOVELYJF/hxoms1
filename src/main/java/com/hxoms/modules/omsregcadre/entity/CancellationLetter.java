package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.modules.file.entity.OmsFile;

import java.util.List;

/**
 * @authore:wjf
 * @data 2020/10/12 16:35
 * @Description:
 ***/
public class CancellationLetter {

    private String id;

    private String userName;

    private String exitType;

    private String tableCode;

    private String fileType;

    private List<OmsFile>  omsFiles;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExitType() {
        return exitType;
    }

    public void setExitType(String exitType) {
        this.exitType = exitType;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<OmsFile> getOmsFiles() {
        return omsFiles;
    }

    public void setOmsFiles(List<OmsFile> omsFiles) {
        this.omsFiles = omsFiles;
    }
}
