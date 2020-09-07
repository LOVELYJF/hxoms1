/**
 * 
 */
package com.hxoms.modules.passportCard.initialise.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Lenovo
 *
 */
@ApiModel(value = "因私出国查看证照返回参数")
public class CfCertificateSeeRes {


    @ApiModelProperty(value="证照信息管理表id") 
    private String id;
    
    @ApiModelProperty(value="人员主键") 
    private String a0100;
   
    @ApiModelProperty(value="姓名")
    private String name;
    
   
    @ApiModelProperty(value="身份证号码")
    private String a0184;

  

    @ColumnAnnotation(FieldName = "sex",   FieldDescription="性别")
    @ApiModelProperty(value="性别")
    private String sex;

   
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期")
    private Date csrq;

    @ApiModelProperty(value="国籍")
    private String gj;

   
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    
    @ApiModelProperty(value="证件号码")
    private String zjhm;

    @ApiModelProperty(value="签发机关")
    private String qfjg;

   
    @ApiModelProperty(value="出生地点")
    private String csdd;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="签发日期")
    private Date qfrq;


	@JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="有效期至")
    private Date yxqz;

    
    @ApiModelProperty(value="保管单位(0:干部监督处,1:省委统战部(台办))")
    private String  surelyUnit;
    
    
    @ApiModelProperty(value="保管方式(0:证照机,1:柜台)")
    private String surelyWay;
    
    @ApiModelProperty(value="机柜编号")
    private String  cabinetNum;
    
    @ApiModelProperty(value="机柜位置")
    private String  place;
    
    @ApiModelProperty(value="柜台编号")
    private Integer  counterNum;
    
    @ApiModelProperty(value="户口所在地")
    private String  registeResidence;
  
    
    public String getId() {
 		return id;
 	}

 	public void setId(String id) {
 		this.id = id;
 	}

 	public String getName() {
 		return name;
 	}

 	public void setName(String name) {
 		this.name = name;
 	}

 	public String getA0184() {
 		return a0184;
 	}

 	public void setA0184(String a0184) {
 		this.a0184 = a0184;
 	}

 	public String getSex() {
 		return sex;
 	}

 	public void setSex(String sex) {
 		this.sex = sex;
 	}

 	public Date getCsrq() {
 		return csrq;
 	}

 	public void setCsrq(Date csrq) {
 		this.csrq = csrq;
 	}

 	public String getGj() {
 		return gj;
 	}

 	public void setGj(String gj) {
 		this.gj = gj;
 	}

 	public Integer getZjlx() {
 		return zjlx;
 	}

 	public void setZjlx(Integer zjlx) {
 		this.zjlx = zjlx;
 	}

 	public String getZjhm() {
 		return zjhm;
 	}

 	public void setZjhm(String zjhm) {
 		this.zjhm = zjhm;
 	}

 	public String getQfjg() {
 		return qfjg;
 	}

 	public void setQfjg(String qfjg) {
 		this.qfjg = qfjg;
 	}

 	public String getCsdd() {
 		return csdd;
 	}

 	public void setCsdd(String csdd) {
 		this.csdd = csdd;
 	}

 	public Date getQfrq() {
 		return qfrq;
 	}

 	public void setQfrq(Date qfrq) {
 		this.qfrq = qfrq;
 	}

 	public Date getYxqz() {
 		return yxqz;
 	}

 	public void setYxqz(Date yxqz) {
 		this.yxqz = yxqz;
 	}

    

 	public String getCabinetNum() {
 		return cabinetNum;
 	}

 	public void setCabinetNum(String cabinetNum) {
 		this.cabinetNum = cabinetNum;
 	}

 	public String getPlace() {
 		return place;
 	}

 	public void setPlace(String place) {
 		this.place = place;
 	}

	public Integer getCounterNum() {
		return counterNum;
	}

	public void setCounterNum(Integer counterNum) {
		this.counterNum = counterNum;
	}

	public String getRegisteResidence() {
		return registeResidence;
	}

	public void setRegisteResidence(String registeResidence) {
		this.registeResidence = registeResidence;
	}

	public String getA0100() {
		return a0100;
	}

	public void setA0100(String a0100) {
		this.a0100 = a0100;
	}

	public String getSurelyUnit() {
		return surelyUnit;
	}

	public void setSurelyUnit(String surelyUnit) {
		this.surelyUnit = surelyUnit;
	}

	public String getSurelyWay() {
		return surelyWay;
	}

	public void setSurelyWay(String surelyWay) {
		this.surelyWay = surelyWay;
	}



   
}
