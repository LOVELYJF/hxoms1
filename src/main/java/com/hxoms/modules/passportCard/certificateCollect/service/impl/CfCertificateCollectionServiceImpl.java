package com.hxoms.modules.passportCard.certificateCollect.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.keySupervision.suspendApproval.mapper.OmsSupSuspendUnitMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.certificateCollect.mapper.CfCertificateCollectionMapper;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionRequestService;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import com.hxoms.modules.publicity.taskSupervise.service.OmsPubTaskSuperviseService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CfCertificateCollectionServiceImpl extends ServiceImpl<CfCertificateCollectionMapper,CfCertificateCollection> implements CfCertificateCollectionService {

    @Autowired
    private CfCertificateCollectionMapper cfCertificateCollectionMapper;

    @Autowired
    private CfCertificateCollectionService cfCertificateCollectionService;

    @Autowired
    private CfCertificateCollectionRequestService CfCertificateCollectionRequestService;

    @Autowired
    private OmsSupSuspendUnitMapper omsSupSuspendUnitMapper;

    @Autowired
    private OmsPubTaskSuperviseService omsPubTaskSuperviseService;
    /**
     * @Desc: 生成催缴任务
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: void
     * @Date: 2020/8/11
     */
    public void createCjTask(List<CfCertificateCollection> cfCertificateCollectionList){
        if(cfCertificateCollectionList==null||cfCertificateCollectionList.size()==0)
            throw new CustomMessageException("参数为空，请核实！");
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        List<CfCertificateCollection> cfCerList=new ArrayList<>();
        for (CfCertificateCollection cfCertificateCollection : cfCertificateCollectionList) {
            cfCertificateCollection.setId(UUIDGenerator.getPrimaryKey());
            //0:登记备案,1:因私出国(境),2:证照借出,3:撤销出国申请
            if("0".equals(cfCertificateCollection.getDataSource())){
                Date date = new Date();
                cfCertificateCollection.setHappenDate(date);
                cfCertificateCollection.setReturnDate(PubUtils.calDate(date,10));
            }
            //0:解除,1;已上缴,2:未上缴
            cfCertificateCollection.setCjStatus("2");
            cfCertificateCollection.setCreatetime(new Date());
            cfCertificateCollection.setCreator(userInfo.getId());
            cfCerList.add(cfCertificateCollection);
            if(cfCerList.size()/1000==0){
                if(!saveBatch(cfCerList,cfCerList.size()))
                    new CustomMessageException("生成催缴任务失败！");
                cfCerList.clear();
            }
        }
        if(cfCerList.size()>0)
            if(!saveBatch(cfCerList,cfCerList.size()))
                new CustomMessageException("生成催缴任务失败！");
    }


    /**
     * @param pageBean
     * @Desc: 查询证照催缴
     * @Author: wangyunquan
     * @Param: [pageBean, cfCertificateCjQuery]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjInfo>
     * @Date: 2020/8/12
     */
    @Override
    public PageBean<CfCertificateCjInfo> selectCerCjApply(PageBean pageBean, CfCertificateCjQueryParam cfCertificateCjQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CfCertificateCjInfo> pageInfo=new PageInfo<CfCertificateCjInfo>(cfCertificateCollectionMapper.selectCerCjApply(cfCertificateCjQueryParam));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 查询催缴机构单位
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.OrganUnit>
     * @Date: 2020/8/12
     */
    @Override
    public List<OrganUnit> selectOrganUnit() {
        return cfCertificateCollectionMapper.selectOrganUnit();
    }

    /**
     * @Desc: 通过单位查询催缴人员
     * @Author: wangyunquan
     * @Param: [rfB0000]
     * @Return: com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjByPhone
     * @Date: 2020/8/12
     */
    @Override
    public CfCertificateCjByPhone selectCerCjInfoByOrgan(String rfB0000) {
        if(StringUtils.isBlank(rfB0000))
            throw new CustomMessageException("请求参数不正确，请核实！");
        List<HandlerInfo> handlerList=cfCertificateCollectionMapper.selectHandlerByOrgan(rfB0000);
        if(handlerList==null||handlerList.size()==0)
            throw new CustomMessageException("该机构下无经办人，请核实！");
        List<CfCertificatePhoneCjInfo> cfCertificatePhoneCjInfoList=cfCertificateCollectionMapper.selectCerCjInfoByOrgan(rfB0000);
        CfCertificateCjByPhone cfCertificateCjByPhone=new CfCertificateCjByPhone();
        cfCertificateCjByPhone.setHandlerList(handlerList);
        cfCertificateCjByPhone.setCfCertificatePhoneCjInfoList(cfCertificatePhoneCjInfoList);
        return cfCertificateCjByPhone;
    }

    /**
     * @Desc: 获取电话催缴内容
     * @Author: wangyunquan
     * @Param: [cjContentParamList]
     * @Return: com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.PhoneContent
     * @Date: 2020/9/1
     */
    @Override
    public PhoneContent createPhoneContent(List<CjContentParam> cjContentParamList) {
        PhoneContent phoneContent=new PhoneContent();
        phoneContent.setContent(createCjcontent(cjContentParamList));
        return phoneContent;
    }


    /**
     * @Desc: 保存催缴结果
     * @Author: wangyunquan
     * @Param: [cerCollectionRequestExList]
     * @Return: void
     * @Date: 2020/8/12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCerCjResult(List<CfCertificateCollectionRequestEx> cerCollectionRequestExList) {
        if(cerCollectionRequestExList==null||cerCollectionRequestExList.size()==0)
            throw new CustomMessageException("请求参数不正确，请核实！");
        List<CfCertificateCollectionRequest> cfCertificateCollectionRequestList=new ArrayList<>();
        List<CfCertificateCollection> cfCertificateCollectionList=new ArrayList<>();
        Date date=new Date();
        for (CfCertificateCollectionRequestEx cfCertificateCollectionRequestEx : cerCollectionRequestExList) {

            CfCertificateCollectionRequest cfCertificateCollectionRequest=new CfCertificateCollectionRequest();
            BeanUtils.copyProperties(cfCertificateCollectionRequestEx,cfCertificateCollectionRequest);
            cfCertificateCollectionRequest.setId(UUIDGenerator.getPrimaryKey());
            cfCertificateCollectionRequestList.add(cfCertificateCollectionRequest);

            CfCertificateCollection cfCertificateCollection=new CfCertificateCollection();
            cfCertificateCollection.setId(cfCertificateCollectionRequestEx.getCerCjId());
            cfCertificateCollection.setCjWay(cfCertificateCollectionRequestEx.getCjWay());
            String allCjResult = cfCertificateCollectionRequestEx.getAllCjResult();
            StringBuffer stringBuffer=new StringBuffer();
            if(!StringUtils.isBlank(allCjResult)){
                stringBuffer.append(allCjResult);
                stringBuffer.append("\r\n");
            }
            String cjWay = "0".equals(cfCertificateCollectionRequestEx.getCjWay()) ? "电话催缴：" : "1".equals(cfCertificateCollectionRequestEx.getCjWay()) ? "短信催缴：" : cfCertificateCollectionRequestEx.getCjWay();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            if(!StringUtils.isBlank(cfCertificateCollectionRequestEx.getCjResult()))
                cfCertificateCollection.setCjResult(stringBuffer.append(simpleDateFormat.format(date)).append(cfCertificateCollectionRequestEx.getCjPerson()).append(cjWay).append(cfCertificateCollectionRequestEx.getCjResult()).toString());
            cfCertificateCollection.setUpdator(cfCertificateCollectionRequestEx.getCjPerson());
            cfCertificateCollection.setUpdatetime(cfCertificateCollectionRequestEx.getCjTime());
            cfCertificateCollectionList.add(cfCertificateCollection);
        }
        if(!CfCertificateCollectionRequestService.saveBatch(cfCertificateCollectionRequestList))
            throw new CustomMessageException("催缴记录保存失败！");
        if(!cfCertificateCollectionService.updateBatchById(cfCertificateCollectionList))
            throw new CustomMessageException("催缴任务表更新失败！");
    }

    /**
     * @Desc: 保存催缴结果（短信催缴）
     * @Author: wangyunquan
     * @Param: [saveCjResultList]
     * @Return: void
     * @Date: 2020/9/4
     */
    @Override
    public void updateCerCjResult(List<SaveCjResult> saveCjResultList) {
        if(saveCjResultList.size()==0)
            throw new CustomMessageException("请求参数不正确，请核实！");
        List<CfCertificateCollection> cfCertificateCollectionList=new ArrayList<>();
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        for (SaveCjResult saveCjResult : saveCjResultList) {
            if(StringUtils.isBlank(saveCjResult.getCjResultAdd()))
                throw new CustomMessageException("未填写催缴结果，请核实！");
            CfCertificateCollection cfCertificateCollection=new CfCertificateCollection();
            cfCertificateCollection.setId(saveCjResult.getId());
            StringBuffer stringBuffer=new StringBuffer();
            if(!StringUtils.isBlank(saveCjResult.getCjResult())){
                stringBuffer.append(saveCjResult.getCjResult());
                stringBuffer.append("\r\n");
            }
            cfCertificateCollection.setCjResult(stringBuffer.append(simpleDateFormat.format(date)).append(saveCjResult.getUserName()).append("短信催缴：").append(saveCjResult.getCjResultAdd()).toString());
            cfCertificateCollection.setUpdator(saveCjResult.getUserId());
            cfCertificateCollection.setUpdatetime(date);
            cfCertificateCollectionList.add(cfCertificateCollection);
        }
        if(!cfCertificateCollectionService.updateBatchById(cfCertificateCollectionList))
            throw new CustomMessageException("保存催缴结果失败！");
    }

    /**
     * @Desc: 解除催缴
     * @Author: wangyunquan
     * @Param: [removeCjApplyList]
     * @Return: void
     * @Date: 2020/8/12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCerCjForRemove(List<RemoveCjApply> removeCjApplyList) {
        if(removeCjApplyList==null||removeCjApplyList.size()==0)
            throw new CustomMessageException("请求参数不正确，请核实！");
        List<CfCertificateCollection> cfCertificateCollectionList=new ArrayList<>();
        for (RemoveCjApply removeCjApply : removeCjApplyList) {
            CfCertificateCollection cfCertificateCollection=new CfCertificateCollection();
            if(!"2".equals(removeCjApply.getCjStatus()))
                throw new CustomMessageException("只能解除未上缴的任务，请核实！");
            BeanUtils.copyProperties(removeCjApply,cfCertificateCollection);
            cfCertificateCollectionList.add(cfCertificateCollection);
        }
        if(!cfCertificateCollectionService.updateBatchById(cfCertificateCollectionList))
            throw new CustomMessageException("更新数据失败！");
    }

    /**
     * @Desc: 获取导出短信催缴名单
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.ExportSMSCjInfo>
     * @Date: 2020/9/3
     */
    @Override
    public List<ExportSMSCjInfo> getExportSMSCjList(List<SMSCjInfo> smsCjInfoList) {
        List<ExportSMSCjInfo> exportSMSCjInfoList=new ArrayList<>();
        for (SMSCjInfo sMSCjInfo : smsCjInfoList) {
            ExportSMSCjInfo exportSMSCjInfo=new ExportSMSCjInfo();
            exportSMSCjInfo.setRfB0000(sMSCjInfo.getRfB0000());
            exportSMSCjInfo.setWorkUnit(sMSCjInfo.getWorkUnit());
            List<CjContentParam> cjContentParamList=new ArrayList<>();
            for (SMSCjPersonInfo smsCjPersonInfo : sMSCjInfo.getSmsCjPersonInfoList()) {
                CjContentParam cjContentParam=new CjContentParam();
                BeanUtils.copyProperties(smsCjPersonInfo,cjContentParam);
                cjContentParamList.add(cjContentParam);
            }
            exportSMSCjInfo.setSmsInfo(createCjcontent(cjContentParamList));
            List<HandlerInfo> handlerList=cfCertificateCollectionMapper.selectHandlerByOrgan(sMSCjInfo.getRfB0000());
            exportSMSCjInfo.setHandlerInfoList(handlerList);
            exportSMSCjInfoList.add(exportSMSCjInfo);
        }
        return exportSMSCjInfoList;
    }

    /**
     * @Desc: 导出短信催缴名单
     * @Author: wangyunquan
     * @Param: [exportRequestPara, outputStream]
     * @Return: void
     * @Date: 2020/9/3
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exportSMSCjList(ExportRequestPara exportRequestPara, ServletOutputStream outputStream) throws IOException {
        List<ExportSMSCjInfo> exportSMSCjInfoList = exportRequestPara.getExportSMSCjInfoList();
        if(exportSMSCjInfoList==null||exportSMSCjInfoList.size()==0)
            throw new CustomMessageException("无数据导出，请核实！");
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        List<List<String>> values=new LinkedList<>();
        Date date=new Date();
        for(ExportSMSCjInfo exportSMSCjInfo: exportSMSCjInfoList){
            //根据机构编码查询数据，并生成对应短信催缴记录。
            String rfB0000 = exportSMSCjInfo.getRfB0000();
            String smsInfo = exportSMSCjInfo.getSmsInfo();
            List<HandlerInfo> handlerInfoList = exportSMSCjInfo.getHandlerInfoList();
            HandlerInfo handlerInfo = handlerInfoList.get(0);
            String userId = handlerInfo.getUserId();
            String userName = handlerInfo.getUserName();
            String userMobile = handlerInfo.getUserMobile();
            List<CfCertificateCollectionRequest>  cfCertificateCollectionRequestList=cfCertificateCollectionMapper.selectCjInfoByOrgan(rfB0000);
            List<CfCertificateCollectionRequest>  cfCertificateCollectionRequestExis=new ArrayList<>();
            for (CfCertificateCollectionRequest cfCertificateCollectionRequest : cfCertificateCollectionRequestList) {
                //核实发送名单
                if(smsInfo.contains(cfCertificateCollectionRequest.getZjhm())){
                    cfCertificateCollectionRequest.setId(UUIDGenerator.getPrimaryKey());
                    //0:电话催缴,1:短信催缴
                    cfCertificateCollectionRequest.setCjWay("0");
                    cfCertificateCollectionRequest.setCjMessage(smsInfo);
                    cfCertificateCollectionRequest.setUserId(userId);
                    cfCertificateCollectionRequest.setName(userName);
                    cfCertificateCollectionRequest.setPhone(userMobile);
                    cfCertificateCollectionRequest.setCjPerson(userInfo.getId());
                    cfCertificateCollectionRequest.setCjTime(date);
                    cfCertificateCollectionRequestExis.add(cfCertificateCollectionRequest);
                }
            }
            //保存催缴记录
            CfCertificateCollectionRequestService.saveBatch(cfCertificateCollectionRequestExis);
            List<String> value=new LinkedList<>();
            value.add(exportSMSCjInfo.getWorkUnit());
            value.add(userName);
            value.add(userMobile);
            value.add(smsInfo);
            values.add(value);
        }
        HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook("短信催缴名单", exportRequestPara.getTitleList(), values, null);
        hssfWorkbook.write(outputStream);
    }

    /**
     * @Desc: 发送催缴通知
     * @Author: wangyunquan
     * @Param: [cjContentParam]
     * @Return: void
     * @Date: 2020/9/3
     */
    @Override
    public void sendCjNotice(List<SendNotice> sendNotices) {
        for (SendNotice sendNotice : sendNotices) {
            //获取催缴内容
            String CjNoticeContent=createCjcontent(sendNotice.getCjContentParamList());
            try {
                omsPubTaskSuperviseService.preAndRecMessage(sendNotice.getRfB0000(),CjNoticeContent,"6","1");
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomMessageException("发通知失败，原因："+e.getMessage());
            }
        }
    }

    /**
     * @Desc: 锁定单位出国
     * @Author: wangyunquan
     * @Param: [omsSupSuspendUnit]
     * @Return: void
     * @Date: 2020/8/13
     */
    @Override
    public void insertSuspendUnit(SupSuspendUnitApply supSuspendUnitApply) {
        if(supSuspendUnitApply==null)
            throw new CustomMessageException("请求参数不正确，请核实！");
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        OmsSupSuspendUnit omsSupSuspendUnit=new OmsSupSuspendUnit();
        BeanUtils.copyProperties(supSuspendUnitApply,omsSupSuspendUnit);
        omsSupSuspendUnit.setId(UUIDGenerator.getPrimaryKey());
        omsSupSuspendUnit.setStatus("0");
        omsSupSuspendUnit.setCreateUser(userInfo.getId());
        omsSupSuspendUnit.setCreateTime(new Date());
        int result = omsSupSuspendUnitMapper.insert(omsSupSuspendUnit);
        if(result==0)
            throw new CustomMessageException("保存失败！");
    }

    /**
     * @Desc: 生成催缴内容
     * @Author: wangyunquan
     * @Param: [cjContentParamList]
     * @Return: java.lang.String
     * @Date: 2020/9/9
     */
    private String createCjcontent(List<CjContentParam> cjContentParamList) {
        return createCjcontent(cjContentParamList,false);
    }
    /**
     * @Desc: 生成催缴内容
     * @Author: wangyunquan
     * @Param: [cjContentParamList]
     * @Return: java.lang.String
     * @Date: 2020/9/3
     */
    private String createCjcontent(List<CjContentParam> cjContentParamList,boolean sendCjNotice) {
        StringBuffer allStr=new StringBuffer();
        //逾期未上缴
        Integer over=10000;
        Map<Integer,String> map=new HashedMap();
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd");
        for (CjContentParam cjContentParam : cjContentParamList) {
            int dateDiff= PubUtils.calDateDiff(formatter.format(cjContentParam.getReturnDate()),formatter.format(date));
            StringBuffer partStr=new StringBuffer();
            //张三的护照（E9435）
            partStr.append(cjContentParam.getName()).append("的").append(cjContentParam.getZjlxName()).append("（"+ cjContentParam.getZjhm()+"）");
            String value = map.get(dateDiff);
            Integer key=0;
            if(dateDiff>=0){
                key=dateDiff;
            }else{
                //逾期未上缴
                key=over;
            }
            if(map.containsKey(key)){
                map.put(key,value+"、"+partStr.toString());
            }else{
                map.put(key,partStr.toString());
            }
        }
        if(!map.isEmpty()){
            Map<Integer, String> sortMap = new TreeMap<Integer, String>(
                    new Comparator<Integer>() {
                        @Override
                        public int compare(Integer o1, Integer o2) {
                            return o1.compareTo(o2);
                        }

                    });
            sortMap.putAll(map);
            //贵单位张三的护照（E9435）还有3天逾期，王五的护照（E9545）、马六的台湾通行证（38445）逾期未上缴，请尽快上缴。
            allStr.append("贵单位");
            for (Integer integer : sortMap.keySet()) {
                if(integer!=over){
                    allStr.append(sortMap.get(integer)).append("还有"+integer).append("天逾期，");
                }else{
                    allStr.append(sortMap.get(over)).append("逾期未上缴，");
                }
            }
            allStr.append("请尽快上缴。");
            if(sendCjNotice)
                allStr.append("如果不及时上缴，会暂停出国境申报业务。");
        }
        return allStr.toString();
    }

    @Override
    public List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection) {
        return cfCertificateCollectionMapper.selectByCfcertificateCollection(cfCertificateCollection);
    }

}
