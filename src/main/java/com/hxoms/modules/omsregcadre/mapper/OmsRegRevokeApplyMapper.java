package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply;
import org.apache.ibatis.annotations.Param;

public interface OmsRegRevokeApplyMapper extends BaseMapper<OmsRegRevokeapply> {

    IPage<OmsRegRevokeapply> selectRevokeApplyList(@Param("page") Page page);
}