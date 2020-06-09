package com.hxoms.modules.omsregcadre.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;

import java.util.List;

public interface OmsEntryexitRecordService {

    PageInfo<OmsEntryexitRecord> getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam);

    List<OmsPriApply> queryPriApplyList(String a0100);
}
