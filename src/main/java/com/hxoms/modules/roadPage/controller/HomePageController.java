package com.hxoms.modules.roadPage.controller;


import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import com.hxoms.modules.roadPage.entity.PersonnelPageParam;
import com.hxoms.modules.roadPage.service.HomePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 首页
 *
 * @author: lijing
 * @date: 2020-10-20
 */
@Api(tags="出国境首页")
@RestController
@RequestMapping("/homePage")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    /**
     * 人员情况列表
     * @param personnelPageParam
     * @return
     * @throws Exception
     */
    @ApiOperation(value="人员情况列表", notes="人员情况列表")
    @GetMapping("selectPersonnelRoster")
    public Result selectPersonnelRoster(PersonnelPageParam personnelPageParam) throws Exception {
        PageInfo<OmsRegProcpersoninfo> omsRegProcpersoninfoPageInfo = homePageService.selectPersonnelRoster(personnelPageParam);
        return Result.success(omsRegProcpersoninfoPageInfo);
    }

    /**
     * 因私出国实时情况
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出国实时情况", notes="因私出国实时情况")
    @GetMapping("selectPrivateRealTimeState")
    public Result selectPrivateRealTimeState() throws Exception {
        List<Integer> result = homePageService.selectPrivateRealTimeState();
        return Result.success(result);
    }

    /**
     * 因私出国实时情况详情
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出国实时情况详情", notes="因私出国实时情况详情")
    @ApiImplicitParam(name = "type", value = "申请中 征求意见中 已出国 已超期 已回国未归还证件", required = true, dataType = "String")
    @GetMapping("selectPrivateRealTimeStateDestail")
    public Result selectPrivateRealTimeStateDestail(String type) throws Exception {
        List<OmsPriApplyVO> result = homePageService.selectPrivateRealTimeStateDestail(type);
        return Result.success(result);
    }

    /**
     * 征求意见概况
     * @return
     * @throws Exception
     */
    @ApiOperation(value="征求意见概况", notes="征求意见概况")
    @GetMapping("selectSeekAdviceCount")
    public Result selectSeekAdviceCount() throws Exception {
        Map<String, Integer> result = homePageService.selectSeekAdviceCount();
        return Result.success(result);
    }

    /**
     * 征求意见概况详情
     * @return
     * @throws Exception
     */
    @ApiOperation(value="征求意见概况详情", notes="征求意见概况详情")
    @ApiImplicitParam(name = "type", value = "1未征求 2已经征求", required = true, dataType = "String")
    @GetMapping("selectSeekAdviceDestail")
    public Result selectSeekAdviceDestail(String type) throws Exception {
        List<OmsPriApplyVO> result = homePageService.selectSeekAdviceDestail(type);
        return Result.success(result);
    }

    /**
     * 因私出国境报警
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出国境报警", notes="因私出国境报警")
    @GetMapping("selectPriPoliceCount")
    public Result selectPriPoliceCount() throws Exception {
        Map<String, Integer> result = homePageService.selectPriPoliceCount();
        return Result.success(result);
    }

    /**
     * 因私出国境报警详情
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出国境报警详情", notes="因私出国境报警详情")
    @ApiImplicitParam(name = "type", value = "1超出回国时间10天内，未申请延期 2超出回国日期10天内，未归还证照 3实际目的地与申请审批目的地不一致4核心涉密人员申请出国", required = true, dataType = "String")
    @GetMapping("selectPriPoliceDestail")
    public Result selectPriPoliceDestail(String type) throws Exception {
        List<OmsPriApplyVO> result = homePageService.selectPriPoliceDestail(type);
        return Result.success(result);
    }

    /**
     * 证照统计
     * @return
     * @throws Exception
     */
    @ApiOperation(value="证照统计", notes="证照统计")
    @GetMapping("selectCertificateCount")
    public Result selectCertificateCount() throws Exception {
        Map<String, Integer> result = homePageService.selectCertificateCount();
        return Result.success(result);
    }

    /**
     * 证照统计详情
     * @return
     * @throws Exception
     */
    @ApiOperation(value="证照统计详情", notes="证照统计详情")
    @ApiImplicitParam(name = "type", value = "1护照 2港澳 3台湾4已过期5半年内过期", required = true, dataType = "String")
    @GetMapping("selectCertificateList")
    public Result selectCertificateList(String type) throws Exception {
        List<CfCertificate> result = homePageService.selectCertificateList(type);
        return Result.success(result);
    }

    /**
     * 证照上缴统计
     * @return
     * @throws Exception
     */
    @ApiOperation(value="证照上缴统计", notes="证照上缴统计")
    @GetMapping("selectCertificateTurninCount")
    public Result selectCertificateTurninCount() throws Exception {
        Map<String, List<Integer>> result = homePageService.selectCertificateTurninCount();
        return Result.success(result);
    }

    /**
     * 证照上缴统计详情
     * @return
     * @throws Exception
     */
    @ApiOperation(value="证照上缴统计详情", notes="selectCertificateTurninList")
    @GetMapping("selectCertificateTurninList")
    public Result selectCertificateTurninList(String certificateType, String turninType) throws Exception {
        List<CfCertificate> result = homePageService.selectCertificateTurninList(certificateType, turninType);
        return Result.success(result);
    }

    /**
     * 证照借出统计
     * @return
     * @throws Exception
     */
    @ApiOperation(value="证照借出统计", notes="证照借出统计")
    @GetMapping("selectCertificateLendCount")
    public Result selectCertificateLendCount() throws Exception {
        List<Integer> result = homePageService.selectCertificateLendCount();
        return Result.success(result);
    }

    /**
     * 证照借出统计详情
     * @return
     * @throws Exception
     */
    @ApiOperation(value="证照借出统计详情", notes="selectCertificateTurninList")
    @GetMapping("selectCertificateLendList")
    public Result selectCertificateTurninList(String type) throws Exception {
        List<CfCertificate> result = homePageService.selectCertificateLendList(type);
        return Result.success(result);
    }
}
