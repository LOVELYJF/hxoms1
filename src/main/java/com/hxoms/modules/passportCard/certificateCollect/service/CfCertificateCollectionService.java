package com.hxoms.modules.passportCard.certificateCollect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.*;

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
     * @Desc: 解除催缴
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: void
     * @Date: 2020/8/12
     */
    void updateCerCjForRemove(List<CfCertificateCollection> cfCertificateCollectionList);

    /**
     * @Desc: 锁定单位出国
     * @Author: wangyunquan
     * @Param: [omsSupSuspendUnit]
     * @Return: void
     * @Date: 2020/8/13
     */
    void insertSuspendUnit(OmsSupSuspendUnit omsSupSuspendUnit);
}
