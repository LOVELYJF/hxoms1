package com.hxoms.modules.keySupervision.majorLeader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.majorLeader.entity.A02;

import java.util.List;

public interface A02Mapper extends BaseMapper<A02> {
	/**
	 * <b>自动识别主要领导（每个单位前两名）</b>
	 * @return
	 */
	List<A02> selectMajorLeaderAutoFromA02();
}