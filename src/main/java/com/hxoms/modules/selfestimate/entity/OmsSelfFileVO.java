package com.hxoms.modules.selfestimate.entity;

import com.hxoms.modules.file.entity.OmsFile;
import java.util.List;

/**
 * 自评文件扩展类
 */
public class OmsSelfFileVO extends OmsSelfFile{
    //自评项目
    private List<OmsSelfestimateItems> omsSelfestimateItems;
    //文件详情
    private OmsFile omsFile;

    public List<OmsSelfestimateItems> getOmsSelfestimateItems() {
        return omsSelfestimateItems;
    }

    public void setOmsSelfestimateItems(List<OmsSelfestimateItems> omsSelfestimateItems) {
        this.omsSelfestimateItems = omsSelfestimateItems;
    }

    public OmsFile getOmsFile() {
        return omsFile;
    }

    public void setOmsFile(OmsFile omsFile) {
        this.omsFile = omsFile;
    }
}