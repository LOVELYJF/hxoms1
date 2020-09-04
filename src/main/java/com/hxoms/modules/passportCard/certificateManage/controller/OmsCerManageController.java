package com.hxoms.modules.passportCard.certificateManage.controller;

import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageQueryParam;
import com.hxoms.modules.passportCard.certificateManage.service.OmsCerManageService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
@Api(tags = "证照信息管理")
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
    @ApiOperation(value = "查询证照信息分页")
    @GetMapping("/selectCerInfo")
    public Result selectCerInfo(PageBean pageBean, CerManageQueryParam cerManageQueryParam) {
        return Result.success(omsCerManageService.selectCerInfo(pageBean, cerManageQueryParam));
    }

    /**
     * @Desc: 查询登记备案人员
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @ApiOperation(value = "查询登记备案人员")
    @GetMapping("/selectRegPerson")
    public Result selectRegPerson(CfCertificate cfCertificate){
        return Result.success(omsCerManageService.selectRegPerson(cfCertificate));
    }

    /**
     * @Desc: 新增证照
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @ApiOperation(value = "新增证照")
    @PostMapping("/insertCertificate")
    public Result insertCertificate(@RequestBody CfCertificate cfCertificate){
        omsCerManageService.insertCertificate(cfCertificate);
        return Result.success();
    }
}
