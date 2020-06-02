package com.hxoms.modules.passportCard.service;

import com.hxoms.modules.passportCard.entity.vo.CfCertificateVo;

import java.util.List;

public interface CfCertificateService {

   List<CfCertificateVo> findAll(CfCertificateVo cfCertificateVo);

   Integer findAllCount(CfCertificateVo cfCertificateVo);

   boolean saveOrUpdate(CfCertificateVo cfCertificateVo);

   boolean delete(String id);
}
