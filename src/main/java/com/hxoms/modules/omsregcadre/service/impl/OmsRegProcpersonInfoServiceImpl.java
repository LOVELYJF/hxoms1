package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.tree.TreeUtil;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegYearCheckIPagParam;
import com.hxoms.modules.omsregcadre.mapper.*;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.omsregcadre.service.OmsRegRevokeApplyService;
import com.hxoms.support.leaderInfo.entity.A01;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsRegProcpersonInfoServiceImpl extends ServiceImpl<OmsRegProcpersoninfoMapper, OmsRegProcpersoninfo> implements OmsRegProcpersonInfoService {

    @Autowired
    private A01Mapper a01Mapper;
    @Autowired
    private A30Mapper a30Mapper;
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

        //缓存民族
        HashMap<String, SysDictItem> hashMapNation = OmsCommonUtil.CacheDictItem("GB3304");

        //缓存政治面貌
        HashMap<String, SysDictItem> hashMapPolitical = OmsCommonUtil.CacheDictItem("GB4762");

        //缓存健康
        HashMap<String, SysDictItem> hashMapHealthy = OmsCommonUtil.CacheDictItem("GB2261D");

        //登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();

        List<OmsRegProcpersoninfo> updateRegList = new ArrayList();
        List<OmsRegProcpersoninfo> insertRegList = new ArrayList();

        if (a01list != null && hashMapReg.size() > 0) {
            for (A01 a01 : a01list) {
                OmsRegProcpersoninfo orpInfo = hashMapReg.get(a01.getA0100());

                boolean isNew = (orpInfo == null ? true : false);
                orpInfo = initData(orpInfo, a01, hashMapA02, hashMapA30, hashMapBaseInfo,
                        hashMapNation, hashMapPolitical, hashMapHealthy);


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
                orpInfo = initData(orpInfo, a01, hashMapA02, hashMapA30, hashMapBaseInfo,
                        hashMapNation, hashMapPolitical, hashMapHealthy);
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
                                          HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo,
                                          HashMap<String, SysDictItem> hashMapNation,
                                          HashMap<String, SysDictItem> hashMapPolitical,
                                          HashMap<String, SysDictItem> hashMapHealthy) throws ParseException {

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
        String incumbencyStatus = this.queryStatusByA0100(orpInfo, a01, hashMapA30);
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
        if (StringUilt.stringIsNullOrEmpty(a01.getA0117A()) &&
                StringUilt.stringIsNullOrEmpty(a01.getA0117()) == false) {
            SysDictItem dictItem = hashMapNation.get(a01.getA0117());
            if (dictItem != null)
                orpInfo.setNationName(dictItem.getItemName());
        } else
            orpInfo.setNationName(a01.getA0117A());

        //政治面貌
        orpInfo.setPoliticalAfficode(a01.getA0141());
        if (StringUilt.stringIsNullOrEmpty(a01.getA0141()) == false) {
            SysDictItem dictItem = hashMapPolitical.get(a01.getA0141());
            if (dictItem != null)
                orpInfo.setPoliticalAffiname(dictItem.getItemName());
        }

        //健康状态
        orpInfo.setHealthCode(a01.getA0127());
        if (StringUilt.stringIsNullOrEmpty(a01.getA0128()) &&
                StringUilt.stringIsNullOrEmpty(a01.getA0127()) == false) {
            SysDictItem dictItem = hashMapHealthy.get(a01.getA0127());
            if (dictItem != null)
                orpInfo.setHealth(dictItem.getItemName());
        } else
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
     *
     * @param
     * @return
     */
    private String queryStatusByA0100(OmsRegProcpersoninfo orpInfo, A01 a01, HashMap<String, A30> hashMapA30) {
        String incumbencyStatus = "";
        A30 a30 = hashMapA30.get(a01.getA0100());
        if (a01.getA0163().equals("2") && a30 != null && !StringUtils.isEmpty(a30.getA3001())) {
            //退出方式
            String a3001 = a30.getA3001().substring(0, 1);
            //退出状态 1退休 2调出 3死亡 4辞职 8转出 9其它(开除)
            //在职状态 1.在职、2.辞职、3.开除、4.解聘，5.免职撤职，6.退休，7.去世，8.调出，9.挂职到期，10.未匹配，99.其他
            if (!StringUtils.isEmpty(a3001)) {
                if (a3001.equals("1")) {//退休
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Retirement.getIndex());
                } else if (a3001.equals("2")) {//调出
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Dispatch.getIndex());
                } else if (a3001.equals("3")) {//死亡
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Death.getIndex());
                } else if (a3001.equals("4")) {//4辞职
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Resignation.getIndex());
                } else if (a3001.equals("92")) {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Expel.getIndex());
                } else if (a3001.equals("94")) {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Dismissal.getIndex());
                } else {
                    incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Other.getIndex());
                }
            }
            if (!StringUilt.stringIsNullOrEmpty(a30.getA3004())) {
                Date date = UtilDateTime.formatDate(a30.getA3004());
                orpInfo.setExitDate(date);
            }
        } else if (a01.getA0163().equals("1") && a01.getA0165().equals("01")) {
            incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Dispatch.getIndex());//省管变中管
        } else if (a01.getA0163().equals("1")) {
            incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Working.getIndex());//在职
        } else {
            incumbencyStatus = String.valueOf(Constants.emIncumbencyStatus.Unmatched.getIndex());
        }
        orpInfo.setIncumbencyStatus(incumbencyStatus);
        return incumbencyStatus;
    }

    /**
     * @description:缓存登记备案信息表到内存，key为身份证号（配偶子女）或A0100（干部）
     * @author:杨波
     * @date:2020-09-15 * @param
     * @return:java.util.HashMap<java.lang.String,com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo>
     **/
    @Override
    public HashMap<String, OmsRegProcpersoninfo> CacheRegProcpersonInfo(HashMap<String, OmsRegProcpersoninfo> nameAndIDCard) {

        List<OmsRegProcpersoninfo> omsRegProcpersoninfos = baseMapper.selectRegProcpersonInfo(null);
        //缓存登记备案人员到哈希表中
        HashMap<String, OmsRegProcpersoninfo> hashMapReg = new HashMap<>();
        for (OmsRegProcpersoninfo omsReg : omsRegProcpersoninfos
        ) {
            if (StringUilt.stringIsNullOrEmpty(omsReg.getA0100()))
                hashMapReg.put(omsReg.getIdnumberGa(), omsReg);//配偶子女\公安数据没有A0100
            else
                hashMapReg.put(omsReg.getA0100(), omsReg);//干部

            if (nameAndIDCard != null) {
                nameAndIDCard.put(omsReg.getSurname() + omsReg.getName() +
                        (omsReg.getIdnumberGa() == null ? omsReg.getIdnumberGb() : omsReg.getIdnumberGa()), omsReg);
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
    @Override
    public HashMap<String, List<Map<String, Object>>> CachePost(List<A01> a01list) {
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
            if (a02s == null) continue;
            a02s.add(map);
        }
        return hashMapA02;
    }

    /**
     * @description:以单位id和a0100为key
     * @author:杨波
     * @date:2020-09-19 * @param
     * @return:java.util.HashMap<java.lang.String,java.util.Map>
     **/
    @Override
    public HashMap<String, Map> CachePost() {

        //取职务并缓存
        Map<String, Object> params = new HashMap<String, Object>();
        List<Map<String, Object>> A02s = a01Mapper.selectPersonInfo(params);
        HashMap<String, Map> hashMapA02 = new HashMap<>();
        for (Map map : A02s
        ) {
//            if (map.get("a0201b") != null && map.get("a0201b").toString().length() > 0)
            hashMapA02.put(map.get("b0100").toString() + map.get("a0100").toString(), map);
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
    public String insertOmsRegGongAn(List<OmsRegProcpersoninfo> list) {
        List<OmsRegProcpersoninfo> newReg = new ArrayList<>();
        List<OmsRegProcpersoninfo> editReg = new ArrayList<>();

        List<OmsRegProcpersoninfo> oldgalist = omsRegProcpersonInfoService.selectListById(null);
        HashMap<String, OmsRegProcpersoninfo> hashMapOld = new HashMap<>();
        for (OmsRegProcpersoninfo oldgaData : oldgalist) {
            hashMapOld.put(oldgaData.getSurname() + oldgaData.getName() +
                    (StringUilt.stringIsNullOrEmpty(oldgaData.getIdnumberGb()) ? oldgaData.getIdnumberGa() : oldgaData.getIdnumberGb()), oldgaData);
        }
        for (OmsRegProcpersoninfo newgaData : list) {
            OmsRegProcpersoninfo oldgaData = hashMapOld.get(newgaData.getSurname() + newgaData.getName() +
                    (StringUilt.stringIsNullOrEmpty(newgaData.getIdnumberGb()) ? newgaData.getIdnumberGa() : newgaData.getIdnumberGb()));

            //数据比对更新
            if (oldgaData != null) {
                this.dataCompareAndUpdate(oldgaData, newgaData, false);
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
        return "本次导入，新增登记备案人员(" + newReg.size() + ")人合并(" + editReg.size() + ")人。";
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
    public Result mergeDataGBandGA(String idStr) {
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        queryWrapper.in("ID", idStr.split(","));
        queryWrapper.orderByAsc("DATA_TYPE");
        List<OmsRegProcpersoninfo> omsregList = baseMapper.selectList(queryWrapper);
        if (omsregList != null && omsregList.size() > 1) {
            OmsRegProcpersoninfo gbData = omsregList.get(0);
            OmsRegProcpersoninfo gaData = omsregList.get(1);
            boolean flag = true;
            //数据比对更新
            this.dataCompareAndUpdate(gbData, gaData, flag);
            int con = baseMapper.updateById(gbData);
            if (con > 0) {
                con = baseMapper.deleteById(gaData.getId());
            }
            return Result.success("合并成功！");
        }
        return Result.error("获取合并数据失败，可能被其他人员合并了！");
    }

    private int dataCompareAndUpdate(OmsRegProcpersoninfo dataGB, OmsRegProcpersoninfo dataGA, boolean flag) {
        int con = 0;
        //身份账号与名称一致
        if (flag == true || (dataGA.getIdnumberGa().equals(dataGB.getIdnumberGb() == null ? dataGB.getIdnumberGa() : dataGB.getIdnumberGb())
                && dataGB.getName().equals(dataGA.getName())
                && dataGB.getSurname().equals(dataGA.getSurname()))) {
            //更新干部相关信息从公安数据中维护
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
            if (StringUilt.equalsWithNull(dataGB.getWorkUnit(), dataGA.getWorkUnit()) == false ||
                    StringUilt.equalsWithNull(dataGB.getPost(), dataGA.getPost()) == false) {
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
        PageInfo<OmsRegProcpersoninfo> pageInfo = null;
        //分页
        PageUtil.pageHelp(personInfoIPagParam.getPageNum(), personInfoIPagParam.getPageSize());
        List<OmsRegProcpersoninfo> provinceCadresList = baseMapper.queryProvinceCadresList(personInfoIPagParam);
        pageInfo = new PageInfo(provinceCadresList);
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
    public Result extractRegPersonInfo() throws ParseException {
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

        //缓存民族
        HashMap<String, SysDictItem> hashMapNation = OmsCommonUtil.CacheDictItem("GB3304");

        //缓存政治面貌
        HashMap<String, SysDictItem> hashMapPolitical = OmsCommonUtil.CacheDictItem("GB4762");

        //缓存健康
        HashMap<String, SysDictItem> hashMapHealthy = OmsCommonUtil.CacheDictItem("GB2261D");

        List<OmsRegProcpersoninfo> updates = new ArrayList<>();
        List<OmsRegProcpersoninfo> adds = new ArrayList<>();

        //循环处理干部
        for (A01 a01 : a01list) {
            String a0100 = a01.getA0100();
            //判断是否已登记备案过，只查找到公安数据，视为未备案
            OmsRegProcpersoninfo omsreginfo = hashMapReg.get(a0100);
            if (omsreginfo != null && "2".equals(omsreginfo.getDataType()))
                omsreginfo = null;

            //已登记备案过
            if (omsreginfo != null) {
                if (DealRegistered(omsreginfo, a01, hashMapA02, hashMapA30, hashMapBaseInfo)) {
                    updates.add(omsreginfo);
                }
            }
            //未找到登记备案信息
            else {
                omsreginfo = nameAndIDCard.get(a01.getA0101() + a01.getA0184());

                OmsRegProcpersoninfo regProcpersoninfo = null;

                if (omsreginfo == null || (omsreginfo != null && "2".equals(omsreginfo.getDataType())))
                    regProcpersoninfo = initData(regProcpersoninfo, a01, hashMapA02, hashMapA30, hashMapBaseInfo,
                            hashMapNation, hashMapPolitical, hashMapHealthy);

                //根据身份证号和姓名找到了公安数据，合并
                if (omsreginfo != null && "2".equals(omsreginfo.getDataType())) {
                    dataCompareAndUpdate(regProcpersoninfo, omsreginfo, false);
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
        return Result.success("本次提取新增备案人员（" + adds.size() +
                ")人，变更备案人员(" + updates.size() + ")人，撤消备案人员(" + applies.size() + ")人。");
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
    private boolean DealRegistered(OmsRegProcpersoninfo omsreginfo, A01 a01,
                                   HashMap<String, List<Map<String, Object>>> hashMapA02,
                                   HashMap<String, A30> hashMapA30,
                                   HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo) {
        //记录是否需要重新登记备案
        boolean updated = false;
        //身份证号姓名一致
        String gbIdCard = (a01.getA0184() == null ? "" : a01.getA0184());
        String cgjIdCard = omsreginfo.getIdnumberGa() == null ? omsreginfo.getIdnumberGb() : omsreginfo.getIdnumberGa();
        if ((omsreginfo.getSurname() + omsreginfo.getName()).equals(a01.getA0101()) &&
                gbIdCard.equals(cgjIdCard)) {

            //在职状态沒有发生变化
            if (a01.getA0163().equals(omsreginfo.getIncumbencyStatus()) ||
                    ("2".equals(a01.getA0163()) && !omsreginfo.getIncumbencyStatus().equals("1"))) {
                //職務发生变化
                if (!a01.getA0192a().equals(omsreginfo.getPost())) {
                    //变更职务
                    SetPost(a01, omsreginfo, hashMapA02, hashMapBaseInfo);
                    //变更登记备案
                    ChangeRegisterState(omsreginfo);
                    updated = true;
                }
            }
            //在职状态发生变化
            else {
                //处理在职状态，退出人员要细分退出方式
                String incumbencyStatus = this.queryStatusByA0100(omsreginfo, a01, hashMapA30);
                omsreginfo.setIncumbencyStatus(incumbencyStatus);

                //在职状态发生变化，更新职务信息
                SetPost(a01, omsreginfo, hashMapA02, hashMapBaseInfo);

                //如果干部是在职状态，登记备案人员已经撤销，需要以新增方式重新登记备案
                if ("1".equals(a01.getA0163()) && omsreginfo.getInboundFlag().equals("D")) {
                    AddRegisterState(omsreginfo);
                    updated = true;
                }
                //如果干部是在职状态，更新职务信息，并以更新方式重新登记备案
                //调出后，还没有撤消登记备案又调回来
                else if ("1".equals(a01.getA0163())) {
                    //更新职务信息
                    ChangeRegisterState(omsreginfo);
                    updated = true;
                }
            }
        }
        //身份证号姓名不一致
        else {
            try {
                OmsCommonUtil.SendMessage(a01.getA0101() + "(" + a01.getA0184() + ")的身份证号或姓名与登记备案不一致，请核查！", "ExtractCadreForRegisterIDCardIllegal");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return updated;
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
