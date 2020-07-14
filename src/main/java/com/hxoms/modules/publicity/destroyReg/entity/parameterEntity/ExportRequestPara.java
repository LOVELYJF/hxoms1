package com.hxoms.modules.publicity.destroyReg.entity.parameterEntity;

import com.hxoms.modules.publicity.destroyReg.entity.OmsPubDestroy;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/2
 */
public class ExportRequestPara {
    //查询参数
    private SelDestroyRegByQuaVo selDestroyRegByQuaVo;
    //标题
    private List<String> titleList;
    //销毁数据
    private List<OmsPubDestroy> omsPubDestroyList;

    public SelDestroyRegByQuaVo getSelDestroyRegByQuaVo() {
        return selDestroyRegByQuaVo;
    }

    public void setSelDestroyRegByQuaVo(SelDestroyRegByQuaVo selDestroyRegByQuaVo) {
        this.selDestroyRegByQuaVo = selDestroyRegByQuaVo;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<OmsPubDestroy> getOmsPubDestroyList() {
        return omsPubDestroyList;
    }

    public void setOmsPubDestroyList(List<OmsPubDestroy> omsPubDestroyList) {
        this.omsPubDestroyList = omsPubDestroyList;
    }
}
