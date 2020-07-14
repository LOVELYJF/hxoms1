package com.hxoms.modules.publicity.docCallback.entity.parameterEntity;

import com.hxoms.modules.publicity.docCallback.entity.OmsPubDoccallback;
import com.hxoms.modules.publicity.docCallback.entity.OmsPubDoccallbackdetail;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/2
 */
public class CallbackRegVo {
    //回收登记
    private OmsPubDoccallback omsPubDoccallback;
    //回收登记明细
    private List<OmsPubDoccallbackdetail> omsPubDoccallbackdetailList;

    public OmsPubDoccallback getOmsPubDoccallback() {
        return omsPubDoccallback;
    }

    public void setOmsPubDoccallback(OmsPubDoccallback omsPubDoccallback) {
        this.omsPubDoccallback = omsPubDoccallback;
    }

    public List<OmsPubDoccallbackdetail> getOmsPubDoccallbackdetailList() {
        return omsPubDoccallbackdetailList;
    }

    public void setOmsPubDoccallbackdetailList(List<OmsPubDoccallbackdetail> omsPubDoccallbackdetailList) {
        this.omsPubDoccallbackdetailList = omsPubDoccallbackdetailList;
    }

}
