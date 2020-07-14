package com.hxoms.modules.roster.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@TableAnnotation(TableName = "sys_roster", TableDescription="InnoDB free: 31744 kB")
public class SysRoster {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="修改用户")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "pid",   FieldDescription="父ID")
    private String pid;

    @ColumnAnnotation(FieldName = "node_name",   FieldDescription="名册名称")
    private String nodeName;

    @ColumnAnnotation(FieldName = "r_type",   FieldDescription="名册分类")
    private String rType;

    @ColumnAnnotation(FieldName = "order_index",   FieldDescription="排序")
    private Integer orderIndex;

    private List<SysRegister> registerList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType == null ? null : rType.trim();
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public List<SysRegister> getRegisterList() {
        return registerList;
    }

    public void setRegisterList(List<SysRegister> registerList) {
        this.registerList = registerList;
    }
}