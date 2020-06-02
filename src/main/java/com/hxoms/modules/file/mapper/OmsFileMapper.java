package com.hxoms.modules.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.OmsFileVO;

import java.util.List;
import java.util.Map;

public interface OmsFileMapper extends BaseMapper<OmsFile> {

    /**
     * 签字盖章列表
     * @param params
     * @return
     */
    List<OmsFileVO> selectSealHandleList(Map<String, String> params);
}