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
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            //0:登记备案,1:因私出国(境),2:证照借出,3:撤销出国申请
            if("0".equals(cfCertificateCollection.getCjWay())){
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
     * @Param: [phoneContentParamList]
     * @Return: java.lang.String
     * @Date: 2020/9/1
     */
    @Override
    public String createPhoneContent(List<PhoneContentParam> phoneContentParamList) {
        StringBuffer allStr=new StringBuffer();
        //逾期未上缴
        Integer over=10000;
        Map<Integer,String> map=new HashedMap();
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd");
        for (PhoneContentParam phoneContentParam : phoneContentParamList) {
            int dateDiff=PubUtils.calDateDiff(formatter.format(phoneContentParam.getReturnDate()),formatter.format(date));
            StringBuffer partStr=new StringBuffer();
            partStr.append(phoneContentParam.getName()).append("的").append(phoneContentParam.getZjlxName()).append("（"+phoneContentParam.getZjhm()+"）");
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
        }
        return allStr.toString();
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
            cfCertificateCollection.setId(cfCertificateCollectionRequestEx.getCerId());
            cfCertificateCollection.setCjWay(cfCertificateCollectionRequestEx.getCjWay());
            String allCjResult = cfCertificateCollectionRequestEx.getAllCjResult();
            StringBuffer stringBuffer=new StringBuffer();
            if(!StringUtils.isBlank(allCjResult))
                stringBuffer.append("\r\n");
            String cjWay = "0".equals(cfCertificateCollectionRequestEx.getCjWay()) ? "电话催缴：" : "1".equals(cfCertificateCollectionRequestEx.getCjWay()) ? "短信催缴：" : cfCertificateCollectionRequestEx.getCjWay();
            cfCertificateCollection.setCjResult(stringBuffer.append(cfCertificateCollectionRequestEx.getCjTime()).append(cfCertificateCollectionRequestEx.getCjPerson()).append(cjWay).append(cfCertificateCollectionRequestEx.getCjResult()).toString());
            cfCertificateCollection.setUpdator(cfCertificateCollectionRequestEx.getCjPerson());
            cfCertificateCollection.setUpdatetime(cfCertificateCollectionRequestEx.getCjTime());
            cfCertificateCollectionList.add(cfCertificateCollection);
        }
        if(!CfCertificateCollectionRequestService.saveBatch(cfCertificateCollectionRequestList,cerCollectionRequestExList.size()))
            throw new CustomMessageException("催缴保存失败！");
        if(cfCertificateCollectionService.updateBatchById(cfCertificateCollectionList))
            throw new CustomMessageException("催缴任务表更新失败！");
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
            BeanUtils.copyProperties(removeCjApply,cfCertificateCollection);
            cfCertificateCollectionList.add(cfCertificateCollection);
        }
        if(!cfCertificateCollectionService.updateBatchById(cfCertificateCollectionList))
            throw new CustomMessageException("更新数据失败！");
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

    @Override
    public List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection) {
        return cfCertificateCollectionMapper.selectByCfcertificateCollection(cfCertificateCollection);
    }

}
