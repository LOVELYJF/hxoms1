package com.hxoms.modules.passportCard.printGetQrCode.controller;


import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CanGetCerInfo;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CreateQrCodeApply;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.RequestList;
import com.hxoms.modules.passportCard.printGetQrCode.service.OmsPrintGetQrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "打印领取证照二维码")
@RestController
@RequestMapping("/printGetQrCode")
public class OmsPrintGetQrCodeController {

    @Autowired
    private OmsPrintGetQrCodeService omsPrintGetQrCodeService;

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, overFlag]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CanGetCerInfo>>
     * @Date: 2020/8/20
     */
    @ApiOperation(value = "查询可领取证照")
    @ApiImplicitParam(value = "是否显示过期证照(Y:是,N:否)",name = "overFlag",required = true)
    @GetMapping("/selectCanGetCer")
    public Result<PageBean<CanGetCerInfo>> selectCanGetCer(PageBean pageBean, String overFlag){
        return Result.success(omsPrintGetQrCodeService.selectCanGetCer(pageBean,overFlag));
    }


    /**
     * @Desc: 生成打印二维码
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/21
     */
    @ApiOperation(value = "生成打印二维码")
    @PostMapping("/printQrCode")
    public Result createPrintQrCode(@RequestBody RequestList<CreateQrCodeApply> requestList, HttpServletResponse httpServletResponse){
        try {
            return Result.success(omsPrintGetQrCodeService.createPrintQrCode(requestList.getList()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("二维码生成失败，原因："+e.getMessage());
        }
    }
}
