package com.hxoms.modules.omsregcadre.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.tree.TreeUtil;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.keySupervision.majorLeader.entity.A02;
import com.hxoms.modules.keySupervision.majorLeader.mapper.A02Mapper;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegYearCheckIPagParam;
import com.hxoms.modules.omsregcadre.mapper.*;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsRegProcpersonInfoServiceImpl extends ServiceImpl<OmsRegProcpersoninfoMapper, OmsRegProcpersoninfo> implements  OmsRegProcpersonInfoService {



    @Autowired
    private Environment environment;
    @Autowired
    private A01EntityMapper a01Mapper;
    @Autowired
    private A30Mapper a30Mapper;
    @Autowired
    private A02Mapper a02Mapper;
    @Autowired
    private OmsRegProcbatchPersonMapper regProcbatchPersonMapper;
    @Autowired
    private OmsRegRevokeApplyMapper revokeApplyMapper;
    @Autowired
    private OmsRegYearcheckinfoMapper yearcheckInfoMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersonInfoMapper;
    @Autowired
    private OmsBaseinfoConfigMapper omsBaseinfoConfigMapper;
    @Autowired
    private SysDictItemMapper sysDictItemMapper;


    /**
     * 初始化登记备案信息
     * @param personInfoIPagParam
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public PageInfo<OmsRegProcpersoninfo> getInitialReginfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam) throws ParseException {

        //查询数据来源为干部的登记备案人员信息ao
        List<String> a0100str = baseMapper.selectRegProcpersonInfo();
        List<OmsRegProcpersoninfo> mepinfoList = null;
        //查询干部信息总库干部信息
        QueryWrapper<A01> a01qw = new QueryWrapper<A01>();
        a01qw.eq("A0165","02");
        a01qw.eq("is_deleted","0");
        List<A01> a01list = a01Mapper.selectList(a01qw);
        int con =0;
        OmsRegProcpersoninfo orpInfo = null;
        if(a01list != null && a0100str != null){
            for (A01 a01 : a01list) {
                orpInfo = initData(a01);
                //登记备案信息中存在此数据，则更新其对应信息
                 if(a0100str.contains(a01.getA0100())){
                     QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
                     queryWrapper.eq("A0100", a01.getA0100());
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
            List<OmsRegProcpersoninfo> orpInfoList = new ArrayList();
            for (A01 a01 : a01list) {
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
        PageInfo<OmsRegProcpersoninfo> pageInfo = null;
        //分页
        PageUtil.pageHelp(personInfoIPagParam.getPageNum(), personInfoIPagParam.getPageSize());
        if(con > 0 ){

            OmsRegProcpersoninfo info = new OmsRegProcpersoninfo();
            info.setRfB0000(personInfoIPagParam.getRfB0000());
            mepinfoList = baseMapper.selectProcpersoninfoList(info);
            //返回数据
            pageInfo = new PageInfo(mepinfoList);
        }
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> selectStatisticsCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        //入库标识
        List<StatisticsCountVo> inboudFlaglist = baseMapper.selectInboudFlagCount();
        //identityCode 省管干部-1 科级以上公务员（企事业单位中层以上管理人员）-2  涉密人员-3 重要岗位人-4 重点监管人员-5 其他人员-9
        List<StatisticsCountVo> identityCodelist = baseMapper.selectIdentityCodeCount();
        //主要领导，裸官，核心涉密人员，重要涉密人员，锁定出国人员，纪委不回复，离琼挂职，到琼挂职
        List<StatisticsCountVo> allFlaglist = baseMapper.selectAllFlagCount();
        map.put("inboudFlaglist",inboudFlaglist);
        map.put("identityCodelist",identityCodelist);
        map.put("allFlaglist",allFlaglist);
        return map;
    }

    /**
     * 新增登记备案人员
     * @param orpInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Object insertRpinfo(OmsRegProcpersoninfo orpInfo) {
        orpInfo.setId(UUIDGenerator.getPrimaryKey());
        return baseMapper.insert(orpInfo);
    }

    /**
     * 更新登记备案人员信息
     * @param orpInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Object updateRpinfo(OmsRegProcpersoninfo orpInfo) {
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
    public int insertOmsRegGongAn(List<OmsRegProcpersoninfo> list) {
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
        return con;
    }

    @Override
    public List<OmsRegProcpersoninfo> selectMergeList() {
        //显示待备案干部数据 和 在职状态为“未匹配”的公安数据
        return  baseMapper.selectMergeList();
    }

    @Override
    public List<OmsRegProcpersoninfo> selectListById(String idStr) {
        List<OmsRegProcpersoninfo> list = new ArrayList<>();
        if (idStr!=null){
            String id = idStr.split(",").toString();
            list = baseMapper.selectListById(id);
        }else{
            list = baseMapper.selectListById(null);
        }
        return list;
    }

    @Override
    public int updateRegProcpersoninfo(String idStr) {
        List<OmsRegProcpersoninfo> list = new ArrayList<>();
        OmsRegProcpersoninfo info = new OmsRegProcpersoninfo();
        info.setRfStatus("1");
        int con = 0;
        if (idStr!=null){
            String id = idStr.split(",").toString();
            info.setId(id);
            con = baseMapper.updateRegProcpersoninfo(info);
        }else{
            con = baseMapper.updateRegProcpersoninfo(info);
        }
        return con;
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
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.in("ID",idStr.split(","));
        queryWrapper.orderByAsc("DATA_TYPE");
        List<OmsRegProcpersoninfo> omsregList = baseMapper.selectList(queryWrapper);
        if (omsregList !=null && omsregList.size() >0){
            OmsRegProcpersoninfo gbData = omsregList.get(0);
            OmsRegProcpersoninfo gaData = omsregList.get(1);
            //身份账号与名称一致
            if (gbData.getIdnumberGb().equals(gaData.getIdnumberGa()) && gbData.getName() .equals(gaData.getName())){
                //更新干部相关信息从公安数据中维护
                OmsRegProcpersoninfo omsreginfo = new OmsRegProcpersoninfo();
                omsreginfo.setId(gbData.getId());
                //入库标识  新增U  修改I  撤消D
                omsreginfo.setInboundFlag(gaData.getInboundFlag());
                //备案状态  0未备案，1已备案，2已确认
                omsreginfo.setRfStatus("1");
                //验收状态  1已验收，0待验收
                omsreginfo.setCheckStatus("1");
                omsreginfo.setModifyTime(new Date());
                //用公安数据的户口所在地
                omsreginfo.setRegisteResidenceCode(gaData.getRegisteResidenceCode());
                omsreginfo.setRegisteResidence(gaData.getRegisteResidence());
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
     * @param personInfoIPagParam
     * @return
     */
    @Override
    public PageInfo<OmsRegProcpersoninfo> getProvinceCadreRegInfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam) {
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        PageInfo<OmsRegProcpersoninfo> pageInfo = null;
        //分页
        PageUtil.pageHelp(personInfoIPagParam.getPageNum(), personInfoIPagParam.getPageSize());

        if (!StringUtils.isBlank(personInfoIPagParam.getSecretLevel())){
            //查询条件涉密等级
            queryWrapper.eq("SECRET_LEVEL",personInfoIPagParam.getSecretLevel());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getIdentityCode())){
            //身份情况
            queryWrapper.eq("IDENTITY_CODE",personInfoIPagParam.getIdentityCode());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getIncumbencyStatus())){
            //在职状态
            queryWrapper.eq("INCUMBENCY_STATUS",personInfoIPagParam.getIncumbencyStatus());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getInboundFlag())){
            //入库状态
            queryWrapper.eq("INBOUND_FLAG",personInfoIPagParam.getInboundFlag());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getRfStatus())){
            //备案状态
            queryWrapper.eq("RF_STATUS",personInfoIPagParam.getRfStatus());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getRfB0000())){
            //机构代码
            queryWrapper.like("RF_B0000",personInfoIPagParam.getRfB0000());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getCheckStatus())){
            //验收状态为待验收
            queryWrapper.eq("CHECK_STATUS","0");
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getDataType())){
            //数据类型为干部
            queryWrapper.eq("DATA_TYPE",personInfoIPagParam.getDataType());
        }

        //排序  姓 名 工作单位
        queryWrapper.orderByAsc("SURNAME","NAME","WORK_UNIT");
        pageInfo = new PageInfo(baseMapper.selectList(queryWrapper));;
        return pageInfo;
    }

    /**
     * 提取备案
     * @return
     * @throws ParseException
     */
    @Override
    public Object extractRegPersonInfo() throws ParseException {
        int con=0;
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.eq("DATA_TYPE","1");

        //数据类型 为干部的省管干部登记备案查询
        List<OmsRegProcpersoninfo> reginfolist = baseMapper.selectList(queryWrapper);
        //干综档案人员基本信息查询
        List<A01> a01list = a01Mapper.selectList(null);
        List<String> a0100s =null;
        for (int i=0;i<reginfolist.size();i++){
            a0100s.add(reginfolist.get(i).getA0100());
        }
        OmsRegProcpersoninfo orpInfo = null;
        for (A01 a01:a01list){
            if (a0100s.contains(a01.getA0100())){
                //获取当前a0100对应的下标
                int index = a0100s.indexOf(a01.getA0100());
                OmsRegProcpersoninfo omsreginfo = reginfolist.get(index);
                for (OmsRegProcpersoninfo orpi:reginfolist) {
                    //状态为“不在职”入库标识为“撤消” 而干部库状态为“在职”
                    if (orpi.getIncumbencyStatus()!="1"
                            && orpi.getIncumbencyStatus()!="8"
                            && orpi.getIncumbencyStatus()!="9"
                            && orpi.getInboundFlag().equals("D")
                            && a01.getA0163().equals("1")){
                            orpInfo = initData(a01);
                            con = baseMapper.insert(orpInfo);
                    }
                    if (orpi.getIncumbencyStatus()!="1" && orpi.getIncumbencyStatus()!="8" && orpi.getIncumbencyStatus()!="9"){
                        QueryWrapper<OmsRegRevokeapply> queryinfo = new QueryWrapper<OmsRegRevokeapply>();
                        queryinfo.eq("RF_ID",orpi.getId());
                        OmsRegRevokeapply apply = revokeApplyMapper.selectOne(queryinfo);
                        //撤销登记备案申请状态已审批并且入库标识为撤销
                        if (apply.getStatus().equals("2") && orpi.getInboundFlag()!="D"){
                            //入库标识  新增U  修改I  撤消D
                            omsreginfo.setInboundFlag("D");
                            //备案状态  0未备案，1已备案，2已确认
                            omsreginfo.setRfStatus("0");
                            //验收状态  1已验收，0待验收
                            omsreginfo.setCheckStatus("0");
                            con = baseMapper.updateById(omsreginfo);
                        }

                    }
                }

                QueryWrapper<A02> queryWrapper1 = new QueryWrapper<A02>();
                queryWrapper1.eq("a0100",a01.getA0100());
                List<A02> list = a02Mapper.selectList(queryWrapper1);
                if(list != null && list.size() > 0){
                    //主职务
                    String a0279 = list.get(0).getA0279();
                    if (!StringUtils.isBlank(a0279) && !a0279.equals(omsreginfo.getPostCode())){
                        //如果职务不变不做任何操作，职务改变，需更新职务，并更新入库状态
                        String code =  environment.getProperty("postcode." + a0279);
                        String zhiwu =  environment.getProperty("post." + code);
                        //职务code
                        omsreginfo.setPostCode(code);
                        //职务
                        omsreginfo.setPost(zhiwu);
                        //工作单位
                        omsreginfo.setWorkUnit(a0279);
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
                //从登记备案数据当中调取身份证号码相同的户口所在地信息及与之相关联的多个身份证号码信息
                String idnumber = a01.getA0184();
                QueryWrapper<OmsRegProcpersoninfo>  qw = new QueryWrapper<OmsRegProcpersoninfo>();
                qw.eq("IDNUMBER",idnumber);
                //查询身份证号所对应的信息
                OmsRegProcpersoninfo info = baseMapper.selectOne(qw);
                //如果存在更新，不存在新增
                if (info!=null){
                    OmsRegProcpersoninfo entity = new OmsRegProcpersoninfo();
                    entity.setA0100(orpInfo.getA0100());
                    entity.setPost(orpInfo.getPost());
                    entity.setPostCode(orpInfo.getPostCode());
                    entity.setInboundFlag("U");
                    con = baseMapper.updateById(entity);
                }else{
                    con = baseMapper.insert(orpInfo);
                }

            }
        }
        return con;
    }

    /**
     * 登记备案数据浏览
     * @param personInfoIPagParam
     * @return
     */
    @Override
    public PageInfo<OmsRegProcpersoninfo> getRegPersonInfoList(OmsRegProcpersoninfoIPagParam personInfoIPagParam) {
        PageInfo<OmsRegProcpersoninfo> pageInfo = null;
        //分页
        PageUtil.pageHelp(personInfoIPagParam.getPageNum(), personInfoIPagParam.getPageSize());
        //返回数据
        pageInfo = new PageInfo(baseMapper.selectRegPersonInfoList(personInfoIPagParam));
        return pageInfo;

    }


    /**
     * 根据批次号查询对应的人员信息
     * @param batchNo
     * @return
     */
    @Override
    public PageInfo<OmsRegProcbatchPerson> selectPersonByBatchNo(String batchNo,Integer pageNum,Integer pageSize) {
        //分页
        PageUtil.pageHelp(pageNum, pageSize);
        QueryWrapper<OmsRegProcbatchPerson> qWrapper = new QueryWrapper<OmsRegProcbatchPerson>();
        if (!StringUtils.isBlank(batchNo)){
            qWrapper.eq("BATCH_ID",batchNo);
        }
        //返回数据
        PageInfo<OmsRegProcbatchPerson> pageInfo = new PageInfo(regProcbatchPersonMapper.selectList(qWrapper));
        return pageInfo;
    }

    /**
     * 登记备案大检查，上传登记备案记录 并查询未备案列表人员
     * @param list
     * @return
     */
    @Override
    public int checkUploadRegRecord(List<OmsRegProcpersoninfo> list) {
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        QueryWrapper<OmsRegYearcheckinfo> yearcheckInfoWrapper = new QueryWrapper<OmsRegYearcheckinfo>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String dateStr = sdf.format(date);
        yearcheckInfoWrapper.eq("RF_STATUS","0");
        yearcheckInfoWrapper.eq("CREATE_DATE",dateStr);
        List<OmsRegYearcheckinfo> yearoldlist = yearcheckInfoMapper.selectList(yearcheckInfoWrapper);
        if (yearoldlist!=null && yearoldlist.size() >0){
            yearcheckInfoMapper.delete(yearcheckInfoWrapper);
        }
        int con =0;
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.eq("DATA_TYPE","1");
        //查询登记备案干部的数据
        List<OmsRegProcpersoninfo> omsregList = baseMapper.selectList(queryWrapper);
        //登记备案为干部的身份证号集合
        List<String> gbidnumbers = new ArrayList<>();
        for (int i=0;i<omsregList.size();i++){
            if (!StringUtils.isBlank(omsregList.get(i).getIdnumberGb())){
                gbidnumbers.add(omsregList.get(i).getIdnumberGb());
            }
        }
        //出国境导入数据集合
        for (OmsRegProcpersoninfo cgjdata :list){
            //登记备案中包含出入境
            if (gbidnumbers.contains(cgjdata.getIdnumberGa())){
                continue;
            }else{
                //登记备案不中包含出入境
                OmsRegYearcheckinfo yearcheckInfo = new OmsRegYearcheckinfo();
                BeanUtils.copyProperties(cgjdata, yearcheckInfo);
                yearcheckInfo.setCreateDate(dateStr);
                yearcheckInfo.setCreateUser(loginUser.getId());
                con = yearcheckInfoMapper.insert(yearcheckInfo);
            }
        }

        /*//出入境（公安）导入数据身份证号集合
        List<String> gaidnumbers = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            gaidnumbers.add(list.get(i).getIdnumberGa());
        }
        //登记备案干部集合
        for (OmsRegProcpersoninfo gbdata :omsregList){
            //出入境包含登记备案
            if (gbidnumbers.contains(gbdata.getIdnumberGb())){
                continue;
            }else{
                //出入境不包含登记备案
                OmsRegYearcheckinfo yearcheckInfo = new OmsRegYearcheckinfo();
                BeanUtils.copyProperties(gbdata, yearcheckInfo);
                yearcheckInfo.setCreateDate(dateStr);
                yearcheckInfo.setCreateUser(loginUser.getId());
                con = yearcheckInfoMapper.insert(yearcheckInfo);
            }
        }*/
        return con;
    }

    /**
     * 查询年度列表
     * @return
     */
    @Override
    public List<String> queryYearList() {
        return yearcheckInfoMapper.selectYearList();
    }

    /**
     * 查询大检查中未备案人员列表（可根据年度进行查询）
     * @param regYearCheckIPagParam
     * @return
     */
    @Override
    public PageInfo<OmsRegYearcheckinfo> queryYearCheckList(OmsRegYearCheckIPagParam regYearCheckIPagParam) {
        QueryWrapper<OmsRegYearcheckinfo> yearcheckWrapper = new QueryWrapper<OmsRegYearcheckinfo>();
        //分页
        PageUtil.pageHelp(regYearCheckIPagParam.getPageNum(), regYearCheckIPagParam.getPageSize());
        if (!StringUtils.isBlank(regYearCheckIPagParam.getYear())){
            yearcheckWrapper.eq("CREATE_DATE",regYearCheckIPagParam.getYear());
        }
        if (!StringUtils.isBlank(regYearCheckIPagParam.getRfB0000())){
            yearcheckWrapper.like("RF_B0000",regYearCheckIPagParam.getRfB0000());
        }
        //返回数据
        PageInfo<OmsRegYearcheckinfo> pageInfo = new PageInfo(yearcheckInfoMapper.selectList(yearcheckWrapper));
        return pageInfo;
    }

    @Override
    public Object selectPersonAndAllowRevoke(OmsRegProcpersoninfo msRegProcpersonInfo) {
        //在职状态为“免职”或 管理状态为“中管干部”或退出方式为“调出”的，且撤销登记备案表中不存在的（排除已备案状态）。
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
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.eq("A0100",a0100);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 初始化信息
     * @param a01
     * @return
     */
    private OmsRegProcpersoninfo initData(A01 a01) throws ParseException {
        //登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy");
        OmsRegProcpersoninfo orpInfo = new OmsRegProcpersoninfo();
        orpInfo.setId(UUIDGenerator.getPrimaryKey());
        //是否复姓。拆除
        boolean isCompoundSurname = OmsRegInitUtil.isCompoundSurname(a01.getA0101().trim());
        //是复姓
        if (isCompoundSurname){
            //姓
            orpInfo.setSurname(a01.getA0101().trim().substring(0,2));
            //名
            orpInfo.setName(a01.getA0101().trim().substring(2,a01.getA0101().trim().length()));
        }else{
            orpInfo.setSurname(a01.getA0101().trim().substring(0,1));
            orpInfo.setName(a01.getA0101().trim().substring(1,a01.getA0101().trim().length()));
        }
        QueryWrapper<A02> queryWrapper = new QueryWrapper<A02>();
        queryWrapper.eq("a0100",a01.getA0100());
        List<A02> list = a02Mapper.selectList(queryWrapper);
        String a0201b ="";
        if(list != null && list.size() > 0){
            //主职务
             a0201b = list.get(0).getA0201b();
            //查询机构id
            if (!StringUtils.isBlank(a0201b)){
                orpInfo.setRfB0000(a0201b);
                //工作单位
                String workunit = a02Mapper.selectB0101ByA0201b(a0201b);
                orpInfo.setWorkUnit(workunit);
            }
            /*if (!StringUtils.isBlank(a0201b)){
                String a0215b = list.get(0).getA0215b();
                if (!StringUtils.isBlank(a0215b)){
                    String code =  environment.getProperty("postcode." + list.get(0).getA0215b());
                    String zhiwu =  environment.getProperty("post." + code);
                    //职务code
                    orpInfo.setPostCode(code);
                    //职务
                    orpInfo.setPost(zhiwu);
                }
            }*/
        }

        String incumbencyStatus = this.queryStatusByA0100(a01.getA0100());
        orpInfo.setA0100(a01.getA0100());
        if (!StringUtils.isBlank(a01.getA0184())){
            //身份证号
            orpInfo.setIdnumberGb(a01.getA0184());
            //根据身份证号截取对应的出生日期
            String birthDay = OmsRegInitUtil.getBirthByIdNumber(a01.getA0184());
            //出生日期(身份证)
            orpInfo.setBirthDate(sdf1.parse(birthDay));
        }
        //出生日期(干部)
        if (a01.getA0107()!=null){
            if (a01.getA0107().length() > 1 && a01.getA0107().length() < 5) {
                orpInfo.setBirthDateGb(sdf3.parse(a01.getA0107()));
            } else if (a01.getA0107().length() > 5 && a01.getA0107().length() < 7) {
                orpInfo.setBirthDateGb(sdf2.parse(a01.getA0107()));
            } else if (a01.getA0107().length() > 7 && a01.getA0107().length() < 9) {
                orpInfo.setBirthDateGb(sdf1.parse(a01.getA0107()));
            }
        }

        //数据类型  1.干部    2 公安
        orpInfo.setDataType("1");
        //备案状态
        orpInfo.setRfStatus("0");
        //入库状态
        orpInfo.setInboundFlag("U");
        //验收状态
        orpInfo.setCheckStatus("0");
        //拼音简称
        String py = PingYinUtil.getFirstSpell(a01.getA0101());
        orpInfo.setPy(py);

        //查询A0100对应的退出管理退出方式录入在职状态
        orpInfo.setIncumbencyStatus(incumbencyStatus);
        //性别
        orpInfo.setSex(a01.getA0104());
        //民族
        orpInfo.setNationCode(a01.getA0117());
        orpInfo.setNationName(a01.getA0117A());
        //政治面貌
        orpInfo.setPoliticalAfficode(a01.getA0141());
        //健康状态
        orpInfo.setHealthCode(a01.getA0127());
        orpInfo.setHealth(a01.getA0128());
        //身份情况 1.省管干部
        orpInfo.setIdentityCode("1");
        orpInfo.setIdentity("省管干部");
        //人事主管单位
        orpInfo.setPersonManager("海南省委组织部");
        //任职时间
        orpInfo.setRzDate(a01.getRxzDate());
        orpInfo.setCreateTime(new Date());
        orpInfo.setCreateUser(loginUser.getId());
        return orpInfo;
    }

    /**
     * 查询A0100对应的退出管理退出方式
     * @param a0100
     * @return
     */
    private String queryStatusByA0100(String a0100) {
        String incumbencyStatus="";
        //在职状态
        QueryWrapper<A30> queryWrapper1 = new QueryWrapper<A30>();
        queryWrapper1.eq("A0100", a0100);
        System.out.println("a0100============"+a0100);
        A30 a30 = a30Mapper.selectOne(queryWrapper1);
        if(a30!=null){
            //退出方式
            String a3001 = a30.getA3001().substring(0,1);
            //退出状态 1退休 2调出 3死亡 4辞职 8转出 9其它(开除)
            //在职状态 1在职 2辞职 3退休 4去世 5开除 6调出 7.省管变中管 8 未匹配 9其它
            if (!StringUtils.isEmpty(a3001)){
                if (a3001.equals("1")){
                    incumbencyStatus="3";
                }else if(a3001.equals("2")){
                    incumbencyStatus="6";
                }else if(a3001.equals("3")){
                    incumbencyStatus="4";
                }else if(a3001.equals("4")){
                    incumbencyStatus="2";
                }else if(a3001.equals("9")){
                    incumbencyStatus="5";
                }else{
                    incumbencyStatus="7";
                }
            }
        }else{
            incumbencyStatus="1";
        }

        return incumbencyStatus;

    }


    /**
     * <b>查询登记备案库中的人员信息（出生日期）</b>
     * @param a0100
     * @author luoshuai
     * @return
     */
    public Date getOmsRegProcpersonBirthDate(String a0100){
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.eq("A0100", a0100);
        OmsRegProcpersoninfo omsRegProcpersonInfo = omsRegProcpersonInfoMapper.selectOne(queryWrapper);
        return omsRegProcpersonInfo.getBirthDate();
    }
    /**
     * 查询出国境机构树
     * @return
     */
    @Override
    public List<Tree> selectCGJPostTree(String dictCode) {
        List<Tree> treeList = TreeUtil.listToTreeJson(omsBaseinfoConfigMapper.selectCGJPostTree(dictCode));
        return treeList;
    }

    /**
     * 查询干综机构树
     * @return
     */
    @Override
    public List<Map<String, Object>> selectGZPostTree() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("dictCode", "ZB08");
        //干综职务list
        List<Map<String, Object>> cgjlist = sysDictItemMapper.getDictInfoByDictCodeAndGl(params);
        return cgjlist;
    }

    /**
     * 新增配置信息
     * @param list
     * @return
     */
    @Override
    public int insertBaseInfoConfig(List<OmsBaseinfoConfig> list) {
        for (OmsBaseinfoConfig omsBaseinfoConfig: list){
            omsBaseinfoConfig.setId(UUIDGenerator.getPrimaryKey());
        }
        return omsBaseinfoConfigMapper.insertBatchList(list);
    }

    /**
     * 删除配置信息
     * @param Ids
     * @return
     */
    @Override
    public int deleteBaseInfoConfig(List<String> Ids) {
        return omsBaseinfoConfigMapper.deleteBatchIds(Ids);
    }

    @Override
    public List<ExcelCheckModelORPinfo> selectCheckModelList(String year) {
        return yearcheckInfoMapper.selectCheckModelList(year);
    }


}
