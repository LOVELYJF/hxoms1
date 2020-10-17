package com.hxoms.modules.leaderSupervision.vo;

import com.hxoms.modules.selfestimate.entity.OmsSelfestimateItems;

import java.util.List;

/**
 * @authore:wjf
 * @data 2020/10/12 16:44
 * @Description:
 ***/
public class JiWeiNoPassVo {

    private String sid; // 材料id

    private String filename;// 材料名称

    private List<OmsSelfestimateItemsVo> items; //干部监督处 自评

    private String clshsftgOpinion;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<OmsSelfestimateItemsVo> getItems() {
        return items;
    }

    public void setItems(List<OmsSelfestimateItemsVo> items) {
        this.items = items;
    }

    public String getClshsftgOpinion() {
        return clshsftgOpinion;
    }

    public void setClshsftgOpinion(String clshsftgOpinion) {
        this.clshsftgOpinion = clshsftgOpinion;
    }
}
