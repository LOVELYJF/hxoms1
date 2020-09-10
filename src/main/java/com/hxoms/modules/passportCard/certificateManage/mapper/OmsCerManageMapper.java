package com.hxoms.modules.passportCard.certificateManage.mapper;

import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageInfo;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageQueryParam;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.RegProcpersoninfo;

import java.util.Date;
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

    /**
     * @Desc: 查询证照是否存在
     * @Author: wangyunquan
     * @Param: [zjlx, zjhm]
     * @Return: java.lang.String
     * @Date: 2020/9/9
     */
    String selectIsExist(Integer zjlx, String zjhm);
    /**
     * @Desc: 查询备案人员
     * @Author: wangyunquan
     * @Param: [name, csrq]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.RegProcpersoninfo>
     * @Date: 2020/9/9
     */
    List<RegProcpersoninfo> selectRegPerson(String name, Date csrq);


}
