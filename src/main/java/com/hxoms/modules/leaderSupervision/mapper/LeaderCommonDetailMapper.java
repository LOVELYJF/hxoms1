package com.hxoms.modules.leaderSupervision.mapper;



import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LeaderCommonDetailMapper {

  List<Map> selectJiweiOpinionAndMaterialsCheckOpinion(@Param("bussinessIds") Object[] bussinessIds,@Param("type") String type);
}
