package com.hxoms.modules.leaderSupervision.vo;

import com.hxoms.common.utils.StringUilt;

import java.util.List;

/**
 * @authore:wjf
 * @data 2020/7/7 9:38  干部监督处
 * @Description:
 ***/
public class AuditOpinionVo {

    private Integer pageNum;  /**页码*/

    private Integer pageSize;   /**分页大小*/

//    private String[] bussinessId; /**业务id 数组**/
//
//    private String[] bussinessName; /**业务名称 数组**/
//
//    private String[] incumbencyStatusArrays;  /** 在职 状态 **/

    private List<BusinessTypeAndIdAndOnJobVo> businessTypeAndIdAndOnJobVos;

    private String busName;  /** 针对 处长 逐条审批  业务流程名称 */

    private String busId;   /** 针对 处长 逐条审批  业务流程id */


    public static final String  pass = "1"; /** 通过 **/
    public static final String  nopass = "2"; /**不通过 **/

    public static String[]  bussinessTypeStatus ={"1","2","3"};

    public static String[]  getBussinessTypeStatusName={"因公出国(境)","因私出国(境)","因私出国（境）延期入境"};

    private String bussinessType;

    private String ispass; /** 处理 批量审批 是否 通过 ***/

    private String reason; /**  部长 审批理由 **/


    public AuditOpinionVo(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public AuditOpinionVo() {
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {

        if(StringUilt.stringIsNullOrEmpty(bussinessType)){

            bussinessType = "1";

        }

        this.bussinessType = bussinessType;
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public List<BusinessTypeAndIdAndOnJobVo> getBusinessTypeAndIdAndOnJobVos() {
        return businessTypeAndIdAndOnJobVos;
    }

    public void setBusinessTypeAndIdAndOnJobVos(List<BusinessTypeAndIdAndOnJobVo> businessTypeAndIdAndOnJobVos) {
        this.businessTypeAndIdAndOnJobVos = businessTypeAndIdAndOnJobVos;
    }

    public String getIspass() {
        return ispass;
    }

    public void setIspass(String ispass) {
        this.ispass = ispass;
    }
}
