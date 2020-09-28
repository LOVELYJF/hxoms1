package com.hxoms.modules.omssmrperson.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfo;
import com.hxoms.modules.omssmrperson.service.OmsSmrRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

/**
 * 省国家保密局备案涉密人员管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@RestController
@RequestMapping("/smrRecordInfo")
public class OmsSmrRecordInfoController {

    @Autowired
    private OmsSmrRecordInfoService smrRecordInfoService;
    /**
     * 获取省国家保密局备案涉密人员信息列表
     * @param smrRecordInfo
     */
    @GetMapping("/getSmrRecordInfo")
    public Result getSmrRecordInfo(Page page, OmsSmrRecordInfo smrRecordInfo) {
        try{
            IPage<OmsSmrRecordInfo> recordInfoList = smrRecordInfoService.getSmrRecordInfoList(page,smrRecordInfo);
            return Result.success(recordInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /**
     * 获取遗漏的省管干部列表
     * @return
     */
    @GetMapping("/getMatchingPerson")
    public Result getMatchingPerson(String importYear,String b0100){
        try{
            Result result = smrRecordInfoService.getMatchingPerson(importYear,b0100);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导出遗漏的省管干部列表
     * @return
     */
    @PostMapping("/exportMatchingPerson")
    public void exportMatchingPerson(String importYear, String b0100, @ApiIgnore HttpServletResponse response){
        try{
            smrRecordInfoService.exportMatchingPerson(importYear,b0100,response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}









