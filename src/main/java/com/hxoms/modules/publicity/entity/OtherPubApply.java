package com.hxoms.modules.publicity.entity;

import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;

import java.util.List;

public class OtherPubApply {
    /** 涉密信息*/
    private List<OmsSmrOldInfoVO> omsSmrOldInfoVOS;
    /** 负面信息*/
    private  String fmxx;
    /** 单位接收巡视*/
    private boolean patrolUnit;
    /** 家庭主要成员*/
    private List<A36> a36List;

    public List<OmsSmrOldInfoVO> getOmsSmrOldInfoVOS() {
        return omsSmrOldInfoVOS;
    }

    public void setOmsSmrOldInfoVOS(List<OmsSmrOldInfoVO> omsSmrOldInfoVOS) {
        this.omsSmrOldInfoVOS = omsSmrOldInfoVOS;
    }

    public String getFmxx() {
        return fmxx;
    }

    public void setFmxx(String fmxx) {
        this.fmxx = fmxx;
    }

    public boolean isPatrolUnit() {
        return patrolUnit;
    }

    public void setPatrolUnit(boolean patrolUnit) {
        this.patrolUnit = patrolUnit;
    }

    public List<A36> getA36List() {
        return a36List;
    }

    public void setA36List(List<A36> a36List) {
        this.a36List = a36List;
    }
}
