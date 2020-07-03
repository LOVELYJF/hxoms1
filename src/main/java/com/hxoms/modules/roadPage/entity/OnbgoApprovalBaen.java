package com.hxoms.modules.roadPage.entity;

import com.hxoms.common.utils.Constants;
import com.hxoms.modules.publicity.entity.OmsPubApply;

import java.util.List;

public class OnbgoApprovalBaen {

    private String pwh;
    private String pwhd;
    private List<OmsPubApply> omspubapply;

    public String getPwh() {
        return pwh;
    }

    public void setPwh(String pwh) {
        this.pwh = pwh;
    }

    public String getPwhd() {
        return pwhd;
    }

    public void setPwhd(String pwhd) {
        this.pwhd = pwhd;
    }

    public List<OmsPubApply> getOmspubapply() {
        return omspubapply;
    }

    public void setOmspubapply(List<OmsPubApply> omspubapply) {
        this.omspubapply = omspubapply;
    }
}
