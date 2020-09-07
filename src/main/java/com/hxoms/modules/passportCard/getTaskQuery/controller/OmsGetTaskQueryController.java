package com.hxoms.modules.passportCard.getTaskQuery.controller;


import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.getTaskQuery.service.OmsGetTaskQueryService;
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
@Api(tags = "证照领取任务查询")
@RestController
@RequestMapping("/conuterGet")
public class OmsGetTaskQueryController {

    @Autowired
    private OmsGetTaskQueryService omsGetTaskQueryService;

    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskInfo>>
     * @Date: 2020/8/18
     */
    @ApiOperation(value = "查询领取证照")
    @GetMapping("/selectGetCer")
    public Result<PageBean<CerGetTaskInfo>> selectGetCer(PageBean pageBean, CerGetTaskQueryParam cerGetTaskQueryParam){
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
    public Result<NoticeContent> getSendNoticeContent(@RequestBody RequestList<SendNoticeContentParam> requestList){
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
    public Result sendNotice(@RequestBody SendNotice sendNotice){
        omsGetTaskQueryService.sendNotice(sendNotice);
        return Result.success();
    }
}
