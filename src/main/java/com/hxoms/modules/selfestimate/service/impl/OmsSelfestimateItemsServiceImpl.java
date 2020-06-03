package com.hxoms.modules.selfestimate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.selfestimate.entity.OmsSelfFile;
import com.hxoms.modules.selfestimate.entity.OmsSelfFileVO;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateItems;
import com.hxoms.modules.selfestimate.mapper.OmsSelfFileMapper;
import com.hxoms.modules.selfestimate.mapper.OmsSelfestimateItemsMapper;
import com.hxoms.modules.selfestimate.service.OmsSelfestimateItemsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<OmsSelfFileVO> selectItemsList(String type) {
        if (StringUtils.isEmpty(type)){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("type", type);
        paramMap.put("b0100", userInfo.getOrgId());
        List<OmsSelfFileVO> omsSelfFileVOS = omsSelfFileMapper.selectOmsSelfFileList(paramMap);
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
        if (StringUtils.isBlank(omsSelfFile.getId())){
            //添加
            omsSelfFile.setId(UUIDGenerator.getPrimaryKey());
            omsSelfFile.setB0100(userInfo.getOrgId());
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
}
