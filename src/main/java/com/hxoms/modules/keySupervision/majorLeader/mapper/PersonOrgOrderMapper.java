package com.hxoms.modules.keySupervision.majorLeader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.majorLeader.entity.PersonOrgOrder;

import java.util.List;

public interface PersonOrgOrderMapper extends BaseMapper<PersonOrgOrder> {

    /**
     * <b>查询人员排序表中的每个单位前两名</b>
     * @return
     */
    List<PersonOrgOrder> selectMajorLeaderAuto();
}