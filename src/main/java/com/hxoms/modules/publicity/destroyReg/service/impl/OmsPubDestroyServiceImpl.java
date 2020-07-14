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

    /**
     * @Desc: 查询提交撤销备案申请
     * @Author: wangyunquan
     * @Param: [pageVO]
     * @Return: com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.PageVO
     * @Date: 2020/7/2
     */
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
    /**
     * @Desc: 获取登陆用户信息
     * @Author: wangyunquan
     * @Param: []
     * @Return: com.hxoms.support.user.entity.User
     * @Date: 2020/7/2
     */
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
    /**
     * @Desc: 备案表销毁登记
     * @Author: wangyunquan
     * @Param: [destroyRegVo]
     * @Return: void
     * @Date: 2020/7/2
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void insertBabDestroyReg(DestroyRegVo destroyRegVo) {
        OmsPubDestroy omsPubDestroy=destroyRegVo.getOmsPubDestroy();
        List<OmsPubDestroydetail> omsPubDestroydetailList=destroyRegVo.getOmsPubDestroydetailList();
        if(omsPubDestroy==null||omsPubDestroydetailList==null||omsPubDestroydetailList.size()==0)
            throw new CustomMessageException("销毁登记失败！原因：数据不正确。");
        omsPubDestroy.setId(UUIDGenerator.getPrimaryKey());
        omsPubDestroyMapper.insert(omsPubDestroy);
        for (OmsPubDestroydetail omsPubDestroydetail:omsPubDestroydetailList) {
            omsPubDestroydetail.setDestroyId(omsPubDestroy.getId());
        }
        int value=omsPubDestroydetailMapper.insertOmsPubDestroydetailList(omsPubDestroydetailList);
        if(value==0)
            throw new CustomMessageException("插入失败！");
    }
    /**
     * @Desc: 查询已销毁登记记录
     * @Author: wangyunquan
     * @Param: [pageBean, selDestroyRegByQuaVo]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/7/2
     */
    @Override
    public PageBean getDestroyRegByQuaVo(PageBean pageBean,SelDestroyRegByQuaVo selDestroyRegByQuaVo) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<OmsPubDestroy> pageInfo= new PageInfo<OmsPubDestroy>(SelDestroyRegByQuaVoAll(selDestroyRegByQuaVo));
        return PageUtil.packagePage(pageInfo);
    }
    /**
     * @Desc: 查询已销毁登记所有记录
     * @Author: wangyunquan
     * @Param: [pageBean, selDestroyRegByQuaVo]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/7/2
     */
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
    /**
     * @Desc: 销毁登记记录导出excel文件
     * @Author: wangyunquan
     * @Param: [exportRequestPara, response]
     * @Return: void
     * @Date: 2020/7/3
     */
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
