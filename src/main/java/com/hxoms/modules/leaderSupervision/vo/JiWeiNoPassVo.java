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

    private List<OmsSelfestimateItems> items; //干部监督处 自评

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

    public List<OmsSelfestimateItems> getItems() {
        return items;
    }

    public void setItems(List<OmsSelfestimateItems> items) {
        this.items = items;
    }
}
