package com.hxoms.modules.selfestimate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.majorLeader.mapper.OmsSupMajorLeaderMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper.OmsCerCancellateApplyMapper;
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
    @Autowired
    private OmsFileMapper omsFileMapper;
    @Autowired
    private OmsCerCancellateApplyMapper omsCerCancellateApplyMapper;

    @Override
    public List<OmsSelfFile> selectItemsList(String type) {
        if (StringUtils.isEmpty(type)){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());

//        UserInfo userInfo = UserInfoUtil.getUserInfo();
//        userInfo.setId("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        B01 b01 = new B01();
//        b01.setB0100("cd3ffb59-d5ba-1038-bdaa-c2ae22a0bcce");



        QueryWrapper<OmsSelfFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TYPE",type)
                .eq("B0100", b01.getB0100());
        List<OmsSelfFile> omsSelfFiles = omsSelfFileMapper.selectList(queryWrapper);
        return omsSelfFiles;
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

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public List<OmsSelfFileVO> selectFileList(String type, String applyId, String personType) {
        if (StringUtils.isEmpty(type) || StringUtils.isBlank(applyId)){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
//        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());


//        UserInfo userInfo = UserInfoUtil.getUserInfo();
//        userInfo.setId("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        B01 b01 = new B01();
//        b01.setB0100("cd3ffb59-d5ba-1038-bdaa-c2ae22a0bcce");


        //查询自评项目是否初始化
        QueryWrapper<OmsSelfFile> wrapper = new QueryWrapper<>();
        wrapper.eq("B0100", userInfo.getOrgId())
                .eq("TYPE", type);
        if (omsSelfFileMapper.selectCount(wrapper) < 1){
            //初始化
            initOmsSelfEstimate(type, userInfo.getOrgId());
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("b0100", userInfo.getOrgId());
        paramMap.put("type", type);
        paramMap.put("applyId", applyId);
        List<OmsSelfFileVO> omsSelfFileVOS = omsSelfFileMapper.selectFileList(paramMap);
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

    @Override
    public List<OmsSelfestimateItems> selectSelfItemList(String selffileId) {
        if (StringUtils.isBlank(selffileId)){
            throw new CustomMessageException("参数错误");
        }
        QueryWrapper<OmsSelfestimateItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SELFFILE_ID", selffileId);
        return omsSelfestimateItemsMapper.selectList(queryWrapper);
    }

    @Override
    public OmsSelfFileVO selectFileItemsList(String type, String selffileId, String applyId, String personType) {
        OmsSelfFileVO omsSelfFileVO = new OmsSelfFileVO();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("selffileId", selffileId);
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
        }else if("oms_cer_cancellate".equals(type)){
            //注销证照
            B01 b01 = omsCerCancellateApplyMapper.getB0100ByApplyId(applyId);
            omsSelfFileVO.setB0100(b01.getB0100());
            omsSelfFileVO.setB0101(b01.getB0101());
        }
        //主要领导
        QueryWrapper<OmsSupMajorLeader> wrapper = new QueryWrapper<>();
        wrapper.select("NAME")
                .eq("B0100", b0100);
        List<OmsSupMajorLeader> omsSupMajorLeaders = omsSupMajorLeaderMapper.selectList(wrapper);
        omsSelfFileVO.setPersonInfoVOS(omsSupMajorLeaders);
        return omsSelfFileVO;
    }

    /**
     * 初始化自评项目
     * @param type 类型（因公，因私）
     */
    private void initOmsSelfEstimate(String type, String b0100){
        QueryWrapper<OmsSelfFile> wrapper = new QueryWrapper<>();
        //初始化
        wrapper.isNull("B0100")
                .eq("TYPE", type);
        List<OmsSelfFile> omsSelfFiles = omsSelfFileMapper.selectList(wrapper);
        for (OmsSelfFile omsSelfFile : omsSelfFiles){
            //查询自评项目
            QueryWrapper<OmsSelfestimateItems> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SELFFILE_ID", omsSelfFile.getId());
            List<OmsSelfestimateItems> omsSelfestimateItems = omsSelfestimateItemsMapper.selectList(queryWrapper);
            omsSelfFile.setId(UUIDGenerator.getPrimaryKey());
            omsSelfFile.setB0100(b0100);
            omsSelfFile.setCreateTime(new Date());
            //查询文件id
            QueryWrapper<OmsFile> omsFileQueryWrapper = new QueryWrapper<>();
            omsFileQueryWrapper.eq("B0100", b0100)
                    .eq("FILE_ID", omsSelfFile.getCheckfileId());
            OmsFile omsFile = omsFileMapper.selectOne(omsFileQueryWrapper);
            omsSelfFile.setCheckfileId(omsFile.getId());
            omsSelfFileMapper.insert(omsSelfFile);
            for (OmsSelfestimateItems omsSelfestimateItems1 : omsSelfestimateItems){
                omsSelfestimateItems1.setId(UUIDGenerator.getPrimaryKey());
                omsSelfestimateItems1.setSelffileId(omsSelfFile.getId());
                omsSelfestimateItemsMapper.insert(omsSelfestimateItems1);
            }
        }
    }
}
