package com.hxoms.modules.passportCard.initialise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateReminder;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateReminderParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate;
import org.springframework.web.multipart.MultipartFile;


public interface CfCertificateService extends IService<CfCertificate> {


   //查询所有证照的信息带分页
   PageInfo<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam);

   //Integer findAllCount(CfCertificate cfCertificate);


   //根据ID删除单行数据
   boolean delete(String id);

   //查询所有需要上缴的证照信息
   PageInfo<CfCertificateReminder> findOverduePass(CfCertificateReminderParam cfCertificateReminderParam);

   //查询没有维护的证照信息
   Integer findSuccessCf();

   /**
    * @Desc: 初始化证照，导入公安的证照信息
    * @Author: wangyunquan
    * @Param: [multipartFile, dataSource]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/7/24
    */
   PageBean excelToDB(MultipartFile multipartFile, String dataSource) throws Exception;

   /**
    * @Desc: 查询所有证照
    * @Author: wangyunquan
    * @Param: [pageBean]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/8/4
    */
   PageBean selectAllCertificate(PageBean pageBean);

   /**
    * @Desc: 通过证照查询公安证照信息及人员信息
    * @Author: wangyunquan
    * @Param: [cfCertificate]
    * @Return: com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate
    * @Date: 2020/8/4
    */
   CfCertificateValidate selectPersonInfo(CfCertificate cfCertificate);

   /**
    * @Desc: 证照验证，有公安数据，则验证并更新状态，否则插入证照信息。
    * @Author: wangyunquan
    * @Param: [cfCertificateGa, cfCertificateZz]
    * @Return: void
    * @Date: 2020/8/5
    */
   void saveOrUpdate(CfCertificate cfCertificateGa,CfCertificate cfCertificateZz);
}
