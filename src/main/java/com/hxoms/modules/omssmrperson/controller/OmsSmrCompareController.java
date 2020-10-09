package com.hxoms.modules.omssmrperson.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.service.OmsSmrCompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

/**
 * 涉密人员身份证对照管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@RestController
@RequestMapping("/smrCompare")
public class OmsSmrCompareController {

    @Autowired
    private OmsSmrCompareService smrCompareService;

    /**
     * 获取身份证纠正列表
     * @return
     */
    @GetMapping("/getCompareIdCard")
    public Result getCompareIdCard(String b0100){
        try{
            Result result = smrCompareService.getCompareIdCard(b0100);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导出身份证纠正列表
     * @return
     */
    @PostMapping("/exportCompareIdCard")
    public void exportCompareIdCard(@RequestBody String jsonParam, @ApiIgnore HttpServletResponse response){
        try{
            smrCompareService.exportCompareIdCard(jsonParam, response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}









