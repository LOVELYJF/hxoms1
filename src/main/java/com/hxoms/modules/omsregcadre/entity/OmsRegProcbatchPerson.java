package com.hxoms.modules.omsregcadre.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_reg_procbatchperson")
public class OmsRegProcbatchPerson {
    private String id;

    private String batchId;

    private String surname;

    private String a0100;

    private String name;

    private String sex;

    private Date birthDate;

    private String idnumber;

    private String registeResidenceCode;

    private String registeResidence;

    private String workUnit;

    private String secretTerm;

    private String post;

    private String postCode;

    private String personManager;

    private String identityCode;

    private String identity;

    private String secretStatus;

    private Date secretDate;

}