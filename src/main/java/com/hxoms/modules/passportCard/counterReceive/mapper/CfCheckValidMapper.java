package com.hxoms.modules.passportCard.counterReceive.mapper;

import com.hxoms.modules.passportCard.counterReceive.entity.CfCheckValid;
import com.hxoms.modules.passportCard.counterReceive.entity.parameterEntity.CfCheckValidParam;

import java.util.List;

public interface CfCheckValidMapper {

     List<CfCheckValid> selectCfCheckValid(CfCheckValidParam cfCheckValidParam);


     List<CfCheckValid> selectCfCheckValidByName(CfCheckValidParam cfCheckValidParam);
}
