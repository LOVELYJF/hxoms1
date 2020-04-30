package com.hxoms.modules.omsregcadre.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;

import java.util.List;

public interface OmsRegProcbatchPersonMapper extends BaseMapper<OmsRegProcbatchPerson> {

    List<String> selectA0100s(String batchId);
}