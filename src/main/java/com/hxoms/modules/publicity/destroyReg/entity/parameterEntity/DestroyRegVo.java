package com.hxoms.modules.publicity.destroyReg.entity.parameterEntity;

import com.hxoms.modules.publicity.destroyReg.entity.OmsPubDestroy;
import com.hxoms.modules.publicity.destroyReg.entity.OmsPubDestroydetail;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/2
 */
public class DestroyRegVo {
    //销毁登记
    private OmsPubDestroy omsPubDestroy;
    //销毁登记明细
    private List<OmsPubDestroydetail> omsPubDestroydetailList;

    public OmsPubDestroy getOmsPubDestroy() {
        return omsPubDestroy;
    }

    public void setOmsPubDestroy(OmsPubDestroy omsPubDestroy) {
        this.omsPubDestroy = omsPubDestroy;
    }

    public List<OmsPubDestroydetail> getOmsPubDestroydetailList() {
        return omsPubDestroydetailList;
    }

    public void setOmsPubDestroydetailList(List<OmsPubDestroydetail> omsPubDestroydetailList) {
        this.omsPubDestroydetailList = omsPubDestroydetailList;
    }
}
