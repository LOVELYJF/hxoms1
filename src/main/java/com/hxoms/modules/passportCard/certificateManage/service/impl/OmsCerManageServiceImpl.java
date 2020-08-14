package com.hxoms.modules.passportCard.certificateManage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageInfo;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageQueryParam;
import com.hxoms.modules.passportCard.certificateManage.mapper.OmsCerManageMapper;
import com.hxoms.modules.passportCard.certificateManage.service.OmsCerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
@Service
public class OmsCerManageServiceImpl implements OmsCerManageService {

    @Autowired
    private OmsCerManageMapper omsCerManageMapper;
    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, cerManageQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/13
     */
    @Override
    public PageBean selectCerInfo(PageBean pageBean, CerManageQueryParam cerManageQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CerManageInfo> pageInfo=new PageInfo<CerManageInfo>(omsCerManageMapper.selectCerInfo(cerManageQueryParam));
        return PageUtil.packagePage(pageInfo);
    }
}
