package com.hxoms.modules.leaderSupervision.mapper;



import com.hxoms.modules.leaderSupervision.vo.JiWeiNoPassVo;
import com.hxoms.modules.selfestimate.entity.OmsSelfFileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LeaderCommonDetailMapper {

  List<Map> selectJiweiOpinionAndMaterialsCheckOpinion(@Param("bussinessIds") Object[] bussinessIds,@Param("type") String type);

  List<Map> selectCreatePutOnRecord(@Param("bussinessIds") Object[] bussinessIds);

  /**
   * 自评信息维护列表
   * @param
   * @return
   */
  List<JiWeiNoPassVo> selectItemsList(@Param("applyId")String applyId);
}
