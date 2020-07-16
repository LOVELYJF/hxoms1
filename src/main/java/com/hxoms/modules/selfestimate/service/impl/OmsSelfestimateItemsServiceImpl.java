package com.hxoms.modules.selfestimate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.hxoms.modules.selfestimate.entity.OmsSelfFileVO;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateItems;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemVO;
import com.hxoms.modules.selfestimate.mapper.OmsSelfFileMapper;
import com.hxoms.modules.selfestimate.mapper.OmsSelfestimateItemsMapper;
import com.hxoms.modules.selfestimate.mapper.OmsSelfestimateResultitemMapper;
import com.hxoms.modules.selfestimate.service.OmsSelfestimateItemsService;
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
 * @desc: 自评项目
 * @author: lijing
 * @date: 2020-05-25
 */
@Service
public class OmsSelfestimateItemsServiceImpl implements OmsSelfestimateItemsService {
    @Autowired
    private OmsSelfestimateItemsMapper omsSelfestimateItemsMapper;
    @Autowired
    private OmsSelfFileMapper omsSelfFileMapper;
    @Autowired
    private B01Mapper b01Mapper;
    @Autowired
    private OmsSupMajorLeaderMapper omsSupMajorLeaderMapper;
    @Autowired
    private OmsSelfestimateResultitemMapper omsSelfestimateResultitemMapper;
    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;

    @Override
    public List<OmsSelfFileVO> selectItemsList(String type) {
        if (StringUtils.isEmpty(type)){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("type", type);
        paramMap.put("b0100", b01.getB0100());
        List<OmsSelfFileVO> omsSelfFileVOS = omsSelfFileMapper.selectItemsList(paramMap);
        return omsSelfFileVOS;
    }

    @Override
    public String insertItem(OmsSelfestimateItems omsSelfestimateItems) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        int result = 0;
        if (StringUtils.isBlank(omsSelfestimateItems.getId())){
            //添加
            omsSelfestimateItems.setId(UUIDGenerator.getPrimaryKey());
            omsSelfestimateItems.setCreateTime(new Date());
            omsSelfestimateItems.setCreateUser(userInfo.getId());
            result = omsSelfestimateItemsMapper.insert(omsSelfestimateItems);
        }else{
            //修改
            omsSelfestimateItems.setModifyTime(new Date());
            omsSelfestimateItems.setModifyUser(userInfo.getId());
            result = omsSelfestimateItemsMapper.updateById(omsSelfestimateItems);
        }
        if (result < 1){
            throw new CustomMessageException("操作失败");
        }
        return "操作成功";
    }

    @Override
    public String deleteItem(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("参数错误");
        }
        int result = omsSelfestimateItemsMapper.deleteById(id);
        if (result < 1){
            throw new CustomMessageException("删除失败");
        }
        return "删除成功";
    }

    @Override
    public String insertSelfFile(OmsSelfFile omsSelfFile) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());
        if (StringUtils.isBlank(omsSelfFile.getId())){
            //添加
            omsSelfFile.setId(UUIDGenerator.getPrimaryKey());
            omsSelfFile.setB0100(b01.getB0100());
            omsSelfFile.setCreateTime(new Date());
            omsSelfFile.setCreateUser(userInfo.getId());
            if (omsSelfFileMapper.insert(omsSelfFile) < 1){
                throw new CustomMessageException("添加失败");
            }
        }else{
            //更新
            omsSelfFile.setModifyUser(userInfo.getId());
            omsSelfFile.setModifyTime(new Date());
            if(omsSelfFileMapper.updateById(omsSelfFile) < 1){
                throw new CustomMessageException("更新失败");
            }
        }
        return "操作成功";
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String deleteSelfFile(String id) {
        if (StringUtils.isEmpty(id)){
            throw new CustomMessageException("参数错误");
        }
        //删除自评项目
        OmsSelfFile omsSelfFile = omsSelfFileMapper.selectById(id);
        if (omsSelfFile == null){
            throw new CustomMessageException("该数据不存在");
        }
        QueryWrapper<OmsSelfestimateItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SELFFILE_ID", omsSelfFile.getId());
        omsSelfestimateItemsMapper.delete(queryWrapper);
        //删除自评文件
        if(omsSelfFileMapper.deleteById(id) < 1){
            throw new CustomMessageException("删除失败");
        }
        return "删除成功";
    }

    @Override
    public List<OmsSelfFileVO> selectFileList(String type, String applyId, String personType) {
        if (StringUtils.isEmpty(type) || StringUtils.isBlank(applyId)){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("b0100", b01.getB0100());
        paramMap.put("type", type);
        paramMap.put("applyId", applyId);
        List<OmsSelfFileVO> omsSelfFileVOS = omsSelfFileMapper.selectFileList(paramMap);
        for (OmsSelfFileVO omsSelfFileVO : omsSelfFileVOS) {
            //查询自评项结果
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("selffileId", omsSelfFileVO.getId());
            paramsMap.put("applyId", applyId);
            paramsMap.put("personType", personType);
            List<OmsSelfestimateResultitemVO> omsSelfestimateResultitems = omsSelfestimateResultitemMapper.selectItemResultList(paramsMap);
            omsSelfFileVO.setOmsSelfestimateResultitems(omsSelfestimateResultitems);
            //查询出国人所在单位
            String b0100 = "";
            if (Constants.oms_business[1].equals(type)){
                //因私出国
                OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(applyId);
                if (omsPriApplyVO != null){
                    b0100 = omsPriApplyVO.getB0100();
                    omsSelfFileVO.setB0100(b0100);
                    omsSelfFileVO.setB0101(omsPriApplyVO.getB0101());
                }else{
                    throw new CustomMessageException("该申请单不存在");
                }
            }
            //主要领导
            QueryWrapper<OmsSupMajorLeader> wrapper = new QueryWrapper<>();
            wrapper.select("NAME")
                    .eq("B0100", b0100);
            List<OmsSupMajorLeader> omsSupMajorLeaders = omsSupMajorLeaderMapper.selectList(wrapper);
            omsSelfFileVO.setPersonInfoVOS(omsSupMajorLeaders);
        }
        return omsSelfFileVOS;
    }

    @Override
    public List<Map<String, String>> selectOmsFileList(String type) {
        if (StringUtils.isEmpty(type)){
            throw new CustomMessageException("参数错误");
        }
        Map<String, String> paramMap = new HashMap<>();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());
        paramMap.put("type", type);
        paramMap.put("b0100", b01.getB0100());
        List<Map<String, String>> result = omsSelfFileMapper.selectOmsFileList(paramMap);
        return result;
    }
}
