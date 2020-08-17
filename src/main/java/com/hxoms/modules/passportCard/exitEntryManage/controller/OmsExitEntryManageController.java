package com.hxoms.modules.passportCard.exitEntryManage.controller;

import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.service.OmsExitEntryManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/17
 */
public class OmsExitEntryManageController {

    @Autowired
    private OmsExitEntryManageService omsExitEntryManageService;

    /**
     * @Desc: 查询证照出入库记录
     * @Author: wangyunquan
     * @Param: [pageBean, omsCerExitEntryRepertory]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @GetMapping("/selectExitEntryRecord")
    public Result selectExitEntryRecord(PageBean pageBean,OmsCerExitEntryRepertory omsCerExitEntryRepertory){
        return Result.success(omsExitEntryManageService.selectExitEntryRecord(pageBean,omsCerExitEntryRepertory));
    }
}
