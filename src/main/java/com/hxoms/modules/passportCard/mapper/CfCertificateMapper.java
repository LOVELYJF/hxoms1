package com.hxoms.modules.passportCard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.entity.CfCertificate;

import java.util.List;
import java.util.Map;

public interface CfCertificateMapper extends BaseMapper<CfCertificate> {

    List<CfCertificate> findAll(Map<String,Object> map);

    int deleteByPrimaryKey(String id);

    int insert(CfCertificate record);

    int insertSelective(CfCertificate record);

    CfCertificate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CfCertificate record);

    int updateByPrimaryKey(CfCertificate record);
}