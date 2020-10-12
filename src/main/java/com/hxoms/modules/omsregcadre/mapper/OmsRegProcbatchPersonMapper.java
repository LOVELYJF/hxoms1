package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsRegProcbatchPersonMapper extends BaseMapper<OmsRegProcbatchPerson> {

    int batchinsertInfo(@Param(value = "list")List<OmsRegProcbatchPerson> subList);

    List<OmsRegProcbatchPerson> getToBeCorrected(@Param(value ="b0100") String b0100);

    List<OmsRegProcbatchPerson> getToBeConfirmed(@Param(value ="batchId") String batchId);
}