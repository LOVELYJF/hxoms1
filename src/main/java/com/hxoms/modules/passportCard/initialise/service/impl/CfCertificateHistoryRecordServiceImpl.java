package com.hxoms.modules.passportCard.initialise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.omsregcadre.entity.CfCertificateHistoryRecordModel;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateHistoryRecord;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateHistoryRecordMapper;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateHistoryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CfCertificateHistoryRecordServiceImpl extends ServiceImpl<CfCertificateHistoryRecordMapper,CfCertificateHistoryRecord> implements CfCertificateHistoryRecordService {

    @Autowired
    private CfCertificateMapper cfCertificateMapper;
    @Autowired
    private CfCertificateHistoryRecordMapper cfCertificateHistoryRecordMapper;
    @Override
    public void saveCfCertificateHistoryRecord(String year) {
        List<CfCertificateHistoryRecord> list = cfCertificateMapper.selectAllExceptionInfo(year);
        if (list.size()>0) {
            List<String> ids = baseMapper.selectHistoryIds(year);
            for (int i = 0; i < list.size(); i++) {
                if (ids!=null && ids.contains(list.get(i).getId())) {
                    list.remove(i);
                    i++;
                }
            }
            saveBatch(list,100);
        }
    }

    /**
         * @Desc: 未上缴证照统计
     * @Author: lijiaojiao
     * @Param: [pageBean,year]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/10/16
     */
    @Override
    public PageBean<CfCertificateInfo> selectNotProvicdeCerRecord(PageBean pageBean, String year) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CfCertificateHistoryRecord> pageInfo=new PageInfo<CfCertificateHistoryRecord>(cfCertificateHistoryRecordMapper.selectNotProvicdeCerRecord(year));
        return PageUtil.packagePage(pageInfo);
    }


    /**
     * @Desc: 存疑证照统计
     * @Author: wangyunquan
     * @Param: [pageBean，year]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/7
     */
    @Override
    public PageBean<CfCertificateHistoryRecord> selectExceptionCerRecord(PageBean pageBean,String year) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CfCertificateHistoryRecord> pageInfo=new PageInfo<CfCertificateHistoryRecord>(cfCertificateHistoryRecordMapper.selectExceptionCerRecord(year));
        return PageUtil.packagePage(pageInfo);
    }

    @Override
    public List<CfCertificateHistoryRecordModel> selectNotProvicdeCerRecords(String year) {
        return cfCertificateHistoryRecordMapper.selectNotProvicdeCerRecords(year);
    }


    @Override
    public List<CfCertificateHistoryRecordModel> selectExceptionCerRecords(String year) {
        return cfCertificateHistoryRecordMapper.selectExceptionCerRecords(year);
    }
}
