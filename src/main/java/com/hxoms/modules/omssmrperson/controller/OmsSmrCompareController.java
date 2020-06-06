package com.hxoms.modules.omssmrperson.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrCompare;
import com.hxoms.modules.omssmrperson.service.OmsSmrCompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    private HttpServletResponse response;

    /**
     * 获取涉密人员身份证信息列表
     * @param smrCompare
     */
    @GetMapping("/getSmrCompare")
    public Result getSmrCompare(Page page, OmsSmrCompare smrCompare) {
        try{
            IPage<OmsSmrCompare> compareList = smrCompareService.getSmrCompareList(page,smrCompare);
            return Result.success(compareList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 添加涉密人员身份证信息
     * @param smrCompare
     */
    @PostMapping("/insertSmrCompare")
    public Result insertSmrCompare(OmsSmrCompare smrCompare) {
        return Result.success(smrCompareService.insert(smrCompare));
    }

   /**
     * 修改涉密人员身份证信息
     * @param smrCompare
     */
    @PostMapping("/updateSmrCompare")
    public Result updateSmrCompare(OmsSmrCompare smrCompare) {
        return Result.success(smrCompareService.update(smrCompare));
    }

    /**
     * 删除涉密人员身份证信息
     * @param id
     */
    @PostMapping("/deleteSmrCompare")
    public Result deleteSmrCompare(String id) {
        return Result.success(smrCompareService.delete(id));
    }

    /**
     * 获取身份证纠正列表
     * @return
     */
    @GetMapping("/getCompareIdCard")
    public Result getCompareIdCard(){
        try{
            List<OmsSmrCompare> list= smrCompareService.getCompareIdCard();;
            return Result.success(list);
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
    public Result exportCompareIdCard(){
        try{
            boolean result = smrCompareService.exportCompareIdCard(response);;
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导出失败");
        }
    }

}









