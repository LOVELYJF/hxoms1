package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeApply;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeApproval;

import java.text.ParseException;

public interface OmsRegRevokeApplyService extends IService<OmsRegRevokeApply> {

    IPage<OmsRegRevokeApply> queryRevokeApplyList(Page page, OmsRegRevokeApply revokeApply);

    Object searchRevokeRegPerson() throws ParseException;

    Object insertRevokeRegPerson(OmsRegProcpersonInfo regProcpersonInfo) throws ParseException;

    Object approvalRevokeRegPerson(OmsRegRevokeApproval regRevokeApproval);
}
