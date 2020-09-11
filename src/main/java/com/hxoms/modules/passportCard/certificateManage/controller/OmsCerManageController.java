package com.hxoms.modules.passportCard.certificateManage.controller;

import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.certificateManage.service.OmsCerManageService;
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
    @ApiOperation(value = "查询证照信息")
    @GetMapping("/selectCerInfo")
    public Result<PageBean<CerManageInfo>> selectCerInfo(PageBean pageBean, CerManageQueryParam cerManageQueryParam) {
        return Result.success(omsCerManageService.selectCerInfo(pageBean, cerManageQueryParam));
    }



    /**
     * @Desc: 新领证照录入
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @ApiOperation(value = "新领证照录入")
    @PostMapping("/insertCertificate")
    public Result<CerAndPerson> insertCertificate(@RequestBody ReadCerInfo readCerInfo){
        return Result.success(omsCerManageService.insertCertificate(readCerInfo));
    }

    /**
     * @Desc: 保存证照
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @ApiOperation(value = "保存证照")
    @PostMapping("/saveCertificate")
    public Result saveCertificate(@RequestBody CerInfoSave cerInfoSave){
        omsCerManageService.saveCertificate(cerInfoSave);
        return Result.success();
    }
}
