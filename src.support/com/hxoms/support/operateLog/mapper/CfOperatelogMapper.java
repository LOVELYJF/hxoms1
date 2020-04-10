package com.hxoms.support.operateLog.mapper;

import com.hxoms.support.operateLog.entity.CfOperatelog;
import com.hxoms.support.operateLog.entity.CfOperatelogExample;
import java.util.List;

public interface CfOperatelogMapper {
    int deleteByPrimaryKey(String id);

    int insert(CfOperatelog record);

    int insertSelective(CfOperatelog record);

    List<CfOperatelog> selectByExample(CfOperatelogExample example);

    CfOperatelog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CfOperatelog record);

    int updateByPrimaryKey(CfOperatelog record);
}