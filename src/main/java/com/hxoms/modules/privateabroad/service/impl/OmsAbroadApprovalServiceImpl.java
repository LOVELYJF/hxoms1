package com.hxoms.modules.privateabroad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.privateabroad.entity.OmsAbroadApproval;
import com.hxoms.modules.privateabroad.mapper.OmsAbroadApprovalMapper;
import com.hxoms.modules.privateabroad.service.OmsAbroadApprovalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @desc: 出国审批管理
 * @author: lijing
 * @date: 2020-06-15
 */
@Service
public class OmsAbroadApprovalServiceImpl implements OmsAbroadApprovalService {
    @Autowired
    private OmsAbroadApprovalMapper omsAbroadApprovalMapper;

    @Override
    public List<OmsAbroadApproval> selectOmsAbroadApprovalList(String applyId, String type) {
        if (StringUtils.isBlank(applyId) || StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        QueryWrapper<OmsAbroadApproval> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("APPLY_ID", applyId)
                .eq("TYPE", type);
        List<OmsAbroadApproval> omsAbroadApprovals = omsAbroadApprovalMapper.selectList(queryWrapper);
        return omsAbroadApprovals;
    }

    @Override
    public String insertOmsAbroadApproval(OmsAbroadApproval omsAbroadApproval) {
        if (StringUtils.isBlank(omsAbroadApproval.getApplyId())
            || StringUtils.isBlank(omsAbroadApproval.getType())){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        omsAbroadApproval.setId(UUIDGenerator.getPrimaryKey());
        omsAbroadApproval.setCreateTime(new Date());
        omsAbroadApproval.setCreateUser(userInfo.getId());
        if (omsAbroadApprovalMapper.insert(omsAbroadApproval) < 1){
            throw new CustomMessageException("操作失败");
        }
        return "操作成功";
    }
}
