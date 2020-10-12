package com.hxoms.modules.privateabroad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.PassportResult;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyParam;
import com.hxoms.modules.publicity.entity.PersonInfoVO;

import java.util.List;
import java.util.Map;

public interface OmsPriApplyService extends IService<OmsPriApply> {
    /**
     * 因私出国申请列表
     * @param omsPriApplyIPageParam  查询条件
     * @return
     */
    PageInfo<OmsPriApplyVO> selectOmsPriApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam);
    /**
     * 选择人员（）
     * @param procpersonId 备案信息id
     * @return
     */
    OmsPriApplyVO selectPersonById(String procpersonId);
    /**
     * 新增或修改因私出国申请
     * @param omsPriApplyParam  申请接收参数类
     * @return
     */
    String insertOrUpdatePriApply(OmsPriApplyParam omsPriApplyParam);
    /**
     * 删除申请
     * @param id 申请id
     * @return
     */
    String deletePriApply(String id);
    /**
     * 修改因私出国申请状态
     * @param omsPriApply 申请id
     * @return
     */
    String updateApplyStatus(OmsPriApply omsPriApply);
    /**
     * 根据姓名或者拼音查找用户
     * @param keyword 关键词
     * @return
     */
    List<PersonInfoVO> selectPersonByKeyword(String keyword);
    /**
     * 申请详情
     * @param id 申请id
     * @return
     */
    OmsPriApplyVO selectPriApplyById(String id);

    /**
     * 基本流程数据统计
     * @param type 因私 延期回国
     * @return
     */
    List<CountStatusResult> selectCountStatus(String type);

    /**
     * 下一步（生成材料）
     * @param applyId
     * @return
     */
    String nextCreateFile(String applyId, String type);

    /**
     * 情况报告
     * @param omsPriApply
     * @return
     */
    String saveAbroadState(OmsPriApply omsPriApply);
    /**
     * 撤销人员统计
     * @return
     */
    List<Map<String, Object>> countCancelPriApply(OmsPriApplyIPageParam omsPriApplyIPageParam);

    /**
     * 签注配置查询
     * @param infoId 出国理由编码
     * @return
     */
    Map<String, Object> selectVisaSettingByCode(String infoId);

    /**
     * 因私出国证照查询
     * @param countries 国家id（逗号分隔）
     * @return
     */
    List<PassportResult> selectPassportByCountry(String countries, String procpersonId);

    OmsCreateFile printApproval(String applyId);
}
