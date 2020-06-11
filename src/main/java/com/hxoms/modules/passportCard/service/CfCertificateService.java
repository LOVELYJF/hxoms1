package com.hxoms.modules.passportCard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.CfCertificateReminder;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.passportCard.entity.param.CfCertificateReminderParam;
import com.hxoms.modules.passportCard.entity.vo.CfCertificateVo;

import java.util.List;

public interface CfCertificateService extends IService<CfCertificate> {


   //查询所有证照的信息带分页
   PageInfo<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam);

   //Integer findAllCount(CfCertificate cfCertificate);

   //保存或者修改证照信息
   boolean saveOrUpdate(CfCertificate cfCertificate);

   //根据ID删除单行数据
   boolean delete(String id);

   //查询所有需要上缴的证照信息
   PageInfo<CfCertificateReminder> findOverduePass(CfCertificateReminderParam cfCertificateReminderParam);

   //查询没有维护的证照信息
   Integer findSuccessCf();
}
