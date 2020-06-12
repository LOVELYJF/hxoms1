package com.hxoms.modules.privateabroad.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsPriApplyMapper extends BaseMapper<OmsPriApply>{
    /**
     * 因私出国申请列表
     * @param omsPriApplyIPageParam  查询条件
     */
    List<OmsPriApplyVO> selectOmsPriApplyIPage(@Param("omsPriApplyIPageParam") OmsPriApplyIPageParam omsPriApplyIPageParam);

    /**
     * 通过人员id查询因私出国申请所需用户信息
     * @param a0100 人员id
     * @return
     */
    OmsPriApplyVO selectPersonInfoByA0100(String a0100);

    /**
     * 根据姓名或者拼音查找用户
     * @param keyword 关键词
     * @return
     * @throws Exception
     */
    List<PersonInfoVO> selectPersonByKeyword(String keyword);
    /**
     * 申请详情
     * @param id
     * @return
     */
    OmsPriApplyVO selectPriApplyById(String id);
    /**
     * 基本流程数据统计
     * @return
     */
    List<CountStatusResult> selectCountStatus();
}