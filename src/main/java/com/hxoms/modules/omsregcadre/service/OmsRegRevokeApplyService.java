package com.hxoms.modules.omsregcadre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapproval;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;

import java.text.ParseException;

public interface OmsRegRevokeApplyService extends IService<OmsRegRevokeapply> {

    PageInfo<OmsRegRevokeapply> queryRevokeApplyList(OmsRegRevokeApplyIPagParam revokeApplyIPagParam);

    Object searchRevokeRegPerson() throws ParseException;

    int insertRevokeRegPerson(OmsRegRevokeapply revokeApply);

    Object approvalRevokeRegPerson(OmsRegRevokeapproval regRevokeApproval, String applyIds);

    Object searchRevokeRegPersonList(OmsRegProcpersoninfo regProcpersonInfo);

    Object updateApplyStatus(OmsRegRevokeapply revokeApply);
}
