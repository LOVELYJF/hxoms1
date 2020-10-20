package com.hxoms.modules.file.entity.paramentity;/*
 * @description:用于根据登记备案人员及业务类型获取涉密等级及可用材料模板类型
 * @author 杨波
 * @date:2020-10-20
 */

import java.util.Date;
import java.util.List;

public class SecretLevelAndFileType {
    //涉密等级，0 非涉密  1 一般 2重要 3核心
    private Integer secretLevel;
    //文件类型，使用人员（1所有人员 2非涉密人员 3涉密人员 4脱密期人员 5主要领导 6挂职干部 7核心涉密 8用户自定义）
    private List<String> fileType;
    //是否主要领导
    private String isLeader;
    //脱密期
    private Date declassificationEndDate;
    //离琼挂职
    private String lqgz;

    public Integer getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(Integer secretLevel) {
        this.secretLevel = secretLevel;
    }

    public List<String> getFileType() {
        return fileType;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }


    public String getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(String isLeader) {
        this.isLeader = isLeader;
    }

    public Date getDeclassificationEndDate() {
        return declassificationEndDate;
    }

    public void setDeclassificationEndDate(Date declassificationEndDate) {
        this.declassificationEndDate = declassificationEndDate;
    }

    public String getLqgz() {
        return lqgz;
    }

    public void setLqgz(String lqgz) {
        this.lqgz = lqgz;
    }

}
