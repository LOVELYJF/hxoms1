package com.hxoms.modules.passportCard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.entity.CfCheckValid;
import com.hxoms.modules.passportCard.entity.param.CfCheckValidParam;
import com.hxoms.modules.passportCard.mapper.CfCheckValidMapper;
import com.hxoms.modules.passportCard.service.CfCheckValidService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfCheckValidServiceImpl implements CfCheckValidService {

    private CfCheckValidMapper cfCheckValidMapper;
    @Override
    public PageInfo<CfCheckValid> selectCfCheckValid(CfCheckValidParam cfCheckValidParam) {

        PageHelper.startPage(cfCheckValidParam.getPageNum(),cfCheckValidParam.getPageSize());
        List<CfCheckValid> cfCheckValidList = cfCheckValidMapper.selectCfCheckValid(cfCheckValidParam);
        PageInfo<CfCheckValid> pageInfo =new PageInfo(cfCheckValidList);
        return pageInfo;
    }

    @Override
    public PageInfo<CfCheckValid> selectCfCheckValidByName(CfCheckValidParam cfCheckValidParam) {
        PageHelper.startPage(cfCheckValidParam.getPageNum(),cfCheckValidParam.getPageSize());
        List<CfCheckValid> cfCheckValidList = cfCheckValidMapper.selectCfCheckValid(cfCheckValidParam);
        PageInfo<CfCheckValid> pageInfo =new PageInfo(cfCheckValidList);
        return pageInfo;
    }
}
