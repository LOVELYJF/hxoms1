package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;

import java.text.ParseException;
import java.util.List;

public interface OmsRegProcpersonInfoService extends IService<OmsRegProcpersonInfo> {

    IPage<OmsRegProcpersonInfo> getInitialReginfo(Page page, OmsRegProcpersonInfo msRegProcpersonInfo);

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
}
