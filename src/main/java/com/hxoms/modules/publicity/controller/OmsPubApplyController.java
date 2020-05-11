package com.hxoms.modules.publicity.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import com.hxoms.modules.publicity.service.OmsPubApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 因公出国境干部申请
 *
 * @author sunqian
 * @date 2020/4/21 17:15
 */
@RestController
@RequestMapping("/omsPubApply")
public class OmsPubApplyController {

    @Autowired
    private OmsPubApplyService omsPubApplyService;

    /**
     * 根据id查询申请记录
     *
     * @author sunqian
     * @date 2020/5/11 15:05
     */
    @GetMapping("/selectPubApplyById")
    public Result selectPubApplyById(String id) {
        OmsPubApply omsPubApply = omsPubApplyService.selectPubApplyById(id);
        return Result.success(omsPubApply);
    }

    /**
     * 根据机构id查询人员
     *
     * @author sunqian
     * @date 2020/4/21 17:16
     */
    @GetMapping("/selectPersonListByOrg")
    public Result selectPersonListByOrg(String b0100) {
        List<PersonInfoVO> list = omsPubApplyService.selectPersonListByOrg(b0100);
        return Result.success(list);
    }

    /**
     * 根据人员id查询申请的人员基本信息
     *
     * @author sunqian
     * @date 2020/4/23 15:18
     */
    @GetMapping("/selectPubApplyPersonInfo")
    public Result selectPubApplyPersonInfo(String b0100, String a0100) {
        OmsPubApplyVO omsPubApply = omsPubApplyService.selectPubApplyPersonInfo(b0100, a0100);
        return Result.success(omsPubApply);
    }

    /**
     * 新增备案申请
     *
     * @author sunqian
     * @date 2020/4/26 17:21
     */
    @PostMapping("/insertOrUpdatePubApply")
    public Result insertOrUpdatePubApply(OmsPubApply omsPubApply) throws Exception {
        String result = omsPubApplyService.insertOrUpdatePubApply(omsPubApply);
        return Result.success().setMsg(result);
    }

    /**
     * 查询单位下申请人员的列表
     *
     * @author sunqian
     * @date 2020/4/29 18:22
     */
    @GetMapping("/selectPubApplyList")
    public Result selectPubApplyList() {
        List<OmsPubApply> list = omsPubApplyService.selectPubApplyList();
        return Result.success(list);
    }
}
