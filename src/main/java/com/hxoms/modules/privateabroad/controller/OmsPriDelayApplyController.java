package com.hxoms.modules.privateabroad.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.service.OmsPriDelayApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc: 延期回国
 * @author: lijing
 * @date: 2020-06-03
 */
@RestController
@RequestMapping("/omsPriDelayApply")
public class OmsPriDelayApplyController {

    @Autowired
    private OmsPriDelayApplyService omsPriDelayApplyService;

    /**
     * 延期回国申请列表
     * @param omsPriApplyIPageParam  查询条件
     * @return
     * @throws Exception
     */
    @GetMapping("/selectOmsDelayApplyIPage")
    public Result selectOmsDelayApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam) throws Exception {
        PageInfo<OmsPriDelayApplyVO> omsPriDelayApplyVOPageInfo = omsPriDelayApplyService.selectOmsDelayApplyIPage(omsPriApplyIPageParam);
        return Result.success(omsPriDelayApplyVOPageInfo);
    }

    /**
     * 新增或修改延期回国申请
     * @param omsPriDelayApply  申请接收参数类
     * @return
     * @throws Exception
     */
    @PostMapping("/insertOrUpdateApply")
    public Result insertOrUpdateApply(OmsPriDelayApply omsPriDelayApply) throws Exception {
        String result = omsPriDelayApplyService.insertOrUpdateApply(omsPriDelayApply);
        return Result.success().setMsg(result);
    }

    /**
     * 修改延期回国申请状态
     * @param omsPriDelayApply 申请id
     * @return
     * @throws Exception
     */
    @PostMapping("/updateDelayApplyStatus")
    public Result updateDelayApplyStatus(OmsPriDelayApply omsPriDelayApply) throws Exception {
        String result = omsPriDelayApplyService.updateDelayApplyStatus(omsPriDelayApply);
        return Result.success().setMsg(result);
    }

    /**
     * 删除延期回国申请
     * @param id 申请id
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteDelayApply")
    public Result deleteDelayApply(String id) throws Exception {
        String result = omsPriDelayApplyService.deleteDelayApply(id);
        return Result.success().setMsg(result);
    }

    /**
     * 延期回国申请详情
     * @param id 申请id
     * @return
     */
    @GetMapping("/selectDelayApplyById")
    public Result selectDelayApplyById(String id){
        OmsPriDelayApplyVO omsPriDelayApplyVO = omsPriDelayApplyService.selectDelayApplyById(id);
        return Result.success(omsPriDelayApplyVO);
    }
}
