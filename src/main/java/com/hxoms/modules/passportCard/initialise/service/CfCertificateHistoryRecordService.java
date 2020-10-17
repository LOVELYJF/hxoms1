package com.hxoms.modules.passportCard.initialise.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateHistoryRecord;
public interface CfCertificateHistoryRecordService {
    /**
     * 根据年度查询对应证照信息并保存对比结果到记录表中
     * @param year
     */
    void saveCfCertificateHistoryRecord(String year);
    /**
     * @Desc: 未上缴证照统计
     * @Author: lijiaojiao
     * @Param: [pageBean,year]
     * @Date: 2020/10/16
     */
    Object selectNotProvicdeCerRecord(PageBean pageBean, String year);
    /**
     * @Desc: 存疑证照统计
     * @Author: lijiaojiao
     * @Param: [pageBean,year]
     * @Date: 2020/10/16
     */
    PageBean<CfCertificateHistoryRecord> selectExceptionCerRecord(PageBean pageBean, String year);
}
