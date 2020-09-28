package com.hxoms.modules.omssmrperson.entity;

import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders;

/**
 * @desc: 原涉密信息扩展类
 * @author: lijing
 * @date: 2020-06-06
 */
public class OmsSmrOldInfoVO extends OmsSmrOldInfo{
    //机构名称
    private String b0101;
    private String a0101;
    private String sex;
    private String birthDay;
    private String nation;
    private String a0141;
    private String idCardNumber;
    private String msg;//备注
    private String dataSource;//数据来源

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public String getA0101() {
        return a0101;
    }

    public void setA0101(String a0101) {
        this.a0101 = a0101;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getA0141() {
        return a0141;
    }

    public void setA0141(String a0141) {
        this.a0141 = a0141;
    }


    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
