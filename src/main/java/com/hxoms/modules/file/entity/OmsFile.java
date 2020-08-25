package com.hxoms.modules.file.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value = "系统材料清单")
@TableAnnotation(TableName = "oms_file", TableDescription="系统材料清单")
public class OmsFile {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value = "主键")
    private String id;

    @ColumnAnnotation(FieldName = "FILE_ID",   FieldDescription="文件ID（初始化文件为空）")
    @ApiModelProperty(value = "文件ID")
    private String fileId;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="机构ID（初始化文件为空）")
    @ApiModelProperty(value = "机构ID")
    private String b0100;

    @ColumnAnnotation(FieldName = "FILE_NAME",   FieldDescription="文件名称")
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ColumnAnnotation(FieldName = "FILE_SHORTNAME",   FieldDescription="文件简称")
    @ApiModelProperty(value = "文件简称")
    private String fileShortname;

    @ColumnAnnotation(FieldName = "FILE_TYPE",   FieldDescription="文件类别（1系统 2非涉密人员 3涉密人员 4涉密人员（重要，一般不是本单位））")
    @ApiModelProperty(value = "文件类别")
    private String fileType;

    @ColumnAnnotation(FieldName = "TABLE_CODE",   FieldDescription="关联模块(因公、因私、延期回国)")
    @ApiModelProperty(value = "关联模块")
    private String tableCode;

    @ColumnAnnotation(FieldName = "IS_EDIT",   FieldDescription="是否可编辑（1文件 2单页单面 3双页 4双面单页）")
    @ApiModelProperty(value = "是否可编辑（1文件 2单页单面 3双页 4双面单页）")
    private String isEdit;

    @ColumnAnnotation(FieldName = "SEAL_DESC",   FieldDescription="签字盖章描述")
    private String sealDesc;

    @ColumnAnnotation(FieldName = "RUN_SQL",   FieldDescription="执行sql")
    private String runSql;

    @ColumnAnnotation(FieldName = "PRINT_NUM",   FieldDescription="打印份数")
    @ApiModelProperty(value = "打印份数")
    private Integer printNum;

    @ColumnAnnotation(FieldName = "ISFILE_LIST",   FieldDescription="是否加入材料清单")
    @ApiModelProperty(value = "是否加入材料清单")
    private String isfileList;

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

    @ColumnAnnotation(FieldName = "SORT_ID",   FieldDescription="排序")
    private Integer sortId;

    @ColumnAnnotation(FieldName = "FRONT_CONTENT",   FieldDescription="可编辑文件（正面，第一页）")
    @ApiModelProperty(value = "可编辑文件（正面，第一页）")
    private String frontContent;

    @ColumnAnnotation(FieldName = "BANK_CONTENT",   FieldDescription="可编辑文件（反面，第二页）")
    @ApiModelProperty(value = "可编辑文件（反面，第二页）")
    private String bankContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileShortname() {
        return fileShortname;
    }

    public void setFileShortname(String fileShortname) {
        this.fileShortname = fileShortname == null ? null : fileShortname.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode == null ? null : tableCode.trim();
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit == null ? null : isEdit.trim();
    }

    public String getSealDesc() {
        return sealDesc;
    }

    public void setSealDesc(String sealDesc) {
        this.sealDesc = sealDesc == null ? null : sealDesc.trim();
    }

    public String getRunSql() {
        return runSql;
    }

    public void setRunSql(String runSql) {
        this.runSql = runSql == null ? null : runSql.trim();
    }

    public Integer getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Integer printNum) {
        this.printNum = printNum;
    }

    public String getIsfileList() {
        return isfileList;
    }

    public void setIsfileList(String isfileList) {
        this.isfileList = isfileList == null ? null : isfileList.trim();
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

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getFrontContent() {
        return frontContent;
    }

    public void setFrontContent(String frontContent) {
        this.frontContent = frontContent == null ? null : frontContent.trim();
    }

    public String getBankContent() {
        return bankContent;
    }

    public void setBankContent(String bankContent) {
        this.bankContent = bankContent == null ? null : bankContent.trim();
    }
}