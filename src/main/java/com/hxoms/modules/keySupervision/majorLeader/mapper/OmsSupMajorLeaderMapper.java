package com.hxoms.modules.keySupervision.majorLeader.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;

import java.util.List;


/**
 * <b>主要领导数据持久层接口/b>
 *  @author luoshuai
 *  @date 2020/5/10 18:29
 */
public interface OmsSupMajorLeaderMapper extends BaseMapper<OmsSupMajorLeader> {


	List<String> selectA0100List();
}