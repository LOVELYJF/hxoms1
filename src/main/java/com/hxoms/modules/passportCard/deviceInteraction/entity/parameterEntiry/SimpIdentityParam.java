package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/14
 */
@ApiModel(value = "简单身份信息")
public class SimpIdentityParam {
    //姓名
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value="姓名",required = true)
    private String name;
    //身份证号
    @NotBlank(message = "身份证号不能为空")
    @ApiModelProperty(value="身份证号",required = true)
    private String idNo;
    //有效期至
    @NotNull(message = "有效期至不能为空")
    @ApiModelProperty(value="有效期至",required = true)
    private Date yxqz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(String yxqz) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        this.yxqz = simpleDateFormat.parse(yxqz);
    }

}
