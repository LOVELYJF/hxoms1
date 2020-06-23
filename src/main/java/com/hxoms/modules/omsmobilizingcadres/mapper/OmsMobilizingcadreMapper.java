package com.hxoms.modules.omsmobilizingcadres.mapper;

import com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface OmsMobilizingcadreMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsMobilizingcadre record);

    int insertSelective(OmsMobilizingcadre record);

    OmsMobilizingcadre selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsMobilizingcadre record);

    int updateByPrimaryKey(OmsMobilizingcadre record);

    List<LinkedHashMap<String, Object>> selectAllMobilizingCadre(@Param("orgIds") List<String> orgIds, @Param("name") String name, @Param("status") String status);

    List<OmsMobilizingcadre> getAllMobilizingCadreByStatus(String s);

    void updateStatusByA0100(@Param("a0100") String a0100, @Param("status") String status);
}