package com.hxoms.modules.selfestimate.entity;

import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;

import java.util.List;

/**
 * 自评文件扩展类
 */
public class OmsSelfFileVO extends OmsSelfFile{
    //自评项目维护列表
    private List<OmsSelfestimateItems> omsSelfestimateItems;
    //生成文件详情
    private OmsCreateFile omsCreateFile;
    //机构id
    private String b0100;
    //机构名称
    private String b0101;
    //机构主要领导
    private List<OmsSupMajorLeader> personInfoVOS;
    //自评项目结果集
    private List<OmsSelfestimateResultitemVO> omsSelfestimateResultitems;

    public List<OmsSelfestimateItems> getOmsSelfestimateItems() {
        return omsSelfestimateItems;
    }

    public void setOmsSelfestimateItems(List<OmsSelfestimateItems> omsSelfestimateItems) {
        this.omsSelfestimateItems = omsSelfestimateItems;
    }

    public OmsCreateFile getOmsCreateFile() {
        return omsCreateFile;
    }

    public void setOmsCreateFile(OmsCreateFile omsCreateFile) {
        this.omsCreateFile = omsCreateFile;
    }

    @Override
    public String getB0100() {
        return b0100;
    }

    @Override
    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public List<OmsSupMajorLeader> getPersonInfoVOS() {
        return personInfoVOS;
    }

    public void setPersonInfoVOS(List<OmsSupMajorLeader> personInfoVOS) {
        this.personInfoVOS = personInfoVOS;
    }

    public List<OmsSelfestimateResultitemVO> getOmsSelfestimateResultitems() {
        return omsSelfestimateResultitems;
    }

    public void setOmsSelfestimateResultitems(List<OmsSelfestimateResultitemVO> omsSelfestimateResultitems) {
        this.omsSelfestimateResultitems = omsSelfestimateResultitems;
    }
}