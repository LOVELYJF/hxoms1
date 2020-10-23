package com.hxoms.modules.passportCard.certificateCollect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.*;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

public interface CfCertificateCollectionService extends IService<CfCertificateCollection> {

    /**
     * @Desc: 生成催缴任务
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: void
     * @Date: 2020/8/11
     */
    void createCjTask(List<CfCertificateCollection> cfCertificateCollectionList);

    //条件查询
    List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection);

    /**
     * @Desc: 查询证照催缴
     * @Author: wangyunquan
     * @Param: [pageBean, cfCertificateCjQuery]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjInfo>
     * @Date: 2020/8/12
     */
    PageBean<CfCertificateCjInfo> selectCerCjApply(PageBean pageBean, CfCertificateCjQueryParam cfCertificateCjQueryParam);

    /**
     * @Desc: 查询催缴机构单位
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.OrganUnit>
     * @Date: 2020/8/12
     */
    List<OrganUnit> selectOrganUnit();

   /**
    * @Desc: 通过单位查询催缴人员
    * @Author: wangyunquan
    * @Param: [rfB0000]
    * @Return: com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjByPhone
    * @Date: 2020/8/12
    */
    CfCertificateCjByPhone selectCerCjInfoByOrgan(String rfB0000);

    /**
     * @Desc: 保存催缴结果
     * @Author: wangyunquan
     * @Param: [cerCollectionRequestExList]
     * @Return: void
     * @Date: 2020/8/12
     */
    void insertCerCjResult(List<CfCertificateCollectionRequestEx> cerCollectionRequestExList);


    /**
     * @Desc: 保存催缴结果（短信催缴）
     * @Author: wangyunquan
     * @Param: [saveCjResultList]
     * @Return: void
     * @Date: 2020/9/4
     */
    void updateCerCjResult(List<SaveCjResult> saveCjResultList);

    /**
     * @Desc: 解除催缴
     * @Author: wangyunquan
     * @Param: [removeCjApplyList]
     * @Return: void
     * @Date: 2020/8/12
     */
    void updateCerCjForRemove(List<RemoveCjApply> removeCjApplyList);

    /**
     * @Desc: 获取导出短信催缴名单
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.ExportSMSCjInfo>
     * @Date: 2020/9/3
     */
    List<ExportSMSCjInfo> getExportSMSCjList(List<SMSCjInfo> smsCjInfoList);

    /**
     * @Desc: 导出短信催缴名单
     * @Author: wangyunquan
     * @Param: [exportRequestPara, outputStream]
     * @Return: void
     * @Date: 2020/9/3
     */
    void exportSMSCjList(ExportRequestPara exportRequestPara, ServletOutputStream outputStream) throws IOException;
    /**
     * @Desc: 锁定单位出国
     * @Author: wangyunquan
     * @Param: [supSuspendUnitApply]
     * @Return: void
     * @Date: 2020/8/13
     */
    void insertSuspendUnit(SupSuspendUnitApply supSuspendUnitApply);

    /**
     * @Desc: 获取电话催缴内容
     * @Author: wangyunquan
     * @Param: [cjContentParamList]
     * @Return: com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.PhoneContent
     * @Date: 2020/9/1
     */
    PhoneContent createPhoneContent(List<CjContentParam> cjContentParamList);

    /**
     * @Desc: 发送催缴通知
     * @Author: wangyunquan
     * @Param: [sendNotices]
     * @Return: void
     * @Date: 2020/9/3
     */
    void sendCjNotice(List<SendNotice> sendNotices);

    /**
     * @Desc: 生成打印文件
     * @Author: wangyunquan
     * @Param: [fileQueryList]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.PrintFile>
     * @Date: 2020/9/13
     */
    List<PrintFile> createFileListByCode(List<FileQuery> fileQueryList);

    /**
     * @Desc: 打印文件详情
     * @Author: wangyunquan
     * @Param: [broadFileDestailParams]
     * @Return: com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.PrintFileDetail
     * @Date: 2020/9/13
     */
    PrintFileDetail selectFileDestail(FileDestailParams fileDestailParams);

    /**
     * @Desc: 保存富文本文件（模板）
     * @Author: wangyunquan
     * @Param: [omsFile]
     * @Return: void
     * @Date: 2020/9/14
     */
    void saveTextOmsFile(OmsFile omsFile);

    /**
     * @Desc: 保存或者更新打印文件
     * @Author: wangyunquan
     * @Param: [omsCreateFile]
     * @Return: void
     * @Date: 2020/9/14
     */
    void insertOrUpdate(OmsCreateFile omsCreateFile);

    /**
     * @Desc: 重新生成文件
     * @Author: wangyunquan
     * @Param: [printFile]
     * @Return: com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.OmsFile
     * @Date: 2020/9/14
     */
    OmsFile selectFileDestailNew(PrintFile printFile);

    /**
     * @Desc: 解除催缴
     * @Author: wangyunquan
     * @Param: [omsId, zjlx, zjhm, userId]
     * @Return: void
     * @Date: 2020/10/22
     */
    void removeCj (String omsId,Integer zjlx,String zjhm,String userId);
}
