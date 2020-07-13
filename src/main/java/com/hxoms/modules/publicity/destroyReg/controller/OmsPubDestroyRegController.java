package com.hxoms.modules.publicity.destroyReg.controller;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.DestroyRegVo;
import com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.ExportRequestPara;
import com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.SelDestroyRegByQuaVo;
import com.hxoms.modules.publicity.destroyReg.service.OmsPubDestroyService;
import com.hxoms.support.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Desc：因公出国（境）备案销毁登记
 * @Author: wangyunquan
 * @Date: 2020/7/1
 */
@RestController
@RequestMapping("/destroyReg")
public class OmsPubDestroyRegController {
    @Autowired
    private OmsPubDestroyService omsPubDestroyService;

    /**
     * @Desc: 查询提交撤销备案申请
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/2
     */
    @GetMapping("/selectSubmitDesApply")
    public Result selectSubmitDesApply(PageBean pageBean){
        pageBean = omsPubDestroyService.selectSubmitDesApply(pageBean);
        return Result.success(pageBean);
    }

    /**
     * @Desc: 获取登陆用户信息
     * @Author: wangyunquan
     * @Param:
     * @Return:
     * @Date: 2020/7/2
     */
    @GetMapping("/selectUserInfo")
    public Result selectUserInfo(){
        User user=omsPubDestroyService.selectUserInfo();
        return Result.success(user);
    }
    /**
     * @Desc: 备案表销毁登记
     * @Author: wangyunquan
     * @Param: [destroyRegVo]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/2
     */
    @PostMapping("/babDestroyReg")
    public  Result babDestroyReg(@RequestBody DestroyRegVo destroyRegVo){
        omsPubDestroyService.babDestroyReg(destroyRegVo);
        return Result.success();
    }

    /**
     * @Desc: 查询已销毁登记记录
     * @Author: wangyunquan
     * @Param: [pageBean, selDestroyRegByQuaVo]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/2
     */
    @GetMapping("/SelDestroyRegByQuaVo")
    public Result SelDestroyRegByQuaVo(PageBean pageBean,SelDestroyRegByQuaVo selDestroyRegByQuaVo){
        pageBean=omsPubDestroyService.SelDestroyRegByQuaVo(pageBean,selDestroyRegByQuaVo);
        return  Result.success(pageBean);
    }

    /**
     * @Desc: 销毁登记记录导出excel文件
     * @Author: wangyunquan
     * @Param: [exportRequestPara, response]
     * @Return: void
     * @Date: 2020/7/3
     */
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody ExportRequestPara exportRequestPara, HttpServletResponse response){
        try {
            omsPubDestroyService.exportExcel(exportRequestPara,response.getOutputStream());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode(UUIDGenerator.getPrimaryKey()+".xls", "utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }
    }
}
