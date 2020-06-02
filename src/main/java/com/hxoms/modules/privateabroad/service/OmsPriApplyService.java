package com.hxoms.modules.privateabroad.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyParam;
import com.hxoms.modules.publicity.entity.PersonInfoVO;

import java.util.List;

public interface OmsPriApplyService {
    /**
     * 因私出国申请列表
     * @param omsPriApplyIPageParam  查询条件
     * @return
     * @throws Exception
     */
    PageInfo<OmsPriApplyVO> selectOmsPriApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam);
    /**
     * 选择人员（保存前约束条件判断）
     * @param b0100 机构id
     * @param a0100 人员id
     * @return
     * @throws Exception
     */
    OmsPriApplyVO selectPersonById(String b0100, String a0100);
    /**
     * 新增或修改因私出国申请
     * @param omsPriApplyParam  申请接收参数类
     * @return
     * @throws Exception
     */
    String insertOrUpdatePriApply(OmsPriApplyParam omsPriApplyParam);
    /**
     * 删除申请
     * @param id 申请id
     * @return
     * @throws Exception
     */
    String deletePriApply(String id);
    /**
     * 修改因私出国申请状态
     * @param omsPriApply 申请id
     * @return
     * @throws Exception
     */
    String updateApplyStatus(OmsPriApply omsPriApply);
    /**
     * 根据姓名或者拼音查找用户
     * @param keyword 关键词
     * @return
     * @throws Exception
     */
    List<PersonInfoVO> selectPersonByKeyword(String keyword);
    /**
     * 申请详情
     * @param id 申请id
     * @return
     */
    OmsPriApplyVO selectPriApplyById(String id);
}
