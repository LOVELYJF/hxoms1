package com.hxoms.modules.omsregcadre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_reg_procpersoninfo")
public class OmsRegProcpersonInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("A0100")
    private String a0100;

    @TableField("RF_B0000")
    private String rfB0000;

    @TableField("INBOUND_FLAG")
    private String inboundFlag;

    @TableField("RF_STATUS")
    private String rfStatus;

    @TableField("CHECK_STATUS")
    private String checkStatus;

    @TableField("INCUMBENCY_STATUS")
    private String incumbencyStatus;

    @TableField("DATA_TYPE")
    private String dataType;

    @TableField("SURNAME")
    private String surname;

    @TableField("NAME")
    private String name;

    @TableField("PY")
    private String py;

    @TableField("SEX")
    private String sex;

    @TableField("BIRTH_DATE")
    private String birthDate;

    @TableField("NATION")
    private String nation;

    @TableField("IDNUMBER")
    private String idnumber;

    @TableField("POLITICAL_AFFI")
    private String politicalAffi;

    @TableField("HEALTH")
    private String health;

    @TableField("REGISTE_RESIDENCE_CODE")
    private String registeResidenceCode;

    @TableField("REGISTE_RESIDENCE")
    private String registeResidence;

    @TableField("WORK_UNIT")
    private String workUnit;

    @TableField("POST_CODE")
    private String postCode;

    @TableField("POST")
    private String post;

    @TableField("PERSON_MANAGER")
    private String personManager;

    @TableField("IDENTITY_CODE")
    private String identityCode;

    @TableField("IDENTITY")
    private String identity;

    @TableField("SECRET_LEVEL")
    private String secretLevel;

    @TableField("DECRYPT_STARTDATE")
    private Date secretStartDate;

    @TableField("DECRYPT_ENDDATE")
    private Date secretEndDate;

    @TableField("MAIN_LEADER")
    private String mainLeader;

    @TableField("LICENCE_IDENTITY")
    private Integer licenceIdentity;

    @TableField("NF")
    private String nf;

    @TableField("FJGNF")
    private String fjgnf;

    @TableField("XRXGW")
    private String xrxgw;

    @TableField("LQGZ")
    private String lqgz;

    @TableField("DQGZ")
    private String dqgz;

    @TableField("REPLYOPINION")
    private String replyopinion;

    @TableField("ABROADTIME")
    private Date abroadtime;

    @TableField("REASON")
    private String reason;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_USER")
    private String createUser;

    @TableField("MODIFY_TIME")
    private Date modifyTime;

    @TableField("MODIFY_USER")
    private String modifyUser;

    @TableField("MODIFY_TIME")
    private Integer sortId;

}