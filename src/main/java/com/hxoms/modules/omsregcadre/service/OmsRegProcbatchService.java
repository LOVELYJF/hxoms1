package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;

import java.util.List;

public interface OmsRegProcbatchService  extends IService<OmsRegProcbatch> {

    Object startOmsReg(OmsRegProcbatch regProcbatch);

    Object determineRegFinish();

    List<String> getHistoryBatch();
}
