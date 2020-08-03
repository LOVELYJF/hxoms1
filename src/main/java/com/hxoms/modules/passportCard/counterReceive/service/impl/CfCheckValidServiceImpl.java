package com.hxoms.modules.passportCard.counterReceive.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.counterReceive.entity.CfCheckValid;
import com.hxoms.modules.passportCard.counterReceive.entity.parameterEntity.CfCheckValidParam;
import com.hxoms.modules.passportCard.counterReceive.mapper.CfCheckValidMapper;
import com.hxoms.modules.passportCard.counterReceive.service.CfCheckValidService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfCheckValidServiceImpl implements CfCheckValidService {

    private CfCheckValidMapper cfCheckValidMapper;
    @Override
    public PageInfo<CfCheckValid> selectCfCheckValid(CfCheckValidParam cfCheckValidParam) {

        PageHelper.startPage(cfCheckValidParam.getPageNum()==null?0:cfCheckValidParam.getPageNum(),
                cfCheckValidParam.getPageSize()==null?10:cfCheckValidParam.getPageSize());
        List<CfCheckValid> cfCheckValidList = cfCheckValidMapper.selectCfCheckValid(cfCheckValidParam);
        PageInfo<CfCheckValid> pageInfo =new PageInfo(cfCheckValidList);
        return pageInfo;
    }

    @Override
    public PageInfo<CfCheckValid> selectCfCheckValidByName(CfCheckValidParam cfCheckValidParam) {
        PageHelper.startPage(cfCheckValidParam.getPageNum()==null?0:cfCheckValidParam.getPageNum(),
                cfCheckValidParam.getPageSize()==null?10:cfCheckValidParam.getPageSize());
        List<CfCheckValid> cfCheckValidList = cfCheckValidMapper.selectCfCheckValid(cfCheckValidParam);
        PageInfo<CfCheckValid> pageInfo =new PageInfo(cfCheckValidList);
        return pageInfo;
    }
}
