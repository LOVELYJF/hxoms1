package com.hxoms.modules.passportCard.certificateCollect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjInfo;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjQueryParam;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.OrganUnit;
import com.hxoms.modules.sysUser.entity.CfUser;

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
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Date: 2020/8/12
     */
    List<CfUser> selectHandlerByOrgan(String rfB0000);

    /**
     * @Desc: 查询催缴人员
     * @Author: wangyunquan
     * @Param: [rfB0000]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjInfo>
     * @Date: 2020/8/12
     */
    List<CfCertificateCjInfo> selectCerCjInfoByOrgan(String rfB0000);
}