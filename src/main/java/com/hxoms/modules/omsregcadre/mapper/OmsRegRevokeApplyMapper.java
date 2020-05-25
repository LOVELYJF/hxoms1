package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeApply;
import org.apache.ibatis.annotations.Param;

public interface OmsRegRevokeApplyMapper extends BaseMapper<OmsRegRevokeApply> {

    IPage<OmsRegRevokeApply> selectRevokeApplyList(@Param("page") Page page);
}