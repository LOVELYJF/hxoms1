package com.hxoms.modules.passportCard.mapper;

import com.hxoms.modules.passportCard.entity.CfCheckValid;
import com.hxoms.modules.passportCard.entity.param.CfCheckValidParam;

import java.util.List;

public interface CfCheckValidMapper {

     List<CfCheckValid> selectCfCheckValid(CfCheckValidParam cfCheckValidParam);


     List<CfCheckValid> selectCfCheckValidByName(CfCheckValidParam cfCheckValidParam);
}
