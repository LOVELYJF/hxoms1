package com.hxoms.modules.omsregcadre.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.A01Entity;

import java.util.List;

public interface A01EntityMapper extends BaseMapper<A01Entity> {

    List<A01Entity> selectA01Data();
}