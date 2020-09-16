package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.common.tree.Tree;
import com.hxoms.modules.omsregcadre.entity.OmsBaseinfoConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OmsBaseinfoConfigMapper extends BaseMapper<OmsBaseinfoConfig> {

    List<Tree> selectCGJPostTree(String dictCode);

    int insertBatchList(@Param("list") List<OmsBaseinfoConfig> list);

    int deleteBaseInfoConfig(@Param("list") String[] infoIds);

    List<OmsBaseinfoConfig> selectPostInfo(Map<String,Object> map);
}