package com.hxoms.modules.publicity.docCallback.controller;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.ExportRequestPara;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.SelCallbackRegByQuaVo;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.CallbackRegVo;
import com.hxoms.modules.publicity.docCallback.service.OmsPubDocCallbackService;
import com.hxoms.support.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Desc: 因公出国（境）批件回收登记
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/7/7
 */
@RestController
@RequestMapping("/docCallBackReg")
public class OmsPubDocCallbackController {
    @Autowired
    private OmsPubDocCallbackService omsPubDocCallbackService;

    /**
     * @Desc: 查询可回收登记备案申请
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/7
     */
    @GetMapping("/selectCanCallbApply")
    public Result selectCanCallbApply(PageBean pageBean){
        pageBean = omsPubDocCallbackService.selectCanCallbApply(pageBean);
        return Result.success(pageBean);
    }

    /**
     * @Desc: 获取登陆用户信息
     * @Author: wangyunquan
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/7
     */
    @GetMapping("/selectUserInfo")
    public Result selectUserInfo(){
        User user=omsPubDocCallbackService.selectUserInfo();
        return Result.success(user);
    }
    /**
     * @Desc: 批件回收登记
     * @Author: wangyunquan
     * @Param: [destroyRegVo]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/7
     */
    @PostMapping("/docCallbackReg")
    public  Result docCallbackReg(@RequestBody CallbackRegVo callbackRegVo){
        omsPubDocCallbackService.docCallbackReg(callbackRegVo);
        return Result.success();
    }

    /**
     * @Desc: 查询批件回收登记记录
     * @Author: wangyunquan
     * @Param: [pageBean, selCallbackRegByQuaVo]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/7
     */
    @GetMapping("/SelCallbackRegByQuaVo")
    public Result SelCallbackRegByQuaVo(PageBean pageBean,SelCallbackRegByQuaVo selCallbackRegByQuaVo){
        pageBean=omsPubDocCallbackService.SelCallbackRegByQuaVo(pageBean,selCallbackRegByQuaVo);
        return  Result.success(pageBean);
    }

    /**
     * @Desc: 批件回收登记记录导出excel文件
     * @Author: wangyunquan
     * @Param: [exportRequestPara, response]
     * @Return: void
     * @Date: 2020/7/7
     */
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody ExportRequestPara exportRequestPara, HttpServletResponse response){
        try {
            omsPubDocCallbackService.exportExcel(exportRequestPara,response.getOutputStream());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode(UUIDGenerator.getPrimaryKey()+".xls", "utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }
    }
}
