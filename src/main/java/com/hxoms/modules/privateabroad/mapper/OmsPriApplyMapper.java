package com.hxoms.modules.privateabroad.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecordVO;
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

    /**
     * 查询禁止性、限制性、敏感性国家和地区
     * @return
     */
    List<Map<String, String>> selectPLSCountry();

    List<OmsEntryexitRecordVO> selectPriList(@Param("procpersonId") String omsId);

    /**
     * 功能描述: <br>
     * 〈获取干部近3年出国记录〉
     * @Param: [a0100]
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.OmsPriPubApply>
     * @Author: 李逍遥
     * @Date: 2020/9/9 16:42
     */
    List<OmsPriApplyVO> selectPriListBy3Year(@Param("a0100") String a0100);

    /**
    * @description:检查登记备案人员在该出国时间是否存在出国境申请
    * @author:杨波
    * @date:2020-10-13
    *  * @param 登记备案人员ID
     *  cgsj 出国境时间
    * @return:
    **/
    List<OmsPriApply> selectExistsAbroad(@Param("id") String id,@Param("cgsj") String cgsj);
}