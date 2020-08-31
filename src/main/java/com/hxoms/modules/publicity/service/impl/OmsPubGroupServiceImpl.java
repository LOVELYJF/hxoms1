package com.hxoms.modules.publicity.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.publicity.destroyReg.entity.OmsPubDestroydetail;
import com.hxoms.modules.publicity.destroyReg.mapper.OmsPubDestroydetailMapper;
import com.hxoms.modules.publicity.docCallback.entity.OmsPubDoccallbackdetail;
import com.hxoms.modules.publicity.docCallback.mapper.OmsPubDoccallbackdetailMapper;
import com.hxoms.modules.publicity.entity.*;
import com.hxoms.modules.publicity.mapper.OmsPubApplyChangeMapper;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.mapper.OmsPubGroupMapper;
import com.hxoms.modules.publicity.service.OmsPubGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsPubGroupServiceImpl extends ServiceImpl<OmsPubGroupMapper, OmsPubGroupPreApproval> implements OmsPubGroupService {


    @Autowired
    private OmsPubGroupMapper pubGroupMapper;
    @Autowired
    private OmsPubApplyMapper pubApplyMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper regProcpersoninfoMapper;
    @Autowired
    private OmsPubApplyChangeMapper pubApplyChangeMapper;
    @Autowired
    private OmsPubDestroydetailMapper pubDestroydetailMapper;
    @Autowired
    private OmsPubDoccallbackdetailMapper pubDoccallbackdetailMapper;
    @Autowired
    private OmsConditionService omsConditionService;


    @Override
    public PageInfo<OmsPubGroupPreApproval> getPubGroupList(Integer pageNum, Integer pageSize,Map<String,String> param) {
        List<OmsPubGroupPreApproval> resultList = pubGroupMapper.getPubGroupList(param);
        PageUtil.pageHelp(pageNum, pageSize);
        PageInfo<OmsPubGroupPreApproval> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    @Override
    public String insertPubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList) {
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        List<OmsPubApplyVO> personList = pubGroupAndApplyList.getOmsPubApplyVOList();
        List<OmsPubApply> applyList = new ArrayList<>();
        int num = personList.size();
        if(num > 0){
            //登录用户信息
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            //团组信息
            String id = UUIDGenerator.getPrimaryKey();
            pubGroup.setId(id);
            pubGroup.setTzrs(num);
            pubGroup.setSqzt(2);
            pubGroup.setCreateUser(userInfo.getId());
            pubGroup.setCreateTime(new Date());
            pubGroupMapper.insertPubGroup(pubGroup);
            //出国人员信息
            for(int i = 0; i < num; i++ ){
                OmsPubApply pubApply = new OmsPubApply();
                if("0".equals(pubGroup.getSource())){
                    pubApply = getInsertOmsPubApply(personList.get(i).getProcpersonId());
                    String fmxx = omsConditionService.selectNegativeInfo(pubApply.getA0100(),pubApply.getCgsj());
                    pubApply.setFmxx(fmxx);
                }
                pubApply.setZtdw(pubGroup.getZtdw());
                pubApply.setCgsj(pubGroup.getCgsj());
                pubApply.setHgsj(pubApply.getHgsj());
                pubApply.setSdgj(pubGroup.getSdgj());
                pubApply.setTlsj(pubGroup.getTjgj());
                pubApply.setCfrw(pubGroup.getCfrw());
                pubApply.setCfsy(pubGroup.getCfsy());
                pubApply.setSfxd(1);
                pubApply.setSfzb("0");
                applyList.add(pubApply);
            }
            pubApplyMapper.insertPubApplyList(applyList);
            return id;
        }else{
            return "未选择备案人员";
        }
    }

    @Override
    public void updatePubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList,String bgyy) {
        if (pubGroupAndApplyList == null || StringUtils.isBlank(bgyy)){
            throw new CustomMessageException("参数为空!");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //创建所需对象
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        OmsPubGroupPreApproval pubGroupOld = pubGroupMapper.getPubGroupDetailById(pubGroup.getId());
        List<OmsPubApplyVO> personList = pubGroupAndApplyList.getOmsPubApplyVOList();
        List<OmsPubApply> applyList = new ArrayList<>();
        int num = personList.size();
        pubGroupMapper.updatePubGroup(pubGroup);
        if(num > 0){
            //出国人员信息
            for(int i = 0; i < num; i++ ){
                OmsPubApply pubApply = new OmsPubApply();
                pubApply.setId(personList.get(i).getId());
                pubApply.setA0100(personList.get(i).getA0100());
                pubApply.setB0100(personList.get(i).getB0100());
                pubApply.setAge(personList.get(i).getAge());
                pubApply.setHealth(personList.get(i).getHealth());
                pubApply.setSfsmry(personList.get(i).getSfsmry());
                pubApply.setZjcgqk(personList.get(i).getZjcgqk());
                pubApply.setSqzt(personList.get(i).getSqzt());
                pubApply.setModifyUser(userInfo.getId());
                pubApply.setModifyTime(new Date());
                applyList.add(pubApply);
            }
            pubApplyMapper.updatePubApplyList(applyList);
        }

        //添加变更记录
        OmsPubApplyChange pubApplyChange = new OmsPubApplyChange();
        pubApplyChange.setId(UUIDGenerator.getPrimaryKey());
        pubApplyChange.setTtId(pubGroup.getId());
        pubApplyChange.setYcgsj(pubGroupOld.getCgsj());
        pubApplyChange.setYhgsj(pubGroupOld.getHgsj());
        pubApplyChange.setYsdgj(pubGroupOld.getSdgj());
        pubApplyChange.setYtjgj(pubGroupOld.getTjgj());
        pubApplyChange.setYcfsy(pubGroupOld.getCfsy());
        pubApplyChange.setYcfrw(pubGroupOld.getCfrw());
        pubApplyChange.setXcgsj(pubGroup.getCgsj());
        pubApplyChange.setXhgsj(pubGroup.getHgsj());
        pubApplyChange.setXsdgj(pubGroup.getSdgj());
        pubApplyChange.setXtjgj(pubGroup.getTjgj());
        pubApplyChange.setXcfsy(pubGroup.getCfsy());
        pubApplyChange.setXcfrw(pubGroup.getCfrw());
        pubApplyChange.setSqzt(pubGroupOld.getSqzt());
        pubApplyChange.setBgyy(bgyy);
        pubApplyChange.setModifyUser(userInfo.getId());
        pubApplyChange.setModifyTime(new Date());

        pubApplyChangeMapper.insertSelective(pubApplyChange);
    }

    @Override
    public void deletePubGroup(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("参数为空!");
        }
        pubApplyMapper.deletePubApplyByYSPId(id);
        pubGroupMapper.deletePubGroup(id);
    }

    @Override
    public OmsPubGroupAndApplyList uploadPubGroupJson(MultipartFile file) throws IOException {
        if (file == null){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubGroupAndApplyList omsPubGroupAndApplyList = readJsonData(file);
        return omsPubGroupAndApplyList;
    }

    @Override
    public List<OmsPubApplyVO> checkoutPerson(List<OmsPubApplyVO> list) {
        if(list.size() < 0){
            throw new CustomMessageException("未选择人员!");
        }
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getProcpersonId();
            List<Map<String,String>> result = omsConditionService.checkConditionByA0100(id,"oms_pub_apply");
            String fmxx = omsConditionService.selectNegativeInfo(list.get(i).getA0100(),list.get(i).getCgsj());
            list.get(i).setCheckResult(result);
            list.get(i).setFmxx(fmxx);
        }
        return list;
    }

    @Override
    public void insertPerson(String personId,String pubId) {
        if (StringUtils.isBlank(personId) || StringUtils.isBlank(pubId)){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubApply pubApply = getInsertOmsPubApply(personId);
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(pubId);
        pubApply.setZtdw(pubGroup.getZtdw());
        pubApply.setCgsj(pubGroup.getCgsj());
        pubApply.setHgsj(pubApply.getHgsj());
        pubApply.setSdgj(pubGroup.getSdgj());
        pubApply.setTlsj(pubGroup.getTjgj());
        pubApply.setCfrw(pubGroup.getCfrw());
        pubApply.setCfsy(pubGroup.getCfsy());

        String fmxx = omsConditionService.selectNegativeInfo(pubApply.getA0100(),pubApply.getCgsj());
        pubApply.setFmxx(fmxx);
        pubApply.setSfysp("1");
        pubApply.setSfzb("1");
        if(pubApplyMapper.insert(pubApply) < 1){
            throw new CustomMessageException("添加失败!");
        }

    }

    @Override
    public void backoutPerson(String id,String cxyy) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(cxyy)){
            throw new CustomMessageException("参数为空!");
        }
        //撤销团组
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(id);
        if(pubGroup != null){
            pubGroup.setSqzt(0);
            pubGroup.setCxyy(cxyy);
            if(pubGroupMapper.updatePubGroup(pubGroup) < 1){
                throw new CustomMessageException("撤销失败!");
            }
    }
        //批量撤销人员
        List<OmsPubApply> applylist = pubGroupMapper.getPubApplyByYspId(id);
        if(applylist.size()>0){
            for (int i = 0; i < applylist.size(); i++) {
                pubApplyMapper.repealPubApplyById(applylist.get(i).getId(),cxyy, Constants.private_business[7]);
            }
        }
    }

    @Override
    public void regainPerson(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("参数为空!");
        }
        //恢复团组
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(id);
        if(pubGroup != null){
            pubGroup.setSqzt(1);
            if(pubGroupMapper.updatePubGroup(pubGroup) < 1){
                throw new CustomMessageException("恢复失败!");
            }
        }
        //批量恢复人员
        List<OmsPubApply> applylist = pubGroupMapper.getPubApplyByYspId(id);
        if(applylist.size()>0){
            for (int i = 0; i < applylist.size(); i++) {
                if(pubGroup.getCxyy().equals(applylist.get(i).getCxyy())){
                    pubApplyMapper.repealPubApplyById(applylist.get(i).getId(),null, Constants.private_business[0]);
                }
            }
        }
    }

    @Override
    public OmsPubGroupAndApplyList getPubGroupDetailById(String yspId) {
        if (StringUtils.isBlank(yspId)){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubGroupAndApplyList beanList = new OmsPubGroupAndApplyList();
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(yspId);
        List<OmsPubApplyVO> applyList = pubApplyMapper.selectByYSPId(yspId);
        for (int i = 0; i < applyList.size(); i++) {
            List<Map<String,String>> result = omsConditionService.checkConditionByA0100(applyList.get(i).getProcpersonId(),"oms_pub_apply");
            applyList.get(i).setCheckResult(result);
        }
        beanList.setOmsPubGroupPreApproval(pubGroup);
        beanList.setOmsPubApplyVOList(applyList);
        return beanList;
    }

    @Override
    public OmsPubApply getPersonDetailById(String id) {
        return pubApplyMapper.selectById(id);
    }

    @Override
    public Map<String, Object> getBackoutDetailById(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("参数为空!");
        }
        Map<String, Object> resultMap = new HashMap<>();
        //获取团组详情
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(id);
        //获取撤销原因
        String cxyy = pubGroup.getCxyy();
        //获取销毁记录
        List<OmsPubDestroydetail> pubDestroydetailList = pubDestroydetailMapper.getPubDestroydetailByYspId(id);
        //获取回收记录
        List<OmsPubDoccallbackdetail> pubDoccallbackdetailList= pubDoccallbackdetailMapper.getPubDoccallbackdetailByYspId(id);
        //返回结果
        resultMap.put("cxyy",cxyy);
        resultMap.put("pubDestroydetailList",pubDestroydetailList);
        resultMap.put("pubDoccallbackdetailList",pubDoccallbackdetailList);
        return resultMap;
    }

    @Override
    public List<OmsPubApplyVO> getAuditOpinion(String yspId) {
        if (StringUtils.isBlank(yspId)){
            throw new CustomMessageException("参数为空!");
        }
        return pubApplyMapper.selectByYSPId(yspId);
    }

    @Override
    public void sendTask(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.selectById(id);
        pubGroup.setSqzt(2);
        pubGroupMapper.updatePubGroup(pubGroup);
    }

    @Override
    public String uploadApproval(MultipartFile file, String id) {
        if (StringUtils.isBlank(id) ){
            throw new CustomMessageException("参数为空!");
        }
        //获得文件的名称
        String fileAllName = file.getOriginalFilename();
        //获得文件的扩展名称
        String fileName = null;
        if ((fileAllName != null) && (fileAllName.length() > 0)) {
            int dot = fileAllName.lastIndexOf('.');
            if ((dot >-1) && (dot < (fileAllName.length()))) {
                fileName = fileAllName.substring(0, dot);
            }
            //更新批文号
            OmsPubGroupPreApproval pubGroup = new OmsPubGroupPreApproval();
            pubGroup.setId(id);
            pubGroup.setZypwh(fileName);
            pubGroup.setSqzt(3);
            if(pubGroupMapper.updatePubGroup(pubGroup) < 1){
                throw new CustomMessageException("批文号上传失败！");
            }
        }
        return fileName;
    }

    @Override
    public String updateApproval(String pwh, String id) {
        if (StringUtils.isBlank(pwh) || StringUtils.isBlank(id)){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubGroupPreApproval pubGroup = new OmsPubGroupPreApproval();
        pubGroup.setId(id);
        pubGroup.setZypwh(pwh);
        if(pubGroupMapper.updatePubGroup(pubGroup) < 1){
            throw new CustomMessageException("批文号更新失败！");
        }
        return "批文号更新成功！";
    }

    @Override
    public List<Map<String,String>> getNumByStatus(String bazt) {
        return pubGroupMapper.getNumByStatus(bazt);
    }

    /**
     * 读取上传Json的数据(导入用)
     * @return OmsPubGroupAndApplyList
     */
    public OmsPubGroupAndApplyList readJsonData(MultipartFile file) throws IOException {
        //读取数据
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String tempStr;
        while ((tempStr = reader.readLine()) != null) {
            sbf.append(tempStr);
        }
        reader.close();

        //转化数据
        String obj = sbf.toString().trim();
        JSONObject jsonObj = new JSONObject();
        if("[".equals(obj.substring(0,1))){
            jsonObj = JSONObject.parseObject(obj.substring(1,obj.length()-1));
        }else{
            jsonObj = JSONObject.parseObject(obj);
        }
        //map对象
        Map<String, Object> jsonData =new HashMap<>();
        //循环转换
        Iterator it =jsonObj.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            jsonData.put(entry.getKey(), entry.getValue());
        }

        //创建对象
        OmsPubGroupPreApproval omsPubGroupPreApproval = new OmsPubGroupPreApproval();
        List<OmsPubApplyVO> applyVOList = new ArrayList<>();
        OmsPubGroupAndApplyList omsPubGroupAndApplyList = new OmsPubGroupAndApplyList();

        //团组解析
        omsPubGroupPreApproval.setTzmc(jsonData.get("团组名称").toString());
        omsPubGroupPreApproval.setTzcy(jsonData.get("团组成员").toString());
        omsPubGroupPreApproval.setTzrs(Integer.parseInt(jsonData.get("团组人数").toString()));
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        try{
            omsPubGroupPreApproval.setCgsj(sdf.parse(jsonData.get("出国时间").toString()));
        }catch (Exception e){
            e.printStackTrace();
        }
        omsPubGroupPreApproval.setZwtlsj(Integer.parseInt(jsonData.get("在外天数").toString()));
        omsPubGroupPreApproval.setCfsy(jsonData.get("出访事由").toString());
        omsPubGroupPreApproval.setCfrw(jsonData.get("出访任务").toString());
        omsPubGroupPreApproval.setSdgj(jsonData.get("出访国家").toString());
        omsPubGroupPreApproval.setTjgj(jsonData.get("途经国家").toString());
        omsPubGroupPreApproval.setZtdw(jsonData.get("组团单位").toString());
        omsPubGroupPreApproval.setFylykzxm(jsonData.get("费用来源开支项目").toString());

        //团组人员解析
        JSONArray jsonArray = (JSONArray) jsonData.get("省管干部");
        for (int i = 0; i < jsonArray.size(); i++){
            OmsPubApplyVO omsPubApplyVO = new OmsPubApplyVO();
            omsPubApplyVO.setName(jsonArray.getJSONObject(i).get("姓名").toString());
            //根据身份证号获取人员A0100等信息
            Map<String,Object> map = new HashMap<>();
            String idCardNum = jsonArray.getJSONObject(i).get("身份证号").toString();
            map.put("idCardNum",idCardNum);
            OmsRegProcpersoninfo regProcpersoninfo = regProcpersoninfoMapper.selectRegIdByMap(map);
            if(regProcpersoninfo != null){
                omsPubApplyVO.setA0100(regProcpersoninfo.getId());
                omsPubApplyVO.setStatus(regProcpersoninfo.getIncumbencyStatus());
            }

            omsPubApplyVO.setIdnumber(idCardNum);
            omsPubApplyVO.setB0101(jsonArray.getJSONObject(i).get("工作单位").toString());
            omsPubApplyVO.setJob(jsonArray.getJSONObject(i).get("职务").toString());
            omsPubApplyVO.setZjcgqk(jsonArray.getJSONObject(i).get("最近一次因公出国信息").toString());
            applyVOList.add(omsPubApplyVO);
        }
        //封装整合对象
        omsPubGroupAndApplyList.setOmsPubGroupPreApproval(omsPubGroupPreApproval);
        omsPubGroupAndApplyList.setOmsPubApplyVOList(applyVOList);
        return omsPubGroupAndApplyList;
    }

    /**
     * 封装插入时所需人员实体类
     * @param id(人员id)
     * @return OmsPubApply
     */
    private OmsPubApply getInsertOmsPubApply(String id){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        OmsPubApply pubApply = new OmsPubApply();
        OmsRegProcpersoninfo personInfo = regProcpersoninfoMapper.selectById(id);
        if(personInfo == null){
            return pubApply;
        }
        pubApply.setId(UUIDGenerator.getPrimaryKey());
        pubApply.setProcpersonId(id);
        pubApply.setA0100(personInfo.getA0100());
        pubApply.setB0100(personInfo.getRfB0000());
        pubApply.setHealth(personInfo.getHealth());
        pubApply.setSfzyld(personInfo.getMainLeader());
        pubApply.setSflg(personInfo.getNf());
        if(StringUtils.isBlank(personInfo.getSecretLevel())){
            pubApply.setSfsmry("0");
        }else{
            pubApply.setSfsmry("1");
        }
        List<OmsPubApply> list = pubApplyMapper.selectPubAbroadLatestInfo(personInfo.getA0100());
        if(list.size() > 0){
            String zjcgqk = "";
            for (int i = 0; i < list.size(); i++) {
                zjcgqk += "出国时间："+list.get(i).getCgsj()+",所赴国家："+list.get(i).getSdgj()+",出访任务："+list.get(i).getCfrw()+";";
            }
            pubApply.setZjcgqk(zjcgqk);
        }
        pubApply.setSfbg("0");
        pubApply.setSqzt(1);
        pubApply.setSfxd(0);
        pubApply.setCreateUser(userInfo.getId());
        pubApply.setCreateTime(new Date());
        return pubApply;
    }
}
