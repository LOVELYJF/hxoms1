package com.hxoms.modules.omsregcadre.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;

public interface OmsEntryexitRecordService {

    PageInfo<OmsEntryexitRecord> getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam);
}
