package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;

import java.util.List;
import java.util.Map;

public interface OmsEntryexitRecordService extends IService<OmsEntryexitRecord> {

    PageInfo<OmsEntryexitRecord> getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam);

    Map<String, Object> queryPriApplyList(String a0100);

    Object batchPriApplyList(List<String> a0100s);

    Object insertEntryexitRecord(OmsEntryexitRecord entryexitRecord);

    Object updateEntryexitRecord(OmsEntryexitRecord entryexitRecord);

    Object deleteEntryexitRecord(String id);
}
