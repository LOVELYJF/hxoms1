package com.hxoms.modules.passportCard.getTaskQuery.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.getTaskQuery.mapper.OmsGetTaskQueryMapper;
import com.hxoms.modules.passportCard.getTaskQuery.service.OmsGetTaskQueryService;
import com.hxoms.modules.publicity.taskSupervise.service.OmsPubTaskSuperviseService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OmsGetTaskQueryServiceImpl implements OmsGetTaskQueryService {

    @Autowired
    private OmsGetTaskQueryMapper omsGetTaskQueryMapper;

    @Autowired
    private OmsPubTaskSuperviseService omsPubTaskSuperviseService;
    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskInfo>
     * @Date: 2020/8/18
     */
    @Override
    public PageBean<CerGetTaskInfo> selectGetCer(PageBean pageBean,CerGetTaskQueryParam cerGetTaskQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageNum());
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
            if("1".equals(sendNoticeContentParam.getGetStatusName()))
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
