package com.hxoms.modules.passportCard.certificateCollect.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.mapper.CfCertificateCollectionMapper;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CfCertificateCollectionServiceImpl extends ServiceImpl<CfCertificateCollectionMapper,CfCertificateCollection> implements CfCertificateCollectionService {


    @Autowired
    private CfCertificateCollectionMapper cfCertificateCollectionMapper;

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
