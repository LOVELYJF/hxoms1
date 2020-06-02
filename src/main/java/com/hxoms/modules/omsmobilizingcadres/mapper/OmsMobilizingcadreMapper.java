package com.hxoms.modules.omsmobilizingcadres.mapper;

import com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre;

import java.util.LinkedHashMap;
import java.util.List;

public interface OmsMobilizingcadreMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsMobilizingcadre record);

    int insertSelective(OmsMobilizingcadre record);

    OmsMobilizingcadre selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsMobilizingcadre record);

    int updateByPrimaryKey(OmsMobilizingcadre record);

    List<LinkedHashMap<String, Object>> selectAllMobilizingCadre(List<String> orgIds, String name, String status);

}