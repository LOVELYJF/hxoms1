package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;

import java.util.List;

public interface OmsRegProcbatchService  extends IService<OmsRegProcbatch> {

    Object startOmsReg(OmsRegProcbatch regProcbatch);

    Object determineRegFinish();

    List<String> getHistoryBatch();

    OmsRegProcbatch selectWbaByOrpbatch();

    Object insertProcbatch(OmsRegProcbatch regProcbatch);

    int batchinsertInfo(List<OmsRegProcbatchPerson> orpbplist);

    int updateOrpbatch(OmsRegProcbatch batchinfo);
}

