package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsEntryexitRecordMapper extends BaseMapper<OmsEntryexitRecord> {

    List<OmsEntryexitRecord> selectEntryexitRecordIPage(OmsEntryexitRecordIPagParam entryexitrecord);

    List<OmsEntryexitRecord> selectNoMatchList(String a0100);

    int batchSaveEntity(@Param("omsEntryexitRecordList") List<OmsEntryexitRecord> omsEntryexitRecordList);
}