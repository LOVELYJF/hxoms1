package com.hxoms.modules.passportCard.getTaskQuery.controller;


import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.getTaskQuery.service.OmsGetTaskQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;


/**
 * @Desc: TODO
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/8/14
 */
@Api(tags = "证照领取任务查询")
@RestController
@RequestMapping("/conuterGet")
@Validated
public class OmsGetTaskQueryController {

    @Autowired
    private OmsGetTaskQueryService omsGetTaskQueryService;

    /**
     * @Desc: 证照管理-证照领取任务-导出
     * @Author: wuqingfan
     * @Param: ids
     * @Return: excel
     * @Date: 2020/9/12
     */
    @ApiOperation(value = "证照管理-证照领取任务-导出")
    @ApiImplicitParam(value = "选中列表ID，利用','隔开拼接", name = "ids", required = true, paramType = "query")
    @PostMapping("/exportExceptionCer")
    public void exportExceptionCer(@ApiIgnore @NotBlank(message = "ids不能为空") String ids, @ApiIgnore HttpServletResponse response) {
        omsGetTaskQueryService.exportSelectGetCer(Arrays.asList(ids.split(",")),response);
    }


    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskInfo>>
     * @Date: 2020/8/18
     */
    @ApiOperation(value = "查询领取证照")
    @GetMapping("/selectGetCer")
    public Result<PageBean<CerGetTaskInfo>> selectGetCer(PageBean pageBean,CerGetTaskQueryParam cerGetTaskQueryParam){
        return Result.success(omsGetTaskQueryService.selectGetCer(pageBean,cerGetTaskQueryParam));
    }

    /**
     * @Desc: 获取发通知内容模板
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/9/5
     */
    @ApiOperation(value = "获取发通知内容模板")
    @PostMapping("/getSendNoticeContent")
    public Result<NoticeContent> getSendNoticeContent(@RequestBody @Validated RequestList<SendNoticeContentParam> requestList){
        return Result.success(omsGetTaskQueryService.getSendNoticeContent(requestList.getList()));
    }

    /**
     * @Desc: 发通知
     * @Author: wangyunquan
     * @Param: [sendNotice]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/9/5
     */
    @ApiOperation(value = "发通知")
    @PostMapping("/sendNotice")
    public Result sendNotice(@RequestBody  @Validated SendNotice sendNotice){
        omsGetTaskQueryService.sendNotice(sendNotice);
        return Result.success();
    }
}
