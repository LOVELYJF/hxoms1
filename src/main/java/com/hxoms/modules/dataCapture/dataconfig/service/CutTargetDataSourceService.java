package com.hxoms.modules.dataCapture.dataconfig.service;

import com.hxoms.modules.dataCapture.entity.DataSource;

/**
 * @authore:wjf
 * @data 2020/4/14 20:03
 * @Description:
 ***/
public interface CutTargetDataSourceService {

    DataSource targetDataSource(String tableName, String code);
}
