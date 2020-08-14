package com.hxoms.modules.passportCard.certificateManage.controller;

import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageQueryParam;
import com.hxoms.modules.passportCard.certificateManage.service.OmsCerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
@RestController
@RequestMapping("/cerManager")
public class OmsCerManageController {

    @Autowired
    private OmsCerManageService omsCerManageService;

    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [cerManageQueryParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/13
     */
    @GetMapping("/selectCerInfo")
    public Result selectCerInfo(PageBean pageBean, CerManageQueryParam cerManageQueryParam) {
        return Result.success(omsCerManageService.selectCerInfo(pageBean, cerManageQueryParam));
    }
}
