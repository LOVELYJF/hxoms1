package com.hxoms.modules.passportCard.getTaskQuery.controller;


import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskQueryParam;
import com.hxoms.modules.passportCard.getTaskQuery.service.OmsGetTaskQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Desc: TODO
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/8/14
 */
@RestController
@RequestMapping("/conuterGet")
public class OmsGetTaskQueryController {

    @Autowired
    private OmsGetTaskQueryService omsGetTaskQueryService;

    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/18
     */
    @GetMapping("/selectGetCer")
    public Result selectGetCer(PageBean pageBean, CerGetTaskQueryParam cerGetTaskQueryParam){
        return Result.success(omsGetTaskQueryService.selectGetCer(pageBean,cerGetTaskQueryParam));
    }


}
