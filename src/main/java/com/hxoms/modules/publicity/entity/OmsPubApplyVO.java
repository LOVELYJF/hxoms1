package com.hxoms.modules.publicity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class OmsPubApplyVO extends OmsPubApply {
    private String name;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date birthDate;

    private String sex;

    private String b0101;

    private String idnumber;
    //涉密信息集合
    private List<OmsSmrOldInfoVO> omsSmrOldInfoVOS;
    //主要家庭人员信息
    private List<A36> a36List;

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public List<OmsSmrOldInfoVO> getOmsSmrOldInfoVOS() {
        return omsSmrOldInfoVOS;
    }

    public void setOmsSmrOldInfoVOS(List<OmsSmrOldInfoVO> omsSmrOldInfoVOS) {
        this.omsSmrOldInfoVOS = omsSmrOldInfoVOS;
    }

    public List<A36> getA36List() {
        return a36List;
    }

    public void setA36List(List<A36> a36List) {
        this.a36List = a36List;
    }
}
