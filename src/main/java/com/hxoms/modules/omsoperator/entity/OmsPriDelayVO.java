package com.hxoms.modules.omsoperator.entity;

import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;


/**
 * @desc: 延期回国扩展
 * @author: lijing
 * @date: 2020-06-03
 */
public class OmsPriDelayVO extends OmsPriDelayApply{
    //单位名称
    private String b0101;
    //身份证号码
    private String idnumber;
    //性别
    private String sex;
    //职务
    private String postrank;
    //姓名
    private String name;

    public String getPostrank() {
        return postrank;
    }

    public void setPostrank(String postrank) {
        this.postrank = postrank;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
