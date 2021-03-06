package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsRegProcbatchMapper extends BaseMapper<OmsRegProcbatch> {

    List<String> getHistoryBatch();

    OmsRegProcbatch selectWbaByOrpbatch();

    void FinishBatch(@Param(value = "batchId")String batchId);

    List<OmsRegProcbatch> getLastBatch();
}