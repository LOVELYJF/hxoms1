package com.hxoms.modules.publicity.destroyReg.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.publicity.destroyReg.entity.OmsPubDestroy;
import com.hxoms.modules.publicity.destroyReg.entity.OmsPubDestroydetail;
import com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.*;
import com.hxoms.modules.publicity.destroyReg.mapper.OmsPubDestroyMapper;
import com.hxoms.modules.publicity.destroyReg.mapper.OmsPubDestroydetailMapper;
import com.hxoms.modules.publicity.destroyReg.service.OmsPubDestroyService;
import com.hxoms.support.user.entity.User;
import com.hxoms.support.user.mapper.UserMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/1
 */
@Service
public class OmsPubDestroyServiceImpl implements OmsPubDestroyService {
    @Autowired
    private OmsPubDestroyMapper omsPubDestroyMapper;

    @Autowired
    private OmsPubDestroydetailMapper omsPubDestroydetailMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean selectSubmitDesApply(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<SelDestroyApplyVO> pageInfo= new PageInfo<SelDestroyApplyVO>(selectSubmitDesApplyAll());
        return PageUtil.packagePage(pageInfo);
    }

    public List<SelDestroyApplyVO> selectSubmitDesApplyAll() {
        //获取用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<SelDestroyApplyVO> selDestroyApplyVOList =null;
        if(userInfo==null)
            return selDestroyApplyVOList;
        //获取撤销申请数据
        selDestroyApplyVOList =omsPubDestroyMapper.selectSubmitDesApplyAll(userInfo.getId());
        return selDestroyApplyVOList;
    }

    @Override
    public User selectUserInfo() {
        User user=userMapper.selectByPrimaryKey(UserInfoUtil.getUserInfo().getId());
        User userInfo=new User();
        if(user==null)
            return userInfo;
        userInfo.setId(user.getId());
        userInfo.setUserName(user.getUserName());
        userInfo.setOrgId(user.getOrgId());
        userInfo.setUserMobile(user.getUserMobile());
        return userInfo;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void babDestroyReg(DestroyRegVo destroyRegVo) {
        OmsPubDestroy omsPubDestroy=destroyRegVo.getOmsPubDestroy();
        List<OmsPubDestroydetail> omsPubDestroydetailList=destroyRegVo.getOmsPubDestroydetailList();
        if(omsPubDestroy==null||omsPubDestroydetailList==null)
            throw new CustomMessageException("销毁登记失败！原因：无操作数据。");
        omsPubDestroy.setId(UUIDGenerator.getPrimaryKey());
        omsPubDestroyMapper.insert(omsPubDestroy);
        for (OmsPubDestroydetail omsPubDestroydetail:omsPubDestroydetailList) {
            omsPubDestroydetail.setDestroyId(omsPubDestroy.getId());
        }
        int value=omsPubDestroydetailMapper.insertOmsPubDestroydetailList(omsPubDestroydetailList);
        if(value==0)
            throw new CustomMessageException("插入失败！");
    }

    @Override
    public PageBean SelDestroyRegByQuaVo(PageBean pageBean,SelDestroyRegByQuaVo selDestroyRegByQuaVo) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<OmsPubDestroy> pageInfo= new PageInfo<OmsPubDestroy>(SelDestroyRegByQuaVoAll(selDestroyRegByQuaVo));
        return PageUtil.packagePage(pageInfo);
    }

    public List<OmsPubDestroy> SelDestroyRegByQuaVoAll(SelDestroyRegByQuaVo selDestroyRegByQuaVo) {
        List<OmsPubDestroy> omsPubDestroyList=null;
        //获取用户信息
        UserInfo userInfo=UserInfoUtil.getUserInfo();
        if(userInfo==null)
            return omsPubDestroyList;
        selDestroyRegByQuaVo.setId(userInfo.getId());
        omsPubDestroyList=omsPubDestroyMapper.SelDestroyRegByQuaVoAll(selDestroyRegByQuaVo);
        return omsPubDestroyList;
    }
    @Override
    public void exportExcel(ExportRequestPara exportRequestPara, ServletOutputStream outputStream) throws IOException {
        List<OmsPubDestroy> omsPubDestroyList = exportRequestPara.getOmsPubDestroyList();
        if(omsPubDestroyList==null||omsPubDestroyList.size()==0){
            //未选中数据，根据查询条件查询数据导出。
            omsPubDestroyList = SelDestroyRegByQuaVoAll(exportRequestPara.getSelDestroyRegByQuaVo());
            if(omsPubDestroyList==null||omsPubDestroyList.size()==0)
                throw new CustomMessageException("无数据导出，请核实导出条件！");
        }
        List<List<String>> values=new LinkedList<>();
        int count=0;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        for(OmsPubDestroy omsPubDestroy: omsPubDestroyList){
            List<String> value=new LinkedList<>();
            value.add(String.valueOf(++count));
            value.add(simpleDateFormat.format(omsPubDestroy.getDestroyTime()));
            value.add(omsPubDestroy.getDestroyer());
            value.add(omsPubDestroy.getMobile());
            value.add(omsPubDestroy.getDestroyDesc());
            values.add(value);
        }
        HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook("已销毁登记记录", exportRequestPara.getTitleList(), values, null);
        hssfWorkbook.write(outputStream);
    }
}
