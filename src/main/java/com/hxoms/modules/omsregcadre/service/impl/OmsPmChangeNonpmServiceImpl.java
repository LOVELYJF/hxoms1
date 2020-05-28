package com.hxoms.modules.omsregcadre.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsPmChangeNonpm;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsPmChangeNonpmIPageParam;
import com.hxoms.modules.omsregcadre.mapper.OmsPmChangeNonpmMapper;
import com.hxoms.modules.omsregcadre.service.OmsPmChangeNonpmService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OmsPmChangeNonpmServiceImpl extends ServiceImpl<OmsPmChangeNonpmMapper, OmsPmChangeNonpm> implements OmsPmChangeNonpmService {

    @Override
    public Object insertPmChangeNonpmApply(OmsPmChangeNonpm pmChangeNonpm) {
        pmChangeNonpm.setCreateDate(new Date());
        return baseMapper.insert(pmChangeNonpm);
    }

    @Override
    public PageInfo<OmsPmChangeNonpm> getPmChangeNonpminfo(OmsPmChangeNonpmIPageParam pmChangeNonpmIPageParam) {
        //分页
        PageUtil.pageHelp(pmChangeNonpmIPageParam.getPageNum(), pmChangeNonpmIPageParam.getPageSize());
        List<OmsPmChangeNonpm> pmChangeNonpmIPage = baseMapper.getPmChangeNonpminfo(pmChangeNonpmIPageParam);
        //返回数据
        PageInfo<OmsPmChangeNonpm> pageInfo = new PageInfo(pmChangeNonpmIPage);
        return pageInfo;
    }


}
