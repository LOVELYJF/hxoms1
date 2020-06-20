package com.hxoms.modules.passportCard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.CfCertificateReminder;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.passportCard.entity.param.CfCertificateReminderParam;
import com.hxoms.modules.passportCard.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.service.CfCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfCertificateServiceImpl extends ServiceImpl<CfCertificateMapper,CfCertificate> implements CfCertificateService {

    @Autowired
    private CfCertificateMapper cfCertificateMapper;



    @Override
    public PageInfo<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam) {


        /*if (cfCertificatePageParam.getPageNum() == null){
            cfCertificatePageParam.setPageNum(0);
        }
        if(cfCertificatePageParam.getPageSize() ==null){
            cfCertificatePageParam.setPageSize(cfCertificateList.size());
        }*/

        PageHelper.startPage(cfCertificatePageParam.getPageNum()==null?0 : cfCertificatePageParam.getPageNum(),
                            cfCertificatePageParam.getPageSize()==null?10 : cfCertificatePageParam.getPageSize());
        List<CfCertificate> cfCertificateList =cfCertificateMapper.selectCfCertificateIPage(cfCertificatePageParam);

        PageInfo<CfCertificate> pageInfo = new PageInfo(cfCertificateList);

        return pageInfo;
    }


   public boolean saveOrUpdate(CfCertificate cfCertificate){

       CfCertificate cf=cfCertificateMapper.selectById(cfCertificate.getId());

       boolean flag = false;

       if (cf != null){

           int count = cfCertificateMapper.updateById(cfCertificate);

           if(count>0){
               flag = true;
           }
       }else{
           int count = cfCertificateMapper.insert(cfCertificate);

           if(count>0){
               flag = true;
           }
       }

        return flag;
    }

   public boolean delete(String id){

        return cfCertificateMapper.delete(id);
    }

    @Override
    public PageInfo<CfCertificateReminder> findOverduePass(CfCertificateReminderParam cfCertificateReminderParam) {
        PageHelper.startPage(cfCertificateReminderParam.getPageNum()== null?0:cfCertificateReminderParam.getPageNum(),
                cfCertificateReminderParam.getPageSize()==null?10:cfCertificateReminderParam.getPageSize());
        List<CfCertificateReminder> cfCertificateReminderList= cfCertificateMapper.findOverduePass(cfCertificateReminderParam);
        PageInfo<CfCertificateReminder> pageInfo = new PageInfo(cfCertificateReminderList);
        return pageInfo;
    }

    public Integer findSuccessCf(){
        return cfCertificateMapper.findSuccessCf();
   }

}
