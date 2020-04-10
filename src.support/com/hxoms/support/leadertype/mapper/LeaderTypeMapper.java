package com.hxoms.support.leadertype.mapper;

import com.hxoms.support.inforesource.entity.DataTable;
import com.hxoms.support.leadertype.entity.LeaderTypeInfo;

import java.util.List;

public interface LeaderTypeMapper {
    List<DataTable> selectOrgLeaderTypeInfo(LeaderTypeInfo leaderTypeInfo);
}
