package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeApproval;

import java.text.ParseException;

public interface OmsRegRevokeApplyService extends IService<OmsRegRevokeapply> {

    IPage<OmsRegRevokeapply> queryRevokeApplyList(Page page);

    Object searchRevokeRegPerson() throws ParseException;

    Object insertRevokeRegPerson(OmsRegProcpersoninfo regProcpersonInfo) throws ParseException;

    Object approvalRevokeRegPerson(OmsRegRevokeApproval regRevokeApproval,String applyIds);

    Object revokeRegApply(OmsRegRevokeapply revokeApply) throws ParseException;

    Object reportLeaderApply(OmsRegRevokeapply revokeApply);
}
