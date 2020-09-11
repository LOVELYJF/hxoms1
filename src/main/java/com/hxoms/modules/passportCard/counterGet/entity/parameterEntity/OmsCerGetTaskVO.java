package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 功能描述: <br>
 * 〈证照领取表扩展表〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/9/10 17:03
 */
public class OmsCerGetTaskVO extends OmsCerGetTask {
    /** 备案表干部身份证号*/
    private String idnumber;
    /** 工作单位*/
    private String b0101;
    /** 职务*/
    private String postrank;
    /** 性别*/
    private String sex;
    /** 归还时间 */
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date endTime;

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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
