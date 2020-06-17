package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegYearcheckInfo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface OmsRegProcpersonInfoService extends IService<OmsRegProcpersonInfo> {


    Object insertRpinfo(OmsRegProcpersonInfo orpInfo);

    Object updateRpinfo(OmsRegProcpersonInfo orpInfo);

    Object deleteRpinfo(String id);

    List<OmsRegProcpersonInfo> insertOmsRegGongAn(List<OmsRegProcpersonInfo> list);

    int selectCountGongAn(String dataType);

    Object mergeDataGBandGA(String idStr);

    IPage<OmsRegProcpersonInfo> getProvinceCadreRegInfo(Page page, OmsRegProcpersonInfo msRegProcpersonInfo);

    Object extractRegPersonInfo() throws ParseException;

    IPage<OmsRegProcpersonInfo> getRegPersonInfoList(Page page, OmsRegProcpersonInfo msRegProcpersonInfo);

    List<OmsRegProcpersonInfo> selectPersonByBatchNo(String batchNo);

    int checkUploadRegRecord(List<OmsRegProcpersonInfo> list);

    List<OmsRegYearcheckInfo> queryYearList(List<OmsRegProcpersonInfo> list);

    List<OmsRegYearcheckInfo> queryYearCheckList(Date year);

    Object selectPersonAndAllowRevoke(OmsRegProcpersonInfo msRegProcpersonInfo);

    Object selectInfoByA0100(String a0100);

    /**
     * <b>查询登记备案库中的人员信息（出生日期）</b>
     * @param a0100
     * @author luoshuai
     * @return
     */
    public Date getOmsRegProcpersonBirthDate(String a0100);

    PageInfo<OmsRegProcpersonInfo> getInitialReginfo(OmsRegProcpersonInfo msRegProcpersonInfo) throws ParseException;
}
