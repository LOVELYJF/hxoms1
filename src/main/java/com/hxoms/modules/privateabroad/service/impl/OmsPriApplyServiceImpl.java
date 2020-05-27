package com.hxoms.modules.privateabroad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.condition.mapper.OmsConditionMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriTogetherperson;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyParam;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriTogetherpersonMapper;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @desc: 因私出国境申请
 *
 * @author: lijing
 * @date: 2020-05-15
 */
@Service
public class OmsPriApplyServiceImpl implements OmsPriApplyService {
    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;
    @Autowired
    private OmsConditionMapper omsConditionMapper;
    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;
    @Autowired
    private OmsPriTogetherpersonMapper omsPriTogetherpersonMapper;

    @Override
    public PageInfo<OmsPriApplyVO> selectOmsPriApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam) {
        if (!StringUtils.isBlank(omsPriApplyIPageParam.getApplyStatusString())){
            omsPriApplyIPageParam.setApplyStatus(omsPriApplyIPageParam.getApplyStatusString().split(","));
        }
        //分页
        PageUtil.pageHelp(omsPriApplyIPageParam.getPageNum(), omsPriApplyIPageParam.getPageSize());
        List<OmsPriApplyVO> omsPriApplyVOS = omsPriApplyMapper.selectOmsPriApplyIPage(omsPriApplyIPageParam);
        //返回数据
        PageInfo<OmsPriApplyVO> pageInfo = new PageInfo(omsPriApplyVOS);
        return pageInfo;
    }

    @Override
    public OmsPriApplyVO selectPersonById(String b0100, String a0100) {
        if (StringUtils.isBlank(a0100) || StringUtils.isBlank(b0100)){
            throw new CustomMessageException("参数错误");
        }
        //约束条件检查
        OmsPriApply omsPriApply = new OmsPriApply();
        omsPriApply.setA0100(a0100);
        omsPriApply.setB0100(b0100);
        //查询用户基本信息
        OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPersonInfoByA0100(a0100);
        //TODO 获取涉密信息
        //TODO  获取负面信息
        //TODO  证件信息
        return omsPriApplyVO;
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String insertOrUpdatePriApply(OmsPriApplyParam omsPriApplyParam) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //基本信息
        OmsPriApply omsPriApply = omsPriApplyParam.getOmsPriApply();
        //随行人员
        List<OmsPriTogetherperson> omsPriTogetherpersonList = omsPriApplyParam.getOmsPriTogetherperson();
        if (StringUtils.isBlank(omsPriApply.getA0100())){
            throw new CustomMessageException("请选择需要出国的人员");
        }
        if (StringUtils.isBlank(omsPriApply.getAbroadReasons())){
            throw new CustomMessageException("出国事由不能为空");
        }
        if (omsPriApply.getAbroadTime() != null){
            throw new CustomMessageException("出国时间不能为空");
        }
        if (omsPriApply.getReturnTime() != null){
            throw new CustomMessageException("回国时间不能为空");
        }
        //基本信息保存
        //设置草稿状态，type为2时，如果验证信息通过则修改相关状态
        omsPriApply.setApplyStatus("1");
        if (StringUtils.isBlank(omsPriApply.getId())) {
            omsPriApply.setId(UUIDGenerator.getPrimaryKey());
            omsPriApplyMapper.insert(omsPriApply);
        } else {
            omsPriApplyMapper.updateById(omsPriApply);
        }
        int result = 0;
        //随行人员信息保存
        for (OmsPriTogetherperson omsPriTogetherperson : omsPriTogetherpersonList) {
            if (StringUtils.isBlank(omsPriTogetherperson.getId())){
                omsPriTogetherperson.setId(UUIDGenerator.getPrimaryKey());
                omsPriTogetherperson.setApplyId(omsPriApply.getId());
                omsPriTogetherperson.setCreateTime(new Date());
                omsPriTogetherperson.setCreateUser(userInfo.getId());
                result = omsPriTogetherpersonMapper.insert(omsPriTogetherperson);
            }else{
                omsPriTogetherperson.setModifyTime(new Date());
                omsPriTogetherperson.setCreateUser(userInfo.getId());
                result = omsPriTogetherpersonMapper.updateById(omsPriTogetherperson);
            }
        }
        if (result < 1){
            throw new CustomMessageException("申请失败");
        }
        return "申请成功";
    }

    @Override
    public String deletePriApply(String id) {
        //只能删除草稿状态的
        QueryWrapper<OmsPriApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("applyStatus", '1');  //草稿
        int count = omsPriApplyMapper.selectCount(queryWrapper);
        if (count > 0){
            throw new CustomMessageException("只能删除草稿");
        }
        int deleteStatus = omsPriApplyMapper.deleteById(id);
        if (deleteStatus < 1){
            throw new CustomMessageException("删除失败");
        }
        return "删除成功";
    }

    @Override
    public String updateApplyStatus(OmsPriApply omsPriApply) {
        if (StringUtils.isBlank(omsPriApply.getApplyStatus()) && StringUtils.isBlank(omsPriApply.getId())){
            throw new CustomMessageException("参数错误");
        }
        int updateStatus = omsPriApplyMapper.updateById(omsPriApply);
        if (updateStatus < 1){
            throw new CustomMessageException("操作失败");
        }
        return "操作成功";
    }

    @Override
    public List<PersonInfoVO> selectPersonByKeyword(String keyword) {
        if (StringUtils.isBlank(keyword)){
            throw new CustomMessageException("参数错误");
        }
        List<PersonInfoVO> personInfoVOS = omsPriApplyMapper.selectPersonByKeyword(keyword);
        return personInfoVOS;
    }

    @Override
    public OmsPriApplyVO selectPriApplyById(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("参数错误");
        }
        OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(id);
        return omsPriApplyVO;
    }

    @Override
    public List<Map<String, String>> checkRrestrainCondition(String id) {
        OmsPriApply omsPriApply = omsPriApplyMapper.selectById(id);
        return checkPriApply(omsPriApply, "2");
    }

    /**
     * 申请信息校验
     * @param omsPriApply 申请信息
     * @param type 1、选择人员后校验（保存前） 2、保存时（保存后）
     * @return
     */
    public List<Map<String, String>> checkPriApply(OmsPriApply omsPriApply, String type) {
        String flag = "1";   //是否通过验证,默认通过
        //结果
        List<Map<String, String>> result = new ArrayList<>();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        QueryWrapper<OmsCondition> queryWrapper = new QueryWrapper<>();
        if ("1".equals(type)){
            queryWrapper.eq("check_type", type);
        }
        //因私出国
        queryWrapper.eq("condition_type", "2")
                .orderByAsc("check_type");
        List<OmsCondition> omsConditions = omsConditionMapper.selectList(queryWrapper);

        if (omsConditions != null && omsConditions.size() > 0){
            //检验条件
            for (OmsCondition omsCondition : omsConditions) {
                String sql = omsCondition.getSql();
                if (!StringUtils.isBlank(sql)) {
                    Map<String, String> map = new HashMap<>();
                    sql = sql
                            .replace("@a0100", omsPriApply.getA0100())
                            .replace("@id", omsPriApply.getId())
                            .replace("@loginA0100", userInfo.getId());

                    int count = omsPubApplyMapper.excuteSelectSql(sql);
                    //条件标题
                    map.put("title", omsCondition.getName());
                    map.put("desc", omsCondition.getDesc());
                    if (count > 0) {
                        //不符合条件
                        map.put("isFit" , "0");
                        flag = "0";
                    }else{
                        //符合条件
                        map.put("isFit" , "1");
                    }
                    result.add(map);
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("title", "是否通过验证");
        map.put("isFit", flag);
        result.add(map);
        return result;
    }
}
