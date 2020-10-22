package com.hxoms.modules.passportCard.initialise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.CfCertificateHistoryRecordModel;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateHistoryRecord;

import java.util.List;

public interface CfCertificateHistoryRecordMapper extends BaseMapper<CfCertificateHistoryRecord> {
    
    List<CfCertificateHistoryRecord> selectNotProvicdeCerRecord(String year);

    List<CfCertificateHistoryRecord> selectExceptionCerRecord(String year);

    List<String> selectHistoryIds(String year);

    List<CfCertificateHistoryRecordModel> selectNotProvicdeCerRecords(String year);

    List<CfCertificateHistoryRecordModel> selectExceptionCerRecords(String year);
}
