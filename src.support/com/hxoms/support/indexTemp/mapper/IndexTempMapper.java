package com.hxoms.support.indexTemp.mapper;

import com.hxoms.support.indexTemp.entity.IndexTemp;

import java.util.List;

public interface IndexTempMapper {

    int insertSelective(IndexTemp record);

    int updateSelective(IndexTemp record);

    IndexTemp selectIndexTempById(String id);

    List<IndexTemp> selectIndexTempByOrg(String orgId);

    int deleteIndexTempById(String orgId);

}