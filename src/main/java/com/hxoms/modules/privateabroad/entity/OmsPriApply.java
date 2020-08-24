package com.hxoms.modules.privateabroad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_pri_apply", TableDescription="因私出国（境）申请")
public class OmsPriApply {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "PROCPERSON_ID",   FieldDescription="登记备案表id")
    private String procpersonId;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="干部id")
    private String a0100;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="工作单位")
    private String b0100;

    @ColumnAnnotation(FieldName = "POLITICAL_OUTLOOK",   FieldDescription="政治面貌")
    private String politicalOutlook;

    @ColumnAnnotation(FieldName = "HEALTH",   FieldDescription="健康状况")
    private String health;

    @ColumnAnnotation(FieldName = "POSTRANK",   FieldDescription="职务（职级）")
    private String postrank;

    @ColumnAnnotation(FieldName = "ABROAD_TIME",   FieldDescription="出国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date abroadTime;

    @ColumnAnnotation(FieldName = "RETURN_TIME",   FieldDescription="回国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnTime;

    @ColumnAnnotation(FieldName = "GO_COUNTRY",   FieldDescription="出访国家")
    private String goCountry;

    @ColumnAnnotation(FieldName = "OUTSIDE_TIME",   FieldDescription="在外停留时间")
    private Integer outsideTime;

    @ColumnAnnotation(FieldName = "ABROAD_REASONS",   FieldDescription="出国（境）事由")
    private String abroadReasons;

    @ColumnAnnotation(FieldName = "APPLY_TIME",   FieldDescription="申请时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date applyTime;

    @ColumnAnnotation(FieldName = "REVERT_LICENCE_TIME",   FieldDescription="预计归还证照时间(根据回国时间推算，10后必须归还)")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date revertLicenceTime;

    @ColumnAnnotation(FieldName = "CLASSIFICA_POST",   FieldDescription="涉密岗位")
    private String classificaPost;

    @ColumnAnnotation(FieldName = "CLASSIFICATION_LEVEL",   FieldDescription="涉密等级")
    private String classificationLevel;

    @ColumnAnnotation(FieldName = "DECLASSIFICA_STARTTIME",   FieldDescription="脱密期开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date declassificaStarttime;

    @ColumnAnnotation(FieldName = "DECLASSIFICA_ENDTIME",   FieldDescription="脱密期结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date declassificaEndtime;

    @ColumnAnnotation(FieldName = "PAPER",   FieldDescription="证件类型（护照1、港澳通行证2、台湾通行证4 ）")
    private Integer paper;

    @ColumnAnnotation(FieldName = "REAL_GO_COUNTRY",   FieldDescription="实际出访国家")
    private String realGoCountry;

    @ColumnAnnotation(FieldName = "REAL_PASS_COUNTRY",   FieldDescription="实际途径国家")
    private String realPassCountry;

    @ColumnAnnotation(FieldName = "REAL_ABROAD_TIME",   FieldDescription="实际出国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date realAbroadTime;

    @ColumnAnnotation(FieldName = "REAL_RETURN_TIME",   FieldDescription="实际回国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date realReturnTime;

    @ColumnAnnotation(FieldName = "REAL_OUTSIDE_TIME",   FieldDescription="实际停留时间")
    private Integer realOutsideTime;

    @ColumnAnnotation(FieldName = "DESCRIPTION",   FieldDescription="说明情况")
    private String description;

    @ColumnAnnotation(FieldName = "IS_TWO_PASSPORT",   FieldDescription="是否有两个护照")
    private String isTwoPassport;

    @ColumnAnnotation(FieldName = "IS_ABROAD",   FieldDescription="是否出入境")
    private String isAbroad;

    @ColumnAnnotation(FieldName = "COMPARISON_RESULT",   FieldDescription="比对结果")
    private String comparisonResult;

    @ColumnAnnotation(FieldName = "ABROAD_PHONE",   FieldDescription="国外通讯及本人联系电话")
    private String abroadPhone;

    @ColumnAnnotation(FieldName = "SFXYZQJWYJ",   FieldDescription="是否需要征求纪委意见(如果上次征求意见距现在少于35天，不需要再征求意见)")
    private String sfxyzqjwyj;

    @ColumnAnnotation(FieldName = "SFZQJWYJ",   FieldDescription="是否征求纪委意见(1是，0否)")
    private String sfzqjwyj;

    @ColumnAnnotation(FieldName = "PASSPORT",   FieldDescription="护照(1申领新证、2换发、3失效申领、4签注)")
    private Integer passport;

    @ColumnAnnotation(FieldName = "PASSPORT_NUM",   FieldDescription="护照号")
    private String passportNum;

    @ColumnAnnotation(FieldName = "HONGKONGANDMACAO_PASSPORT",   FieldDescription="港澳通行证(1申领新证、2换发、3失效申领、4签注)")
    private Integer hongkongandmacaoPassport;

    @ColumnAnnotation(FieldName = "HONGKONGANDMACAO_PASSPORT_NUM",   FieldDescription="港澳通行证号")
    private String hongkongandmacaoPassportNum;

    @ColumnAnnotation(FieldName = "MACAO_VISA",   FieldDescription="澳门签注")
    private Integer macaoVisa;

    @ColumnAnnotation(FieldName = "HONGKONG_VISA",   FieldDescription="香港签注(1一次、2多次)")
    private Integer hongkongVisa;

    @ColumnAnnotation(FieldName = "TAIWAN_PASSPORT",   FieldDescription="台湾通行证(1申领新证、2换发、3失效申领、4签注)")
    private Integer taiwanPassport;

    @ColumnAnnotation(FieldName = "TAIWAN_PASSPORT_NUM",   FieldDescription="台湾通行证号")
    private String taiwanPassportNum;

    @ColumnAnnotation(FieldName = "TAIWAN_VISA",   FieldDescription="台湾签注(1一次、2多次)")
    private Integer taiwanVisa;

    @ColumnAnnotation(FieldName = "IS_LUOGUAN",   FieldDescription="是否裸官(1是，0否)")
    private String isLuoguan;

    @ColumnAnnotation(FieldName = "IS_SPOUSEANDCHILDREN_SUPERVISE",   FieldDescription="配偶子女受监管(1是，0否)")
    private String isSpouseandchildrenSupervise;

    @ColumnAnnotation(FieldName = "IS_LEADERS",   FieldDescription="是否主要领导(1是，0否)")
    private String isLeaders;

    @ColumnAnnotation(FieldName = "IS_SPECIALLY_APPROVED",   FieldDescription="是否特批(1是，0否)")
    private String isSpeciallyApproved;

    @ColumnAnnotation(FieldName = "NEGATIVE_INFO",   FieldDescription="负面信息")
    private String negativeInfo;

    @ColumnAnnotation(FieldName = "PASSPORT_TERM",   FieldDescription="证件有效期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date passportTerm;

    @ColumnAnnotation(FieldName = "FUNDS_SOURCE",   FieldDescription="经费来源")
    private String fundsSource;

    @ColumnAnnotation(FieldName = "IS_COMPARISON",   FieldDescription="是否已比对(1是、0否    是否与出入境管理局提供的出入境记录进行过比对)")
    private String isComparison;

    @ColumnAnnotation(FieldName = "APPLY_STATUS",   FieldDescription="申请状态(1草稿、2生成材料、3打印材料、4自评上报、5业务办理、6征求有关单位意见、7待反馈意见、8组织部审批、9核实批件、10制作备案表、11已办结、12待领证、13已领证、14撤销)")
    private Integer applyStatus;

    @ColumnAnnotation(FieldName = "CANCEL_REASON",   FieldDescription="撤销原因")
    private String cancelReason;

    @ColumnAnnotation(FieldName = "IS_ENTRUST",   FieldDescription="是否需要委托书")
    private Integer isEntrust;

    @ColumnAnnotation(FieldName = "REMARKS",   FieldDescription="备注")
    private String remarks;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "SORT_ID",   FieldDescription="排序")
    private Integer sortId;

    @ColumnAnnotation(FieldName = "leader_batch_id",   FieldDescription="")
    private String leaderBatchId;

    @ColumnAnnotation(FieldName = "SCZQJWYJSJ",   FieldDescription="上次征求 纪委意见时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date sczqjwyjsj;

    @ColumnAnnotation(FieldName = "CLSHSFTG",   FieldDescription="材料审核是否通过")
    private String clshsftg;

    @ColumnAnnotation(FieldName = "JDCJL",   FieldDescription="监督处最终结论")
    private String jdcjl;

    @ColumnAnnotation(FieldName = "JWJL",   FieldDescription="纪委结论")
    private String jwjl;

    @ColumnAnnotation(FieldName = "ZZJL",   FieldDescription="最终结论")
    private String zzjl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public String getPoliticalOutlook() {
        return politicalOutlook;
    }

    public void setPoliticalOutlook(String politicalOutlook) {
        this.politicalOutlook = politicalOutlook == null ? null : politicalOutlook.trim();
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health == null ? null : health.trim();
    }

    public String getPostrank() {
        return postrank;
    }

    public void setPostrank(String postrank) {
        this.postrank = postrank == null ? null : postrank.trim();
    }

    public Date getAbroadTime() {
        return abroadTime;
    }

    public void setAbroadTime(Date abroadTime) {
        this.abroadTime = abroadTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getGoCountry() {
        return goCountry;
    }

    public void setGoCountry(String goCountry) {
        this.goCountry = goCountry == null ? null : goCountry.trim();
    }

    public Integer getOutsideTime() {
        return outsideTime;
    }

    public void setOutsideTime(Integer outsideTime) {
        this.outsideTime = outsideTime;
    }

    public String getAbroadReasons() {
        return abroadReasons;
    }

    public void setAbroadReasons(String abroadReasons) {
        this.abroadReasons = abroadReasons == null ? null : abroadReasons.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getRevertLicenceTime() {
        return revertLicenceTime;
    }

    public void setRevertLicenceTime(Date revertLicenceTime) {
        this.revertLicenceTime = revertLicenceTime;
    }

    public String getClassificaPost() {
        return classificaPost;
    }

    public void setClassificaPost(String classificaPost) {
        this.classificaPost = classificaPost == null ? null : classificaPost.trim();
    }

    public String getClassificationLevel() {
        return classificationLevel;
    }

    public void setClassificationLevel(String classificationLevel) {
        this.classificationLevel = classificationLevel == null ? null : classificationLevel.trim();
    }

    public Date getDeclassificaStarttime() {
        return declassificaStarttime;
    }

    public void setDeclassificaStarttime(Date declassificaStarttime) {
        this.declassificaStarttime = declassificaStarttime;
    }

    public Date getDeclassificaEndtime() {
        return declassificaEndtime;
    }

    public void setDeclassificaEndtime(Date declassificaEndtime) {
        this.declassificaEndtime = declassificaEndtime;
    }

    public Integer getPaper() {
        return paper;
    }

    public void setPaper(Integer paper) {
        this.paper = paper;
    }

    public String getRealGoCountry() {
        return realGoCountry;
    }

    public void setRealGoCountry(String realGoCountry) {
        this.realGoCountry = realGoCountry == null ? null : realGoCountry.trim();
    }

    public String getRealPassCountry() {
        return realPassCountry;
    }

    public void setRealPassCountry(String realPassCountry) {
        this.realPassCountry = realPassCountry == null ? null : realPassCountry.trim();
    }

    public Date getRealAbroadTime() {
        return realAbroadTime;
    }

    public void setRealAbroadTime(Date realAbroadTime) {
        this.realAbroadTime = realAbroadTime;
    }

    public Date getRealReturnTime() {
        return realReturnTime;
    }

    public void setRealReturnTime(Date realReturnTime) {
        this.realReturnTime = realReturnTime;
    }

    public Integer getRealOutsideTime() {
        return realOutsideTime;
    }

    public void setRealOutsideTime(Integer realOutsideTime) {
        this.realOutsideTime = realOutsideTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIsTwoPassport() {
        return isTwoPassport;
    }

    public void setIsTwoPassport(String isTwoPassport) {
        this.isTwoPassport = isTwoPassport == null ? null : isTwoPassport.trim();
    }

    public String getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(String isAbroad) {
        this.isAbroad = isAbroad == null ? null : isAbroad.trim();
    }

    public String getComparisonResult() {
        return comparisonResult;
    }

    public void setComparisonResult(String comparisonResult) {
        this.comparisonResult = comparisonResult == null ? null : comparisonResult.trim();
    }

    public String getAbroadPhone() {
        return abroadPhone;
    }

    public void setAbroadPhone(String abroadPhone) {
        this.abroadPhone = abroadPhone == null ? null : abroadPhone.trim();
    }

    public String getSfxyzqjwyj() {
        return sfxyzqjwyj;
    }

    public void setSfxyzqjwyj(String sfxyzqjwyj) {
        this.sfxyzqjwyj = sfxyzqjwyj == null ? null : sfxyzqjwyj.trim();
    }

    public String getSfzqjwyj() {
        return sfzqjwyj;
    }

    public void setSfzqjwyj(String sfzqjwyj) {
        this.sfzqjwyj = sfzqjwyj == null ? null : sfzqjwyj.trim();
    }

    public Integer getPassport() {
        return passport;
    }

    public void setPassport(Integer passport) {
        this.passport = passport;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum == null ? null : passportNum.trim();
    }

    public Integer getHongkongandmacaoPassport() {
        return hongkongandmacaoPassport;
    }

    public void setHongkongandmacaoPassport(Integer hongkongandmacaoPassport) {
        this.hongkongandmacaoPassport = hongkongandmacaoPassport;
    }

    public String getHongkongandmacaoPassportNum() {
        return hongkongandmacaoPassportNum;
    }

    public void setHongkongandmacaoPassportNum(String hongkongandmacaoPassportNum) {
        this.hongkongandmacaoPassportNum = hongkongandmacaoPassportNum == null ? null : hongkongandmacaoPassportNum.trim();
    }

    public Integer getMacaoVisa() {
        return macaoVisa;
    }

    public void setMacaoVisa(Integer macaoVisa) {
        this.macaoVisa = macaoVisa;
    }

    public Integer getHongkongVisa() {
        return hongkongVisa;
    }

    public void setHongkongVisa(Integer hongkongVisa) {
        this.hongkongVisa = hongkongVisa;
    }

    public Integer getTaiwanPassport() {
        return taiwanPassport;
    }

    public void setTaiwanPassport(Integer taiwanPassport) {
        this.taiwanPassport = taiwanPassport;
    }

    public String getTaiwanPassportNum() {
        return taiwanPassportNum;
    }

    public void setTaiwanPassportNum(String taiwanPassportNum) {
        this.taiwanPassportNum = taiwanPassportNum == null ? null : taiwanPassportNum.trim();
    }

    public Integer getTaiwanVisa() {
        return taiwanVisa;
    }

    public void setTaiwanVisa(Integer taiwanVisa) {
        this.taiwanVisa = taiwanVisa;
    }

    public String getIsLuoguan() {
        return isLuoguan;
    }

    public void setIsLuoguan(String isLuoguan) {
        this.isLuoguan = isLuoguan == null ? null : isLuoguan.trim();
    }

    public String getIsSpouseandchildrenSupervise() {
        return isSpouseandchildrenSupervise;
    }

    public void setIsSpouseandchildrenSupervise(String isSpouseandchildrenSupervise) {
        this.isSpouseandchildrenSupervise = isSpouseandchildrenSupervise == null ? null : isSpouseandchildrenSupervise.trim();
    }

    public String getIsLeaders() {
        return isLeaders;
    }

    public void setIsLeaders(String isLeaders) {
        this.isLeaders = isLeaders == null ? null : isLeaders.trim();
    }

    public String getIsSpeciallyApproved() {
        return isSpeciallyApproved;
    }

    public void setIsSpeciallyApproved(String isSpeciallyApproved) {
        this.isSpeciallyApproved = isSpeciallyApproved == null ? null : isSpeciallyApproved.trim();
    }

    public String getNegativeInfo() {
        return negativeInfo;
    }

    public void setNegativeInfo(String negativeInfo) {
        this.negativeInfo = negativeInfo == null ? null : negativeInfo.trim();
    }

    public Date getPassportTerm() {
        return passportTerm;
    }

    public void setPassportTerm(Date passportTerm) {
        this.passportTerm = passportTerm;
    }

    public String getFundsSource() {
        return fundsSource;
    }

    public void setFundsSource(String fundsSource) {
        this.fundsSource = fundsSource == null ? null : fundsSource.trim();
    }

    public String getIsComparison() {
        return isComparison;
    }

    public void setIsComparison(String isComparison) {
        this.isComparison = isComparison == null ? null : isComparison.trim();
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

    public Integer getIsEntrust() {
        return isEntrust;
    }

    public void setIsEntrust(Integer isEntrust) {
        this.isEntrust = isEntrust;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    public String getLeaderBatchId() {
        return leaderBatchId;
    }

    public void setLeaderBatchId(String leaderBatchId) {
        this.leaderBatchId = leaderBatchId == null ? null : leaderBatchId.trim();
    }

    public Date getSczqjwyjsj() {
        return sczqjwyjsj;
    }

    public void setSczqjwyjsj(Date sczqjwyjsj) {
        this.sczqjwyjsj = sczqjwyjsj;
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
}