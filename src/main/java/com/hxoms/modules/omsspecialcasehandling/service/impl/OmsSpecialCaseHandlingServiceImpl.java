package com.hxoms.modules.omsspecialcasehandling.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling;
import com.hxoms.modules.omsspecialcasehandling.mapper.OmsSpecialcasehandlingMapper;
import com.hxoms.modules.omsspecialcasehandling.service.OmsSpecialCaseHandlingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OmsSpecialCaseHandlingServiceImpl implements OmsSpecialCaseHandlingService {
    @Autowired
    private OmsSpecialcasehandlingMapper specialcasehandlingMapper;
    /**
     * 功能描述: <br>
     * 〈新增或修改特殊情况〉
     * @Param: [specialcasehandling]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:52
     */
    @Override
    public void insertOrUpdateSpecialCase(OmsSpecialcasehandling specialCaseHandling) {
        if (specialCaseHandling == null){
            throw new CustomMessageException("参数为空!");
        }
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        if (specialCaseHandling.getId() != null && !specialCaseHandling.getId().equals("")){
            //修改
            //修改人
            specialCaseHandling.setModifyUser(loginUser.getId());
            //修改时间
            specialCaseHandling.setModifyTime(new Date());
            specialcasehandlingMapper.updateByPrimaryKeySelective(specialCaseHandling);
        }else {
            //新增
            specialCaseHandling.setId(UUIDGenerator.getPrimaryKey());
            //创建人
            specialCaseHandling.setCreateUser(loginUser.getId());
            //创建时间
            specialCaseHandling.setCreateDate(new Date());
            specialcasehandlingMapper.insertSelective(specialCaseHandling);
        }
    }


    @Override
    public void deleteSpecialCaseById(String id) {
        if (id == null || id == ""){
            throw new CustomMessageException("参数为空!");
        }
        specialcasehandlingMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo getAllSpecialCase(Integer pageNum, Integer pageSize, String keyWord) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);   //设置传入页码，以及每页的大小
        List<LinkedHashMap<String, Object>> list = specialcasehandlingMapper.getAllSpecialCase(keyWord);
        PageInfo info1 = new PageInfo(list);
        return info1;
    }
}
