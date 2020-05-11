package com.hxoms.modules.publicity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OmsPubApplyMapper extends BaseMapper<OmsPubApply> {

    OmsPubApply selectById();

    List<PersonInfoVO> selectPersonListByOrg(String b0100);

    Map<String, Object> selectBasePersonInfo(@Param("b0100") String b0100, @Param("a0100") String a0100);

    /**
     * 最近一次因公出国情况
     *
     * @author sunqian
     * @date 2020/4/25 17:00
     */
    List<OmsPubApply> selectPubAbroadLatestInfo(String a0100);

    /**
     * 查询条件
     *
     * @author sunqian
     * @date 2020/4/28 15:17
     */
    List<OmsCondition> selectCheckCondition(String conditionType);

    int excuteSelectSql(String sql);

    /**
     * 查询申请人员信息
     * 
     * @author sunqian
     * @date 2020/5/6 14:56
     */
    List<OmsPubApply> selectPubApplyList(String batchId);

    Map<String, Object> selectPersonInfoByApplyId(String applyId);
}