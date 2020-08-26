package com.hxoms.modules.passportCard.admintorGet.controller;


import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetApplyList;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetCerInfo;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetQueryParam;
import com.hxoms.modules.passportCard.admintorGet.service.OmsAdmintorGetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Desc: TODO
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/8/14
 */
@Api(tags = "管理员取证")
@RestController
@RequestMapping("/admintorGet")
public class OmsAdmintorGetController {

    @Autowired
    private OmsAdmintorGetService omsAdmintorGetService;

    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, admintorGetQueryParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/18
     */
    @ApiOperation(value = "查询证照信息")
    @GetMapping("/selectCerInfo")
    public Result<PageBean<AdmintorGetCerInfo>> selectCerInfo(PageBean pageBean, AdmintorGetQueryParam admintorGetQueryParam){
        return Result.success(omsAdmintorGetService.selectCerInfo(pageBean,admintorGetQueryParam));
    }

    /**
     * @Desc: 保存管理员取证申请
     * @Author: wangyunquan
     * @Param: [admintorGetApplyList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/18
     */
    @ApiOperation(value = "保存管理员取证申请")
    @PostMapping("/insertAdmintorGetApply")
    public Result insertAdmintorGetApply(@RequestBody AdmintorGetApplyList admintorGetApplyList){
        omsAdmintorGetService.insertAdmintorGetApply(admintorGetApplyList);
        return Result.success();
    }
}
