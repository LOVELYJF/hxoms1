package com.hxoms.modules.file.entity;

import java.util.List;

public class OmsMB {
    //文件
    private OmsFile omsFile;
    //关键字
    private List<OmsReplaceKeywords> omsReplaceKeywordList;

    public OmsFile getOmsFile() {
        return omsFile;
    }

    public void setOmsFile(OmsFile omsFile) {
        this.omsFile = omsFile;
    }

    public List<OmsReplaceKeywords> getOmsReplaceKeywordList() {
        return omsReplaceKeywordList;
    }

    public void setOmsReplaceKeywordList(List<OmsReplaceKeywords> omsReplaceKeywordList) {
        this.omsReplaceKeywordList = omsReplaceKeywordList;
    }
}
