package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.tree.TreeUtil;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.*;
import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.entity.paramentity.SendMessageParam;
import com.hxoms.message.message.service.MessageService;
import com.hxoms.message.msguser.entity.MsgUser;
import com.hxoms.modules.keySupervision.majorLeader.mapper.A02Mapper;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegYearCheckIPagParam;
import com.hxoms.modules.omsregcadre.mapper.*;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.omsregcadre.service.OmsRegRevokeApplyService;
import com.hxoms.support.leaderInfo.entity.A01;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import com.hxoms.support.parameter.service.ParameterService;
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
public class OmsRegProcpersonInfoServiceImpl extends ServiceImpl<OmsRegProcpersoninfoMapper, OmsRegProcpersoninfo> implements OmsRegProcpersonInfoService {


    @Autowired
    private Environment environment;
    @Autowired
    private A01Mapper a01Mapper;
    @Autowired
    private A30Mapper a30Mapper;
    @Autowired
    private A02Mapper a02Mapper;
    @Autowired
    private OmsRegProcbatchPersonMapper regProcbatchPersonMapper;
    @Autowired
    private OmsRegRevokeApplyService revokeApplyService;
    @Autowired
    private OmsRegYearcheckinfoMapper yearcheckInfoMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersonInfoMapper;
    @Autowired
    private OmsBaseinfoConfigMapper omsBaseinfoConfigMapper;
    @Autowired
    private SysDictItemMapper sysDictItemMapper;

    @Autowired
    private OmsRegProcpersonInfoService omsRegProcpersonInfoService;

    @Autowired
    private MessageService messageService;
    @Autowired
    private ParameterService parameterService;

