package com.hxoms.modules.selfestimate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.selfestimate.entity.OmsSelfFile;
import com.hxoms.modules.selfestimate.entity.OmsSelfFileVO;

import java.util.List;
import java.util.Map;

public interface OmsSelfFileMapper extends BaseMapper<OmsSelfFile> {

    /**
     * 自评信息维护列表
     * @param paramMap
     * @return
     */
    List<OmsSelfFileVO> selectOmsSelfFileList(Map<String, String> paramMap);
}