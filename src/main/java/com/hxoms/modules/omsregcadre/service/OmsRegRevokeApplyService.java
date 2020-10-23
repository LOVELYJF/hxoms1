package com.hxoms.modules.omsregcadre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;

import java.text.ParseException;
import java.util.List;

public interface OmsRegRevokeApplyService extends IService<OmsRegRevokeapply> {

    PageInfo<OmsRegRevokeapply> queryRevokeApplyList(OmsRegRevokeApplyIPagParam revokeApplyIPagParam);

    Result searchRevokeRegPerson() throws ParseException;

    int insertRevokeRegPerson(OmsRegRevokeapply revokeApply);

    Result approvalRevokeRegPerson(OmsRegRevokeapproval regRevokeApproval, String applyIds);

    Object searchRevokeRegPersonList(OmsRegProcpersoninfo regProcpersonInfo);

    Object updateApplyStatus(OmsRegRevokeapply revokeApply);

    Result updateApplyStatusByCLD(String status,String applyIds);

    Object deleteRevokeRegPerson(String id);

    /**
     * 生成撤销函
     * **/

    List<CancellationLetter> createCancellationLetter(List<CancellationLetter> lists);
}
