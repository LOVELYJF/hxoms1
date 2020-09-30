package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;

import java.util.List;

public interface OmsRegProcbatchService  extends IService<OmsRegProcbatch> {

    Object startOmsReg(OmsRegProcbatch regProcbatch);

    Result determineRegFinish(String data);

    List<String> getHistoryBatch();

    OmsRegProcbatch selectWbaByOrpbatch();

    Object insertProcbatch(OmsRegProcbatch regProcbatch);

    int batchinsertInfo(List<OmsRegProcbatchPerson> orpbplist);

    int updateOrpbatch(OmsRegProcbatch batchinfo);

    Result getToBeConfirmed(String batchId);
}

