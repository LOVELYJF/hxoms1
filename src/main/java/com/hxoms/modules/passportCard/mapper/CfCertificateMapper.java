package com.hxoms.modules.passportCard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.vo.CfCertificateVo;

import java.util.List;
import java.util.Map;

public interface CfCertificateMapper extends BaseMapper<CfCertificate>{

    List<CfCertificateVo> findAll(CfCertificateVo cfCertificateVo);

    Integer findAllCount(CfCertificateVo cfCertificateVo);

    CfCertificateVo findCfById(String id);

    boolean delete(String id);

}