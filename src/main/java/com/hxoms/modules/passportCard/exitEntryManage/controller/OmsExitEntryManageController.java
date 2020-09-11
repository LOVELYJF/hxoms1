package com.hxoms.modules.passportCard.exitEntryManage.controller;

import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.CerExitEntryInfo;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.CerInfo;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo;
import com.hxoms.modules.passportCard.exitEntryManage.service.OmsExitEntryManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/17
 */
@Api(tags = "出入库管理")
@RestController
@RequestMapping("/exitEntryManage")
public class OmsExitEntryManageController {

    @Autowired
    private OmsExitEntryManageService omsExitEntryManageService;

    /**
     * @Desc: 查询证照出入库记录
     * @Author: wangyunquan
     * @Param: [pageBean, omsCerExitEntryRepertory]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.CerExitEntryInfo>>
     * @Date: 2020/8/17
     */

    @ApiOperation(value = "查询证照出入库记录")
    @GetMapping("/selectExitEntryRecord")
    public Result<PageBean<CerExitEntryInfo>> selectExitEntryRecord(PageBean pageBean, @Validated CerInfo cerInfo){
        return Result.success(omsExitEntryManageService.selectExitEntryRecord(pageBean,cerInfo));
    }

    /**
     * @Desc: 查看签名
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @ApiOperation(value = "查看领取签名")
    @ApiImplicitParam(value = "领取表id",name = "getId",required = true,paramType = "query")
    @GetMapping("/selectSignByGetId")
    public Result<List<ExitEntrySignInfo>> selectSignByGetId(@NotBlank(message = "领取表id不能为空") String getId){
        return Result.success(omsExitEntryManageService.selectSignByGetId(getId));
    }
}
