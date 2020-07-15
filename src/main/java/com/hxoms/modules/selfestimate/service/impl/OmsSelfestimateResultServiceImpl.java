package com.hxoms.modules.selfestimate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.majorLeader.mapper.OmsSupMajorLeaderMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.selfestimate.entity.OmsSelfFile;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitem;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemResult;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemVO;
import com.hxoms.modules.selfestimate.entity.paramentity.ResultListParam;
import com.hxoms.modules.selfestimate.mapper.OmsSelfestimateResultitemMapper;
import com.hxoms.modules.selfestimate.service.OmsSelfestimateResultService;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.mapper.B01Mapper;
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

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public List<OmsSelfestimateResultitem> insertOrUpdateResult(List<OmsSelfestimateResultitem> omsSelfestimateResultitems) {
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
        return omsSelfestimateResultitems;
    }
}
