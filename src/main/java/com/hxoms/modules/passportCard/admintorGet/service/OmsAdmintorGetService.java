package com.hxoms.modules.passportCard.admintorGet.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.admintorGet.entity.OmsCerAdmintorGetApply;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetApplyList;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetQueryParam;

public interface OmsAdmintorGetService extends IService<OmsCerAdmintorGetApply> {


    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, admintorGetQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/18
     */
    PageBean selectCerInfo(PageBean pageBean, AdmintorGetQueryParam admintorGetQueryParam);

    /**
     * @Desc: 保存管理员取证申请
     * @Author: wangyunquan
     * @Param: [admintorGetApplyList]
     * @Return: void
     * @Date: 2020/8/18
     */
    void insertAdmintorGetApply(AdmintorGetApplyList admintorGetApplyList);
}
