package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;

import java.io.IOException;
import java.util.List;

public interface OmsRegProcbatchService  extends IService<OmsRegProcbatch> {

    Object startOmsReg(OmsRegProcbatch regProcbatch);

    Result determineRegFinish(String data) throws IOException;

    List<String> getHistoryBatch();

    OmsRegProcbatch selectWbaByOrpbatch();

    Object insertProcbatch(OmsRegProcbatch regProcbatch);

    int updateOrpbatch(OmsRegProcbatch batchinfo);

    Result getToBeConfirmed(String batchId);

    Result getToBeCorrected(String b0100);

    Result saveCorrected(String data) throws IOException;

    void FinishBatch(String batchId);
}

