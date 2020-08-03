package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.ExcelCheckModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegYearcheckinfo;

import java.util.List;

public interface OmsRegYearcheckinfoMapper extends BaseMapper<OmsRegYearcheckinfo> {

    List<String> selectYearList();

    List<ExcelCheckModelORPinfo> selectCheckModelList(String year);
}