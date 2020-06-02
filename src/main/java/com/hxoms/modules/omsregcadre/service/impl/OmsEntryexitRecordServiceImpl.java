package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.mapper.OmsEntryexitRecordMapper;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OmsEntryexitRecordServiceImpl extends ServiceImpl<OmsEntryexitRecordMapper, OmsEntryexitRecord> implements OmsEntryexitRecordService {


    @Override
    public PageInfo<OmsEntryexitRecord> getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) {
        //分页
        PageUtil.pageHelp(entryexitRecordIPagParam.getPageNum(), entryexitRecordIPagParam.getPageSize());
        List<OmsEntryexitRecord> omsPriApplyVOS = baseMapper.selectEntryexitRecordIPage(entryexitRecordIPagParam);
        //返回数据
        PageInfo<OmsEntryexitRecord> pageInfo = new PageInfo(omsPriApplyVOS);
        return pageInfo;
    }
}
