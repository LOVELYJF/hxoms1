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
    List<OmsSelfFileVO> selectItemsList(Map<String, String> paramMap);

    /**
     * 自评文件列表
     * @return
     * @param paramMap
     */
    List<OmsSelfFileVO> selectFileList(Map<String, String> paramMap);
}