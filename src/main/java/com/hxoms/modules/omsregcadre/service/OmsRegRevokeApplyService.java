package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeApply;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeApproval;

import java.text.ParseException;

public interface OmsRegRevokeApplyService extends IService<OmsRegRevokeApply> {

    IPage<OmsRegRevokeApply> queryRevokeApplyList(Page page);

    Object searchRevokeRegPerson() throws ParseException;

    Object insertRevokeRegPerson(OmsRegProcpersoninfo regProcpersonInfo) throws ParseException;

    Object approvalRevokeRegPerson(OmsRegRevokeApproval regRevokeApproval,String applyIds);

    Object revokeRegApply(OmsRegRevokeApply revokeApply) throws ParseException;

    Object reportLeaderApply(OmsRegRevokeApply revokeApply);
}
