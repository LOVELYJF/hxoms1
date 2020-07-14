package com.hxoms.modules.roster.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "sys_register", TableDescription="InnoDB free: 31744 kB")
public class SysRegister {
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

    @ColumnAnnotation(FieldName = "node_name",   FieldDescription="关注信息名称")
    private String nodeName;

    @ColumnAnnotation(FieldName = "att_type",   FieldDescription="关注分类")
    private String attType;

    @ColumnAnnotation(FieldName = "order_index",   FieldDescription="排序")
    private Integer orderIndex;

    @ColumnAnnotation(FieldName = "att_content",   FieldDescription="关注内容")
    private String attContent;

    private SysRoster sysRoster;

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

    public String getAttType() {
        return attType;
    }

    public void setAttType(String attType) {
        this.attType = attType == null ? null : attType.trim();
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getAttContent() {
        return attContent;
    }

    public void setAttContent(String attContent) {
        this.attContent = attContent == null ? null : attContent.trim();
    }

    public SysRoster getSysRoster() {
        return sysRoster;
    }

    public void setSysRoster(SysRoster sysRoster) {
        this.sysRoster = sysRoster;
    }
}