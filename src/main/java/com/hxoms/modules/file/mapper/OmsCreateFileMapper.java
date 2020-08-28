package com.hxoms.modules.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.file.entity.OmsCreateFile;

public interface OmsCreateFileMapper extends BaseMapper<OmsCreateFile> {
    /**
     * 因私出国查询审批表
     */
    OmsCreateFile priApplyPrintApproval(String applyId);
}