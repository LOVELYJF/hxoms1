package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "sys_attachment", TableDescription="")
@ApiModel(value = "")
public class SysAttachment {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="")
    @ApiModelProperty(value="")
    private String id;

    @ColumnAnnotation(FieldName = "TITLE",   FieldDescription="名称")
    @ApiModelProperty(value="名称")
    private String title;

    @ColumnAnnotation(FieldName = "FILETYPE",   FieldDescription="文件类型")
    @ApiModelProperty(value="文件类型")
    private String filetype;

    @ColumnAnnotation(FieldName = "BUSINESSTYPE",   FieldDescription="业务类型")
    @ApiModelProperty(value="业务类型")
    private String businesstype;

    @ColumnAnnotation(FieldName = "U_SORTNO",   FieldDescription="顺序号")
    @ApiModelProperty(value="顺序号")
    private Integer uSortno;

    @ColumnAnnotation(FieldName = "U_CREATOR",   FieldDescription="创建人")
    @ApiModelProperty(value="创建人")
    private String uCreator;

    @ColumnAnnotation(FieldName = "U_CREATEDATE",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="创建时间")
    private Date uCreatedate;

    @ColumnAnnotation(FieldName = "U_CREATORORGID",   FieldDescription="创建人所属机构")
    @ApiModelProperty(value="创建人所属机构")
    private String uCreatororgid;

    @ColumnAnnotation(FieldName = "U_LASTMODIFIEDER",   FieldDescription="最后修改人")
    @ApiModelProperty(value="最后修改人")
    private String uLastmodifieder;

    @ColumnAnnotation(FieldName = "U_LASTMODIFIEDDATE",   FieldDescription="最后修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="最后修改时间")
    private Date uLastmodifieddate;

    @ColumnAnnotation(FieldName = "U_LASTMODIFIEDERORGID",   FieldDescription="最后修改人所属机构")
    @ApiModelProperty(value="最后修改人所属机构")
    private String uLastmodifiederorgid;

    @ColumnAnnotation(FieldName = "U_ISVALID",   FieldDescription="是否有效")
    @ApiModelProperty(value="是否有效")
    private String uIsvalid;

    @ColumnAnnotation(FieldName = "U_AREACODE",   FieldDescription="所属区域")
    @ApiModelProperty(value="所属区域")
    private String uAreacode;

    @ColumnAnnotation(FieldName = "U_ISSYSTEM",   FieldDescription="是否系统内置")
    @ApiModelProperty(value="是否系统内置")
    private String uIssystem;

    @ColumnAnnotation(FieldName = "BUSINESSID",   FieldDescription="分類id")
    @ApiModelProperty(value="分類id")
    private String businessid;

    @ColumnAnnotation(FieldName = "IMAGEFILE",   FieldDescription="图片文件")
    @ApiModelProperty(value="图片文件")
    private String imagefile;

    @ColumnAnnotation(FieldName = "FILE",   FieldDescription="文件")
    @ApiModelProperty(value="文件")
    private String file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype == null ? null : businesstype.trim();
    }

    public Integer getuSortno() {
        return uSortno;
    }

    public void setuSortno(Integer uSortno) {
        this.uSortno = uSortno;
    }

    public String getuCreator() {
        return uCreator;
    }

    public void setuCreator(String uCreator) {
        this.uCreator = uCreator == null ? null : uCreator.trim();
    }

    public Date getuCreatedate() {
        return uCreatedate;
    }

    public void setuCreatedate(Date uCreatedate) {
        this.uCreatedate = uCreatedate;
    }

    public String getuCreatororgid() {
        return uCreatororgid;
    }

    public void setuCreatororgid(String uCreatororgid) {
        this.uCreatororgid = uCreatororgid == null ? null : uCreatororgid.trim();
    }

    public String getuLastmodifieder() {
        return uLastmodifieder;
    }

    public void setuLastmodifieder(String uLastmodifieder) {
        this.uLastmodifieder = uLastmodifieder == null ? null : uLastmodifieder.trim();
    }

    public Date getuLastmodifieddate() {
        return uLastmodifieddate;
    }

    public void setuLastmodifieddate(Date uLastmodifieddate) {
        this.uLastmodifieddate = uLastmodifieddate;
    }

    public String getuLastmodifiederorgid() {
        return uLastmodifiederorgid;
    }

    public void setuLastmodifiederorgid(String uLastmodifiederorgid) {
        this.uLastmodifiederorgid = uLastmodifiederorgid == null ? null : uLastmodifiederorgid.trim();
    }

    public String getuIsvalid() {
        return uIsvalid;
    }

    public void setuIsvalid(String uIsvalid) {
        this.uIsvalid = uIsvalid == null ? null : uIsvalid.trim();
    }

    public String getuAreacode() {
        return uAreacode;
    }

    public void setuAreacode(String uAreacode) {
        this.uAreacode = uAreacode == null ? null : uAreacode.trim();
    }

    public String getuIssystem() {
        return uIssystem;
    }

    public void setuIssystem(String uIssystem) {
        this.uIssystem = uIssystem == null ? null : uIssystem.trim();
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

    public String getImagefile() {
        return imagefile;
    }

    public void setImagefile(String imagefile) {
        this.imagefile = imagefile == null ? null : imagefile.trim();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }
}