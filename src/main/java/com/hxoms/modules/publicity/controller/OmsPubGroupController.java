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
    @PostMapping("/updateTimeTask")
    public Result updateTimeTask(@RequestBody OmsPubGroupAndApplyList pubGroupAndApplyList,
                                 String bgyy) {
        try {
            pubGroupService.updateTimeTask(pubGroupAndApplyList,bgyy);
            return Result.success();
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
    public Result updatePubGroup(@RequestBody OmsPubGroupAndApplyList pubGroupAndApplyList) {
        try {
            pubGroupService.updatePubGroup(pubGroupAndApplyList);
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
            if(file == null){
                return Result.error("未获取到上传文件，请检查！");
            }
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
     * @param pubGroupAndApplyList
     */
    @PostMapping("/checkoutPerson")
    public Result checkoutPerson(@RequestBody OmsPubGroupAndApplyList pubGroupAndApplyList) {
        try {
            return Result.success(pubGroupService.checkoutPerson(pubGroupAndApplyList));
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }

    /**
     * 添加人员
     * @param personId 人员主键
     * @param id 团组主键
     */
    @PostMapping("/insertPerson")
    public Result insertPerson(String personId,String id) {
        try {
            pubGroupService.insertPerson(personId,id);
            return Result.success();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }

    /**
     * 撤销人员
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
     * 撤销团组
     * @param id
     */
    @PostMapping("/backoutGroup")
    public Result backoutGroup(String id,String cxyy) {
        try {
            pubGroupService.backoutGroup(id,cxyy);
            return Result.success();
        }catch (Exception e){
            return Result.error("系统错误！");
        }
    }

    /**
     * 恢复团组
     * @param id
     */
    @PostMapping("/regainGroup")
    public Result regainGroup(String id) {
        try {
            pubGroupService.regainGroup(id);
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
     * 递送任务
     * @param pubGroupAndApplyList(团队及人员信息)
     */
    @PostMapping("/sendTask")
    public Result sendTask(@RequestBody OmsPubGroupAndApplyList pubGroupAndApplyList) {
        try {
            pubGroupService.sendTask(pubGroupAndApplyList);
            return Result.success();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }

    /**
     * 审核备案下一步
     * @param id(团队id)
     */
    @PostMapping("/goToUploadApproval")
    public Result goToUploadApproval(String id) {
        try {
            String msg = pubGroupService.goToUploadApproval(id);
            if(msg.length() > 0){
                return Result.error(msg);
            }
            return Result.success();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }


    /** 上传批文
     * @param file
     * @param id
     */
    @PostMapping("/uploadApproval")
    public Result uploadApproval(@RequestParam("file") MultipartFile file, String id) {
        try{
            if(file == null){
                return Result.error("未获取到上传文件，请检查！");
            }
            //获得文件的名称
            String fileName = file.getOriginalFilename();
            //获得文件的扩展名称
            String extensionName = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
            if(!"pdf".equals(extensionName)){
                return Result.error("请上传pdf格式文件!");
            }else{
                return Result.success(pubGroupService.uploadApproval(file,id));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
    }

    /** 更新批文号
     * @param pwh 新的批文号
     * @param id 团组id
     */
    @PostMapping("/updateApproval")
    public Result updateApproval(String pwh, String id) {
        try{
            return Result.success(pubGroupService.updateApproval(pwh,id));
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









