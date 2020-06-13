package com.hxoms.modules.passportCard.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.entity.CfCheckValid;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.passportCard.entity.param.CfCheckValidParam;

public interface CfCheckValidService {

    /**
     * 查看所有证件重复的证件集合
     * @param cfCheckValidParam
     * @return
     */
    PageInfo<CfCheckValid> selectCfCheckValid(CfCheckValidParam cfCheckValidParam);


    /**
     * 查看有重复证件的信息
     * @param cfCheckValidParam
     * @return
     */
    PageInfo<CfCheckValid> selectCfCheckValidByName(CfCheckValidParam cfCheckValidParam);

}
