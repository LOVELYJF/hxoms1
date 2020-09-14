package com.hxoms.modules.dataCapture.synchdata;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.dataCapture.dataconfig.service.CutTargetDataSourceService;
import com.hxoms.modules.dataCapture.entity.DataSource;
import com.hxoms.modules.dataCapture.entity.DefultTargetDataSource;
import com.hxoms.modules.dataCapture.entity.DynamicData;
import com.hxoms.modules.dataCapture.extracttable.ExtractData;
import com.hxoms.modules.dataCapture.masterdata.service.DataCaptureService;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;
import com.hxoms.modules.keySupervision.nakedOfficial.service.OmsSupNakedSignService;
import com.hxoms.modules.omsmobilizingcadres.service.MobilizingcadreService;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Synchdata {

    @Autowired
    private DataCaptureService a01Service;

    @Autowired
    private CutTargetDataSourceService cutTargetDataSourceService;

    @Autowired
    private DefultTargetDataSource defultTargetDataSource;
    @Autowired
    private DynamicData dynamicData;
    @Autowired
    private InsertAndUpdate insertAndUpdate;

    @Autowired
    private ExtractData extractData;

    @Autowired
    private OmsSmrOldInfoService omsSmrOldInfoService;

    @Autowired
    private OmsSupNakedSignService omsSupNakedSignService;

    @Autowired
    MobilizingcadreService mobilizingcadreService;

    @Autowired
    private SelectMapper selectMapper;  // 通用 自定义sql

    // 需要插入的集合
    private List<Map> insertList;
    // 需要修改的集合
    private List<Map> updateList;
    // 需要对比身份证的集合
    private List<Map> idCardListByA01;

    //杨波 不需要判断身份证号
    //private Set<String> idCards = new HashSet<>();
    //緩存所有脫密期記錄，以a0100+b0100为key
    private HashMap<String, List<Object>> hashMapSmrOldPerson = new HashMap<>();
    //緩存所有登記備案人員，以a0100为key
    private HashMap<String, Map> hashMapMasterA01 = new HashMap<>();
    //緩存職務變動信息，以A0100+B0100為key
    private HashMap<String, Map> hashMapUpdatedPost = new HashMap<>();
    //新增的職務，以A0100+B0100為key
    private HashMap<String, Map> hashMapInsertedPost = new HashMap<>();
    //緩存退出信息，以a0100为key
    private HashMap<String, Map> hashMapLeaveInfo = new HashMap<>();
    //干部库基本信息
    private List<Map> targetMapA01 = null;
    //干部库職務，以a0100为key
    private HashMap<String, List<Map>> targetPosts = new HashMap<>();
    //出国境库职务，以a0100为key
    private HashMap<String, List<Map>> masterPosts = new HashMap<>();
    //-------yangbo---------

    private final Logger log = LoggerFactory.getLogger(getClass());


    public void synchronizationData() throws ParseException {
        DataSource dataSource = cutTargetDataSourceService.targetDataSource("dataSource", "2");
        if (dataSource == null) {
            dataSource = new DataSource();
            dataSource.setUserName(defultTargetDataSource.getUserName());
            dataSource.setUrl(defultTargetDataSource.getUrl());
            dataSource.setPassWord(defultTargetDataSource.getPassWord());
            dataSource.setDatabasetype(defultTargetDataSource.getDatabasetype());
            dataSource.setCode(defultTargetDataSource.getCode());
            dataSource.setDatasourceId(defultTargetDataSource.getDatasourceId());
        }

        //单位
        int b01counts = a01Service.getMasterB01Count();
        int b01targetCounts = extractData.getTargetB01Count(dataSource);
        List<Map> targetMapB01 = splicCountTarget(dataSource, b01targetCounts, "b0100");
        List<Map> masterMapB01 = splicCount(b01counts, "b0100");
        diffListMap(targetMapB01, masterMapB01, "b0100");

        //基本信息
        int a01counts = a01Service.getMasterA01Count();
        int a01targetCounts = extractData.getTargetA01Count(dataSource);
        targetMapA01 = splicCountTarget(dataSource, a01targetCounts, "a0100");
        List<Map> masterMapA01 = splicCount(a01counts, "a0100");

        //職務
        int a02counts = a01Service.getMasterA02Count();
        int a02targetCounts = extractData.getTargetA02Count(dataSource);
        List<Map> targetMapA02 = splicCountTarget(dataSource, a02targetCounts, "a0200");
        List<Map> masterMapA02 = splicCount(a02counts, "a0200");

        //退出管理
        int a30counts = a01Service.getMasterA30Count();
        int a30targetCounts = extractData.getTargetA30Count(dataSource);
        List<Map> targetMapA30 = splicCountTarget(dataSource, a30targetCounts, "a3000");
        List<Map> masterMapA30 = splicCount(a30counts, "a3000");

        //緩存幹部庫人員、出國境幹部庫人員、涉密信息、脫密期信息、退出信息、新增職務、變動職務
        CacheDataToHashMap(targetMapA01, masterMapA01, targetMapA02, masterMapA02, targetMapA30);

        diffListMap(targetMapA01, masterMapA01, "a0100");
        diffListMap(targetMapA02, masterMapA02, "a0200");
        diffListMap(targetMapA30, masterMapA30, "a3000");

        //出国境库如果没有记录，不需要做涉密人员、裸官、调整期干部的处理，第一次同步不处理业务问题
        if (masterMapA01.size() > 0) {
            DealRelatedBussiness();
        }

        //职级信息集
        int a05counts = a01Service.getMasterA05Count();
        int a05targetCounts = extractData.getTargetA05Count(dataSource);
        List<Map> targetMapA05 = splicCountTarget(dataSource, a05targetCounts, "a0500");
        List<Map> masterMapA05 = splicCount(a05counts, "a0500");
        diffListMap(targetMapA05, masterMapA05, "a0500");

        //专业技术职务信息集
        int a06counts = a01Service.getMasterA06Count();
        int a06targetCounts = extractData.getTargetA06Count(dataSource);
        List<Map> targetMapA06 = splicCountTarget(dataSource, a06targetCounts, "a0600");
        List<Map> masterMapA06 = splicCount(a06counts, "a0600");
        diffListMap(targetMapA06, masterMapA06, "a0600");

        //学历学位
        int a08counts = a01Service.getMasterA08Count();
        int a08targetCounts = extractData.getTargetA08Count(dataSource);
        List<Map> targetMapA08 = splicCountTarget(dataSource, a08targetCounts, "a0800");
        List<Map> masterMapA08 = splicCount(a08counts, "a0800");
        diffListMap(targetMapA08, masterMapA08, "a0800");

        //奖惩信息集
        int a14counts = a01Service.getMasterA14Count();
        int a14targetCounts = extractData.getTargetA14Count(dataSource);
        List<Map> targetMapA14 = splicCountTarget(dataSource, a14targetCounts, "a1400");
        List<Map> masterMapA14 = splicCount(a14counts, "a1400");
        diffListMap(targetMapA14, masterMapA14, "a1400");

        //年度考核信息集
        int a15counts = a01Service.getMasterA15Count();
        int a15targetCounts = extractData.getTargetA15Count(dataSource);
        List<Map> targetMapA15 = splicCountTarget(dataSource, a15targetCounts, "a1500");
        List<Map> masterMapA15 = splicCount(a15counts, "a1500");
        diffListMap(targetMapA15, masterMapA15, "a1500");

        //简历信息集
        int a17counts = a01Service.getMasterA17Count();
        int a17targetCounts = extractData.getTargetA17Count(dataSource);
        List<Map> targetMapA17 = splicCountTarget(dataSource, a17targetCounts, "a1700");
        List<Map> masterMapA17 = splicCount(a17counts, "a1700");
        diffListMap(targetMapA17, masterMapA17, "a1700");

        //家庭成员及社会关系信息集
        int a36counts = a01Service.getMasterA36Count();
        int a36targetCounts = extractData.getTargetA36Count(dataSource);
        List<Map> targetMapA36 = splicCountTarget(dataSource, a36targetCounts, "a3600");
        List<Map> masterMapA36 = splicCount(a36counts, "a3600");
        diffListMap(targetMapA36, masterMapA36, "a3600");

        InitialRights();
    }

    /**
     * @description:初始化权限
     * @author:杨波
     * @date:2020-09-12 * @param
     * @return:void
     **/
    private void InitialRights() {

        //检查机构模块是否有数据，有数据代表已经初始化
        String sql = "select count(1) as count from cf_org_module";
        SqlVo instance = SqlVo.getInstance(sql);
        List<LinkedHashMap<String, Object>> result = selectMapper.select(instance);
        if (result.get(0).get("count").toString().equals("0") ) {
            //修改机构的上级机构ID，因为机构主键不一样，干综以b0111为主键，出国境以b0100为主键
            sql = "update b01 inner join b01 as pb01 on b01.b0121=pb01.b0111 set b01.b0121=pb01.B0100";
            instance = SqlVo.getInstance(sql);
            selectMapper.update(instance);

            //查找顶级机构作为管理员所在机构
            sql = "select b0100 from b01 where b0121 is null";
            instance = SqlVo.getInstance(sql);
            result = selectMapper.select(instance);

            String b0100 = result.get(0).get("b0100").toString();
            if (b0100 != null && b0100.length() > 0) {

                //为机构分配模块权限
                sql="delete from cf_org_module where b0111='"+b0100+"'";
                instance = SqlVo.getInstance(sql);
                selectMapper.delete(instance);
                sql = "insert into cf_org_module(id, b0111, mu_id,modify_user,modify_time) select UUID(),'" + b0100 +
                        "',mu_id,'admin','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "' from cf_module";
                instance = SqlVo.getInstance(sql);
                selectMapper.insert(instance);

                //修改管理员所在机构
                sql = "update cf_user set org_id='" + b0100 + "' where user_id='00000000-0000-0000-0000-000000000000' ";
                instance = SqlVo.getInstance(sql);
                selectMapper.update(instance);

                //修改角色所在机构
                sql="update cf_role set org_id='" + b0100 + "'";
                instance = SqlVo.getInstance(sql);
                selectMapper.update(instance);

                //为管理员分配管理员角色
                sql="delete from cf_user_roles where user_id='00000000-0000-0000-0000-000000000000'";
                instance = SqlVo.getInstance(sql);
                selectMapper.delete(instance);
                sql = "insert into cf_user_roles(id,user_id,role_id) values(UUID(),'00000000-0000-0000-0000-000000000000','88888888-8888-8888-8888-8888888888')";
                instance = SqlVo.getInstance(sql);
                selectMapper.insert(instance);

                //为管理员角色分配机构权限
                sql="delete from cf_roleb01 where role_id='88888888-8888-8888-8888-8888888888'";
                instance = SqlVo.getInstance(sql);
                selectMapper.delete(instance);
                sql = "insert into cf_roleb01(id,org_id,role_id,modify_user,modify_time) select UUID(),b0100,'88888888-8888-8888-8888-8888888888','admin','" +
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "' from b01";
                instance = SqlVo.getInstance(sql);
                selectMapper.insert(instance);

                //为顶级机构分配能管理的机构
                sql="delete from cf_org_sub where b0111='"+b0100+"'";
                instance = SqlVo.getInstance(sql);
                selectMapper.delete(instance);
                sql = "insert into cf_org_sub(id,b0111,managed_org,modify_user,modify_time) select UUID(),'"+b0100+"',b0100,'admin','" +
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "' from b01 ";
                instance = SqlVo.getInstance(sql);
                selectMapper.insert(instance);
            }
        }
    }

    //TODO
    public void diffListMap(List<Map> targetMapList, List<Map> masterMapList, String tabelName) throws ParseException {

        if (targetMapList != null && targetMapList.size() > 0) {

            // String  sameMajorkey =tabelName;

            //   List<String> sameA0100 = new ArrayList();
            Set<String> sameA0100 = new HashSet<>();

            if (masterMapList != null && masterMapList.size() > 0) {

                for (Map masterMap : masterMapList) {

                    String a0100 = (String) masterMap.get(tabelName);
                    sameA0100.add(a0100);
                    //杨波 不需要判断身份证了
//                    if("a0100".equals(tabelName)){  //a01表需要身份证判断
//                        String a0184 =(String) masterMap.get("a0184");
//                        idCards.add(a0184);
//                    }
                    //----------yangbo---------
                }

            } else {

                log.info("主数据源为空，直接执行插入操作");

            }

            updateList = targetMapList.stream().filter((Map m) -> sameA0100.contains(m.get(tabelName))).collect(Collectors.toList());

            log.info("需要修改的集合长度为" + updateList.size() + "\t" + tabelName);

            if ("a0100".equals(tabelName)) {
                // 对A01单独处理，因为A01表有身份证号 当主键不一致的情况，判断身份证是否相同 插入情况(主键不一致，& 身份证号也不相同)
//            insertList =  targetMapList.stream().filter((Map m) ->(!sameA0100.contains(m.get(tabelName)) && !idCards.contains(m.get("a0184"))) ).collect(Collectors.toList());

                //楊波 ，不需要判断身份证号，只以A0100判断
                //insertList =  targetMapList.stream().filter((Map m) ->(!sameA0100.contains(m.get(tabelName)) && !idCards.contains(m.get("a0184")!=null?m.get("a0184"):"身份证为空")   )).collect(Collectors.toList());
                //idCardListByA01 =  targetMapList.stream().filter((Map m) ->(!sameA0100.contains(m.get(tabelName)) && idCards.contains(m.get("a0184")!=null?m.get("a0184"):"身份证为空") )).collect(Collectors.toList());
                insertList = targetMapList.stream().filter((Map m) -> !sameA0100.contains(m.get(tabelName))).collect(Collectors.toList());

                //------yangbo-------
//                log.info("需要进行身份证号修改集合长度为" + idCardListByA01.size() + "\t" + tabelName);
            } else {
                insertList = targetMapList.stream().filter((Map m) -> !sameA0100.contains(m.get(tabelName))).collect(Collectors.toList());
            }
            log.info("需要插入的集合长度为" + insertList.size() + "\t" + tabelName);

            if (insertList != null && insertList.size() > 0) {

                insertAndUpdate.insertTable(tabelName, insertList);

//            a01Service.insertMasterA01(insertList);
            }
            if (updateList != null && updateList.size() > 0) {
                insertAndUpdate.upDataTable(tabelName, updateList, idCardListByA01);
//            idCards.clear();
//            a01Service.updateMasterA01(updateList);
            }

        } else {
            log.info("目标数据源为空不需要任何操作");
        }

    }

    //TODO 分页查询 (Mysql)
    public List<Map> splicCount(int counts, String tableName) {

        //以每次 2500 条 为基准
        int splic = dynamicData.getInitSplic();
        int parts = counts / splic + 1;
        //偏移量
        int offset = 0;
        // 返回记录最大 行数
        int rows = splic;

        List<Map> masterListMap = new ArrayList<>();

        // 切割成了几份
        for (int i = 1; i <= parts; i++) {

            offset = (i - 1) * splic;

            switch (tableName) {
                case "a0100":
                    List<Map> masterMapA01 = a01Service.getMasterA01(offset, rows);

                    masterListMap.addAll(masterMapA01);
                    break;
                case "a0200":
                    List<Map> masterMapA02 = a01Service.getMasterA02(offset, rows);

                    masterListMap.addAll(masterMapA02);
                    break;
                case "a0500":
                    List<Map> masterMapA05 = a01Service.getMasterA05(offset, rows);

                    masterListMap.addAll(masterMapA05);
                    break;
                case "a0600":
                    List<Map> masterMapA06 = a01Service.getMasterA06(offset, rows);

                    masterListMap.addAll(masterMapA06);
                    break;
                case "a0800":
                    List<Map> masterMapA08 = a01Service.getMasterA08(offset, rows);

                    masterListMap.addAll(masterMapA08);
                    break;
                case "a1400":
                    List<Map> masterMapA14 = a01Service.getMasterA14(offset, rows);

                    masterListMap.addAll(masterMapA14);
                    break;
                case "a1500":
                    List<Map> masterMapA15 = a01Service.getMasterA15(offset, rows);

                    masterListMap.addAll(masterMapA15);
                    break;
                case "a1700":
                    List<Map> masterMapA17 = a01Service.getMasterA17(offset, rows);

                    masterListMap.addAll(masterMapA17);
                    break;
                case "a3600":
                    List<Map> masterMapA36 = a01Service.getMasterA36(offset, rows);

                    masterListMap.addAll(masterMapA36);
                    break;
                case "a3000":
                    List<Map> masterMapA30 = a01Service.getMasterA30(offset, rows);

                    masterListMap.addAll(masterMapA30);
                    break;
                case "b0100":
                    List<Map> masterMapB01 = a01Service.getMasterB01(offset, rows);

                    masterListMap.addAll(masterMapB01);
                    break;
                default:
            }
        }
        return masterListMap;
    }

    //TODO 分页查询 (orcal)
    public List<Map> splicCountTarget(DataSource dataSource, int counts, String tableName) {

        //以每次 2500 条 为基准
        int splic = dynamicData.getInitSplic();
        int parts = counts / splic + 1;
        //偏移量
        int minpage = 0;
        // 返回记录最大 行数
        int maxpage = 0;

        List<Map> targetListMap = new ArrayList<>();

        // 切割成了几份
        for (int i = 1; i <= parts; i++) {

            minpage = (i - 1) * splic;
            maxpage = i * splic;

            switch (tableName) {
                case "a0100":
                    List<Map<String, Object>> targetMapA01 = extractData.getTargetA01(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA01);
                    break;
                case "a0200":
                    List<Map<String, Object>> targetMapA02 = extractData.getTargetA02(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA02);
                    break;
                case "a0500":
                    List<Map<String, Object>> targetMapA05 = extractData.getTargetA05(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA05);
                    break;
                case "a0600":
                    List<Map<String, Object>> targetMapA06 = extractData.getTargetA06(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA06);
                    break;
                case "a0800":
                    List<Map<String, Object>> targetMapA08 = extractData.getTargetA08(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA08);
                    break;
                case "a1400":
                    List<Map<String, Object>> targetMapA14 = extractData.getTargetA14(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA14);
                    break;
                case "a1500":
                    List<Map<String, Object>> targetMapA15 = extractData.getTargetA15(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA15);
                    break;
                case "a1700":
                    List<Map<String, Object>> targetMapA17 = extractData.getTargetA17(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA17);
                    break;
                case "a3600":
                    List<Map<String, Object>> targetMapA36 = extractData.getTargetA36(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA36);
                    break;
                case "a3000":
                    List<Map<String, Object>> targetMapA30 = extractData.getTargetA30(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapA30);
                    break;
                case "b0100":
                    List<Map<String, Object>> targetMapB01 = extractData.getTargetB01(dataSource, maxpage, minpage);

                    targetListMap.addAll(targetMapB01);
                    break;
                default:
            }
        }
        return targetListMap;
    }

    private void CacheSmrOldInfo() throws ParseException {
        hashMapSmrOldPerson.clear();
        List<OmsSmrOldInfo> smrOldInfo = omsSmrOldInfoService.getSmrOldInfoById(1, 100000, "").getList();
        //原涉密信息
        for (Object o : smrOldInfo
        ) {
            OmsSmrOldInfo oldInfo = (OmsSmrOldInfo) o;

            List<Object> oldInfos = hashMapSmrOldPerson.get(oldInfo.getA0100());
            if (oldInfos != null)
                oldInfos.add(oldInfo);
        }

    }

    private void CacheDataToHashMap(List<Map> targetMapA01, List<Map> masterMapA01,
                                    List<Map> targetMapA02, List<Map> masterMapA02,
                                    List<Map> targetMapA30) throws ParseException {

        //將所有數據循環一次放到HASH表里，加快檢索速度
        //出國境幹部庫人員
        for (int i = 0; i < masterMapA01.size(); i++) {
            Map map = masterMapA01.get(i);
            String a0100 = map.get("a0100").toString();

            hashMapMasterA01.put(a0100, map);
            masterPosts.put(a0100, new ArrayList<>());
            hashMapSmrOldPerson.put(a0100, new ArrayList<>());
        }
        CacheSmrOldInfo();
        //退出信息
        for (int i = 0; i < targetMapA30.size(); i++) {
            Map map = targetMapA30.get(i);
            hashMapLeaveInfo.put(map.get("a0100").toString(), map);
        }


        //將每個人的職務放到hash表中，hash表以a0100为主键，以List保存每个职务
        for (int m = 0; m < targetMapA01.size(); m++) {
            String a0100 = targetMapA01.get(m).get("a0100").toString();
            targetPosts.put(a0100, new ArrayList<>());
        }

        CachePost(targetMapA02, targetPosts);

        CachePost(masterMapA02, masterPosts);

        for (int m = 0; m < targetMapA01.size(); m++) {
            Map cadreA01 = targetMapA01.get(m);
            String a0100 = cadreA01.get("a0100").toString();

            //幹部庫職務
            List<Map> tposts = targetPosts.get(a0100);
            if (tposts == null) continue;
            //出國境庫職務
            List<Map> mposts = masterPosts.get(a0100);
            for (int i = 0; i < tposts.size(); i++) {
                Map tMap = tposts.get(i);
                if (tMap.get("a0201b") == null || tMap.get("a0201b").toString().length() < 0)
                    continue;
                //判斷幹部庫的職務是否導入，導入后是否發生變化
                Map changePost = null;
                boolean exists = false;
                if (mposts != null) {
                    for (int j = 0; j < mposts.size(); j++) {
                        Map mMap = mposts.get(j);
                        //沒有發生變化
                        if (tMap.get("a0200") == mMap.get("a0200") &&//職務表主鍵
                                tMap.get("a0201a") == mMap.get("a0201a") &&//任職機構名稱
                                tMap.get("a0215a") == mMap.get("a0215a") &&//職務名稱
                                tMap.get("a0255") == mMap.get("a0255")) //任职状态
                        {
                            exists = true;
                            break;
                        }

                        //職務發生變化
                        if (tMap.get("a0200") == mMap.get("a0200") &&//職務表主鍵
                                (tMap.get("a0201a") != mMap.get("a0201a") ||//任職機構名稱
                                        tMap.get("a0215a") != mMap.get("a0215a") ||//職務名稱
                                        tMap.get("a0255") != mMap.get("a0255")))  //任职状态
                        {
                            exists = true;
                            //在职的才处理职务变化，不在职的，全部职务都要处理脱密期，
                            // 避免没有设置职务的免职时间而采用当前时间作为脱密期开始时间，
                            //退出人员的脱密期开始时间取退出信息集的时间
                            if (cadreA01.get("a0163").toString() == "1")
                                changePost = tMap;
                            break;
                        }
                    }//mposts
                }

                if (changePost != null) {
                    //缓存变化了的职务
                    hashMapUpdatedPost.put(a0100 + changePost.get("a0201b").toString(), changePost);
                } else if (exists == false) {
                    //缓存新加的职务
                    hashMapInsertedPost.put(a0100 + tMap.get("a0201b").toString(), tMap);
                }
            }//tposts

            //如果干部库删除了的职务，出国境干部库对应的职务设置为不在职状态
            if (mposts != null) {
                List<Map> cadreDBDeletedPost = new ArrayList<>();
                for (int i = 0; i < mposts.size(); i++) {
                    boolean find = false;
                    for (int j = 0; j < tposts.size(); j++) {
                        if (tposts.get(j).get("id") == mposts.get(i).get("id")) {
                            find = true;
                            break;
                        }
                    }
                    if (find == false) {
                        mposts.get(i).put("a0255", "0");
                        cadreDBDeletedPost.add(mposts.get(i));
                    }
                }
                if (cadreDBDeletedPost.size() > 0) {
                    insertAndUpdate.upDataTable("a0200", cadreDBDeletedPost, null);
                }
            }

        }//targetMapA01
    }//CacheDataToHashMap

    /**
     * @description:处理涉密人员脱密期、退出裸官、调整期干部等业务
     * @author:杨波
     * @date:2020-09-09
     * @return:void
     **/
    private void DealRelatedBussiness() throws ParseException {
        //处理涉密人员
        // 新入职省管干部在登记备案时要更新原涉密信息表的PROC_PERSON_ID和身份证号字段，因为这里无法确认登记备案表的主键

        //根据变化了的职务，设置脱密期
        List<OmsSmrOldInfo> updateOldInfos = new ArrayList<>();
        for (String key : hashMapUpdatedPost.keySet()
        ) {
            Map updatePost = hashMapUpdatedPost.get(key);
            String a0100 = updatePost.get("a0100").toString();
            String b0100 = updatePost.get("a0201b").toString();

            List<Object> oldInfos = hashMapSmrOldPerson.get(a0100);
            if (oldInfos == null || oldInfos.size() == 0) continue;

            for (Object o : oldInfos
            ) {
                OmsSmrOldInfo oldInfo = (OmsSmrOldInfo) o;
                //不是当前单位或者脱密开始时间已经设置，跳过
                if (oldInfo.getA0100() != a0100 ||
                        oldInfo.getB0100() != b0100 ||
                        oldInfo.getStartDate() != null) continue;

                CalcDeclassification(updatePost, oldInfo);
                updateOldInfos.add(oldInfo);
            }
        }
        if (updateOldInfos.size() > 0)
            omsSmrOldInfoService.updateBatchById(updateOldInfos);

        //将新添加的职务放到OmsSmrOldInfo（脱密信息）表中
        List<OmsSmrOldInfo> newOldInfos = new ArrayList<>();
        for (String key : hashMapInsertedPost.keySet()
        ) {
            Map insertPost = hashMapInsertedPost.get(key);
            OmsSmrOldInfo smrOld = new OmsSmrOldInfo();
            smrOld.setId(UUIDGenerator.getPrimaryKey());
            smrOld.setA0100(insertPost.get("a0100").toString());
            smrOld.setB0100(insertPost.get("a0201b").toString());
            smrOld.setImportYear(new SimpleDateFormat("yyyy").format(new Date()));

            //为不在职的设置脱密期，有可能添加以免职务
            if (insertPost.get("a0255") == "0") {
                CalcDeclassification(insertPost, smrOld);
            }
            newOldInfos.add(smrOld);
        }
        if (newOldInfos.size() > 0)
            omsSmrOldInfoService.saveBatch(newOldInfos);

        //数据保存后从新从数据库取数据初始化缓存，没有试hashMapInsertedPost时往hashMapSmrOldPerson添加缓存，
        // 不重新查数据库，在后面处理退出人员胶密期时会报数据冲突错误不
        CacheSmrOldInfo();

        //查询所有未撤销裸官
        Page<OmsSupNakedSign> page = new Page<>();
        page.setCurrent(1);
        page.setSize(100000);
        OmsSupNakedSign nakedSign = new OmsSupNakedSign();
        nakedSign.setIsDelete("0");
        List<OmsSupNakedSign> nakedSigns = omsSupNakedSignService.getNakedOfficialList(page, nakedSign, null).getRecords();

        //为退出干部设置脱密期，处理退出干部的裸官状态
        //根据干综库的干部，如果出国境存在，并且状态不一致，且干综库的管理状态不是在职
        //所有退出方式的干部都根据退出时间设置脱密期，没有设置退出信息集的，取当前时间作为脱密期开始时间
        List<OmsSmrOldInfo> smrOldInfos = new ArrayList<>();
        for (Map map : targetMapA01
        ) {
            String a0100 = map.get("a0100").toString();

            Map masterCadre = hashMapMasterA01.get(a0100);
            //如果不在职了，要处理脱密期，先不判断退出类型，都给生成脱密期
            if (masterCadre != null && masterCadre.get("a0163").toString() != map.get("a0163").toString() &&
                    map.get("a0163").toString() != "1") {
                //只处理脱密期没有计算过的，防止以前职务变化设置过脱密期的被修改
                //以退出信息集的日期为脱密开始日期，没有退出信息的，以当前时间为脱密开始时间

                //取原涉密信息
                List<Object> oldInfos = hashMapSmrOldPerson.get(a0100);
                if (oldInfos != null && oldInfos.size() > 0) {
                    String startDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    //检查是否有退出日期
                    Map exitMap = hashMapLeaveInfo.get(a0100);
                    if (exitMap != null && exitMap.get("a3004") != null && exitMap.get("a3004").toString().length() > 0) {
                        startDate = exitMap.get("a3004").toString();
                        if (startDate.length() < 7)
                            startDate += "01";
                    }
                    Date declassificationStartDate = new SimpleDateFormat("yyyyMMdd").parse(startDate);
                    for (Object o : oldInfos
                    ) {
                        OmsSmrOldInfo oldInfo = (OmsSmrOldInfo) o;
                        //已经设置过脱密期的，不再处理
                        if (oldInfo.getStartDate() != null) continue;

                        Date declassificationEndDate = CalcDeclassificationFinishDate(declassificationStartDate, oldInfo);
                        SetDeclassificationInfo(declassificationStartDate, declassificationEndDate, oldInfo);

                        smrOldInfos.add(oldInfo);
                    }
                }

                //处理裸官
                for (OmsSupNakedSign nakedSign1 : nakedSigns) {
                    if (nakedSign1.getA0100() != a0100) continue;
                    nakedSign1.setIsDelete("1");
                    omsSupNakedSignService.updateOmsNaked(nakedSign1);
                }
            }
        }
        if (smrOldInfos.size() > 0) {
            omsSmrOldInfoService.updateBatchById(smrOldInfos);
        }

        //处理调整期干部
        PageInfo info = mobilizingcadreService.getAllMobilizingCadre(1, 100000, null, "", "0");
        List<Map> mobilizingCadres = info.getList();
        for (Map map : mobilizingCadres
        ) {
            String a0100 = map.get("a0100").toString();
            if (hashMapUpdatedPost.get(a0100) != null || hashMapInsertedPost.get(a0100) != null) {
                mobilizingcadreService.updateStatus(a0100);
            }
        }
    }

    private void CachePost(List<Map> a02, HashMap<String, List<Map>> post) {
        for (int j = 0; j < a02.size(); j++) {
            Map map = a02.get(j);
            String a0100 = map.get("a0100").toString();
            List<Map> posts = post.get(a0100);
            posts.add(map);
        }
    }

    private void CalcDeclassification(Map post, OmsSmrOldInfo smrOldInfo) throws ParseException {
        String startDate = "";
        if (post.get("a0265") == null || post.get("a0265").toString().length() <= 0) {
            startDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            startDate = post.get("a0265").toString();
        }

        Date declassificationStartDate = new SimpleDateFormat("yyyyMMdd").parse(startDate);
        Date declassificationEndDate = CalcDeclassificationFinishDate(declassificationStartDate, smrOldInfo);

        SetDeclassificationInfo(declassificationStartDate, declassificationEndDate, smrOldInfo);
    }

    private Date CalcDeclassificationFinishDate(Date declassificationStartDate, OmsSmrOldInfo smrOldInfo) {
        Calendar c = Calendar.getInstance();
        c.setTime(declassificationStartDate);
        c.add(Calendar.YEAR, Integer.parseInt(smrOldInfo.getSecretRelatedLevel()));
        Date declassificationEndDate = c.getTime();

        return declassificationEndDate;
    }

    private void SetDeclassificationInfo(Date declassificationStartDate, Date declassificationEndDate,
                                         OmsSmrOldInfo smrOldInfo) {
        smrOldInfo.setStartDate(declassificationStartDate);
        smrOldInfo.setFinishDate(declassificationEndDate);
        smrOldInfo.setQrStartDate(smrOldInfo.getStartDate());
        smrOldInfo.setQrFinishDate(smrOldInfo.getFinishDate());
        smrOldInfo.setQrSecretRelatedLevel(smrOldInfo.getSecretRelatedLevel());
        smrOldInfo.setQrSecretRelatedPost(smrOldInfo.getSecretRelatedPost());
    }
    //TODO 统一修改方法
//   public void updateTable(String tableTame){
//
//       List<List<Map>> splicUpdateList = fixedGrouping(updateList,dynamicData.getInitSplic());
//       if(splicUpdateList !=null && splicUpdateList.size()>0){
//
//           for(int i =0;i<splicUpdateList.size();i++){
//
//               a01Service.updateMasterA17(splicUpdateList.get(i));
//
//           }
//
//       }
//   }


    public static void main(String[] args) {


//        List a = new ArrayList();
//        a.add(1);
//        a.add(2);
//
//        if(a.contains(null)){
//            System.out.println(true);
//
//        }else{
//            System.out.println(false);
//        }
//
//
//        Map  map = new HashMap();

        //以每次 2500 条 为基准
//       int splic = 2500;
//       int parts =   55667/splic +1;
//
//       int fristCount =0;
//
//       int lastCount = splic;
//
//
//       // 切割成了几份
//       for(int i=1;i<= parts; i++){
//
//           fristCount = (i-1)*splic;
//
//
//           System.out.println("fristCount : lastCount" + fristCount+"\t" + lastCount);
//
//
//
//
//       }

        List list = new ArrayList();
        Map map = new HashMap();

        System.out.println(list.contains(2));
        System.out.println(map.get("wjf"));

        String str = "w";


    }


}
