package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;

import java.util.List;

public interface OmsRegProcbatchMapper extends BaseMapper<OmsRegProcbatch> {

    List<String> getHistoryBatch();

    ExcelModelORPinfo selectWbaByOrpbatch();
}