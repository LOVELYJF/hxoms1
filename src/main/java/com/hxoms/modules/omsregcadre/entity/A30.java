package com.hxoms.modules.omsregcadre.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("A30")
public class A30 {
    private String a3000;

    private String id;

    private String isDeleted;

    private String modifyUser;

    private Date modifyTime;

    private String nodeId;

    private String a0100;

    private String a3001;

    private String a3004;

    private String a3007a;

    private String a3034;

    private String a3038;

    private String isNormal;

    private String operateBatch;

    private String a3117a;

    private String a3137;

    private String a3101;

    private byte[] timeStamp;


}