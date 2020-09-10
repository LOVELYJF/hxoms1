package com.hxoms.modules.passportCard.certificateCollect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.*;

import java.util.List;

public interface CfCertificateCollectionMapper extends BaseMapper<CfCertificateCollection> {
    //条件查询
    List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection);

    /**
     * @Desc: 查询证照催缴
     * @Author: wangyunquan
     * @Param: [cfCertificateCjQuery]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection>
     * @Date: 2020/8/12
     */
    List<CfCertificateCjInfo> selectCerCjApply(CfCertificateCjQueryParam cfCertificateCjQueryParam);

    /**
     * @Desc: 查询催缴机构单位
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.OrganUnit>
     * @Date: 2020/8/12
     */
    List<OrganUnit> selectOrganUnit();

    /**
     * @Desc: 查询经办人
     * @Author: wangyunquan
     * @Param: [rfB0000]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.HandlerInfo>
     * @Date: 2020/8/12
     */
    List<HandlerInfo> selectHandlerByOrgan(String rfB0000);

    /**
     * @Desc: 查询催缴人员
     * @Author: wangyunquan
     * @Param: [rfB0000]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificatePhoneCjInfo>
     * @Date: 2020/8/12
     */
    List<CfCertificatePhoneCjInfo> selectCerCjInfoByOrgan(String rfB0000);


    /**
     * @Desc: 查询证件催缴信息
     * @Author: wangyunquan
     * @Param: [rfB0000]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest>
     * @Date: 2020/9/4
     */
    List<CfCertificateCollectionRequest> selectCjInfoByOrgan(String rfB0000);

}