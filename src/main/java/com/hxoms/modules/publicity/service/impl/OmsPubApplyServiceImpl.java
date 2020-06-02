package com.hxoms.modules.publicity.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.b01temp.entity.OmsB01Temp;
import com.hxoms.modules.b01temp.mapper.OmsB01TempMapper;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.service.OmsPubApplyService;
import com.hxoms.support.b01.entity.B01Tree;
import com.hxoms.support.b01.service.OrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OmsPubApplyServiceImpl implements OmsPubApplyService {
    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;
    @Autowired
    private OrgService orgService;
    @Autowired
    private OmsB01TempMapper omsB01TempMapper;

    @Override
    public List<PersonInfoVO> selectPersonListByOrg(String b0100) {
        return omsPubApplyMapper.selectPersonListByOrg(b0100);
    }

    @Override
    public OmsPubApplyVO selectPubApplyPersonInfo(String b0100, String a0100) {
        OmsPubApplyVO omsPubApply = new OmsPubApplyVO();
        //备案表中获取基本信息
        Map<String, Object> personInfo = omsPubApplyMapper.selectBasePersonInfo(b0100, a0100);
        omsPubApply.setName((String) personInfo.get("NAME"));
        omsPubApply.setA0100((String) personInfo.get("A0100"));
        omsPubApply.setB0100((String) personInfo.get("B0100"));
        omsPubApply.setB0101((String) personInfo.get("B0101"));
        omsPubApply.setBirthDate((Date) personInfo.get("BIRTH_DATE"));
        omsPubApply.setAge((String) personInfo.get("AGE"));
        omsPubApply.setSex((String) personInfo.get("SEX"));
        omsPubApply.setHealth((String) personInfo.get("HEALTH"));
        omsPubApply.setPoliticalAff((String) personInfo.get("POLITICAL_AFFI"));
        omsPubApply.setJob((String) personInfo.get("JOB"));
        omsPubApply.setSfsmry("0");
        String smdj = (String) personInfo.get("SECRET_LEVEL");
        if (!StringUtils.isBlank(smdj) && !smdj.equals("非涉密")) {
            omsPubApply.setSfsmry("1");
            omsPubApply.setSmdj((String) personInfo.get("SECRET_LEVEL"));
        }
        omsPubApply.setJob((String) personInfo.get("JOB"));
        omsPubApply.setSflg((String) personInfo.get("NF"));
        //获取最近一次出国情况
        List<OmsPubApply> latestInfoList = omsPubApplyMapper.selectPubAbroadLatestInfo(a0100);
        if (latestInfoList != null && !latestInfoList.isEmpty()) {
            OmsPubApply latestInfo = latestInfoList.get(0);
            StringBuilder sb = new StringBuilder();
            Date cgsj = latestInfo.getCgsj();
            Date hgsj = latestInfo.getHgsj();
            String sdgj = latestInfo.getSdgj();
            String cfrw = latestInfo.getCfrw();
            if (cgsj != null && hgsj != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd号");
                sb.append(sdf.format(cgsj)).append("至").append(sdf.format(hgsj)).append("，");
            }
            if (!StringUtils.isBlank(sdgj)) {
                sb.append("赴").append(sdgj);
            }
            if (!StringUtils.isBlank(cfrw)) {
                sb.append("进行").append(cfrw).append("。");
            }
            omsPubApply.setZjcgqk(sb.toString());
        }
        //获取涉密信息
        //TODO
        //获取负面信息
        //TODO
        return omsPubApply;
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String insertOrUpdatePubApply(OmsPubApply omsPubApply) {
        if (StringUtils.isBlank(omsPubApply.getA0100())) {
            throw new CustomMessageException("请先选择申请的干部");
        }
        String result = "";//返回信息
        String primaryKey = UUIDGenerator.getPrimaryKey();
        if (StringUtils.isBlank(omsPubApply.getId())) {
            omsPubApply.setId(primaryKey);
            omsPubApplyMapper.insert(omsPubApply);
        } else {
            primaryKey = omsPubApply.getId();
            omsPubApplyMapper.updateById(omsPubApply);
        }
        //        int i = omsPubApplyMapper.excuteSelectSql("select count(1) from oms_pub_apply where id='" + primaryKey + "'");
        //判断校验类型
        result = checkPersonApply(omsPubApply.getA0100(), primaryKey, "1");
        if (StringUtils.isBlank(result)) {
            result = "保存成功";
        }
        return result;
    }

    @Override
    public List<OmsPubApply> selectPubApplyList() {
        //获取当前人员的机构和子机构
        List<Tree> treeList = orgService.selectOrgTreeList();
        List<OmsPubApply> applyList = null;
        if (treeList == null || treeList.isEmpty()) {
            return applyList;
        }
        String batchId = UUIDGenerator.getPrimaryKey();
        List<OmsB01Temp> b01TempList = new ArrayList<>();
        for (Tree tree : treeList) {
            OmsB01Temp omsB01Temp = new OmsB01Temp();
            omsB01Temp.setBatchId(batchId);
            omsB01Temp.setB0100(((B01Tree) tree).getB0100());
            b01TempList.add(omsB01Temp);
        }
        //分批次查询,每次查询500机构
        int batch = 500;
        int size = b01TempList.size();
        for (int i = 0; i < size / batch; i++) {
            int count = (i + 1) * batch;
            if (count <= size) {
                omsB01TempMapper.insertBatch(b01TempList.subList(i * batch, count));
            } else {
                omsB01TempMapper.insertBatch(b01TempList.subList(i * batch, size));
            }
        }
        //关联表查询机构下的人员
        applyList = omsPubApplyMapper.selectPubApplyList(batchId);
        //删除临时表中的当前批次的数据
        omsB01TempMapper.deleteByBatchId(batchId);
        return applyList;
    }

    @Override
    public OmsPubApply selectPubApplyById(String id) {
        return omsPubApplyMapper.selectById(id);
    }

    /**
     * 校验用户信息
     *
     * @author sunqian
     * @date 2020/4/28 11:01
     */
    public String checkPersonApply(String a0100, String id, String conditionType) {
        //返回的校验信息集合
        List<String> stringList = new ArrayList<>();
        String result = null;
        //查询所有的校验信息
        List<OmsCondition> list = omsPubApplyMapper.selectCheckCondition(conditionType);
        //保存前校验
        List<OmsCondition> preList = new ArrayList<>();
        //保存后校验
        List<OmsCondition> sufList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (OmsCondition omsCondition : list) {
            if ("1".equals(omsCondition.getCheckType())) {
                preList.add(omsCondition);
            } else {
                sufList.add(omsCondition);
            }
        }
        //先进行保存前校验
        if (!preList.isEmpty()) {
            for (OmsCondition omsCondition : preList) {
                String sql = omsCondition.getSqlContent();
                if (StringUtils.isBlank(sql)) {
                    continue;
                }
                sql = sql.replace("@personId", a0100).replace("@id", id);
                int count = omsPubApplyMapper.excuteSelectSql(sql);
                if (count > 0) {
                    stringList.add(omsCondition.getName());
                }
            }
            if (!stringList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String string : stringList) {
                    sb.append(string).append("<br/>");
                }
                //保存前校验,直接抛出异常,中断执行,上层方法捕获异常后回滚事务
                throw new CustomMessageException(sb.toString());
            }
        }
        //保存后校验
        if (!sufList.isEmpty()) {
            for (OmsCondition omsCondition : sufList) {
                String sql = omsCondition.getSqlContent();
                if (StringUtils.isBlank(sql)) {
                    continue;
                }
                sql = sql.replace("@personId", a0100).replace("@id", id);
                int count = omsPubApplyMapper.excuteSelectSql(sql);
                if (count > 0) {
                    stringList.add(omsCondition.getName());
                }
            }
            if (!stringList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String string : stringList) {
                    sb.append(string).append("<br/>");
                }
                result = sb.toString();
            }
        }
        return result;
    }
}
