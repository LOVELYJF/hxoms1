package com.hxoms.modules.publicity.taskSupervise.controller;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.publicity.taskSupervise.entity.*;
import com.hxoms.modules.publicity.taskSupervise.service.OmsPubTaskSuperviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;


/**
 * @desc: 组团单位出国（境）任务监管
 * @author: wangyunquan
 * @date: 2020/6/19
 */
@RestController
@RequestMapping("/taskSupervise")
public class OmsPubTaskSuperviseController {

    @Autowired
    private OmsPubTaskSuperviseService omsPubTaskSuperviseService;

    /**
     * @Desc: 查询经办人所在单位的团组
     * @Author: wangyunquan
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/6/29
     */
    @GetMapping("/selectZtDwApplyList")
    public Result selectZtDwApplyList() {
        List<ZtDwTreeVO> ztDwTreeVOList = omsPubTaskSuperviseService.selectZtDwApplyList();
        return Result.success(ztDwTreeVOList);
    }

    /**
     * @Desc: 查询团组人员
     * @Author: wangyunquan
     * @Param: [pageBean, year, ztDwName]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/6/23
     */
    @GetMapping("/selectZtDwPerson")
    public Result selectZtDwPerson(PageBean pageBean,String year, String ztDwName) {
        pageBean = omsPubTaskSuperviseService.selectZtDwPerson(pageBean,year,ztDwName);
        return Result.success(pageBean);
    }

    /**
     * @Desc: 筛选团组人员
     * @Author: wangyunquan
     * @Param: [ztDwPersionQuery]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/6/23
     */
    @GetMapping("/selectZtDwPersonByQua")
    public Result selectZtDwPersonByQua(PageBean pageBean,ZtDwPersionQuery ztDwPersionQuery) {
        pageBean = omsPubTaskSuperviseService.selectZtDwPersonByQua(pageBean,ztDwPersionQuery);
        return Result.success(pageBean);
    }

    /**
     * @Desc: 批量下载个人备案表
     * @Author: wangyunquan
     * @Param: [downloadBabParam]
     * @Return: org.springframework.http.ResponseEntity<byte[]>
     * @Date: 2020/7/7
     */
    @PostMapping("/batchDownloadBab")
    public  ResponseEntity<byte[]> batchDownloadBab(@RequestBody DownloadBabParam downloadBabParam){
        ResponseEntity<byte[]> responseEntity=null;
        try {
            FileInfo fileInfo = omsPubTaskSuperviseService.batchDownloadBab(downloadBabParam);
            responseEntity=ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode(fileInfo.getFileName(), "utf-8")))
                    .body(fileInfo.getFileDataByte());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomMessageException("下载失败，原因："+e.getMessage());
        }
        return responseEntity;
    }
    /**
     * @Desc: 查询催办信息
     * @Author: wangyunquan
     * @Param: [urgeBusiness]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/6/28
     */
    @GetMapping("/selectUrgeInfo")
    public Result selectUrgeInfo(UrgeBusiness urgeBusiness) {
        try {
            omsPubTaskSuperviseService.selectUrgeInfo(urgeBusiness);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomMessageException("获取失败，原因："+e.getMessage());
        }
        return Result.success(urgeBusiness);
    }
    /**
     * @Desc: 办理催办业务
     * @Author: wangyunquan
     * @Param: [urgeBusiness]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/14
     */
    @PostMapping("/insertUrgeBusiness")
    public Result insertUrgeBusiness(@RequestBody UrgeBusiness urgeBusiness) {
        try {
            omsPubTaskSuperviseService.insertUrgeBusiness(urgeBusiness);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomMessageException("催办失败，原因："+e.getMessage());
        }
        return Result.success();
    }

}
