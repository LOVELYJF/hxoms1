package com.hxoms.modules.passportCard.certificateCollect.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.keySupervision.suspendApproval.mapper.OmsSupSuspendUnitMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.certificateCollect.mapper.CfCertificateCollectionMapper;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionRequestService;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CfCertificateCollectionServiceImpl extends ServiceImpl<CfCertificateCollectionMapper,CfCertificateCollection> implements CfCertificateCollectionService {

    @Autowired
    private CfCertificateCollectionMapper cfCertificateCollectionMapper;

    @Autowired
    private CfCertificateCollectionRequestService CfCertificateCollectionRequestService;

    @Autowired
    private OmsSupSuspendUnitMapper omsSupSuspendUnitMapper;
    /**
     * @Desc: 生成催缴任务
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: void
     * @Date: 2020/8/11
     */
    public void createCjTask(List<CfCertificateCollection> cfCertificateCollectionList){
        if(cfCertificateCollectionList==null||cfCertificateCollectionList.size()==0)
            throw new CustomMessageException("参数为空，请核实！");
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        List<CfCertificateCollection> cfCerList=new ArrayList<>();
        for (CfCertificateCollection cfCertificateCollection : cfCertificateCollectionList) {
            //0:登记备案,1:因私出国(境),2:证照借出,3:撤销出国申请
            if("0".equals(cfCertificateCollection.getCjWay())){
                Date date = new Date();
                cfCertificateCollection.setHappenDate(date);
                cfCertificateCollection.setReturnDate(calDate(date,10));
            }
            //0:解除,1;已上缴,2:未上缴
            cfCertificateCollection.setCjStatus("2");
            cfCertificateCollection.setCreatetime(new Date());
            cfCertificateCollection.setCreator(userInfo.getId());
            cfCerList.add(cfCertificateCollection);
            if(cfCerList.size()/1000==0){
                if(!saveBatch(cfCerList,cfCerList.size()))
                    new CustomMessageException("生成催缴任务失败！");
                cfCerList.clear();
            }
        }
        if(cfCerList.size()>0)
            if(!saveBatch(cfCerList,cfCerList.size()))
                new CustomMessageException("生成催缴任务失败！");
    }


    /**
     * @param pageBean
     * @Desc: 查询证照催缴
     * @Author: wangyunquan
     * @Param: [pageBean, cfCertificateCjQuery]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/12
     */
    @Override
    public PageBean selectCerCjApply(PageBean pageBean, CfCertificateCjQueryParam cfCertificateCjQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CfCertificateCjInfo> pageInfo=new PageInfo<CfCertificateCjInfo>(cfCertificateCollectionMapper.selectCerCjApply(cfCertificateCjQueryParam));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 查询催缴机构单位
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.OrganUnit>
     * @Date: 2020/8/12
     */
    @Override
    public List<OrganUnit> selectOrganUnit() {
        return cfCertificateCollectionMapper.selectOrganUnit();
    }

    /**
     * @Desc: 通过单位查询催缴人员
     * @Author: wangyunquan
     * @Param: [rfB0000]
     * @Return: com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjByPhone
     * @Date: 2020/8/12
     */
    @Override
    public CfCertificateCjByPhone selectCerCjInfoByOrgan(String rfB0000) {
        if(StringUtils.isBlank(rfB0000))
            throw new CustomMessageException("请求参数不正确，请核实！");
        List<CfUser> handlerList=cfCertificateCollectionMapper.selectHandlerByOrgan(rfB0000);
        if(handlerList==null||handlerList.size()==0)
            throw new CustomMessageException("该机构下无经办人，请核实！");
        List<CfCertificateCjInfo> cfCertificateCjInfoList=cfCertificateCollectionMapper.selectCerCjInfoByOrgan(rfB0000);
        CfCertificateCjByPhone cfCertificateCjByPhone=new CfCertificateCjByPhone();
        cfCertificateCjByPhone.setHandlerList(handlerList);
        cfCertificateCjByPhone.setCfCertificateCjInfoList(cfCertificateCjInfoList);
        return cfCertificateCjByPhone;
    }

    /**
     * @Desc: 保存催缴结果
     * @Author: wangyunquan
     * @Param: [cerCollectionRequestExList]
     * @Return: void
     * @Date: 2020/8/12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCerCjResult(List<CfCertificateCollectionRequestEx> cerCollectionRequestExList) {
        if(cerCollectionRequestExList==null||cerCollectionRequestExList.size()==0)
            throw new CustomMessageException("请求参数不正确，请核实！");
        List<CfCertificateCollectionRequest> cfCertificateCollectionRequestList=new ArrayList<>();
        for (CfCertificateCollectionRequestEx cfCertificateCollectionRequestEx : cerCollectionRequestExList) {
            cfCertificateCollectionRequestEx.setId(UUIDGenerator.getPrimaryKey());
            //0:电话催缴,1:短信催缴
            cfCertificateCollectionRequestEx.setCjWay("0");
            cfCertificateCollectionRequestList.add(cfCertificateCollectionRequestEx);

            CfCertificateCollection cfCertificateCollection=new CfCertificateCollection();
            cfCertificateCollection.setCjWay("0");
            String allCjResult = cfCertificateCollectionRequestEx.getAllCjResult();
            StringBuffer stringBuffer=new StringBuffer();
            if(!StringUtils.isBlank(allCjResult))
                stringBuffer.append("\r\n");
            cfCertificateCollection.setCjResult(stringBuffer.append(cfCertificateCollectionRequestEx.getCjTime()).append(cfCertificateCollectionRequestEx.getCjPerson()).append("电话催缴：").append(cfCertificateCollectionRequestEx.getCjResult()).toString());
            cfCertificateCollection.setUpdator(cfCertificateCollectionRequestEx.getCjPerson());
            cfCertificateCollection.setUpdatetime(cfCertificateCollectionRequestEx.getCjTime());
            int result = cfCertificateCollectionMapper.updateById(cfCertificateCollection);
            if(result==0)
                throw new CustomMessageException("保存失败！");
        }
        if(!CfCertificateCollectionRequestService.saveBatch(cfCertificateCollectionRequestList,cerCollectionRequestExList.size()))
            throw new CustomMessageException("保存失败！");
    }

    /**
     * @Desc: 解除催缴
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: void
     * @Date: 2020/8/12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCerCjForRemove(List<CfCertificateCollection> cfCertificateCollectionList) {
        if(cfCertificateCollectionList==null||cfCertificateCollectionList.size()==0)
            throw new CustomMessageException("请求参数不正确，请核实！");
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        for (CfCertificateCollection cfCertificateCollection : cfCertificateCollectionList) {
            //0:解除,1;已上缴,2:未上缴
            cfCertificateCollection.setCjStatus("0");
            cfCertificateCollection.setUpdator(userInfo.getId());
            cfCertificateCollection.setUpdatetime(new Date());
            int result = cfCertificateCollectionMapper.updateById(cfCertificateCollection);
            if(result==0)
                throw new CustomMessageException("保存失败！");
        }
    }

    /**
     * @Desc: 锁定单位出国
     * @Author: wangyunquan
     * @Param: [omsSupSuspendUnit]
     * @Return: void
     * @Date: 2020/8/13
     */
    @Override
    public void insertSuspendUnit(OmsSupSuspendUnit omsSupSuspendUnit) {
        if(omsSupSuspendUnit==null)
            throw new CustomMessageException("请求参数不正确，请核实！");
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        omsSupSuspendUnit.setId(UUIDGenerator.getPrimaryKey());
        omsSupSuspendUnit.setStatus("0");
        omsSupSuspendUnit.setCreateUser(userInfo.getId());
        omsSupSuspendUnit.setCreateTime(new Date());
        int result = omsSupSuspendUnitMapper.insert(omsSupSuspendUnit);
        if(result==0)
            throw new CustomMessageException("保存失败！");
    }

    @Override
    public List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection) {
        return cfCertificateCollectionMapper.selectByCfcertificateCollection(cfCertificateCollection);
    }

    /**
     * @Desc: 计算日期
     * @Author: wangyunquan
     * @Param: [date, value]
     * @Return: java.util.Date
     * @Date: 2020/8/11
     */
    public Date calDate(Date date,int value){
        Calendar calendar  =   Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, value);
        return calendar.getTime();
    }

}
