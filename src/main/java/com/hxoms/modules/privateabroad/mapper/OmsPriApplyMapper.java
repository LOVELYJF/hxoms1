package com.hxoms.modules.privateabroad.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriPubApply;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OmsPriApplyMapper extends BaseMapper<OmsPriApply>{
    /**
     * 因私出国申请列表
     * @param params  查询条件
     */
    List<OmsPriApplyVO> selectOmsPriApplyIPage(@Param("params") OmsPriApplyIPageParam params);

    /**
     * 通过人员id查询因私出国申请所需用户信息
     * @param paramMap 人员id
     * @return
     */
    OmsPriApplyVO selectPersonInfoByA0100(Map<String, String> paramMap);

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

    /**
     * 撤销申请人员统计
     * @return
     */
    List<Map<String, Object>> selectCancelInfor(@Param("params") OmsPriApplyIPageParam params);

    /**
     * 因私撤销统计
     * @return
     */
    Integer cancelCount(Map<String, String> paramMap);

    /**
     * 签注配置查询
     * @param params
     * @return
     */
    List<Map<String, String>> selectVisaSettingByCode(Map<String, String> params);

    List<OmsPriPubApply> selectPriPubList(String a0100);

    /**
     * 查询禁止性、限制性、敏感性国家和地区
     * @return
     */
    List<Map<String, String>> selectSensitiveCountry();

}