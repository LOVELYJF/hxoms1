package com.hxoms.modules.sensitiveCountry.sensitiveLimited.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveCountry;

import java.util.List;
import java.util.Map;

public interface OmsSensitiveCountryMapper extends BaseMapper<OmsSensitiveCountry> {

	List<OmsSensitiveCountry> selectOmsSensitiveCountry(Map<String,Object> map);
}