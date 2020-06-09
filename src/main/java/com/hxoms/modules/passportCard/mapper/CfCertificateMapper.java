package com.hxoms.modules.passportCard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.passportCard.entity.vo.CfCertificateVo;

import java.util.List;
import java.util.Map;

public interface CfCertificateMapper extends BaseMapper<CfCertificate>{


    /**
     * 查询所有证照信息
     * @return
     */

    List<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam);

    //Integer findAllCount(CfCertificate cfCertificate);

    List<CfCertificate> findOverduePass(CfCertificatePageParam cfCertificatePageParam);

    CfCertificate findCfById(String id);

    boolean delete(String id);

    /**
     * 查询已经验证的证照的数量
     */
    Integer findSuccessCf();

}