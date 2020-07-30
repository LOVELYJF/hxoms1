package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;

import java.util.List;

public interface OmsRegRevokeApplyMapper extends BaseMapper<OmsRegRevokeapply> {


    List<String> selectrfIdList();

    List<OmsRegRevokeapply> selectRevokeApplyList(OmsRegRevokeApplyIPagParam revokeApplyIPagParam);
}