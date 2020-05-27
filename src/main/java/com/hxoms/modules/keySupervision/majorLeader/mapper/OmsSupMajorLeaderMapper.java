package com.hxoms.modules.keySupervision.majorLeader.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;

import java.util.List;
import java.util.Map;

/**
 * <b>主要领导数据持久层接口/b>
 *  @author luoshuai
 *  @date 2020/5/10 18:29
 */
public interface OmsSupMajorLeaderMapper extends BaseMapper<OmsSupMajorLeader> {


    /**
     * <b>查询主要领导信息</b>
     * @param map
     * @return
     */
    List<OmsSupMajorLeader> selectMajorLeaderList(Map<String, Object> map);

}