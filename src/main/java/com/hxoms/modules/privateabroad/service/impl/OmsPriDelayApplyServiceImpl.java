package com.hxoms.modules.privateabroad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriDelayApplyMapper;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.privateabroad.service.OmsPriDelayApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc: 延期回国
 * @author: lijing
 * @date: 2020-06-03
 */
@Service
public class OmsPriDelayApplyServiceImpl implements OmsPriDelayApplyService {
    @Autowired
    private OmsPriDelayApplyMapper omsPriDelayApplyMapper;
    @Autowired
    private OmsPriApplyService omsPriApplyService;
    @Autowired
    private OmsConditionService omsConditionService;

    @Override
    public PageInfo<OmsPriDelayApplyVO> selectOmsDelayApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam) {
        if (!StringUtils.isBlank(omsPriApplyIPageParam.getApplyStatusString())){
           // omsPriApplyIPageParam.setApplyStatus(omsPriApplyIPageParam.getApplyStatusString().split(","));
        }
        //分页
        PageUtil.pageHelp(omsPriApplyIPageParam.getPageNum(), omsPriApplyIPageParam.getPageSize());
        List<OmsPriDelayApplyVO> omsPriDelayApplyVOS = omsPriDelayApplyMapper.selectOmsDelayApplyIPage(omsPriApplyIPageParam);
        //返回数据
        PageInfo<OmsPriDelayApplyVO> pageInfo = new PageInfo(omsPriDelayApplyVOS);
        return pageInfo;
    }

    @Override
    public List<Map<String, String>> insertOrUpdateApply(OmsPriDelayApply omsPriDelayApply) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if (StringUtils.isBlank(omsPriDelayApply.getId())){
            //添加
            omsPriDelayApply.setApplyStatus(1);
            omsPriDelayApply.setId(UUIDGenerator.getPrimaryKey());
            omsPriDelayApply.setCreateUser(userInfo.getId());
            omsPriDelayApply.setCreateTime(new Date());
            if (omsPriDelayApplyMapper.insert(omsPriDelayApply) < 1){
                throw new CustomMessageException("操作失败");
            }
        }else{
            //更新
            omsPriDelayApply.setModifyUser(userInfo.getId());
            omsPriDelayApply.setModifyTime(new Date());
            if (omsPriDelayApplyMapper.updateById(omsPriDelayApply) < 1){
                throw new CustomMessageException("操作失败");
            }
        }
        //约束条件
        List<Map<String, String>> condition = omsConditionService.checkCondition(omsPriDelayApply.getApplyId(), Constants.oms_business[2]);
        return condition;
    }

    @Override
    public String updateDelayApplyStatus(OmsPriDelayApply omsPriDelayApply) {
        if (StringUtils.isBlank(omsPriDelayApply.getId())){
            throw new CustomMessageException("参数错误");
        }
        if (omsPriDelayApplyMapper.updateById(omsPriDelayApply) < 1){
            throw new CustomMessageException("操作失败");
        }
        return "操作成功";
    }

    @Override
    public String deleteDelayApply(String id) {
        //只能删除草稿状态的
        QueryWrapper<OmsPriDelayApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("applyStatus", 1);  //草稿
        int count = omsPriDelayApplyMapper.selectCount(queryWrapper);
        if (count == 0){
            throw new CustomMessageException("只能删除草稿");
        }
        int deleteStatus = omsPriDelayApplyMapper.deleteById(id);
        if (deleteStatus < 1){
            throw new CustomMessageException("删除失败");
        }
        return "删除成功";
    }

    @Override
    public OmsPriDelayApplyVO selectDelayApplyById(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("参数错误");
        }
        OmsPriDelayApplyVO omsPriDelayApplyVO = (OmsPriDelayApplyVO) omsPriDelayApplyMapper.selectById(id);
        if (omsPriDelayApplyVO == null){
            throw new CustomMessageException("数据错误");
        }
        //查询申请记录
        OmsPriApplyVO omsPriApplyVO = omsPriApplyService.selectPriApplyById(omsPriDelayApplyVO.getApplyId());
        if (omsPriApplyVO == null){
            throw new CustomMessageException("数据错误");
        }
        omsPriDelayApplyVO.setOmsPriApplyVO(omsPriApplyVO);
        return omsPriDelayApplyVO;
    }
}