    /**
     * 初始化登记备案信息
     *
     * @param personInfoIPagParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<OmsRegProcpersoninfo> getInitialReginfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam) throws ParseException {

        //缓存登记备案人员到哈希表中
        HashMap<String, OmsRegProcpersoninfo> hashMapReg = CacheRegProcpersonInfo(null);

        //查询干部信息总库干部信息
        List<A01> a01list = a01Mapper.extractCadreForRegister();

        //缓存职务
        HashMap<String, List<Map<String, Object>>> hashMapA02 = CachePost(a01list);

        //提取省管干部退出信息并缓存
        HashMap<String, A30> hashMapA30 = CacheA30();

        //缓存职务对应关系
        HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo = CachePostMapping();

        //登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();

        List<OmsRegProcpersoninfo> updateRegList = new ArrayList();
        List<OmsRegProcpersoninfo> insertRegList = new ArrayList();

        if (a01list != null && hashMapReg.size() > 0) {
            for (A01 a01 : a01list) {
                OmsRegProcpersoninfo orpInfo = hashMapReg.get(a01.getA0100());

                boolean isNew = (orpInfo == null ? true : false);
                orpInfo = initData(orpInfo, a01, hashMapA02, hashMapA30, hashMapBaseInfo);


                //登记备案信息中存在此数据，则更新其对应信息
                if (isNew == false) {
                    orpInfo.setModifyTime(new Date());
                    orpInfo.setModifyUser(loginUser.getId());
                    updateRegList.add(orpInfo);
                } else {//不包含直接新增
                    AddRegisterState(orpInfo);
                    insertRegList.add(orpInfo);
                }
            }
        } else if (a01list != null && hashMapReg.size() == 0) {
            for (A01 a01 : a01list) {
                OmsRegProcpersoninfo orpInfo = null;
                orpInfo = initData(orpInfo, a01, hashMapA02, hashMapA30, hashMapBaseInfo);
                insertRegList.add(orpInfo);
            }
        }
        if (insertRegList.size() > 0)
            baseMapper.batchAddorpInfo(insertRegList);
        if (updateRegList.size() > 0)
            omsRegProcpersonInfoService.updateBatchById(updateRegList);

        PageInfo<OmsRegProcpersoninfo> pageInfo = null;
        //分页
        PageUtil.pageHelp(personInfoIPagParam.getPageNum(), personInfoIPagParam.getPageSize());
        if (a01list.size() > 0) {

            OmsRegProcpersoninfo info = new OmsRegProcpersoninfo();
            //info.setRfB0000(personInfoIPagParam.getRfB0000());
            List<OmsRegProcpersoninfo> mepinfoList = baseMapper.selectRegProcpersonInfo(null);
            //返回数据
            pageInfo = new PageInfo(mepinfoList);
        }
        return pageInfo;
    }

    /**
     * 初始化信息
     *
     * @param a01
     * @return
     */
    private OmsRegProcpersoninfo initData(OmsRegProcpersoninfo orpInfo, A01 a01,
                             HashMap<String, List<Map<String, Object>>> hashMapA02,
                             HashMap<String, A30> hashMapA30,
                             HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo) throws ParseException {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy");

        if (orpInfo == null) {
            orpInfo = new OmsRegProcpersoninfo();
            orpInfo.setId(UUIDGenerator.getPrimaryKey());
        }

        SplitName(orpInfo, a01.getA0101().trim());

        SetPost(a01, orpInfo, hashMapA02, hashMapBaseInfo);

        //处理在职状态，退出人员要细分退出方式
        String incumbencyStatus = this.queryStatusByA0100(a01, hashMapA30);
        orpInfo.setIncumbencyStatus(incumbencyStatus);

        orpInfo.setA0100(a01.getA0100());
        if (!StringUtils.isBlank(a01.getA0184())) {
            //身份证号
            orpInfo.setIdnumberGb(a01.getA0184());
            //根据身份证号截取对应的出生日期
            String birthDay = OmsRegInitUtil.getBirthByIdNumber(a01.getA0184());
            //出生日期(身份证)
            if (!StringUilt.stringIsNullOrEmpty(birthDay))
                orpInfo.setBirthDate(sdf1.parse(birthDay));
        }
        //出生日期(干部)
        if (a01.getA0107() != null) {
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
        return orpInfo;
    }

    /**
     * 查询A0100对应的退出管理退出方式
     * @param
     * @return
     */
    private String queryStatusByA0100(A01 a01, HashMap<String, A30> hashMapA30) {
        String incumbencyStatus = "";
        A30 a30 = hashMapA30.get(a01.getA0100());
        if (a01.getA0163().equals("2") && a30 != null && !StringUtils.isEmpty(a30.getA3001())) {
            //退出方式
            String a3001 = a30.getA3001().substring(0, 1);
            //退出状态 1退休 2调出 3死亡 4辞职 8转出 9其它(开除)
            //在职状态 1在职 2辞职 3退休 4去世 5开除 6调出 7.省管变中管  8其它 99 未匹配
            if (!StringUtils.isEmpty(a3001)) {
                if (a3001.equals("1")) {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Retirement.getIndex());
                } else if (a3001.equals("2")) {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Dispatch.getIndex());
                } else if (a3001.equals("3")) {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Death.getIndex());
                } else if (a3001.equals("4")) {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Resignation.getIndex());
                } else if (a3001.equals("9")) {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Expel.getIndex());
                } else {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Other.getIndex());
                }
            }
        } else if (a01.getA0163().equals("1") && a01.getA0165().equals("01")) {
            incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.ProvinceToCentral.getIndex());//省管变中管
        } else if (a01.getA0163().equals("1")) {
            incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Working.getIndex());//在职
        } else {
            incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Unmatched.getIndex());
        }
        return incumbencyStatus;
    }

    /**
     * @description:缓存登记备案信息表到内存，key为身份证号（配偶子女）或A0100（干部）
     * @author:杨波
     * @date:2020-09-15 * @param
     * @return:java.util.HashMap<java.lang.String,com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo>
     **/
    private HashMap<String, OmsRegProcpersoninfo> CacheRegProcpersonInfo(HashMap<String, OmsRegProcpersoninfo> nameAndIDCard) {

        List<OmsRegProcpersoninfo> omsRegProcpersoninfos = baseMapper.selectRegProcpersonInfo(null);
        //缓存登记备案人员到哈希表中
        HashMap<String, OmsRegProcpersoninfo> hashMapReg = new HashMap<>();
        for (OmsRegProcpersoninfo omsReg : omsRegProcpersoninfos
        ) {
            if (StringUilt.stringIsNullOrEmpty(omsReg.getA0100()))
                hashMapReg.put(omsReg.getIdnumberGa(), omsReg);//配偶子女没有A0100
            else
                hashMapReg.put(omsReg.getA0100(), omsReg);//干部

            if (nameAndIDCard != null) {
                nameAndIDCard.put(omsReg.getIdnumberGa() + omsReg.getSurname() + omsReg.getName(), omsReg);
            }
        }
        return hashMapReg;
    }

    /**
     * @description:取人员职务并缓存，以a0100为key，每个key对应一个职务列表 在初始化登记备案和登记备案管理中提取需备案省管干部时用到
     * @author:杨波
     * @date:2020-09-15 * @param a01list 出国境a01中省管干部
     * @return:java.util.HashMap<java.lang.String,java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     **/
    private HashMap<String, List<Map<String, Object>>> CachePost(List<A01> a01list) {
        //取职务并缓存
        Map<String, Object> params = new HashMap<String, Object>();
        List<Map<String, Object>> A02s = a01Mapper.selectPersonInfo(params);
        HashMap<String, List<Map<String, Object>>> hashMapA02 = new HashMap<>();
        for (A01 a01 : a01list) {
            hashMapA02.put(a01.getA0100(), new ArrayList<>());
        }
        for (Map map : A02s
        ) {
            String a0100 = map.get("a0100").toString();
            List<Map<String, Object>> a02s = hashMapA02.get(a0100);
            a02s.add(map);
        }
        return hashMapA02;
    }

    /**
     * @description:缓存省管干部的退出信息，以a0100为key，A30为value
     * @author:杨波
     * @date:2020-09-15 * @param
     * @return:java.util.HashMap<java.lang.String,com.hxoms.modules.omsregcadre.entity.A30>
     **/
    private HashMap<String, A30> CacheA30() {
        List<A30> a30s = a30Mapper.extractForRegister();
        HashMap<String, A30> hashMapA30 = new HashMap<>();
        for (A30 a30 : a30s
        ) {
            //防止一个人有两条退出信息，因为是按人员和退出时间倒序的，只缓存最近一条
            try {
                hashMapA30.put(a30.getA0100(), a30);
            } catch (Exception ep) {
                ep.printStackTrace();
            }
        }
        return hashMapA30;
    }

    /**
     * @description:缓存干综职务与出入境职务的对应关系
     * @author:杨波
     * @date:2020-09-15 * @param
     * @return:java.util.HashMap<java.lang.String,com.hxoms.modules.omsregcadre.entity.OmsBaseinfoConfig>
     **/
    private HashMap<String, OmsBaseinfoConfig> CachePostMapping() {
        Map<String, Object> params = new HashMap<String, Object>();
        List<OmsBaseinfoConfig> baseInfos = omsBaseinfoConfigMapper.selectPostInfo(params);
        HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo = new HashMap<>();
        for (OmsBaseinfoConfig baseInfo : baseInfos
        ) {
            //InfoId为出国境职务字典，parentid为出入境管理局职务字典
            if (baseInfo.getInfoId() != null && StringUilt.stringIsNullOrEmpty(baseInfo.getInfoId()) == false)
                hashMapBaseInfo.put(baseInfo.getInfoId(), baseInfo);
        }
        return hashMapBaseInfo;
    }

    public void SplitName(OmsRegProcpersoninfo orpInfo, String name) {
        //是否复姓。拆除
        boolean isCompoundSurname = OmsRegInitUtil.isCompoundSurname(name);
        //是复姓
        if (isCompoundSurname) {
            //姓
            orpInfo.setSurname(name.substring(0, 2));
            //名
            orpInfo.setName(name.substring(2, name.length()));
        } else {
            orpInfo.setSurname(name.substring(0, 1));
            orpInfo.setName(name.substring(1, name.length()));
        }
        //拼音简称
        String py = PingYinUtil.getFirstSpell(name);
        orpInfo.setPy(py);
    }

    private void SetPost(A01 a01, OmsRegProcpersoninfo orpInfo,
                         HashMap<String, List<Map<String, Object>>> hashMapA02,
                         HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo) {
        //现任职务全称
        orpInfo.setPost(a01.getA0192a());

        //处理职务代码并和出入境的对应
        List<Map<String, Object>> mapList = hashMapA02.get(a01.getA0100());
        if (mapList != null && mapList.size() > 0) {
            //首先取主职务，没有主职务取第一个职务
            Map<String, Object> post = null;
            for (Map map : mapList
            ) {
                Object a0279 = map.get("a0279");//是否主职务
                if (a0279 == null || a0279.toString() == "0") continue;
                post = map;
            }
            if (post == null) post = mapList.get(0);

            //工作单位
            orpInfo.setWorkUnit(post.get("b0101").toString());

            //机构id
            if (post.get("b0100") != null) {
                orpInfo.setRfB0000((String) post.get("b0100"));
            }
            if (post.get("a0215b") != null) {
                //职务转换
                OmsBaseinfoConfig baseinfo = hashMapBaseInfo.get(post.get("a0215b").toString());
                if (baseinfo != null) {
                    orpInfo.setPostCode(baseinfo.getParentId());
                }
            }
        }
    }

    private void ChangeRegisterState(OmsRegProcpersoninfo regProcpersoninfo) {
        //入库标识  新增U  修改I  撤消D
        regProcpersoninfo.setInboundFlag("I");
        //备案状态  0未备案，1已备案，2已确认
        regProcpersoninfo.setRfStatus("0");
        //验收状态  1已验收，0待验收
        regProcpersoninfo.setCheckStatus("0");
        regProcpersoninfo.setModifyUser(UserInfoUtil.getUserId());
        regProcpersoninfo.setModifyTime(new Date());
    }

    private void AddRegisterState(OmsRegProcpersoninfo regProcpersoninfo) {
        //入库标识  新增U  修改I  撤消D
        regProcpersoninfo.setInboundFlag("U");
        //备案状态  0未备案，1已备案，2已确认
        regProcpersoninfo.setRfStatus("0");
        //验收状态  1已验收，0待验收
        regProcpersoninfo.setCheckStatus("0");
        regProcpersoninfo.setCreateTime(new Date());
        regProcpersoninfo.setCreateUser(UserInfoUtil.getUserId());
        regProcpersoninfo.setModifyUser(UserInfoUtil.getUserId());
        regProcpersoninfo.setModifyTime(new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> selectStatisticsCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        //入库标识
        List<StatisticsCountVo> inboudFlaglist = baseMapper.selectInboudFlagCount();
        //identityCode 省管干部-1 科级以上公务员（企事业单位中层以上管理人员）-2  涉密人员-3 重要岗位人-4 重点监管人员-5 其他人员-9
        List<StatisticsCountVo> identityCodelist = baseMapper.selectIdentityCodeCount();
        //主要领导，裸官，核心涉密人员，重要涉密人员，锁定出国人员，纪委不回复，离琼挂职，到琼挂职
        List<StatisticsCountVo> allFlaglist = baseMapper.selectAllFlagCount();
        map.put("inboudFlaglist", inboudFlaglist);
        map.put("identityCodelist", identityCodelist);
        map.put("allFlaglist", allFlaglist);
        return map;
    }

    /**
     * 新增登记备案人员
     *
     * @param orpInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertRpinfo(OmsRegProcpersoninfo orpInfo) {
        orpInfo.setId(UUIDGenerator.getPrimaryKey());
        return baseMapper.insert(orpInfo);
    }

    /**
     * 更新登记备案人员信息
     *
     * @param orpInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateRpinfo(OmsRegProcpersoninfo orpInfo) {
        if (StringUtils.isBlank(orpInfo.getId()) || StringUtils.isBlank(orpInfo.getA0100())) {
            throw new CustomMessageException("缺少必要参数");
        } else {
            orpInfo.setInboundFlag("I");
            orpInfo.setModifyTime(new Date());
            return baseMapper.updateById(orpInfo);
        }
    }

    /**
     * 删除登记备案人员信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteRpinfo(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("缺少必要参数id");
        } else {
            return baseMapper.deleteById(id);
        }
    }

    /**
     * 导入公安数据
     *
     * @param list
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOmsRegGongAn(List<OmsRegProcpersoninfo> list) {
        int con = 0;
        List<OmsRegProcpersoninfo> newReg = new ArrayList<>();
        List<OmsRegProcpersoninfo> editReg = new ArrayList<>();

        List<OmsRegProcpersoninfo> oldgalist = omsRegProcpersonInfoService.selectListById(null);
        HashMap<String, OmsRegProcpersoninfo> hashMapOld = new HashMap<>();
        for (OmsRegProcpersoninfo oldgaData : oldgalist) {
            hashMapOld.put(oldgaData.getSurname() + oldgaData.getName() + oldgaData.getIdnumberGb(), oldgaData);
        }
        for (OmsRegProcpersoninfo newgaData : list) {
            OmsRegProcpersoninfo oldgaData = hashMapOld.get(newgaData.getSurname() + newgaData.getName() + newgaData.getIdnumberGa());

            //数据比对更新
            if (oldgaData != null) {
                this.dataCompareAndUpdate(oldgaData, newgaData,false);
                editReg.add(oldgaData);
            } else {
                newReg.add(newgaData);
                log.debug(newgaData.getId());
            }
        }
        if (newReg.size() > 0)
            omsRegProcpersonInfoService.saveBatch(newReg);
        if (editReg.size() > 0)
            omsRegProcpersonInfoService.updateBatchById(editReg);
        return con;
    }

    @Override
    public List<OmsRegProcpersoninfo> selectMergeList(String sortType) {
        //显示待备案干部数据 和 在职状态为“未匹配”的公安数据
        return baseMapper.selectMergeList(sortType);
    }

    @Override
    public List<OmsRegProcpersoninfo> selectListById(String idStr) {
        List<OmsRegProcpersoninfo> list = new ArrayList<>();
        if (idStr != null) {
            List<String> ids = Arrays.asList(idStr.split(","));
            list = baseMapper.selectListById(ids);
        } else {
            list = baseMapper.selectListById(null);
        }
        return list;
    }

    @Override
    public int updateRegProcpersoninfo(String idStr) {
        OmsRegProcpersoninfoIPagParam info = new OmsRegProcpersoninfoIPagParam();
        info.setRfStatus("1");
        int con = 0;
        if (idStr != null) {
            List<String> ids = Arrays.asList(idStr.split(","));
            info.setIds(ids);
            con = baseMapper.updateRegProcpersoninfo(info);
        } else {
            con = baseMapper.updateRegProcpersoninfo(info);
        }
        return con;
    }


    /**
     * 合并干部和公安数据
     *
     * @param idStr
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int mergeDataGBandGA(String idStr) {
        int con = 0;
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.in("ID", idStr.split(","));
        queryWrapper.orderByAsc("DATA_TYPE");
        List<OmsRegProcpersoninfo> omsregList = baseMapper.selectList(queryWrapper);
        if (omsregList != null && omsregList.size() > 0) {
            OmsRegProcpersoninfo gbData = omsregList.get(0);
            OmsRegProcpersoninfo gaData = omsregList.get(1);
            boolean flag = true;
            //数据比对更新
            this.dataCompareAndUpdate(gbData, gaData,flag);
            con = baseMapper.updateById(gbData);
            if (con > 0) {
                con = baseMapper.deleteById(gaData.getId());
            }
        }
        return con;
    }

    private int dataCompareAndUpdate(OmsRegProcpersoninfo dataGB, OmsRegProcpersoninfo dataGA,boolean flag) {
        int con = 0;
        //身份账号与名称一致
        if (flag==true || (dataGB.getIdnumberGb().equals(dataGA.getIdnumberGa())
                &&dataGB.getName().equals(dataGA.getName())
                &&dataGB.getSurname().equals(dataGA.getSurname()))) {
            //更新干部相关信息从公安数据中维护
            //将公安的身份证号写入干部数据的公安身份证号字段里
            dataGB.setIdnumberGa(dataGA.getIdnumberGa());
            //将公安的出生日期写入干部数据的公安出生日期字段里
            dataGB.setBirthDate(dataGA.getBirthDate());
            dataGB.setModifyTime(new Date());
            //用公安数据的户口所在地
            dataGB.setRegisteResidenceCode(dataGA.getRegisteResidenceCode());
            dataGB.setRegisteResidence(dataGA.getRegisteResidence());

            //公安工作单位和干部工作单位及职务不一致时要将入库标识置为变更、登记备案状态置为待备案、验收状态置为未验收
            if ((dataGB.getWorkUnit()==null && dataGA.getWorkUnit()!=null)||
                    (dataGB.getWorkUnit()!=null && dataGA.getWorkUnit()==null)||
                    (dataGB.getWorkUnit()!=null &&dataGA.getWorkUnit()!=null&&dataGB.getWorkUnit().equals(dataGA.getWorkUnit()) == false) ||
                    (dataGB.getPost()==null&&dataGA.getPost()!=null)||
                    (dataGB.getPost()!=null&&dataGA.getPost()==null)||
                    (dataGB.getPost()!=null&&dataGA.getPost()!=null&&dataGB.getPost().equals(dataGA.getPost()) == false)) {
                //入库标识  新增U  修改I  撤消D
                dataGB.setInboundFlag("I");
                //备案状态  0未备案，1已备案，2已确认
                dataGB.setRfStatus("0");
                //验收状态  1已验收，0待验收
                dataGB.setCheckStatus("0");
            } else {
                //入库标识  新增U  修改I  撤消D
                dataGB.setInboundFlag(dataGA.getInboundFlag());
                //备案状态  0未备案，1已备案，2已确认
                dataGB.setRfStatus("1");
                //验收状态  1已验收，0待验收
                dataGB.setCheckStatus("1");
            }

        } else {
            throw new CustomMessageException("当前选择数据姓名、身份证号不一致，请进行人工核对后再合并。");
        }
        return con;
    }

    /**
     * 查询省管干部登记备案信息
     * （打开登记备案页面）
     *
     * @param personInfoIPagParam
     * @return
     */
    @Override
    public PageInfo<OmsRegProcpersoninfo> getProvinceCadreRegInfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam) {
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        PageInfo<OmsRegProcpersoninfo> pageInfo = null;
        //分页
        PageUtil.pageHelp(personInfoIPagParam.getPageNum(), personInfoIPagParam.getPageSize());

        if (!StringUtils.isBlank(personInfoIPagParam.getSecretLevel())) {
            //查询条件涉密等级
            queryWrapper.eq("SECRET_LEVEL", personInfoIPagParam.getSecretLevel());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getIdentityCode())) {
            //身份情况
            queryWrapper.eq("IDENTITY_CODE", personInfoIPagParam.getIdentityCode());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getIncumbencyStatus())) {
            //在职状态
            queryWrapper.eq("INCUMBENCY_STATUS", personInfoIPagParam.getIncumbencyStatus());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getInboundFlag())) {
            //入库状态
            queryWrapper.eq("INBOUND_FLAG", personInfoIPagParam.getInboundFlag());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getRfStatus())) {
            //备案状态
            queryWrapper.eq("RF_STATUS", personInfoIPagParam.getRfStatus());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getRfB0000())) {
            //机构代码
            queryWrapper.like("RF_B0000", personInfoIPagParam.getRfB0000());
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getCheckStatus())) {
            //验收状态为待验收
            queryWrapper.eq("CHECK_STATUS", "0");
        }
        if (!StringUtils.isBlank(personInfoIPagParam.getDataType())) {
            //数据类型为干部
            queryWrapper.eq("DATA_TYPE", personInfoIPagParam.getDataType());
        }

        //排序  姓 名 工作单位
        queryWrapper.orderByAsc("SURNAME", "NAME", "WORK_UNIT");
        pageInfo = new PageInfo(baseMapper.selectList(queryWrapper));
        ;
        return pageInfo;
    }

    /**
     * 提取备案
     *
     * @return
     * @throws ParseException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object extractRegPersonInfo() throws ParseException {
        int con = 0;

        //干综档案人员基本信息查询
        List<A01> a01list = a01Mapper.extractCadreForRegister();

        //缓存职务
        HashMap<String, List<Map<String, Object>>> hashMapA02 = CachePost(a01list);

        //提取省管干部退出信息并缓存
        HashMap<String, A30> hashMapA30 = CacheA30();

        //缓存职务对应关系
        HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo = CachePostMapping();

        //根据身份证号和姓名缓存
        HashMap<String, OmsRegProcpersoninfo> nameAndIDCard = new HashMap<>();
        //缓存登记备案人员到哈希表中
        HashMap<String, OmsRegProcpersoninfo> hashMapReg = CacheRegProcpersonInfo(nameAndIDCard);

        List<OmsRegProcpersoninfo> updates = new ArrayList<>();
        List<OmsRegProcpersoninfo> adds = new ArrayList<>();

        //循环处理干部
        for (A01 a01 : a01list) {
            String a0100 = a01.getA0100();
            //判断是否已登记备案过
            OmsRegProcpersoninfo omsreginfo = hashMapReg.get(a0100);

            //已登记备案过
            if (omsreginfo != null) {
                DealRegistered(omsreginfo, a01, hashMapA02, hashMapA30, hashMapBaseInfo);
                updates.add(omsreginfo);
            }
            //未找到登记备案信息
            else {
                omsreginfo = nameAndIDCard.get(a01.getA0184() + a01.getA0101());

                OmsRegProcpersoninfo regProcpersoninfo = null;

                if (omsreginfo == null || (omsreginfo != null && "2".equals(omsreginfo.getDataType())))
                    regProcpersoninfo = initData(regProcpersoninfo, a01, hashMapA02, hashMapA30, hashMapBaseInfo);

                //根据身份证号和姓名找到了公安数据，合并
                if (omsreginfo != null && "2".equals(omsreginfo.getDataType())) {
                    dataCompareAndUpdate(regProcpersoninfo, omsreginfo,false);
                    adds.add(regProcpersoninfo);
                    omsRegProcpersonInfoService.deleteRpinfo(omsreginfo.getId());
                }
                //根据身份证号和姓名找到了干部数据，重新设置A0100和职务
                else if (omsreginfo != null && "1".equals(omsreginfo.getDataType())) {
                    omsreginfo.setA0100(a0100);
                    SetPost(a01, omsreginfo, hashMapA02, hashMapBaseInfo);
                    ChangeRegisterState(omsreginfo);
                    updates.add(omsreginfo);
                } else {
                    AddRegisterState(regProcpersoninfo);
                    adds.add(regProcpersoninfo);
                }
            }
        }
        //处理撤销登记备案
        OmsRegRevokeApplyIPagParam param = new OmsRegRevokeApplyIPagParam();
        param.setPageNum(1);
        param.setPageSize(100000);
        param.setStatus("6");//部长审批
        List<OmsRegRevokeapply> applies = revokeApplyService.queryRevokeApplyList(param).getList();
        for (OmsRegRevokeapply apply : applies
        ) {
            OmsRegProcpersoninfo reginfo = hashMapReg.get(apply.getRfId());
            //入库标识  新增U  修改I  撤消D
            reginfo.setInboundFlag("D");
            //备案状态  0未备案，1已备案，2已确认
            reginfo.setRfStatus("0");
            //验收状态  1已验收，0待验收
            reginfo.setCheckStatus("0");
            updates.add(reginfo);

            apply.setStatus("8");//已抽取
        }
        if (updates.size() > 0)
            omsRegProcpersonInfoService.updateBatchById(updates);
        if (adds.size() > 0)
            omsRegProcpersonInfoService.saveBatch(adds);

        if (applies != null && applies.size() > 0)
            revokeApplyService.updateBatchById(applies);
        return con;
    }

    /**
     * @param a01             干部信息
     * @param hashMapA02      干部职务
     * @param hashMapA30      干部退出信息
     * @param hashMapBaseInfo 干部库和出入境职务映射
     * @description:已登记备案人员的处理
     * @author:杨波
     * @date:2020-09-15 * @param omsreginfo 已登记备案信息
     * @return:void
     **/
    private void DealRegistered(OmsRegProcpersoninfo omsreginfo, A01 a01,
                                HashMap<String, List<Map<String, Object>>> hashMapA02,
                                HashMap<String, A30> hashMapA30,
                                HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo) {

        //身份证号姓名一致
        if ((omsreginfo.getSurname() + omsreginfo.getName()).equals(a01.getA0101()) &&
                omsreginfo.getIdnumberGa().equals(a01.getA0184())) {

            //在职状态沒有发生变化
            if (a01.getA0163().equals(omsreginfo.getIncumbencyStatus()) ||
                    ("2".equals(a01.getA0163()) && !omsreginfo.getIncumbencyStatus().equals("1"))) {
                //職務发生变化
                if (!a01.getA0192a().equals(omsreginfo.getPost())) {
                    //变更登记备案
                    ChangeRegisterState(omsreginfo);
                }
            }
            //在职状态发生变化
            else {
                //处理在职状态，退出人员要细分退出方式
                String incumbencyStatus = this.queryStatusByA0100(a01, hashMapA30);
                omsreginfo.setIncumbencyStatus(incumbencyStatus);

                //如果干部是在职状态，登记备案人员已经撤销，需要以新增方式重新登记备案
                if ("1".equals(a01.getA0163()) && omsreginfo.getInboundFlag().equals("D")) {
                    AddRegisterState(omsreginfo);
                }
                //如果干部是在职状态，更新职务信息，并以更新方式重新登记备案
                //调出后，还没有撤消登记备案又调回来
                else if ("1".equals(a01.getA0163())) {
                    //更新职务信息
                    SetPost(a01, omsreginfo, hashMapA02, hashMapBaseInfo);
                    ChangeRegisterState(omsreginfo);
                }
            }
        }
        //身份证号姓名不一致
        else {
            try {
                SendMessage(a01.getA0101() + "(" + a01.getA0184() + ")的身份证号或姓名与登记备案不一致，请核查！", "ExtractCadreForRegisterIDCardIllegal");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 登记备案数据浏览
     *
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
     *
     * @param batchNo
     * @return
     */
    @Override
    public PageInfo<OmsRegProcbatchPerson> selectPersonByBatchNo(String batchNo, Integer pageNum, Integer pageSize) {
        //分页
        PageUtil.pageHelp(pageNum, pageSize);
        QueryWrapper<OmsRegProcbatchPerson> qWrapper = new QueryWrapper<OmsRegProcbatchPerson>();
        if (!StringUtils.isBlank(batchNo)) {
            qWrapper.eq("BATCH_ID", batchNo);
        }
        //返回数据
        PageInfo<OmsRegProcbatchPerson> pageInfo = new PageInfo(regProcbatchPersonMapper.selectList(qWrapper));
        return pageInfo;
    }

    /**
     * 登记备案大检查，上传登记备案记录 并查询未备案列表人员
     *
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
        yearcheckInfoWrapper.eq("RF_STATUS", "0");
        yearcheckInfoWrapper.eq("CREATE_DATE", dateStr);
        List<OmsRegYearcheckinfo> yearoldlist = yearcheckInfoMapper.selectList(yearcheckInfoWrapper);
        if (yearoldlist != null && yearoldlist.size() > 0) {
            yearcheckInfoMapper.delete(yearcheckInfoWrapper);
        }
        int con = 0;
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.eq("DATA_TYPE", "1");
        //查询登记备案干部的数据
        List<OmsRegProcpersoninfo> omsregList = baseMapper.selectList(queryWrapper);
        //登记备案为干部的身份证号集合
        List<String> gbidnumbers = new ArrayList<>();
        for (int i = 0; i < omsregList.size(); i++) {
            if (!StringUtils.isBlank(omsregList.get(i).getIdnumberGb())) {
                gbidnumbers.add(omsregList.get(i).getIdnumberGb());
            }
        }
        //出国境导入数据集合
        for (OmsRegProcpersoninfo cgjdata : list) {
            //登记备案中包含出入境
            if (gbidnumbers.contains(cgjdata.getIdnumberGa())) {
                continue;
            } else {
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
     *
     * @return
     */
    @Override
    public List<String> queryYearList() {
        return yearcheckInfoMapper.selectYearList();
    }

    /**
     * 查询大检查中未备案人员列表（可根据年度进行查询）
     *
     * @param regYearCheckIPagParam
     * @return
     */
    @Override
    public PageInfo<OmsRegYearcheckinfo> queryYearCheckList(OmsRegYearCheckIPagParam regYearCheckIPagParam) {
        QueryWrapper<OmsRegYearcheckinfo> yearcheckWrapper = new QueryWrapper<OmsRegYearcheckinfo>();
        //分页
        PageUtil.pageHelp(regYearCheckIPagParam.getPageNum(), regYearCheckIPagParam.getPageSize());
        if (!StringUtils.isBlank(regYearCheckIPagParam.getYear())) {
            yearcheckWrapper.eq("CREATE_DATE", regYearCheckIPagParam.getYear());
        }
        if (!StringUtils.isBlank(regYearCheckIPagParam.getRfB0000())) {
            yearcheckWrapper.like("RF_B0000", regYearCheckIPagParam.getRfB0000());
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
     *
     * @param a0100
     * @return
     */
    @Override
    public Object selectInfoByA0100(String a0100) {
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.eq("A0100", a0100);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * <b>查询登记备案库中的人员信息（出生日期）</b>
     *
     * @param a0100
     * @return
     * @author luoshuai
     */
    public Date getOmsRegProcpersonBirthDate(String a0100) {
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.eq("A0100", a0100);
        OmsRegProcpersoninfo omsRegProcpersonInfo = omsRegProcpersonInfoMapper.selectOne(queryWrapper);
        return omsRegProcpersonInfo.getBirthDate();
    }

    /**
     * 查询出国境机构树
     *
     * @return
     */
    @Override
    public List<Tree> selectCGJPostTree(String dictCode) {
        List<Tree> treeList = TreeUtil.listToTreeJson(omsBaseinfoConfigMapper.selectCGJPostTree(dictCode));
        return treeList;
    }

    /**
     * 查询干综机构树
     *
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
     *
     * @param list
     * @return
     */
    @Override
    public int insertBaseInfoConfig(List<OmsBaseinfoConfig> list) {

        for (OmsBaseinfoConfig omsBaseinfoConfig : list) {
            omsBaseinfoConfig.setId(UUIDGenerator.getPrimaryKey());
        }
        return omsBaseinfoConfigMapper.insertBatchList(list);
    }

    /**
     * 删除配置信息
     *
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

    /**
     * @param paramCode 接收人的系统参数编码，格式：类型（1个人 2处室 3机构 4讨论组）,机构或处室ID，机构或处室名称
     * @description:发送消息通用方法
     * @author:杨波
     * @date:2020-09-15 * @param msg 要发送的消息
     * @return:void
     **/
    @Override
    public void SendMessage(String msg, String paramCode) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        SendMessageParam param = new SendMessageParam();
        Message message = new Message();
        message.setContent(msg);
        param.setMessage(message);

        String receiver = parameterService.selectPValueByCode(paramCode);

        //格式：类型（1个人 2处室 3机构 4讨论组）,机构或处室ID，机构或处室名称
        String[] receivers = receiver.split(",");

        Map<String, List<MsgUser>> msgUserMap = new HashMap<>();
        List<MsgUser> msgUsers = new ArrayList<>();
        MsgUser msgUser = new MsgUser();
        msgUser.setHandleIdentify(receivers[0]);//类型
        msgUser.setReceiveUserId(receivers[1]);//机构或处室ID
        msgUser.setReceiveUsername(receivers[2]);//机构或处室名称
        msgUsers.add(msgUser);
        msgUserMap.put(receivers[0], msgUsers);

        param.setMsgUserMap(msgUserMap);

        messageService.sendMessage(param);
    }

    @Override
    public List<Map> selectRegInfoListById(String idStr) {
        List<Map> list = new ArrayList<Map>();
        if (idStr != null) {
            List<String> ids = Arrays.asList(idStr.split(","));
            list = baseMapper.selectRegInfoListById(ids);
        } else {
            list = baseMapper.selectRegInfoListById(null);
        }
        return list;
    }


}
