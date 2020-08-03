package com.hxoms.modules.passportCard.initialise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateReminder;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateReminderParam;
import org.apache.ibatis.annotations.Param;

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

    List<OmsRegProcpersoninfo> selectA0100ByQua(String idNo, String namePY);

    List<CfCertificate> selectZJExisByQua(String a0100);

    List<OmsEntryexitRecord> selectRecordExisByQua(String a0100);

    int batchSaveEntity(@Param("cfCertificateList") List<CfCertificate> cfCertificateList);
}