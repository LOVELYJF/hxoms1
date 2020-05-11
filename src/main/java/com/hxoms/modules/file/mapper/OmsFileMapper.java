package com.hxoms.modules.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.file.entity.OmsFile;

import java.util.List;

public interface OmsFileMapper extends BaseMapper<OmsFile> {

    List<OmsFile> selectFileListByCode(String tableCode);
}