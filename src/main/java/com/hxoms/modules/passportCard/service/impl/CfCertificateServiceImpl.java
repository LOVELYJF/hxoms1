package com.hxoms.modules.passportCard.service.impl;

import com.hxoms.modules.passportCard.entity.vo.CfCertificateVo;
import com.hxoms.modules.passportCard.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.service.CfCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfCertificateServiceImpl implements CfCertificateService {



    @Autowired
    private CfCertificateMapper cfCertificateMapper;


    public List<CfCertificateVo> findAll(CfCertificateVo cfCertificateVo){

        int pageNum = cfCertificateVo.getPageNum();

        cfCertificateVo.setPageNum((pageNum)*10);

        return cfCertificateMapper.findAll(cfCertificateVo);

    }

    public Integer findAllCount(CfCertificateVo cfCertificateVo){
        return cfCertificateMapper.findAllCount(cfCertificateVo);
    }

   public boolean saveOrUpdate(CfCertificateVo cfCertificateVo){

       CfCertificateVo cf=cfCertificateMapper.findCfById(cfCertificateVo.getId());

       boolean flag = false;

       if (cf != null){

           int count = cfCertificateMapper.updateById(cfCertificateVo);

           if(count>0){
               flag = true;
           }
       }else{
           int count = cfCertificateMapper.insert(cfCertificateVo);

           if(count>0){
               flag = true;
           }
       }

        return flag;
    }

   public boolean delete(String id){

        return cfCertificateMapper.delete(id);
    }

}
