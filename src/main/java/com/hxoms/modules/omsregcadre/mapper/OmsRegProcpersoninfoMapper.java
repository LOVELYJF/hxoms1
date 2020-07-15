package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.StatisticsCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OmsRegProcpersoninfoMapper extends BaseMapper<OmsRegProcpersoninfo> {

    List<String> selectRegProcpersonInfo();

    int batchAddorpInfo(@Param(value = "list")List<OmsRegProcpersoninfo> subList);

    Object selectPersonAndAllowRevoke(OmsRegProcpersoninfo msRegProcpersonInfo);

    List<OmsRegProcpersoninfo> selectA0100ByMap(Map<String, Object> map);

    List<OmsRegProcpersoninfo> selectProcpersoninfoList(OmsRegProcpersoninfo info);

    List<StatisticsCountVo> selectInboudFlagCount();

    List<StatisticsCountVo> selectIdentityCodeCount();

    List<StatisticsCountVo> selectAllFlagCount();

    List<OmsRegProcpersoninfo> selectMergeList(String dataType);
}


