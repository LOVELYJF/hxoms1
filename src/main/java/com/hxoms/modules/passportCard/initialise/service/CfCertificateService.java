package com.hxoms.modules.passportCard.initialise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface CfCertificateService extends IService<CfCertificate> {


   //查询所有证照的信息带分页
   PageInfo<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam);

   /**
    * @Desc: 初始化证照，导入公安的证照信息
    * @Author: wangyunquan
    * @Param: [multipartFile]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/7/24
    */
   PageBean excelToDB(MultipartFile multipartFile) throws Exception;

   /**
    * @Desc: 查询所有证照
    * @Author: wangyunquan
    * @Param: [pageBean]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/8/4
    */
   PageBean selectAllCertificate(PageBean pageBean);

   /**
    * @Desc: 验证证照信息
    * @Author: wangyunquan
    * @Param: [cfCertificate]
    * @Return: com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate
    * @Date: 2020/8/4
    */
   CfCertificateValidate validateCerInfo(CfCertificate cfCertificate);

   /**
    * @Desc: 插入证照信息
    * @Author: wangyunquan
    * @Param: [cfCertificate]
    * @Return: void
    * @Date: 2020/8/5
    */
   void insertCertificate(CfCertificate cfCertificate);

   /**
    * @Desc: 未上缴证照统计
    * @Author: wangyunquan
    * @Param: [pageBean]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/8/7
    */
   PageBean selectNotProvicdeCer(PageBean pageBean);

   /**
    * @Desc: 已上缴未入库统计
    * @Author: wangyunquan
    * @Param: [pageBean]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/8/7
    */
   PageBean selectProNotstorCer(PageBean pageBean);

   /**
    * @Desc: 存疑证照统计
    * @Author: wangyunquan
    * @Param: [pageBean]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/8/7
    */
   PageBean selectExceptionCer(PageBean pageBean);

   /**
    * @Desc: 公安已注销证照，更新状态
    * @Author: wangyunquan
    * @Param: [cfCertificate]
    * @Return: void
    * @Date: 2020/8/10
    */
   void updateCerForCerIsCancel(CfCertificate cfCertificate);
   /**
    * @Desc: 存疑处理，以证照信息为准
    * @Author: wangyunquan
    * @Param: [cfCertificate]
    * @Return: void
    * @Date: 2020/8/10
    */
    void updateCerForCerIsRight(CfCertificate cfCertificate);

    /**
     * @Desc: 存疑处理，以公安信息为准
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: void
     * @Date: 2020/8/10
     */
   void updateCerForGaInfoIsRight(CfCertificate cfCertificate);


   /**
    * @Desc: 生成催缴任务
    * @Author: wangyunquan
    * @Param: [cfCertificateCollectionList]
    * @Return: void
    * @Date: 2020/8/11
    */
   void createCjTask(List<CfCertificateCollection> cfCertificateCollectionList);
}
