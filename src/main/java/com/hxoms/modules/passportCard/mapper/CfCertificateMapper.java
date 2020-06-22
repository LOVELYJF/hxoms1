package com.hxoms.modules.passportCard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.CfCertificateReminder;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.passportCard.entity.param.CfCertificateReminderParam;

import java.util.List;

public interface CfCertificateMapper extends BaseMapper<CfCertificate>{


    /**
     * 查询所有证照信息
     * @return
     */

    List<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam);

    //Integer findAllCount(CfCertificate cfCertificate);

    List<CfCertificateReminder> findOverduePass(CfCertificateReminderParam cfCertificateReminderParam);

    CfCertificate findCfById(String id);

    boolean delete(String id);

    /**
     * 查询已经验证的证照的数量
     */
    Integer findSuccessCf();

}