package com.hxoms.modules.passportCard.getTaskQuery.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskInfo;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskQueryParam;
import com.hxoms.modules.passportCard.getTaskQuery.mapper.OmsGetTaskQueryMapper;
import com.hxoms.modules.passportCard.getTaskQuery.service.OmsGetTaskQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OmsGetTaskQueryServiceImpl implements OmsGetTaskQueryService {

    @Autowired
    private OmsGetTaskQueryMapper omsGetTaskQueryMapper;
    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/18
     */
    @Override
    public PageBean selectGetCer(PageBean pageBean,CerGetTaskQueryParam cerGetTaskQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageNum());
        PageInfo<CerGetTaskInfo> pageInfo=new PageInfo<CerGetTaskInfo>(omsGetTaskQueryMapper.selectGetCer(cerGetTaskQueryParam));
        return PageUtil.packagePage(pageInfo);
    }


}
