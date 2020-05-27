package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.majorLeader.entity.A02;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OmsRegProcpersonInfoMapper extends BaseMapper<OmsRegProcpersonInfo> {

    List<String> selectRegProcpersonInfo();

    int batchAddorpInfo(@Param(value = "list")List<OmsRegProcpersonInfo> subList);

}


