package com.hxoms.modules.omsregcadre.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.A01;

import java.util.List;

public interface A01EntityMapper extends BaseMapper<A01> {

    List<A01> selectA01List();
}