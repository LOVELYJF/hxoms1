package com.hxoms.modules.privateabroad.service;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturn;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturnVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApprovalReturnIPageParam;

public interface OmsApprovalReturnService {
    /**
     * 因私出国审批表回收登记
     * @param omsApprovalReturn
     * @return
     */
    String savePriApprovalReturn(OmsApprovalReturn omsApprovalReturn);
    /**
     * 因私出国审批表回收登记删除
     * @param omsApprovalReturn
     * @return
     */
    String deletePriApprovalReturn(OmsApprovalReturn omsApprovalReturn);
    /**
     * 因私出国审批表回收登记列表
     * @param omsPriApprovalReturnIPageParam
     * @return
     */
    PageInfo<OmsApprovalReturnVO> selectPriApprovalReturnPagelist(OmsPriApprovalReturnIPageParam omsPriApprovalReturnIPageParam);

    
    /**
     * 查询回收登记详情
     * @param applyId
     * @return
     * @throws Exception
     */
    OmsApprovalReturn selectPriApprovalReturnDestail(String applyId);
    
    
   /**
    * 导出因私出国审批表回收登记列表
    * @param omsPriApprovalReturnIPageParam
    * @param response
    */
    void exportPriApprovalReturn (OmsPriApprovalReturnIPageParam omsPriApprovalReturnIPageParam,HttpServletResponse response);
}
