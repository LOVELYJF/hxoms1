package com.hxoms.modules.publicity.docCallback.entity.parameterEntity;

import com.hxoms.modules.publicity.docCallback.entity.OmsPubDoccallback;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/2
 */
public class ExportRequestPara {
    //查询参数
    private SelCallbackRegByQuaVo selCallbackRegByQuaVo;
    //标题
    private List<String> titleList;
    //批件回收数据
    private List<OmsPubDoccallback> omsPubDoccallbackList;

    public SelCallbackRegByQuaVo getSelCallbackRegByQuaVo() {
        return selCallbackRegByQuaVo;
    }

    public void setSelCallbackRegByQuaVo(SelCallbackRegByQuaVo selCallbackRegByQuaVo) {
        this.selCallbackRegByQuaVo = selCallbackRegByQuaVo;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<OmsPubDoccallback> getOmsPubDoccallbackList() {
        return omsPubDoccallbackList;
    }

    public void setOmsPubDoccallbackList(List<OmsPubDoccallback> omsPubDoccallbackList) {
        this.omsPubDoccallbackList = omsPubDoccallbackList;
    }
}
