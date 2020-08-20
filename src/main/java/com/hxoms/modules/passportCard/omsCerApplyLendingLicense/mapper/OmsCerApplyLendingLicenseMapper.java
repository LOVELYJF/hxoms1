package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;

import java.util.List;
import java.util.Map;

public interface OmsCerApplyLendingLicenseMapper extends BaseMapper<OmsCerApplyLendingLicense> {

    /**
     * <b>功能描述: 查询证照借出申请信息</b>
     * @Param: [omsCerApplyLendingLicense]
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/11 8:41
     */
    List<Map<String, Object>> selectApplyLendingLicenseInfo(OmsCerApplyLendingLicense omsCerApplyLendingLicense);

    /**
     * <b>功能描述: 查询借出证照申请记录</b>
     * @Param: [page]
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/11 16:29
     */
    List<Map<String, Object>> getApplyLendingLicenseApprovalRecord();


}