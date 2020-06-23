package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OmsRegProcpersonInfoMapper extends BaseMapper<OmsRegProcpersonInfo> {

    List<String> selectRegProcpersonInfo();

    int batchAddorpInfo(@Param(value = "list")List<OmsRegProcpersonInfo> subList);

    Object selectPersonAndAllowRevoke(OmsRegProcpersonInfo msRegProcpersonInfo);

    List<OmsRegProcpersonInfo> selectA0100ByMap(Map<String, Object> map);

}


