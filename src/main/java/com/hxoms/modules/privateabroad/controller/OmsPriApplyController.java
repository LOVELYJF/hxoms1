package com.hxoms.modules.privateabroad.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyParam;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @desc: 因私出国境申请
 *
 * @author: lijing
 * @date: 2020-05-15
 */
@RestController
@RequestMapping("/omsPriApply")
public class OmsPriApplyController {

    @Autowired
    private OmsPriApplyService omsPriApplyService;

    /**
     * 因私出国申请列表
     * @param omsPriApplyIPageParam  查询条件
     * @return
     * @throws Exception
     */
    @GetMapping("/selectOmsPriApplyIPage")
    public Result selectOmsPriApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam) throws Exception {
        PageInfo<OmsPriApplyVO> omsPriApplyVOIPage = omsPriApplyService.selectOmsPriApplyIPage(omsPriApplyIPageParam);
        return Result.success(omsPriApplyVOIPage);
    }

    /**
     * 根据姓名或者拼音查找用户
     * @param keyword 关键词
     * @return
     * @throws Exception
     */
    @GetMapping("selectPersonByKeyword")
    public Result selectPersonByKeyword(String keyword) throws Exception {
        List<PersonInfoVO> personInfoVOS = omsPriApplyService.selectPersonByKeyword(keyword);
        return Result.success(personInfoVOS);
    }

    /**
     * 选择人员
     * @param b0100 机构id
     * @param a0100 人员id
     * @return
     * @throws Exception
     */
    @GetMapping("/selectPersonById")
    public Result selectPersonById(String b0100, String a0100) throws Exception {
        OmsPriApplyVO omsPriApplyVO = omsPriApplyService.selectPersonById(b0100,a0100);
        return Result.success(omsPriApplyVO);
    }

    /**
     * 新增或修改因私出国申请
     * @param omsPriApplyParam  申请接收参数类
     * @return
     * @throws Exception
     */
    @PostMapping("/insertOrUpdatePriApply")
    public Result insertOrUpdatePriApply(OmsPriApplyParam omsPriApplyParam) throws Exception {
        String result = omsPriApplyService.insertOrUpdatePriApply(omsPriApplyParam);
        return Result.success(result);
    }

    /**
     * 修改因私出国申请状态
     * @param omsPriApply 申请id
     * @return
     * @throws Exception
     */
    @PostMapping("/updateApplyStatus")
    public Result updateApplyStatus(OmsPriApply omsPriApply) throws Exception {
        String result = omsPriApplyService.updateApplyStatus(omsPriApply);
        return Result.success().setMsg(result);
    }

    /**
     * 删除申请
     * @param id 申请id
     * @return
     * @throws Exception
     */
    @PostMapping("/deletePriApply")
    public Result deletePriApply(String id) throws Exception {
        String result = omsPriApplyService.deletePriApply(id);
        return Result.success().setMsg(result);
    }

    /**
     * 申请详情
     * @param id 申请id
     * @return
     */
    @GetMapping("/selectPriApplyById")
    public Result selectPriApplyById(String id){
        OmsPriApplyVO omsPriApplyVO = omsPriApplyService.selectPriApplyById(id);
        return Result.success(omsPriApplyVO);
    }

    /**
     * 基本流程数据统计
     * @param type 因私 延期回国
     * @return
     */
    @GetMapping("/selectCountStatus")
    public Result selectCountStatus(String type){
        List<CountStatusResult> countStatusResults = omsPriApplyService.selectCountStatus(type);
        return Result.success(countStatusResults);
    }

    /**
     * 下一步（生成材料）
     * @param applyId
     * @return
     * @throws Exception
     */
    @PostMapping("/nextCreateFile")
    public Result nextCreateFile(String applyId, String type) throws Exception {
        String result = omsPriApplyService.nextCreateFile(applyId, type);
        return Result.success().setMsg(result);
    }

    /**
     * 情况报告
     * @param omsPriApply
     * @return
     * @throws Exception
     */
    @PostMapping("/saveAbroadState")
    public Result saveAbroadState(OmsPriApply omsPriApply) throws Exception {
        String result = omsPriApplyService.saveAbroadState(omsPriApply);
        return Result.success().setMsg(result);
    }
}
