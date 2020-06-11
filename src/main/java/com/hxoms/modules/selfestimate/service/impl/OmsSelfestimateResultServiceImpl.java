package com.hxoms.modules.selfestimate.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitem;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemResult;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemVO;
import com.hxoms.modules.selfestimate.mapper.OmsSelfestimateResultitemMapper;
import com.hxoms.modules.selfestimate.service.OmsSelfestimateResultService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 自评项目结果
 * @author: lijing
 * @date: 2020-05-26
 */
@Service
public class OmsSelfestimateResultServiceImpl implements OmsSelfestimateResultService {

    @Autowired
    private OmsSelfestimateResultitemMapper omsSelfestimateResultitemMapper;
    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String insertOrUpdateResult(List<OmsSelfestimateResultitem> omsSelfestimateResultitems) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //处理自评项结果
        for (OmsSelfestimateResultitem omsSelfestimateResultitem : omsSelfestimateResultitems) {
            if (StringUtils.isBlank(omsSelfestimateResultitem.getId())) {
                //新增
                omsSelfestimateResultitem.setId(UUIDGenerator.getPrimaryKey());
                omsSelfestimateResultitem.setCreateTime(new Date());
                omsSelfestimateResultitem.setCreateUser(userInfo.getId());
                if (omsSelfestimateResultitemMapper.insert(omsSelfestimateResultitem) < 1) {
                    throw new CustomMessageException("操作失败");
                }
            } else {
                //编辑
                omsSelfestimateResultitem.setModifyTime(new Date());
                omsSelfestimateResultitem.setModifyUser(userInfo.getId());
                if (omsSelfestimateResultitemMapper.updateById(omsSelfestimateResultitem) < 1) {
                    throw new CustomMessageException("操作失败");
                }
            }
        }
        return "操作成功";
    }

    @Override
    public OmsSelfestimateResultitemResult selectResultList(String applyId, String selffileId, String type) {
        OmsSelfestimateResultitemResult omsSelfestimateResultitemResult = new OmsSelfestimateResultitemResult();
        if (StringUtils.isBlank(applyId) || StringUtils.isBlank(selffileId) || StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        //查询自评项结果
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("selffileId", selffileId);
        paramsMap.put("applyId", applyId);
        List<OmsSelfestimateResultitemVO> omsSelfestimateResultitems = omsSelfestimateResultitemMapper.selectItemResultList(paramsMap);
        omsSelfestimateResultitemResult.setOmsSelfestimateResultitems(omsSelfestimateResultitems);
        //查询出国人所在单位
        String b0100;
        if (Constants.oms_business[1].equals(type)){
            //因私出国
            OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(applyId);
            if (omsPriApplyVO != null){
                omsSelfestimateResultitemResult.setB0100(omsPriApplyVO.getB0100());
                omsSelfestimateResultitemResult.setB0101(omsPriApplyVO.getB0101());
                b0100 = omsPriApplyVO.getB0100();
            }else{
                throw new CustomMessageException("该申请单不存在");
            }
        }
        // TODO  主要领导
        return omsSelfestimateResultitemResult;
    }
}
