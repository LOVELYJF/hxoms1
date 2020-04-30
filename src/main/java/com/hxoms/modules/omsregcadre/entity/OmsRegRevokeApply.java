package com.hxoms.modules.omsregcadre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_reg_revokeapply")
public class OmsRegRevokeApply {

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("A0100")
    private String a0100;

    @TableField("CREATE_USER")
    private String createUser;

    @TableField("CREATE_DATE")
    private Date createDate;

    @TableField("SURNAME")
    private String surname;

    @TableField("NAME")
    private String name;

    @TableField("SEX")
    private String sex;

    @TableField("BIRTH_DATE")
    private Date birthDate;

    @TableField("IDNUMBER")
    private String idnumber;

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

    @TableField("SECRET_STATUS")
    private String secretStatus;

    @TableField("SECRET_START_DATE")
    private Date secretStartDate;

    @TableField("SECRET_END_DATE")
    private Date secretEndDate;

    @TableField("APPLY_REASON")
    private String applyReason;

    @TableField("STATUS")
    private String status;


}