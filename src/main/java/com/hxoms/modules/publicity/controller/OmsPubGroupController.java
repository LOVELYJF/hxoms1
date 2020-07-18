package com.hxoms.modules.publicity.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubGroupPreApproval;
import com.hxoms.modules.publicity.entity.OmsPubGroupPreApprovalVO;
import com.hxoms.modules.publicity.service.OmsPubGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 省外办预备案/港澳办预备案
 * @author gaozhenyuan
 * @date 2020/6/2 16:28
 */
@RestController
@RequestMapping("/omsPubGroup")
public class OmsPubGroupController {

    @Autowired
    private OmsPubGroupService pubGroupService;

    /**
     * 获取团体预备案申请信息
     * @param orgId
     * @param status
     * @param startDate
     * @param endDate
     */
    @GetMapping("/getPubGroupList")
    public Result getPubProvinceList(String type,String orgId,String status,String startDate,String endDate) {
        Map<Object,String> param = new HashMap<>();
        try{
            param.put("type",type);
            param.put("orgId",orgId);
            param.put("status",status);
            param.put("startDate",startDate);
            param.put("endDate",endDate);
            PageInfo<OmsPubGroupPreApproval> pageInfo = pubGroupService.getPubGroupList(param);
            return Result.success(pageInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 添加团体预备案申请信息
     * @param pubGroup
     * @param personList
     */
    @PostMapping("/insertPubGroup")
    public Result insertPubGroup(OmsPubGroupPreApproval pubGroup, OmsPubGroupPreApprovalVO personList) {
        return Result.success(pubGroupService.insertPubGroup(pubGroup,personList));
    }

    /**
     * 修改团体预备案申请信息
     * @param pubGroup
     * @param personList
     */
    @PostMapping("/updatePubGroup")
    public Result updatePubGroup(OmsPubGroupPreApproval pubGroup,OmsPubGroupPreApprovalVO personList) {
        return Result.success(pubGroupService.updatePubGroup(pubGroup,personList));
    }

    /**
     * 删除团体预备案申请信息
     * @param id
     */
    @PostMapping("/deletePubGroup")
    public Result deletePubGroup(String id) {
        return Result.success(pubGroupService.deletePubGroup(id));
    }


    /** 上传团体预备案申请信息
     * @param file
     * @param orgName
     * @param orgId
     */
    @PostMapping("/uploadPubGroupExcel")
    public Result uploadPubGroupExcel(MultipartFile file, String orgName,String orgId) {
        try{

            return Result.success(pubGroupService.uploadPubGroupExcel(file,orgName,orgId));
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导入失败");
        }
    }
}









