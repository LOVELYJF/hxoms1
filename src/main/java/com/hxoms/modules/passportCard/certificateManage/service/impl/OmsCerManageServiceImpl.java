package com.hxoms.modules.passportCard.certificateManage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageInfo;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageQueryParam;
import com.hxoms.modules.passportCard.certificateManage.mapper.OmsCerManageMapper;
import com.hxoms.modules.passportCard.certificateManage.service.OmsCerManageService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
@Service
public class OmsCerManageServiceImpl implements OmsCerManageService {

    @Autowired
    private OmsCerManageMapper omsCerManageMapper;

    @Autowired
    private CfCertificateMapper cfCertificateMapper;

    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, cerManageQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/13
     */
    @Override
    public PageBean<PageInfo<CerManageInfo>> selectCerInfo(PageBean pageBean, CerManageQueryParam cerManageQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CerManageInfo> pageInfo=new PageInfo<CerManageInfo>(omsCerManageMapper.selectCerInfo(cerManageQueryParam));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 查询登记备案人员
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: java.util.List<com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo>
     * @Date: 2020/8/17
     */
    @Override
    public List<OmsRegProcpersoninfo> selectRegPerson(CfCertificate cfCertificate) {
        //return cfCertificateMapper.selectRegPerson(null,cfCertificate.getName(),cfCertificate.getCsrq());
        return null;
    }

    /**
     * @Desc: 新增证照
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: void
     * @Date: 2020/8/17
     */
    @Override
    public void insertCertificate(CfCertificate cfCertificate) {
        if(cfCertificate==null)
            throw new CustomMessageException("参数不能为空，请核实！");
        if(StringUtils.isBlank(cfCertificate.getOmsId()))
            throw new CustomMessageException("未关联登记备案人员，请核实！");
        cfCertificate.setId(UUIDGenerator.getPrimaryKey());
        cfCertificate.setPy(PingYinUtil.getFirstSpell(cfCertificate.getName()));
        //已取出
        cfCertificate.setSaveStatus("1");
        //待验证
        cfCertificate.setCardStatus("5");
        int resule=cfCertificateMapper.insert(cfCertificate);
        if(resule==0)
            throw new CustomMessageException("保存失败！");
    }
}
