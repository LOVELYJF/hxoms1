package com.hxoms.modules.privateabroad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_pri_delay_apply", TableDescription="延期回国申请")
public class OmsPriDelayApply {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "APPLY_ID",   FieldDescription="申请ID")
    private String applyId;

    @ColumnAnnotation(FieldName = "PROCPERSON_ID",   FieldDescription="登记备案表id")
    private String procpersonId;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="干部id")
    private String a0100;

    @ColumnAnnotation(FieldName = "APPLY_STATUS",   FieldDescription="申请状态(1草稿、2生成材料、3打印材料、4自评上报、5业务办理、6征求有关单位意见、7待反馈意见、8组织部审批、9核实批件、10制作备案表、11已办结、12待领证、13已领证、14撤销)")
    private Integer applyStatus;

    @ColumnAnnotation(FieldName = "CANCEL_REASON",   FieldDescription="撤销原因")
    private String cancelReason;

    @ColumnAnnotation(FieldName = "ESTIMATE_RETURNTIME",   FieldDescription="预计回国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date estimateReturntime;

    @ColumnAnnotation(FieldName = "DELAY_REASON",   FieldDescription="延期理由")
    private String delayReason;

    @ColumnAnnotation(FieldName = "APPLY_TIME",   FieldDescription="申请时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date applyTime;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "leader_batch_id",   FieldDescription="")
    private String leaderBatchId;

    @ColumnAnnotation(FieldName = "CLSHSFTG",   FieldDescription="材料审核是否通过")
    private String clshsftg;

    @ColumnAnnotation(FieldName = "JDCJL",   FieldDescription="监督处最终结论")
    private String jdcjl;

    @ColumnAnnotation(FieldName = "JWJL",   FieldDescription="纪委结论")
    private String jwjl;

    @ColumnAnnotation(FieldName = "ZZJL",   FieldDescription="最终结论")
    private String zzjl;

    @ColumnAnnotation(FieldName = "SFZQJWYJ",   FieldDescription="是否需要征求纪委意见")
    private String sfzqjwyj;

    @ColumnAnnotation(FieldName = "SCZQJWYJSJ",   FieldDescription="上次征求纪委意见时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date sczqjwyjsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getProcpersonId() {
        return procpersonId;
    }

    public void setProcpersonId(String procpersonId) {
        this.procpersonId = procpersonId == null ? null : procpersonId.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
    }

    public Date getEstimateReturntime() {
        return estimateReturntime;
    }

    public void setEstimateReturntime(Date estimateReturntime) {
        this.estimateReturntime = estimateReturntime;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason == null ? null : delayReason.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getLeaderBatchId() {
        return leaderBatchId;
    }

    public void setLeaderBatchId(String leaderBatchId) {
        this.leaderBatchId = leaderBatchId == null ? null : leaderBatchId.trim();
    }

    public String getClshsftg() {
        return clshsftg;
    }

    public void setClshsftg(String clshsftg) {
        this.clshsftg = clshsftg == null ? null : clshsftg.trim();
    }

    public String getJdcjl() {
        return jdcjl;
    }

    public void setJdcjl(String jdcjl) {
        this.jdcjl = jdcjl == null ? null : jdcjl.trim();
    }

    public String getJwjl() {
        return jwjl;
    }

    public void setJwjl(String jwjl) {
        this.jwjl = jwjl == null ? null : jwjl.trim();
    }

    public String getZzjl() {
        return zzjl;
    }

    public void setZzjl(String zzjl) {
        this.zzjl = zzjl == null ? null : zzjl.trim();
    }

    public String getSfzqjwyj() {
        return sfzqjwyj;
    }

    public void setSfzqjwyj(String sfzqjwyj) {
        this.sfzqjwyj = sfzqjwyj == null ? null : sfzqjwyj.trim();
    }

    public Date getSczqjwyjsj() {
        return sczqjwyjsj;
    }

    public void setSczqjwyjsj(Date sczqjwyjsj) {
        this.sczqjwyjsj = sczqjwyjsj;
    }
}