package com.hxoms.modules.privateabroad.service.impl;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturn;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturnVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApprovalReturnIPageParam;
import com.hxoms.modules.privateabroad.mapper.OmsApprovalReturnMapper;
import com.hxoms.modules.privateabroad.service.OmsAbroadApprovalService;
import com.hxoms.modules.privateabroad.service.OmsApprovalReturnService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @desc: 因私出国审批回收管理
 * @author: lijing
 * @date: 2020-06-16
 */
@Service
public class OmsApprovalReturnServiceImpl implements OmsApprovalReturnService {
    @Autowired
    private OmsApprovalReturnMapper omsApprovalReturnMapper;

    @Override
    public String savePriApprovalReturn(OmsApprovalReturn omsApprovalReturn) {
        if (StringUtils.isBlank(omsApprovalReturn.getApplyId())){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if (StringUtils.isBlank(omsApprovalReturn.getId())){
            //新增
            omsApprovalReturn.setId(UUIDGenerator.getPrimaryKey());
            omsApprovalReturn.setCreateTime(new Date());
            omsApprovalReturn.setCreateUser(userInfo.getId());
            if (omsApprovalReturnMapper.insert(omsApprovalReturn) < 1){
                throw new CustomMessageException("操作失败");
            }
        }else{
            //编辑
            omsApprovalReturn.setModifyTime(new Date());
            omsApprovalReturn.setModifyUser(userInfo.getId());
            if (omsApprovalReturnMapper.updateById(omsApprovalReturn) < 1){
                throw new CustomMessageException("操作失败");
            }
        }
        return "操作成功";
    }

    @Override
    public String deletePriApprovalReturn(OmsApprovalReturn omsApprovalReturn) {
        if (StringUtils.isBlank(omsApprovalReturn.getId())){
            throw new CustomMessageException("参数错误");
        }
        if (omsApprovalReturnMapper.selectById(omsApprovalReturn.getId()) == null){
            throw new CustomMessageException("该条信息不存在");
        }
        if (omsApprovalReturnMapper.deleteById(omsApprovalReturn.getId()) < 1){
            throw new CustomMessageException("删除失败");
        }
        return "删除成功";
    }

    @Override
    public PageInfo<OmsApprovalReturnVO> selectPriApprovalReturnPagelist(OmsPriApprovalReturnIPageParam omsPriApprovalReturnIPageParam) {
        //分页
        PageUtil.pageHelp(omsPriApprovalReturnIPageParam.getPageNum(), omsPriApprovalReturnIPageParam.getPageSize());
        List<OmsApprovalReturnVO> omsApprovalReturnVOS = omsApprovalReturnMapper.selectPriApprovalReturnPagelist(omsPriApprovalReturnIPageParam);
        //返回数据
        PageInfo<OmsApprovalReturnVO> pageInfo = new PageInfo(omsApprovalReturnVOS);
        return pageInfo;
    }
}
