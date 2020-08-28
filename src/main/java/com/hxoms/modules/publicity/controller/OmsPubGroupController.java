package com.hxoms.modules.publicity.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.publicity.entity.*;
import com.hxoms.modules.publicity.service.OmsPubGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
     * @param bazt
     * @param orgId
     * @param status
     * @param startDate
     * @param endDate
     */
    @GetMapping("/getPubGroupList")
    public Result getPubGroupList(Integer pageNum, Integer pageSize,String bazt,String orgId,String status,String startDate,String endDate) {
        Map<String,String> param = new HashMap<>();
        try{
            param.put("bazt",bazt);
            param.put("orgId",orgId);
            param.put("status",status);
            param.put("startDate",startDate);
            param.put("endDate",endDate);
            PageInfo<OmsPubGroupPreApproval> pageInfo = pubGroupService.getPubGroupList(pageNum,pageSize,param);
            return Result.success(pageInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 添加团体预备案申请信息
     * @param pubGroupAndApplyList(集合实体类)
     */
    @PostMapping("/insertPubGroup")
    public Result insertPubGroup(@RequestBody OmsPubGroupAndApplyList pubGroupAndApplyList) {
        try {
            return Result.success(pubGroupService.insertPubGroup(pubGroupAndApplyList));
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 修改团体预备案申请信息
     * @param pubGroupAndApplyList(集合实体类)
     */
    @PostMapping("/updatePubGroup")
    public Result updatePubGroup(@RequestBody OmsPubGroupAndApplyList pubGroupAndApplyList,
                                 String bgyy) {
        try {
            pubGroupService.updatePubGroup(pubGroupAndApplyList,bgyy);
            return Result.success();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 删除团体预备案申请信息
     * @param id
     */
    @PostMapping("/deletePubGroup")
    public Result deletePubGroup(String id) {
        try {
            pubGroupService.deletePubGroup(id);
            return Result.success();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /** 上传团体预备案申请信息
     * @param file
     */
    @PostMapping("/uploadPubGroupJson")
    public Result uploadPubGroupJson(@RequestParam("file") MultipartFile file) {
        try{
            //获得文件的名称
            String fileName = file.getOriginalFilename();
            //获得文件的扩展名称
            String extensionName = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
            if(!"json".equals(extensionName)){
                return Result.error("请上传json格式文件");
            }else{
                return Result.success(pubGroupService.uploadPubGroupJson(file));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导入失败");
        }
    }

    /**
     * 重新校验
     * @param idList
     */
    @PostMapping("/checkoutPerson")
    public Result checkoutPerson(String idList) {
        return Result.success(pubGroupService.checkoutPerson(idList));
    }

    /**
     * 添加人员
     * @param A0100 人员主键
     * @param id 团组主键
     */
    @PostMapping("/insertPerson")
    public Result insertPerson(String A0100,String id) {
        try {
            pubGroupService.insertPerson(A0100,id);
            return Result.success();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }

    /**
     * 撤销团组
     * @param id
     */
    @PostMapping("/backoutPerson")
    public Result backoutPerson(String id,String cxyy) {
        try {
            pubGroupService.backoutPerson(id,cxyy);
            return Result.success();
        }catch (Exception e){
            return Result.error("系统错误！");
        }
    }

    /**
     * 恢复团组
     * @param id
     */
    @PostMapping("/regainPerson")
    public Result regainPerson(String id) {
        try {
            pubGroupService.regainPerson(id);
            return Result.success();
        }catch (Exception e){
            return Result.error("系统错误！");
        }
    }

    /**
     * 查看团组详情
     * @param id
     */
    @GetMapping("/getPubGroupDetailById")
    public Result getPubGroupDetailById(String id) {
        return Result.success(pubGroupService.getPubGroupDetailById(id));
    }

    /**
     * 查看人员详情
     * @param id
     */
    @GetMapping("/getPersonDetailById")
    public Result getPersonDetailById(String id) {
        return Result.success(pubGroupService.getPersonDetailById(id));
    }

    /**
     * 查看撤销详情
     * @param id
     */
    @GetMapping("/getBackoutDetailById")
    public Result getBackoutDetailById(String id) {
        return Result.success(pubGroupService.getBackoutDetailById(id));
    }

    /**
     * 查看流程详情
     * @param id（人员id）
     */
    @GetMapping("/getFlowDetail")
    public Result getFlowDetail(String id) {
        return Result.success(pubGroupService.getFlowDetail(id));
    }

    /**
     * 递送任务
     * @param id(团队id)
     */
    @PostMapping("/sendTask")
    public Result sendTask(String id) {
        try {
            pubGroupService.sendTask(id);
            return Result.success();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }

    /**
     * 获取审核意见
     * @param id（团组id）
     */
    @GetMapping("/getAuditOpinion")
    public Result getAuditOpinion(String id) {
        return Result.success(pubGroupService.getAuditOpinion(id));
    }

    /** 上传批文
     * @param file
     * @param id
     */
    @PostMapping("/uploadApproval")
    public Result uploadApproval(MultipartFile file, String id) {
        try{
            return Result.success(pubGroupService.uploadApproval(file,id));
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
    }

    /**
     * 获取备案步骤任务数
     * @param bazt (备案主体类型)
     */
    @GetMapping("/getNumByStatus")
    public Result getNumByStatus(String bazt) {
        return Result.success(pubGroupService.getNumByStatus(bazt));
    }

}









