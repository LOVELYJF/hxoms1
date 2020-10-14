package com.hxoms.modules.leaderSupervision.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/7/29 10:28
 * @Description:
 ***/
public interface LeaderDetailProcessingService {

    /**
     *  通知经办人重新递交备案函
     * **/
    void sendMessageToAgent(String applyId,String tableCode);

    /**
     *
     * **/

    List<Map> getApplicationType();

    /**
     * 查询 批次 列表
     * **/

    PageInfo selectOmsLeaderBatch(LeaderSupervisionVo leaderSupervisionVo);

    /**
     *  纳入选择人员 修改 批次
     * **/

    void updateLeaderBatch(OmsLeaderBatch omsLeaderBatch);

    /**
     *  获取 批次 状态
     * **/

    List<Map> selectLeaderBatchStatus();

    /**
     *业务 处理 材料审核 的 (最后一个) 下一步 触发的事件
     * **/

    void materialReviewNextStep(String applyId,String tableCode,String clshsftgOpinion);

    /**
     * 纪委意见 按批次上传附件
     * **/
     void fileUpload(MultipartFile[] files,String[] leaderBatchIds,String bussinessType,int bussinessOccureStpet,String bussiness_occure_stpet_name,  HttpServletRequest request);

    /**
     * 征求纪委意见 查询条件
     * **/
    List<Map> selectMaterialStatus();

    /**
     * 查看附件列表
     * **/
    List<Map> selectAttachmentList(String[] leaderBatchIds);

    /**
     * 下载 附件
     * **/

    Map downloadAttachmentById(String id);

    /**
     *
     * 生成 审批单
     * */

    List<Map> makeApprovalFor(LeaderSupervisionVo leaderSupervisionVo);

    /**
     * 生成备案表
     * **/
    List<Map> createPutOnRecord(LeaderSupervisionVo leaderSupervisionVo);


    OmsCreateFile insertOrUpadateCreateFileAndUpdateStaus(OmsCreateFile omsCreateFile, String bussinessId, String type,String pass);

    /**
     * 保存 备案表
     * **/
    OmsCreateFile insertOrUpdatePutOnRecordFile(OmsCreateFile omsCreateFile,String userName);

    /**
     *  处长批量审批
     * **/
    void chuzhangAbroadApprovalBatch(LeaderSupervisionVo leaderSupervisionVo);



    /**
     * 保存 单条处长审批记录
     * **/
    void saveChuZhangOneApproveRecord(AuditOpinionVo auditOpinionVo);

    /** 保存部长审批记录 **/

    void saveBuZhangApprover(AuditOpinionVo auditOpinionVo);

    /**备案表查询列表**/

    PageInfo createPutOnRecordList(LeaderSupervisionVo leaderSupervisionVo,Object[] bussinessIds);

    List<Map> selectGroupConditions();

    /** 批量下载备案表***/

    Map  batchDownloadPutOnRecord(LeaderSupervisionVo leaderSupervisionVo);


}

