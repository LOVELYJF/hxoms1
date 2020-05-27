package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hxoms.modules.omsregcadre.entity.OmsPmChangeNonpm;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsPmChangeNonpmIPageParam;

import java.util.List;
import java.util.Map;

public interface OmsPmChangeNonpmMapper extends BaseMapper<OmsPmChangeNonpm> {

    List<OmsPmChangeNonpm> getPmChangeNonpminfo(OmsPmChangeNonpmIPageParam pmChangeNonpm);
}