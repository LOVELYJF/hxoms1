package com.hxoms.modules.omsregcadre.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.mapper.*;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OmsRegProcpersonInfoServiceImpl extends ServiceImpl<OmsRegProcpersonInfoMapper,OmsRegProcpersonInfo> implements  OmsRegProcpersonInfoService {


    @Autowired
    private Environment environment;
    @Autowired
    private A01EntityMapper a01Mapper;
    @Autowired
    private A30Mapper a30Mapper;
    @Autowired
    private OmsRegProcbatchMapper regProcbatchMapper;
    @Autowired
    private OmsRegRevokeApplyMapper revokeApplyMapper;
    @Autowired
    private OmsRegYearcheckInfoMapper yearcheckInfoMapper;

    /**
     * 初始化登记备案信息
     * @param page
     * @param msRegProcpersonInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public IPage<OmsRegProcpersonInfo> getInitialReginfo(Page page,OmsRegProcpersonInfo msRegProcpersonInfo) throws ParseException {
        //查询数据来源为干部的登记备案人员信息
        List<String> a0100str = baseMapper.selectRegProcpersonInfo();
        IPage<OmsRegProcpersonInfo> mepinfoList = null;
        //查询干部信息总库干部信息
        List<A01Entity> a01list = a01Mapper.selectList(null);
        int con =0;
        OmsRegProcpersonInfo orpInfo = null;
        if(a01list != null && a0100str != null){
            for (A01Entity a01 : a01list) {
                orpInfo = initData(a01);
                //登记备案信息中存在此数据，则更新其对应信息
                 if(a0100str.contains(a01)){
                     QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
                     queryWrapper.eq("A0100", a01.getA0100());
                     //备案状态
                     orpInfo.setRfStatus("0");
                     //入库状态
                     orpInfo.setInboundFlag("I");
                     //验收状态
                     orpInfo.setCheckStatus("0");
                     orpInfo.setModifyTime(new Date());
                    con = baseMapper.update(orpInfo,queryWrapper);
                 }else{//不包含直接新增
                     //备案状态
                     orpInfo.setRfStatus("0");
                     //入库状态
                     orpInfo.setInboundFlag("U");
                     //验收状态
                     orpInfo.setCheckStatus("0");
                     orpInfo.setCreateTime(new Date());
                    con = baseMapper.insert(orpInfo);
                 }
            }

        }else if(a01list!=null && a0100str==null){
            List<OmsRegProcpersonInfo> orpInfoList = new ArrayList();
            for (A01Entity a01 : a01list) {
                orpInfo = initData(a01);
            }
            orpInfoList.add(orpInfo);
            //批量添加的方法
            int count = 30;
            int n1 = orpInfoList.size() / count;
            int n2 = orpInfoList.size() % count;
            if (n2 > 0) {
                n1 = n1 + 1;
            }
            for (int j = 0; j < n1; j++) {
                if ((j + 1) * count > orpInfoList.size()) {
                    //批量保存
                    con = baseMapper.batchAddorpInfo(orpInfoList.subList(j * count, orpInfoList.size()));
                } else {
                    con = baseMapper.batchAddorpInfo(orpInfoList.subList(j * count, (j + 1) * count));
                }
            }
        }else {
            throw new CustomMessageException("当前无可初始化的干部数据");
        }
        if(con > 0 ){
            //查询登记备案备案状态为“未备案”和在职状态为“未匹配”的数据
            QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
            queryWrapper.eq("RF_B0000",msRegProcpersonInfo.getRfB0000());
            queryWrapper.eq("RF_STATUS","0");
            queryWrapper.eq("INCUMBENCY_STATUS","未匹配");
            //排序  姓 名 工作单位
            queryWrapper.orderByAsc("SURNAME","NAME","WORK_UNIT");

            mepinfoList = baseMapper.selectPage(page,queryWrapper);
        }
        return mepinfoList;
    }

    /**
     * 新增登记备案人员
     * @param orpInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Object insertRpinfo(OmsRegProcpersonInfo orpInfo) {
        String id = UUIDGenerator.getPrimaryKey();
        orpInfo.setId(id);
        orpInfo.setCreateTime(new Date());
        return baseMapper.insert(orpInfo);
    }

    /**
     * 更新登记备案人员信息
     * @param orpInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Object updateRpinfo(OmsRegProcpersonInfo orpInfo) {
        if (StringUtils.isBlank(orpInfo.getId()) || StringUtils.isBlank(orpInfo.getA0100())){
            throw new CustomMessageException("缺少必要参数");
        }else{
            orpInfo.setInboundFlag("I");
            orpInfo.setModifyTime(new Date());
            return baseMapper.updateById(orpInfo);
        }
    }

    /**
     * 删除登记备案人员信息
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Object deleteRpinfo(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("缺少必要参数id");
        }else {
            return baseMapper.deleteById(id);
        }
    }

    /**
     *  导入公安数据
     * @param list
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public List<OmsRegProcpersonInfo> insertOmsRegGongAn(List<OmsRegProcpersonInfo> list) {
        List<OmsRegProcpersonInfo> mepinfoList = null;
        int con = 0;
        //批量添加的方法
        int count = 30;
        int n1 = list.size() / count;
        int n2 = list.size() % count;
        if (n2 > 0) {
            n1 = n1 + 1;
        }
        for (int j = 0; j < n1; j++) {
            if ((j + 1) * count > list.size()) {
                //批量保存
                con = baseMapper.batchAddorpInfo(list.subList(j * count, list.size()));
            } else {
                con = baseMapper.batchAddorpInfo(list.subList(j * count, (j + 1) * count));
            }
        }
        if (con > 0){
            mepinfoList = baseMapper.selectList(null);
        }
        return mepinfoList;
    }


    /**
     * 查询当前存在的公安数据记录
     * @param dataType
     * @return
     */
    @Override
    public int selectCountGongAn(String dataType) {
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        queryWrapper.eq("DATA_TYPE",dataType);
        return baseMapper.selectCount(queryWrapper);
    }


    /**
     * 合并干部和公安数据
     * @param idStr
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Object mergeDataGBandGA(String idStr) {
        int con =0;
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        queryWrapper.in("ID",idStr.split(","));
        queryWrapper.orderByAsc("DATA_TYPE");
        List<OmsRegProcpersonInfo> omsregList = baseMapper.selectList(queryWrapper);
        if (omsregList !=null && omsregList.size() >0){
            OmsRegProcpersonInfo gbData = omsregList.get(0);
            OmsRegProcpersonInfo gaData = omsregList.get(1);
            //身份账号与名称一致
            if (gbData.getIdnumber().equals(gaData.getIdnumber())
                    && gbData.getName() .equals(gaData.getName())){
                //更新干部相关信息从公安数据中维护
                OmsRegProcpersonInfo omsreginfo = new OmsRegProcpersonInfo();
                omsreginfo.setId(gbData.getId());
                //户口所在地
                omsreginfo.setRegisteResidence(gaData.getRegisteResidence());
                //入库标识  新增U  修改I  撤消D
                omsreginfo.setInboundFlag("I");
                //备案状态  0未备案，1已备案，2已确认
                omsreginfo.setRfStatus("1");
                //验收状态  1已验收，0待验收
                omsreginfo.setCheckStatus("1");
                omsreginfo.setModifyTime(new Date());
                con = baseMapper.updateById(omsreginfo);
                if (con > 0){
                    baseMapper.deleteById(gaData.getId());
                }
            }else{
                throw new CustomMessageException("当前选择数据姓名、身份证号不一致，请进行人工核对后再合并。");
            }
        }
        return con;
    }

    /**
     * 查询省管干部登记备案信息
     * （打开登记备案页面）
     * @param page
     * @param msRegProcpersonInfo
     * @return
     */
    @Override
    public IPage<OmsRegProcpersonInfo> getProvinceCadreRegInfo(Page page, OmsRegProcpersonInfo msRegProcpersonInfo) {
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        if (!StringUtils.isBlank(msRegProcpersonInfo.getSecretLevel())){
            //查询条件涉密等级
            queryWrapper.eq("SECRET_LEVEL",msRegProcpersonInfo.getSecretLevel());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getIdentityCode())){
            //身份情况
            queryWrapper.eq("IDENTITY_CODE",msRegProcpersonInfo.getIdentityCode());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getIncumbencyStatus())){
            //在职状态
            queryWrapper.eq("INCUMBENCY_STATUS",msRegProcpersonInfo.getIncumbencyStatus());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getInboundFlag())){
            //入库状态
            queryWrapper.eq("INBOUND_FLAG",msRegProcpersonInfo.getInboundFlag());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getRfStatus())){
            //备案状态
            queryWrapper.eq("RF_STATUS",msRegProcpersonInfo.getRfStatus());
        }
        //验收状态为待验收
        queryWrapper.eq("CHECK_STATUS","0");
        //排序  姓 名 工作单位
        queryWrapper.orderByAsc("SURNAME","NAME","WORK_UNIT");
        return baseMapper.selectPage(page,queryWrapper);
    }

    /**
     * 提取备案
     * @return
     * @throws ParseException
     */
    @Override
    public Object extractRegPersonInfo() throws ParseException {
        int con=0;
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        queryWrapper.eq("DATA_TYPE","1");

        //数据类型 为干部的省管干部登记备案查询
        List<OmsRegProcpersonInfo> reginfolist = baseMapper.selectList(queryWrapper);
        //干综档案人员基本信息查询
        List<A01Entity> a01list = a01Mapper.selectList(null);
        List<String> a0100s =null;
        for (int i=0;i<reginfolist.size();i++){
            a0100s.add(reginfolist.get(i).getA0100());
        }
        OmsRegProcpersonInfo orpInfo = null;
        for (A01Entity a01:a01list){
            if (a0100s.contains(a01.getA0100())){
                //获取当前a0100对应的下标
                int index = a0100s.indexOf(a01.getA0100());
                OmsRegProcpersonInfo omsreginfo = reginfolist.get(index);
                QueryWrapper<A30> queryWrapper1 = new QueryWrapper<A30>();
                queryWrapper1.eq("A0100",omsreginfo.getA0100());
                //退出方式
                A30 a30 = a30Mapper.selectOne(queryWrapper1);
                //在职状态变更为辞职、免职、退休、开除、去世、挂职等
                if (!StringUtils.isBlank(a30.getA3001())){
                    //撤销登记备案申请表
                    OmsRegRevokeApply applyinfo = new OmsRegRevokeApply();

                    //将日期格式化
                    SimpleDateFormat sd = new SimpleDateFormat("yyyyMM");
                    //脱密开始日期
                    String secretStartDate = sd.format(omsreginfo.getDecryptStartdate());
                    //脱密结束日期
                    String secretEndDate = sd.format(omsreginfo.getDecryptEnddate());
                    //计算两个日期相差年数
                    int year = OmsRegInitUtil.yearDateDiff(secretStartDate,secretEndDate);

                    if (a30.getA3001().equals("辞职") || a30.getA3001().equals("开除")){
                        //将满足要求的人员放到撤销登记备案申请表
                        if (year > OmsRegInitUtil.czyear){
                            //复制登记备案相同字段的数据到撤销登记申请表
                            BeanUtils.copyProperties(omsreginfo, applyinfo);
                            applyinfo.setId(UUIDGenerator.getPrimaryKey());
                            //退出时间
                            applyinfo.setExitDate(a30.getA3004());
                            applyinfo.setCreateDate(new Date());
                            applyinfo.setCreateUser("");
                            con = revokeApplyMapper.insert(applyinfo);
                        }
                    }else if(a30.getA3001().equals("退休")){
                        //将满足要求的人员放到撤销登记备案申请表
                        if (year > OmsRegInitUtil.txyear){
                            //复制登记备案相同字段的数据到撤销登记申请表
                            BeanUtils.copyProperties(omsreginfo, applyinfo);
                            applyinfo.setId(UUIDGenerator.getPrimaryKey());
                            applyinfo.setExitDate(a30.getA3004());
                            applyinfo.setCreateDate(new Date());
                            applyinfo.setCreateUser("");
                            con = revokeApplyMapper.insert(applyinfo);
                        }
                    }else if(a30.getA3001().equals("到琼挂职")){
                        //将满足要求的人员放到撤销登记备案申请表
                        if (year > 0){
                            //复制登记备案相同字段的数据到撤销登记申请表
                            BeanUtils.copyProperties(omsreginfo, applyinfo);
                            applyinfo.setId(UUIDGenerator.getPrimaryKey());
                            applyinfo.setExitDate(a30.getA3004());
                            applyinfo.setCreateDate(new Date());
                            applyinfo.setCreateUser("");
                            con = revokeApplyMapper.insert(applyinfo);
                        }
                    }else if(a30.getA3001().equals("去世")){
                        //去世的，立即启动撤销备案及
                        omsreginfo.setInboundFlag("D");
                        //备案状态  0未备案，1已备案，2已确认
                        omsreginfo.setRfStatus("1");
                        //验收状态  1已验收，0待验收
                        omsreginfo.setCheckStatus("1");
                        omsreginfo.setModifyTime(new Date());
                        baseMapper.updateById(omsreginfo);
                        //TODO:归还证照程序

                    }

                }else{
                    //如果职务不变不做任何操作，职务改变，需更新职务，并更新入库状态
                    String a0221 =  environment.getProperty("postcode." + a01.getA0221());
                    String zhiwu =  environment.getProperty("post." + a0221);
                    if (!a0221.equals(omsreginfo.getPostCode())){
                        omsreginfo.setPostCode(a0221);
                        omsreginfo.setPost(zhiwu);
                        //入库标识  新增U  修改I  撤消D
                        omsreginfo.setInboundFlag("I");
                        //备案状态  0未备案，1已备案，2已确认
                        omsreginfo.setRfStatus("0");
                        //验收状态  1已验收，0待验收
                        omsreginfo.setCheckStatus("0");
                        con = baseMapper.updateById(omsreginfo);
                    }
                }

            }else{
                orpInfo = initData(a01);
                con = baseMapper.insert(orpInfo);
            }
        }

        return con;
    }

    /**
     * 登记备案数据浏览
     * @param page
     * @param msRegProcpersonInfo
     * @return
     */
    @Override
    public IPage<OmsRegProcpersonInfo> getRegPersonInfoList(Page page, OmsRegProcpersonInfo msRegProcpersonInfo) {
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        if (!StringUtils.isBlank(msRegProcpersonInfo.getSecretLevel())){
            //查询条件涉密等级
            queryWrapper.eq("SECRET_LEVEL",msRegProcpersonInfo.getSecretLevel());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getIdentityCode())){
            //身份情况
            queryWrapper.eq("IDENTITY_CODE",msRegProcpersonInfo.getIdentityCode());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getIncumbencyStatus())){
            //在职状态
            queryWrapper.eq("INCUMBENCY_STATUS",msRegProcpersonInfo.getIncumbencyStatus());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getInboundFlag())){
            //入库状态
            queryWrapper.eq("INBOUND_FLAG",msRegProcpersonInfo.getInboundFlag());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getRfStatus())){
            //备案状态
            queryWrapper.eq("RF_STATUS",msRegProcpersonInfo.getRfStatus());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getCheckStatus())){
            //验收状态
            queryWrapper.eq("CHECK_STATUS",msRegProcpersonInfo.getCheckStatus());
        }
        if (!StringUtils.isBlank(msRegProcpersonInfo.getPost())){
            //职务职称
            queryWrapper.eq("POST",msRegProcpersonInfo.getPost());
        }
        //排序  姓 名 工作单位
        queryWrapper.orderByAsc("SURNAME","NAME","WORK_UNIT");
        return baseMapper.selectPage(page,queryWrapper);

    }


    /**
     * 根据批次号查询对应的人员信息
     * @param batchNo
     * @return
     */
    @Override
    public List<OmsRegProcpersonInfo> selectPersonByBatchNo(String batchNo) {
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        queryWrapper.eq("BATCH_NO",batchNo);
        return baseMapper.selectList(queryWrapper);
    }



    /**
     * 登记备案大检查，上传登记备案记录 并查询未备案列表人员
     * @param list
     * @return
     */
    @Override
    public int checkUploadRegRecord(List<OmsRegProcpersonInfo> list) {
        QueryWrapper<OmsRegYearcheckInfo> yearcheckInfoWrapper = new QueryWrapper<OmsRegYearcheckInfo>();
        yearcheckInfoWrapper.eq("RF_STATUS","0");
        yearcheckInfoWrapper.eq("CREATE_DATE",new Date("yyyy"));
        List<OmsRegYearcheckInfo> yearoldlist = yearcheckInfoMapper.selectList(yearcheckInfoWrapper);
        if (yearoldlist!=null){
            yearcheckInfoMapper.delete(null);
        }
        int con =0;
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        queryWrapper.eq("DATA_TYPE","1");
        //查询登记备案干部的数据
        List<OmsRegProcpersonInfo> omsregList = baseMapper.selectList(queryWrapper);
        //登记备案为干部的身份证号集合
        List<String> gbidnumbers =null;
        for (int i=0;i<omsregList.size();i++){
            gbidnumbers.add(omsregList.get(i).getIdnumber());
        }
        //出国境导入数据集合
        for (OmsRegProcpersonInfo cgjdata :list){
            //登记备案中包含出入境
            if (gbidnumbers.contains(cgjdata.getIdnumber())){
                continue;
            }else{
                //登记备案不中包含出入境
                OmsRegYearcheckInfo yearcheckInfo = new OmsRegYearcheckInfo();
                BeanUtils.copyProperties(cgjdata, yearcheckInfo);
                yearcheckInfo.setCreateDate(new Date("yyyy"));
                yearcheckInfo.setCreateUser("");
                con = yearcheckInfoMapper.insert(yearcheckInfo);
            }
        }

        //出入境（公安）导入数据身份证号集合
        List<String> gaidnumbers =null;
        for (int i=0;i<list.size();i++){
            gaidnumbers.add(list.get(i).getIdnumber());
        }
        //登记备案干部集合
        for (OmsRegProcpersonInfo gbdata :omsregList){
            //出入境包含登记备案
            if (gbidnumbers.contains(gbdata.getIdnumber())){
                continue;
            }else{
                //出入境不包含登记备案
                OmsRegYearcheckInfo yearcheckInfo = new OmsRegYearcheckInfo();
                BeanUtils.copyProperties(gbdata, yearcheckInfo);
                yearcheckInfo.setCreateDate(new Date("yyyy"));
                yearcheckInfo.setCreateUser("");
                con = yearcheckInfoMapper.insert(yearcheckInfo);
            }
        }
        return con;
    }

    /**
     * 查询年度列表
      * @param list
     * @return
     */
    @Override
    public List<OmsRegYearcheckInfo> queryYearList(List<OmsRegProcpersonInfo> list) {
        QueryWrapper<OmsRegYearcheckInfo> yearcheckWrapper = new QueryWrapper<OmsRegYearcheckInfo>();
        yearcheckWrapper.groupBy("CREATE_DATE");
        return yearcheckInfoMapper.selectList(yearcheckWrapper);
    }

    /**
     * 查询大检查中未备案人员列表（可根据年度进行查询）
     * @param year
     * @return
     */
    @Override
    public List<OmsRegYearcheckInfo> queryYearCheckList(Date year) {
        QueryWrapper<OmsRegYearcheckInfo> yearcheckWrapper = new QueryWrapper<OmsRegYearcheckInfo>();
        if (year!=null){
            yearcheckWrapper.eq("CREATE_DATE",year);
        }
        return yearcheckInfoMapper.selectList(yearcheckWrapper);
    }

    @Override
    public Object selectPersonAndAllowRevoke(OmsRegProcpersonInfo msRegProcpersonInfo) {
        //查询符合撤销登记备案的人员信息（1.免职人员 2.脱密结束时间 < 当前时间）
        return baseMapper.selectPersonAndAllowRevoke(msRegProcpersonInfo);
    }

    /**
     * 根据人员编号查询对应备案申请
     * @param a0100
     * @return
     */
    @Override
    public Object selectInfoByA0100(String a0100) {
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        queryWrapper.eq("A0100",a0100);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 初始化信息
     * @param a01
     * @return
     */
    private OmsRegProcpersonInfo initData(A01Entity a01) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        OmsRegProcpersonInfo orpInfo = new OmsRegProcpersonInfo();
        //根据身份证号截取对应的出生日期
        String birthDay = OmsRegInitUtil.getBirthByIdNumber(a01.getA0184());
        //是否复姓。拆除
        boolean isCompoundSurname = OmsRegInitUtil.isCompoundSurname(a01.getA0101());
        //是复姓
        if (isCompoundSurname){
            //姓
            orpInfo.setSurname(a01.getA0101().substring(0,2));
            //名
            orpInfo.setName(a01.getA0101().substring(2,a01.getA0101().length()));
        }else{
            orpInfo.setSurname(a01.getA0101().substring(0,1));
            orpInfo.setName(a01.getA0101().substring(1,a01.getA0101().length()));
        }
        orpInfo.setA0100(a01.getA0100());
        //数据类型  1.干部    2 公安
        orpInfo.setDataType("1");
        //备案状态
        orpInfo.setRfStatus("0");
        //入库状态
        orpInfo.setInboundFlag("U");
        //验收状态
        orpInfo.setCheckStatus("0");
        //出生日期
        orpInfo.setBirthDate(simpleDateFormat.parse(birthDay));
        //拼音简称
        orpInfo.setPy("");
        //身份证号
        orpInfo.setIdnumber(a01.getA0184());
        //工作单位
        orpInfo.setWorkUnit(a01.getA0192a());
        //性别
        orpInfo.setSex(a01.getA0104());
        //政治面貌  TODO:待定
        orpInfo.setPoliticalAffi(a01.getA0141());
        //TODO:待定
        orpInfo.setPost("");
        //人事主管单位
        orpInfo.setPersonManager((a01.getA0195()));
        orpInfo.setCreateTime(new Date());
        return orpInfo;
    }

}
