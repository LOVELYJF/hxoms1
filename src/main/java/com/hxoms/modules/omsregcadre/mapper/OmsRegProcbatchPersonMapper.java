package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsRegProcbatchPersonMapper extends BaseMapper<OmsRegProcbatchPerson> {
    List<String> selectRfIds(String batchId);

    int batchinsertInfo(@Param(value = "list")List<OmsRegProcbatchPerson> subList);

    int selectCountByBatchId(String batchNo);

    int selectPersonByRfId(@Param(value = "id")String id, @Param(value = "batchNo")String batchNo);
}