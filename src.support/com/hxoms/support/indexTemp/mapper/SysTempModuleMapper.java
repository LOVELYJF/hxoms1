package com.hxoms.support.indexTemp.mapper;

import com.hxoms.support.indexTemp.entity.SysTempModule;

public interface SysTempModuleMapper {
    int deleteByPrimaryKey(String tempId);

    int insertSelective(SysTempModule record);

}