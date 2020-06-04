package com.hxoms.modules.selfestimate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 自评文件扩展类
 */
public class OmsSelfFileVO extends OmsSelfFile{
    //自评项目
    private List<OmsSelfestimateItems> omsSelfestimateItems;

    public List<OmsSelfestimateItems> getOmsSelfestimateItems() {
        return omsSelfestimateItems;
    }

    public void setOmsSelfestimateItems(List<OmsSelfestimateItems> omsSelfestimateItems) {
        this.omsSelfestimateItems = omsSelfestimateItems;
    }
}