package com.hxoms.modules.passportCard.getTaskQuery.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.ExportExcelUtil;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.getTaskQuery.mapper.OmsGetTaskQueryMapper;
import com.hxoms.modules.passportCard.getTaskQuery.service.OmsGetTaskQueryService;
import com.hxoms.modules.publicity.taskSupervise.service.OmsPubTaskSuperviseService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class OmsGetTaskQueryServiceImpl implements OmsGetTaskQueryService {

    @Autowired
    private OmsGetTaskQueryMapper omsGetTaskQueryMapper;

    @Autowired
    private OmsPubTaskSuperviseService omsPubTaskSuperviseService;

    /**
     * @Desc: 证照管理-证照领取任务-导出
     * @Author: wuqingfan
     * @Param: ids
     * @Return: excel
     * @Date: 2020/9/12
     */
    @Override
    public  void exportSelectGetCer(List<String> ids, HttpServletResponse response){
        CerGetTaskQueryParam param=new CerGetTaskQueryParam();
        param.setIds(ids);
        List<CerGetTaskInfo> taskInfos=omsGetTaskQueryMapper.selectGetCer(param);
        List<CerGetTaskInfoExport> exports=new ArrayList<>();
        CerGetTaskInfoExport export=null;
        if (taskInfos.size()>0){
            for (CerGetTaskInfo info:taskInfos){
                export=new CerGetTaskInfoExport();
                BeanUtils.copyProperties(info,export);
                exports.add(export);
            }
        }
        String[] headers=("序号,工作单位,姓名,性别,出生日期,职务（级）或职称,领取状态,证照类型,证件号码,有效期至,证照状态,审批表,保管方式," +
                "机柜,位置,柜台编号,来源,业务日期,任务产生日期,领取时间,工作单位联系人,联系电话").split(",");
        ExportExcelUtil.exportNotTitleExcel("证照领取任务",headers,(List) exports,response);
    }


    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskInfo>
     * @Date: 2020/8/18
     */
    @Override
    public PageBean<CerGetTaskInfo> selectGetCer(PageBean pageBean,CerGetTaskQueryParam cerGetTaskQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CerGetTaskInfo> pageInfo=new PageInfo<CerGetTaskInfo>(omsGetTaskQueryMapper.selectGetCer(cerGetTaskQueryParam));
        return PageUtil.packagePage(pageInfo);
    }

   /**
    * @Desc: 获取发通知内容模板
    * @Author: wangyunquan
    * @Param: [sendNoticeParamList]
    * @Return: com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.NoticeContent
    * @Date: 2020/9/5
    */
    @Override
    public NoticeContent getSendNoticeContent(List<SendNoticeContentParam> sendNoticeContentParamList) {
        if(sendNoticeContentParamList.size()==0)
            throw new CustomMessageException("请求参数不正确，请核实！");
        Map<String,String> noticeMap=new HashedMap();
        StringBuffer allStr=new StringBuffer();
        String rfB0000=null;
        for (SendNoticeContentParam sendNoticeContentParam : sendNoticeContentParamList) {
            StringBuffer partStr=new StringBuffer();
            rfB0000 = sendNoticeContentParam.getRfB0000();
            if("1".equals(sendNoticeContentParam.getGetStatus()))
                throw new CustomMessageException(sendNoticeContentParam.getZjlxName()+"("+ sendNoticeContentParam.getZjhm()+")"+"已领取，不能发通知，请重新选择！");
            //张三的护照（E9435）
            partStr.append(sendNoticeContentParam.getName()).append("的").append(sendNoticeContentParam.getZjlxName()).append("（"+ sendNoticeContentParam.getZjhm()+"）");
            String value = noticeMap.get(sendNoticeContentParam.getRfB0000());
            if(noticeMap.containsKey(sendNoticeContentParam.getRfB0000())){
                noticeMap.put(rfB0000,value+"、"+partStr.toString());
            }else{
                noticeMap.put(rfB0000,partStr.toString());
            }
            if(noticeMap.keySet().size()>1)
                throw new CustomMessageException("不能同时选择两个工作单位，请核实！");
        }
        if(!noticeMap.isEmpty()){
            //贵单位张三的护照（E9435）、王五的护照（E9545）可领取，请尽快领取证件。
            allStr.append("贵单位").append(noticeMap.get(rfB0000)).append("可领取，请尽快领取证件。");
        }
        NoticeContent noticeContent=new NoticeContent();
        noticeContent.setRfB0000(rfB0000);
        noticeContent.setContent(allStr.toString());
        return noticeContent;
    }

    /**
     * @param sendNotice
     * @Desc: 发通知
     * @Author: wangyunquan
     * @Param: [sendNotice]
     * @Return: void
     * @Date: 2020/9/5
     */
    @Override
    public void sendNotice(SendNotice sendNotice) {
        try {
            omsPubTaskSuperviseService.preAndRecMessage(sendNotice.getRfB0000(),sendNotice.getContent(),"6","1");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomMessageException("发通知失败，原因："+e.getMessage());
        }
    }

}
