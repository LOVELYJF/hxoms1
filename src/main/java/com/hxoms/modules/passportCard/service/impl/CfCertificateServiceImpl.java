package com.hxoms.modules.passportCard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.passportCard.entity.vo.CfCertificateVo;
import com.hxoms.modules.passportCard.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.service.CfCertificateService;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfCertificateServiceImpl extends ServiceImpl<CfCertificateMapper,CfCertificate> implements CfCertificateService {

    @Autowired
    private CfCertificateMapper cfCertificateMapper;



    @Override
    public PageInfo<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam) {
        //PageUtil.pageHelp(cfCertificatePageParam.getPageNum(),cfCertificatePageParam.getPageSize());
        PageHelper.startPage(cfCertificatePageParam.getPageNum(), cfCertificatePageParam.getPageSize());
        List<CfCertificate> cfCertificateList =cfCertificateMapper.selectCfCertificateIPage(cfCertificatePageParam);

        PageInfo<CfCertificate> pageInfo = new PageInfo(cfCertificateList);
        return pageInfo;
    }

    /*public Integer findAllCount(CfCertificate cfCertificate){
        return cfCertificateMapper.findAllCount(cfCertificate);
    }*/

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
    public PageInfo<CfCertificate> findOverduePass(CfCertificatePageParam cfCertificatePageParam) {
        return null;
    }

    public Integer findSuccessCf(){
        return cfCertificateMapper.findSuccessCf();
   }

}
