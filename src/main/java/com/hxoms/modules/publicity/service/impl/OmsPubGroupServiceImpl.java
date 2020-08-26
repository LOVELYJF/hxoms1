package com.hxoms.modules.publicity.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.publicity.entity.*;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.mapper.OmsPubGroupMapper;
import com.hxoms.modules.publicity.service.OmsPubGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
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

    @Override
    public PageInfo<OmsPubGroupPreApproval> getPubGroupList(Integer pageNum, Integer pageSize,Map<String,String> param) throws ParseException {
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
            pubGroup.setSqzt(1);
            pubGroup.setCreateUser(userInfo.getId());
            pubGroup.setCreateTime(new Date());
            pubGroupMapper.insertPubGroup(pubGroup);
            //出国人员信息
            for(int i = 0; i < num; i++ ){
                OmsPubApply pubApply = new OmsPubApply();
                pubApply.setId(UUIDGenerator.getPrimaryKey());
                pubApply.setA0100(personList.get(i).getA0100());
                pubApply.setB0100(personList.get(i).getB0100());
                pubApply.setAge(personList.get(i).getAge());
                pubApply.setYspId(id);
                pubApply.setHealth(personList.get(i).getHealth());
                pubApply.setSfsmry(personList.get(i).getSfsmry());
                pubApply.setZjcgqk(personList.get(i).getZjcgqk());
                pubApply.setSqzt(1);
                pubApply.setCreateUser(userInfo.getId());
                pubApply.setCreateTime(new Date());
                applyList.add(pubApply);
            }
            pubApplyMapper.insertPubApplyList(applyList);
            return id;
        }else{
            return "未选择备案人员";
        }
    }

    @Override
    public void updatePubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList) {
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        List<OmsPubApplyVO> personList = pubGroupAndApplyList.getOmsPubApplyVOList();
        List<OmsPubApply> applyList = new ArrayList<>();
        int num = personList.size();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
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
    }

    @Override
    public void deletePubGroup(String id) {
        pubGroupMapper.deletePubGroup(id);
        pubApplyMapper.deletePubApplyByYSPId(id);
    }

    @Override
    public OmsPubGroupAndApplyList uploadPubGroupJson(MultipartFile file) throws IOException {
        OmsPubGroupAndApplyList omsPubGroupAndApplyList = readJsonData(file);
        return omsPubGroupAndApplyList;
    }

    @Override
    public String checkoutPerson(String idList) {
        return null;
    }

    @Override
    public void insertPerson(String a0100) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        OmsPubApply pubApply = new OmsPubApply();
        pubApply.setId(UUIDGenerator.getPrimaryKey());
        pubApply.setA0100(a0100);
        pubApply.setSqzt(1);
        pubApply.setCreateUser(userInfo.getId());
        pubApply.setCreateTime(new Date());
        pubApplyMapper.insert(pubApply);
    }

    @Override
    public void backoutPerson(String id,String cxyy) {
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.selectById(id);
        pubGroup.setSqzt(0);
        pubGroupMapper.updatePubGroup(pubGroup);
    }

    @Override
    public OmsPubGroupAndApplyList getPubGroupDetailById(String yspId) {
        OmsPubGroupAndApplyList beanList = new OmsPubGroupAndApplyList();
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(yspId);
        List<OmsPubApplyVO> pubApplyVOList = pubApplyMapper.selectByYSPId(yspId);
        beanList.setOmsPubGroupPreApproval(pubGroup);
        beanList.setOmsPubApplyVOList(pubApplyVOList);
        return  beanList;
    }

    @Override
    public OmsPubApply getPersonDetailById(String id) {
        return pubApplyMapper.selectById(id);
    }

    @Override
    public List<OmsPubApplyVO> getAuditOpinion(String yspId) {
        return pubApplyMapper.selectByYSPId(yspId);
    }

    @Override
    public void sendTask(String id) {
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.selectById(id);
        pubGroup.setSqzt(2);
        pubGroupMapper.updatePubGroup(pubGroup);
    }

    @Override
    public List<Map<String, String>>  getFlowDetail(String id) {
        return pubGroupMapper.getFlowDetail(id);
    }

    @Override
    public String uploadApproval(MultipartFile file, String id) {
        return "";
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
            OmsRegProcpersoninfo regProcpersoninfo = regProcpersoninfoMapper.selectA0100ByMap(map);
            if(regProcpersoninfo != null){
                omsPubApplyVO.setA0100(regProcpersoninfo.getA0100());
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
}
