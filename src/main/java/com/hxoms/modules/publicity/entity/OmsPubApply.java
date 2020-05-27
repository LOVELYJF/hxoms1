package com.hxoms.modules.publicity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_pub_apply", TableDescription="因公出国（境）备案申请")
public class OmsPubApply {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="工作单位")
    private String b0100;

    @ColumnAnnotation(FieldName = "YSP_ID",   FieldDescription="预审批主键")
    private String yspId;

    @ColumnAnnotation(FieldName = "YSPDW_ID",   FieldDescription="预审批发起单位")
    private String yspdwId;

    @ColumnAnnotation(FieldName = "AGE",   FieldDescription="年龄")
    private String age;

    @ColumnAnnotation(FieldName = "POLITICAL_AFF",   FieldDescription="政治面貌")
    private String politicalAff;

    @ColumnAnnotation(FieldName = "JOB",   FieldDescription="职务")
    private String job;

    @ColumnAnnotation(FieldName = "HEALTH",   FieldDescription="健康状况")
    private String health;

    @ColumnAnnotation(FieldName = "SFSMRY",   FieldDescription="是否为涉密人员")
    private String sfsmry;

    @ColumnAnnotation(FieldName = "SMDJ",   FieldDescription="涉密等级")
    private String smdj;

    @ColumnAnnotation(FieldName = "NSSJ",   FieldDescription="核心涉密人员年审")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date nssj;

    @ColumnAnnotation(FieldName = "YSMDJ1",   FieldDescription="原单位涉密等级1")
    private String ysmdj1;

    @ColumnAnnotation(FieldName = "YTMJSSJ1",   FieldDescription="原单位脱密结束时间1")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date ytmjssj1;

    @ColumnAnnotation(FieldName = "YSMDJ2",   FieldDescription="原单位涉密等级2")
    private String ysmdj2;

    @ColumnAnnotation(FieldName = "YTMJSSJ2",   FieldDescription="原单位脱密结束时间2")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date ytmjssj2;

    @ColumnAnnotation(FieldName = "ZTDW",   FieldDescription="组团单位")
    private String ztdw;

    @ColumnAnnotation(FieldName = "ZTNRZW",   FieldDescription="在组团单位中拟任职务")
    private String ztnrzw;

    @ColumnAnnotation(FieldName = "CGSJ",   FieldDescription="出国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date cgsj;

    @ColumnAnnotation(FieldName = "HGSJ",   FieldDescription="回国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date hgsj;

    @ColumnAnnotation(FieldName = "SDGJ",   FieldDescription="所到国境")
    private String sdgj;

    @ColumnAnnotation(FieldName = "TLSJ",   FieldDescription="在外停留时间")
    private String tlsj;

    @ColumnAnnotation(FieldName = "CFRW",   FieldDescription="出访任务")
    private String cfrw;

    @ColumnAnnotation(FieldName = "CFSY",   FieldDescription="出访事由")
    private String cfsy;

    @ColumnAnnotation(FieldName = "CGSPDW",   FieldDescription="出国任务审批单位")
    private String cgspdw;

    @ColumnAnnotation(FieldName = "PWH",   FieldDescription="批文号")
    private String pwh;

    @ColumnAnnotation(FieldName = "ZYPWH",   FieldDescription="中央批准文号")
    private String zypwh;

    @ColumnAnnotation(FieldName = "ZJCGQK",   FieldDescription="最近一次出国情况")
    private String zjcgqk;

    @ColumnAnnotation(FieldName = "SJCGSJ",   FieldDescription="实际出国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date sjcgsj;

    @ColumnAnnotation(FieldName = "SJHGSJ",   FieldDescription="实际回国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date sjhgsj;

    @ColumnAnnotation(FieldName = "NFGS",   FieldDescription="能否公示")
    private String nfgs;

    @ColumnAnnotation(FieldName = "SFYSP",   FieldDescription="是否预审批")
    private String sfysp;

    @ColumnAnnotation(FieldName = "SFZQJWYJ",   FieldDescription="是否需要征求纪委意见")
    private String sfzqjwyj;

    @ColumnAnnotation(FieldName = "SCZQJWYJSJ",   FieldDescription="上次征求纪委意见时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date sczqjwyjsj;

    @ColumnAnnotation(FieldName = "SFBCZQJWYJ",   FieldDescription="本次是否征求纪委意见")
    private String sfbczqjwyj;

    @ColumnAnnotation(FieldName = "JWJL",   FieldDescription="省纪委结论")
    private String jwjl;

    @ColumnAnnotation(FieldName = "CLSHSFTG",   FieldDescription="材料审核是否通过")
    private String clshsftg;

    @ColumnAnnotation(FieldName = "JDCJL",   FieldDescription="监督处最终结论")
    private String jdcjl;

    @ColumnAnnotation(FieldName = "SFZYLD",   FieldDescription="是否主要领导")
    private String sfzyld;

    @ColumnAnnotation(FieldName = "SFLG",   FieldDescription="是否裸官")
    private String sflg;

    @ColumnAnnotation(FieldName = "POZNSJD",   FieldDescription="配偶子女受监督")
    private String poznsjd;

    @ColumnAnnotation(FieldName = "FMXX",   FieldDescription="负面信息")
    private String fmxx;

    @ColumnAnnotation(FieldName = "DWJSXS",   FieldDescription="单位正在接受巡视")
    private String dwjsxs;

    @ColumnAnnotation(FieldName = "ZZJL",   FieldDescription="最终结论")
    private String zzjl;

    @ColumnAnnotation(FieldName = "CXYY",   FieldDescription="撤销原因")
    private String cxyy;

    @ColumnAnnotation(FieldName = "SFTSRY",   FieldDescription="是否特殊人员")
    private String sftsry;

    @ColumnAnnotation(FieldName = "SFZB",   FieldDescription="是否增补")
    private String sfzb;

    @ColumnAnnotation(FieldName = "SFBG",   FieldDescription="是否变更")
    private String sfbg;

    @ColumnAnnotation(FieldName = "SQZT",   FieldDescription="申请状态")
    private String sqzt;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="")
    private String createUser;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "SORT_ID",   FieldDescription="")
    private Integer sortId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public String getYspId() {
        return yspId;
    }

    public void setYspId(String yspId) {
        this.yspId = yspId == null ? null : yspId.trim();
    }

    public String getYspdwId() {
        return yspdwId;
    }

    public void setYspdwId(String yspdwId) {
        this.yspdwId = yspdwId == null ? null : yspdwId.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getPoliticalAff() {
        return politicalAff;
    }

    public void setPoliticalAff(String politicalAff) {
        this.politicalAff = politicalAff == null ? null : politicalAff.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health == null ? null : health.trim();
    }

    public String getSfsmry() {
        return sfsmry;
    }

    public void setSfsmry(String sfsmry) {
        this.sfsmry = sfsmry == null ? null : sfsmry.trim();
    }

    public String getSmdj() {
        return smdj;
    }

    public void setSmdj(String smdj) {
        this.smdj = smdj == null ? null : smdj.trim();
    }

    public Date getNssj() {
        return nssj;
    }

    public void setNssj(Date nssj) {
        this.nssj = nssj;
    }

    public String getYsmdj1() {
        return ysmdj1;
    }

    public void setYsmdj1(String ysmdj1) {
        this.ysmdj1 = ysmdj1 == null ? null : ysmdj1.trim();
    }

    public Date getYtmjssj1() {
        return ytmjssj1;
    }

    public void setYtmjssj1(Date ytmjssj1) {
        this.ytmjssj1 = ytmjssj1;
    }

    public String getYsmdj2() {
        return ysmdj2;
    }

    public void setYsmdj2(String ysmdj2) {
        this.ysmdj2 = ysmdj2 == null ? null : ysmdj2.trim();
    }

    public Date getYtmjssj2() {
        return ytmjssj2;
    }

    public void setYtmjssj2(Date ytmjssj2) {
        this.ytmjssj2 = ytmjssj2;
    }

    public String getZtdw() {
        return ztdw;
    }

    public void setZtdw(String ztdw) {
        this.ztdw = ztdw == null ? null : ztdw.trim();
    }

    public String getZtnrzw() {
        return ztnrzw;
    }

    public void setZtnrzw(String ztnrzw) {
        this.ztnrzw = ztnrzw == null ? null : ztnrzw.trim();
    }

    public Date getCgsj() {
        return cgsj;
    }

    public void setCgsj(Date cgsj) {
        this.cgsj = cgsj;
    }

    public Date getHgsj() {
        return hgsj;
    }

    public void setHgsj(Date hgsj) {
        this.hgsj = hgsj;
    }

    public String getSdgj() {
        return sdgj;
    }

    public void setSdgj(String sdgj) {
        this.sdgj = sdgj == null ? null : sdgj.trim();
    }

    public String getTlsj() {
        return tlsj;
    }

    public void setTlsj(String tlsj) {
        this.tlsj = tlsj == null ? null : tlsj.trim();
    }

    public String getCfrw() {
        return cfrw;
    }

    public void setCfrw(String cfrw) {
        this.cfrw = cfrw == null ? null : cfrw.trim();
    }

    public String getCfsy() {
        return cfsy;
    }

    public void setCfsy(String cfsy) {
        this.cfsy = cfsy == null ? null : cfsy.trim();
    }

    public String getCgspdw() {
        return cgspdw;
    }

    public void setCgspdw(String cgspdw) {
        this.cgspdw = cgspdw == null ? null : cgspdw.trim();
    }

    public String getPwh() {
        return pwh;
    }

    public void setPwh(String pwh) {
        this.pwh = pwh == null ? null : pwh.trim();
    }

    public String getZypwh() {
        return zypwh;
    }

    public void setZypwh(String zypwh) {
        this.zypwh = zypwh == null ? null : zypwh.trim();
    }

    public String getZjcgqk() {
        return zjcgqk;
    }

    public void setZjcgqk(String zjcgqk) {
        this.zjcgqk = zjcgqk == null ? null : zjcgqk.trim();
    }

    public Date getSjcgsj() {
        return sjcgsj;
    }

    public void setSjcgsj(Date sjcgsj) {
        this.sjcgsj = sjcgsj;
    }

    public Date getSjhgsj() {
        return sjhgsj;
    }

    public void setSjhgsj(Date sjhgsj) {
        this.sjhgsj = sjhgsj;
    }

    public String getNfgs() {
        return nfgs;
    }

    public void setNfgs(String nfgs) {
        this.nfgs = nfgs == null ? null : nfgs.trim();
    }

    public String getSfysp() {
        return sfysp;
    }

    public void setSfysp(String sfysp) {
        this.sfysp = sfysp == null ? null : sfysp.trim();
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

    public String getSfbczqjwyj() {
        return sfbczqjwyj;
    }

    public void setSfbczqjwyj(String sfbczqjwyj) {
        this.sfbczqjwyj = sfbczqjwyj == null ? null : sfbczqjwyj.trim();
    }

    public String getJwjl() {
        return jwjl;
    }

    public void setJwjl(String jwjl) {
        this.jwjl = jwjl == null ? null : jwjl.trim();
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

    public String getSfzyld() {
        return sfzyld;
    }

    public void setSfzyld(String sfzyld) {
        this.sfzyld = sfzyld == null ? null : sfzyld.trim();
    }

    public String getSflg() {
        return sflg;
    }

    public void setSflg(String sflg) {
        this.sflg = sflg == null ? null : sflg.trim();
    }

    public String getPoznsjd() {
        return poznsjd;
    }

    public void setPoznsjd(String poznsjd) {
        this.poznsjd = poznsjd == null ? null : poznsjd.trim();
    }

    public String getFmxx() {
        return fmxx;
    }

    public void setFmxx(String fmxx) {
        this.fmxx = fmxx == null ? null : fmxx.trim();
    }

    public String getDwjsxs() {
        return dwjsxs;
    }

    public void setDwjsxs(String dwjsxs) {
        this.dwjsxs = dwjsxs == null ? null : dwjsxs.trim();
    }

    public String getZzjl() {
        return zzjl;
    }

    public void setZzjl(String zzjl) {
        this.zzjl = zzjl == null ? null : zzjl.trim();
    }

    public String getCxyy() {
        return cxyy;
    }

    public void setCxyy(String cxyy) {
        this.cxyy = cxyy == null ? null : cxyy.trim();
    }

    public String getSftsry() {
        return sftsry;
    }

    public void setSftsry(String sftsry) {
        this.sftsry = sftsry == null ? null : sftsry.trim();
    }

    public String getSfzb() {
        return sfzb;
    }

    public void setSfzb(String sfzb) {
        this.sfzb = sfzb == null ? null : sfzb.trim();
    }

    public String getSfbg() {
        return sfbg;
    }

    public void setSfbg(String sfbg) {
        this.sfbg = sfbg == null ? null : sfbg.trim();
    }

    public String getSqzt() {
        return sqzt;
    }

    public void setSqzt(String sqzt) {
        this.sqzt = sqzt == null ? null : sqzt.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
}