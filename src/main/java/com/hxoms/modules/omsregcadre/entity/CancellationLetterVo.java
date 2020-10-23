package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.entity.OmsFile;

import java.util.List;

/**
 * @authore:ljj
 * @data 2020/10/22 16:35
 * @Description:
 ***/
public class CancellationLetterVo {

    private List<CancellationLetter> lists;

    private List<OmsFile>  omsFiles;

    private OmsCreateFile omsCreateFile;

    public List<CancellationLetter> getLists() {
        return lists;
    }

    public void setLists(List<CancellationLetter> lists) {
        this.lists = lists;
    }

    public List<OmsFile> getOmsFiles() {
        return omsFiles;
    }

    public void setOmsFiles(List<OmsFile> omsFiles) {
        this.omsFiles = omsFiles;
    }

    public OmsCreateFile getOmsCreateFile() {
        return omsCreateFile;
    }

    public void setOmsCreateFile(OmsCreateFile omsCreateFile) {
        this.omsCreateFile = omsCreateFile;
    }
}
