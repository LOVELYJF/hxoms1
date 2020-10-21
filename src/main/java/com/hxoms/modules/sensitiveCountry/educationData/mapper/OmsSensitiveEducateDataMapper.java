package com.hxoms.modules.sensitiveCountry.educationData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.sensitiveCountry.educationData.entity.OmsSensitiveEducateData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsSensitiveEducateDataMapper extends BaseMapper<OmsSensitiveEducateData> {


	/**
	 * <b>功能描述: 查询文件路径</b>
	 * @Param: [filepathList]
	 * @Return: java.util.List<java.lang.String>
	 * @Author: luoshuai
	 * @Date: 2020/10/21 9:23
	 */
	List<String> selectFilepathList(@Param("filepathList") List<String> filepathList);
}