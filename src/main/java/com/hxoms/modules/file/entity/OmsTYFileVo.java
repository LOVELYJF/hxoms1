package com.hxoms.modules.file.entity;

import java.util.List;

public class OmsTYFileVo {
    /** 文件*/
    private OmsFile file;
    /** 关键字*/
    private List<OmsReplaceKeywords> omsReplaceKeywordList;

    public OmsFile getFile() {
        return file;
    }

    public void setFile(OmsFile file) {
        this.file = file;
    }

    public List<OmsReplaceKeywords> getOmsReplaceKeywordList() {
        return omsReplaceKeywordList;
    }

    public void setOmsReplaceKeywordList(List<OmsReplaceKeywords> omsReplaceKeywordList) {
        this.omsReplaceKeywordList = omsReplaceKeywordList;
    }
}
