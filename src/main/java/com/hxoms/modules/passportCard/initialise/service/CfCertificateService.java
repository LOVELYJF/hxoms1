package com.hxoms.modules.passportCard.initialise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.*;
import com.hxoms.support.sysdict.entity.SysDictItem;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface CfCertificateService extends IService<CfCertificate> {

   /**
    * @Desc: 初始化证照，导出存疑证照统计-导出证照查询
    * @Author: wuqingfan
    * @Param: [ids]
    * @Return: excel
    * @Date: 2020/9/10
    */
   void exportExceptionCerForOmsId(List<String> ids, HttpServletResponse response);

   /**
    * @Desc: 初始化证照，导出存疑证照统计-导出证照查询
    * @Author: wuqingfan
    * @Param: [ids]
    * @Return: excel
    * @Date: 2020/9/10
    */
   void exportExceptionCer(List<String> ids, HttpServletResponse response);

   /**
    * @Desc: 初始化证照，导出未上缴证照统计
    * @Author: wuqingfan
    * @Param: [ids]
    * @Return: List<ExportNotProvicdeCer>
    * @Date: 2020/9/10
    */
   void exportNotProvicdeCer(List<String> ids, HttpServletResponse response);

   //查询所有证照的信息带分页
   PageInfo<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam);

   /**
    * @Desc: 初始化证照，导入公安的证照信息
    * @Author: wangyunquan
    * @Param: [multipartFile]
    * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.ImportInterface>
    * @Date: 2020/7/24
    */
   PageBean<ImportInterface> excelToDB(MultipartFile multipartFile) throws Exception;

   /**
    * @Desc: 查询所有证照
    * @Author: wangyunquan
    * @Param: [pageBean]
    * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.ImportInterface>
    * @Date: 2020/8/4
    */
   PageBean<ImportInterface> selectAllCertificate(PageBean pageBean);

   /**
    * @Desc: 验证证照信息
    * @Author: wangyunquan
    * @Param: [validateCerInfoParam]
    * @Return: com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate
    * @Date: 2020/8/4
    */
   CfCertificateValidate validateCerInfo(ValidateCerInfo validateCerInfo);

   /**
    * @Desc: 保存证照信息
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
    * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>
    * @Date: 2020/8/7
    */
   PageBean<CfCertificateInfo> selectNotProvicdeCer(PageBean pageBean);

   /**
    * @Desc: 已上缴未入库统计
    * @Author: wangyunquan
    * @Param: [pageBean]
    * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>
    * @Date: 2020/8/7
    */
   PageBean<CfCertificateInfo> selectProNotstorCer(PageBean pageBean);

   /**
    * @Desc: 存疑证照统计
    * @Author: wangyunquan
    * @Param: [pageBean]
    * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>
    * @Date: 2020/8/7
    */
   PageBean<CfCertificateInfo> selectExceptionCer(PageBean pageBean);

   /**
    * @Desc: 公安已注销证照，更新状态
    * @Author: wangyunquan
    * @Param: [id]
    * @Return: void
    * @Date: 2020/8/10
    */
   void updateCerForCerIsCancel(String id);
   /**
    * @Desc: 存疑处理，以证照信息为准
    * @Author: wangyunquan
    * @Param: [qureyDealRequestInfo]
    * @Return: void
    * @Date: 2020/8/10
    */
    void updateCerForCerIsRight(QureyDealRequestInfo qureyDealRequestInfo);

    /**
     * @Desc: 存疑处理，以公安信息为准
     * @Author: wangyunquan
     * @Param: [qureyDealRequestInfo]
     * @Return: void
     * @Date: 2020/8/10
     */
   void updateCerForGaInfoIsRight(QureyDealRequestInfo qureyDealRequestInfo);


   /**
    * @Desc: 生成催缴任务
    * @Author: wangyunquan
    * @Param: [cfCertificateCollectionApplyList]
    * @Return: void
    * @Date: 2020/8/11
    */
   void createCjTask(CfCertificateCollectionApplyList cfCertificateCollectionApplyList);


   /**
    * <b>功能描述: 查询证照状态</b>
    * @Param: []
    * @Return: com.hxoms.common.utils.Result
    * @Author: luoshuai
    * @Date: 2020/8/27 14:41
    */
	List<SysDictItem> getCfCertificateStatus();


   /**
    * <b>功能描述: 查询证照类型</b>
    * @Param: []
    * @Return: com.hxoms.common.utils.Result
    * @Author: luoshuai
    * @Date: 2020/8/27 14:41
    */
   List<SysDictItem> getCfCertificateType();


   /**
    * <b>功能描述: 查询证照形式</b>
    * @Param: []
    * @Return: com.hxoms.common.utils.Result
    * @Author: luoshuai
    * @Date: 2020/8/27 14:41
    */
   List<SysDictItem> getCfCertificateForm();


   /**
    * <b>功能描述: 查询证照保管状态</b>
    * @Param: []
    * @Return: com.hxoms.common.utils.Result
    * @Author: luoshuai
    * @Date: 2020/8/27 14:41
    */
   List<SysDictItem> getCfCertificateSaveStatus();


   /**
    * <b>功能描述: 查询证照保管单位</b>
    * @Param: []
    * @Return: com.hxoms.common.utils.Result
    * @Author: luoshuai
    * @Date: 2020/8/27 14:41
    */
   List<SysDictItem> getCfCertificateSaveCompany();


   /**
    * <b>功能描述: 查询证照保管方式</b>
    * @Param: []
    * @Return: com.hxoms.common.utils.Result
    * @Author: luoshuai
    * @Date: 2020/8/27 14:41
    */
   List<SysDictItem> getCfCertificateSaveWay();


   PageBean queryCertificateByOmsId(PageBean pageBean, String b0100);
}
