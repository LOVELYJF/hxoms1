package com.hxoms.modules.b01temp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.b01temp.entity.OmsB01Temp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsB01TempMapper extends BaseMapper<OmsB01Temp> {
    /**
     * 批量新增数据
     * 
     * @author sunqian
     * @date 2020/5/6 11:24
     */
    void insertBatch(List<OmsB01Temp> subList);

    /**
     * 根据批次id删除批次下的数据
     *
     * @author sunqian
     * @date 2020/5/6 14:43
     */
    void deleteByBatchId(String batchId);
}