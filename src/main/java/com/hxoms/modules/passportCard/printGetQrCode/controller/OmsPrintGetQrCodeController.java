package com.hxoms.modules.passportCard.printGetQrCode.controller;


import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.printGetQrCode.entity.OmsCerPrintQrCode;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.RequestList;
import com.hxoms.modules.passportCard.printGetQrCode.service.OmsPrintGetQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Desc: TODO
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/8/14
 */
@RestController
@RequestMapping("/printGetQrCode")
public class OmsPrintGetQrCodeController {

    @Autowired
    private OmsPrintGetQrCodeService omsPrintGetQrCodeService;

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, overFlag]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/20
     */
    @GetMapping("/selectCanGetCer")
    public Result selectCanGetCer(PageBean pageBean,String overFlag){
        return Result.success(omsPrintGetQrCodeService.selectCanGetCer(pageBean,overFlag));
    }


    /**
     * @Desc: 生成打印二维码
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/21
     */
    @PostMapping("/printQrCode")
    public Result createPrintQrCode(@RequestBody RequestList<OmsCerPrintQrCode> requestList, HttpServletResponse httpServletResponse){
        try {
            omsPrintGetQrCodeService.createPrintQrCode(requestList.getList());
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
            throw new CustomMessageException("二维码生成失败，原因："+e.getMessage());
        }
        return Result.success();
    }
}
