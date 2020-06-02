package com.hxoms.modules.dataCapture.dataconfig.mapper;

import com.hxoms.modules.dataCapture.entity.DataSource;
import org.apache.ibatis.annotations.Param;

/**
 * @authore:wjf
 * @data 2020/4/14 17:17
 * @Description:
 ***/
public interface CutTargetDataSourceMapper {



    DataSource targetDataSource(@Param("tableName") String tableName, @Param("code") String code);

}
