package com.hxoms.modules.omsregcadre.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_reg_procbatch")
public class OmsRegProcbatch {
    private String id;

    private Date createDate;

    private String createUser;

    private String batchNo;

    private String rfUnnit;

    private String rfUcontacts;

    private String rfUphone;

    private String submitUb0000;

    private String submitUname;

    private String submitUcategory;

    private String submitUcontacts;

    private String submitPhone;

    private Date submitTime;

    private String submitPerson;

    private String status;

    private Date modifyTime;

    private String modifyUser;

    private Integer sortId;

}