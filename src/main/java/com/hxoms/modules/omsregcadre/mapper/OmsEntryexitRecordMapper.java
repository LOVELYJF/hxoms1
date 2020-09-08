package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecordModel;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecordVO;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsEntryexitRecordMapper extends BaseMapper<OmsEntryexitRecord> {

    List<OmsEntryexitRecord> selectEntryexitRecordIPage(OmsEntryexitRecordIPagParam entryexitrecord);

    List<OmsEntryexitRecord> selectNoMatchList(String omsId);

    List<OmsEntryexitRecordVO> getExceptionPriApply(OmsEntryexitRecordIPagParam entryexitRecordIPagParam);

    OmsEntryexitRecordVO queryCompresultByYear(String year);

    List<OmsEntryexitRecordModel> newexitRecordsList(@Param("ids") List<String> ids);
}