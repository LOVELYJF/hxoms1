package com.hxoms.modules.privateabroad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriTogetherperson;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyParam;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriTogetherpersonMapper;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
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
}
