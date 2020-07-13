package com.hxoms.modules.publicity.docCallback.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.ExportRequestPara;
import com.hxoms.modules.publicity.docCallback.entity.OmsPubDoccallback;
import com.hxoms.modules.publicity.docCallback.entity.OmsPubDoccallbackdetail;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.CallbackRegVo;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.SelCallbackRegByQuaVo;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.SelCanCallbApplyVO;
import com.hxoms.modules.publicity.docCallback.mapper.OmsPubDoccallbackMapper;
import com.hxoms.modules.publicity.docCallback.mapper.OmsPubDoccallbackdetailMapper;
import com.hxoms.modules.publicity.docCallback.service.OmsPubDocCallbackService;
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
public class OmsPubDocCallbackServiceImpl implements OmsPubDocCallbackService {
    @Autowired
    private OmsPubDoccallbackMapper omsPubDoccallbackMapper;

    @Autowired
    private OmsPubDoccallbackdetailMapper omsPubDoccallbackdetailMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean selectCanCallbApply(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<SelCanCallbApplyVO> pageInfo= new PageInfo<SelCanCallbApplyVO>(selectSubmitDesApplyAll());
        return PageUtil.packagePage(pageInfo);
    }

    public List<SelCanCallbApplyVO> selectSubmitDesApplyAll() {
        //获取用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<SelCanCallbApplyVO> selDestroyApplyVOList =null;
        if(userInfo==null)
            return selDestroyApplyVOList;
        //获取撤销申请数据
        selDestroyApplyVOList =omsPubDoccallbackMapper.selectCanCallbApplyAll(userInfo.getId());
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
    public void docCallbackReg(CallbackRegVo callbackRegVo) {
        OmsPubDoccallback omsPubDocCallback=callbackRegVo.getOmsPubDoccallback();
        List<OmsPubDoccallbackdetail> omsPubDoccallbackdetailList =callbackRegVo.getOmsPubDoccallbackdetailList();
        if(omsPubDocCallback==null|| omsPubDoccallbackdetailList ==null)
            throw new CustomMessageException("批件回收登记失败！原因：无操作数据。");
        omsPubDocCallback.setId(UUIDGenerator.getPrimaryKey());
        omsPubDoccallbackMapper.insert(omsPubDocCallback);
        for (OmsPubDoccallbackdetail omsPubDocCallbackdetail: omsPubDoccallbackdetailList) {
            omsPubDocCallbackdetail.setDoccallbackId(omsPubDocCallback.getId());
        }
        int value=omsPubDoccallbackdetailMapper.insertOmsPubDoccallbackdetailList(omsPubDoccallbackdetailList);
        if(value==0)
            throw new CustomMessageException("插入失败！");
    }

    @Override
    public PageBean SelCallbackRegByQuaVo(PageBean pageBean, SelCallbackRegByQuaVo selCallbackRegByQuaVo) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<OmsPubDoccallback> pageInfo= new PageInfo<OmsPubDoccallback>(SelCallbackRegByQuaVoAll(selCallbackRegByQuaVo));
        return PageUtil.packagePage(pageInfo);
    }

    public List<OmsPubDoccallback> SelCallbackRegByQuaVoAll(SelCallbackRegByQuaVo selCallbackRegByQuaVo) {
        List<OmsPubDoccallback> omsPubDoccallbackList =null;
        //获取用户信息
        UserInfo userInfo=UserInfoUtil.getUserInfo();
        if(userInfo==null)
            return omsPubDoccallbackList;
        selCallbackRegByQuaVo.setId(userInfo.getId());
        omsPubDoccallbackList =omsPubDoccallbackMapper.SelCallbackRegByQuaVoAll(selCallbackRegByQuaVo);
        return omsPubDoccallbackList;
    }
    @Override
    public void exportExcel(ExportRequestPara exportRequestPara, ServletOutputStream outputStream) throws IOException {
        List<OmsPubDoccallback> omsPubDoccallbackList = exportRequestPara.getOmsPubDoccallbackList();
        if(omsPubDoccallbackList ==null|| omsPubDoccallbackList.size()==0){
            //未选中数据，根据查询条件查询数据导出。
            omsPubDoccallbackList = SelCallbackRegByQuaVoAll(exportRequestPara.getSelCallbackRegByQuaVo());
            if(omsPubDoccallbackList ==null|| omsPubDoccallbackList.size()==0)
                throw new CustomMessageException("无数据导出，请核实导出条件！");
        }
        List<List<String>> values=new LinkedList<>();
        int count=0;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        for(OmsPubDoccallback omsPubDocCallback: omsPubDoccallbackList){
            List<String> value=new LinkedList<>();
            value.add(String.valueOf(++count));
            value.add(simpleDateFormat.format(omsPubDocCallback.getReceiveTime()));
            value.add(omsPubDocCallback.getReceiver());
            value.add(omsPubDocCallback.getMobile());
            value.add(omsPubDocCallback.getReceiveDesc());
            values.add(value);
        }
        HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook("批件回收登记记录", exportRequestPara.getTitleList(), values, null);
        hssfWorkbook.write(outputStream);
    }
}
