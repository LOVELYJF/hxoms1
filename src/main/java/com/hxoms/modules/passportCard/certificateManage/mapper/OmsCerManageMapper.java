package com.hxoms.modules.passportCard.certificateManage.mapper;

import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageInfo;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageQueryParam;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
public interface OmsCerManageMapper {

    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [cerManageQueryParam]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageInfo>
     * @Date: 2020/8/13
     */
    List<CerManageInfo> selectCerInfo(CerManageQueryParam cerManageQueryParam);
}
