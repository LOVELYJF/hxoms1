package com.hxoms.modules.leaderSupervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfoVO;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcbatchPersonMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchPersonService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.service.OrgService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @authore:wjf
 * @data 2020/7/15 17:30
 * @Description:
 ***/
@Service("leaderEXportExcelService")
public class LeaderEXportExcelService {

    @Autowired
    private LeaderCommonMapper leaderCommonQueryMapper;

    @Autowired
    private SelectMapper selectMapper;  // 通用 自定义sql

    @Autowired
    private OmsRegProcpersoninfoMapper mrpinfoMapper;

    @Autowired
    private OmsRegProcbatchService orpbatchService;

    @Autowired
    private OmsRegProcpersonInfoService mrpinfoService;

    @Autowired
    private OmsRegProcbatchPersonMapper procbatchPersonMapper;
    @Autowired
    private OmsRegProcbatchPersonService procbatchPersonService;

    @Autowired
    private OrgService orgService;
    /**
     * 因公出国境管理 导出
     **/
    public HSSFWorkbook pubApplyMangerExport(OmsPubApplyQueryParam omsPubApplyQueryParam) {

        List<Map> dataList = leaderCommonQueryMapper.selectPulicApplyManager(omsPubApplyQueryParam);


///	SELECT
//     pua.id AS id,   # 业务id
//     pua.leader_batch_id as batchId, # 批次id
//
//     b.b0101 AS department,  # "部门名称"
//     a.a0101 AS userName, # 人员名称
//     pua.CGSJ AS abroadTime,  # 出国时间 == 出境日期
//     TIMESTAMPDIFF(DAY,now(),pua.CGSJ) as daynums, #  距离 出境天数
//     pua.HGSJ AS returnTime,  #  回国时间 == 入境日期
//     pua.SDGJ AS sdgj,  #  所到国境 == 目的地
//     pua.CFRW AS cfrw,  #  出访任务
//     "因公" AS businessType,  # 申请类型
//     pua.CGSPDW AS cgspdw, # 审批单位
//     IF (
//             a.A0104 NOT IN ('1', '2'),
//             '未知',
//             IF (a.A0104 = 1, '男', '女')
//     ) AS sex,  # 性别
//     mrp.BIRTH_DATE AS birthDate,   # 从登记备案库中 去的 人员出生日期
//     pua.POLITICAL_AFF AS politicalAffi,  # 政治面貌
//     pua.JOB AS job,   #职务
//     "暂无" AS applyTime,   # 申请日期
//     pua.SMDJ AS secretLevel,  # 涉密等级
//     "暂无" AS declassificaEndTime,  # 脱密结束日期
//     pua.SFLG AS sflg,  # 是否裸官
//     mrp.IDENTITY AS identity, #身份类别
//     pua.SFZYLD as sfzyld,  # 主要领导
//     pua.FMXX AS fmxx, # 负面信息
//     pua.SFBG as sfbg, # 是否变更
//     pua.SFZQJWYJ,    # 是否需要征求纪委意见
//     pua.SCZQJWYJSJ, # 上次 征求 时间
//     case when pua.JWJL =1 then '同意' when pua.JWJL = 2 then '不同意'  when pua.JWJL=3 then '不回复'  when pua.JWJL=4 then '反复'  else '' end
//     AS jwjl, # 材料审核是否通过
//
//     case when pua.CLSHSFTG =1 then '通过' when pua.CLSHSFTG = 2 then '不通过' else '' end
//     AS clshsftg # 材料审核是否通过
        List listK = new ArrayList();
        List listV = new ArrayList();
        listK.add("num");
        listV.add("序号");
        listK.add("department");
        listV.add("单位");
        listK.add("userName");
        listV.add("姓名");
        listK.add("sex");
        listV.add("性别");
        listK.add("politicalAffi");
        listV.add("政治面貌");  // 少一列 健康情况
        listK.add("health");
        listV.add("健康情况");  // 少一列 健康情况
        listK.add("job");
        listV.add("职务");               // 少一列 备案号
        listK.add("bah");
        listV.add("备案号");
        listK.add("abroadTime");
        listV.add("出境日期");
        listK.add("returnTime");
        listV.add("回国时间");
        listK.add("sdgj");
        listV.add("目的地");
        listK.add("cfrw");
        listV.add("事由");  //  少一列 状态
        listK.add("secretLevel");
        listV.add("涉密等级");
        listK.add("declassificaEndTime");
        listV.add("脱密期结束时间");
        listK.add("sflg");
        listV.add("是否裸官");
        listK.add("identity");
        listV.add("身份类别");
        listK.add("sfzyld");
        listV.add("主要领导");
        listK.add("fmxx");
        listV.add("负面信息");
        listK.add("clshsftg");
        listV.add("材料审核");
        listK.add("cadresupervisionOpinion");
        listV.add("干部监督处意见");
        listK.add("chulingdaoOpinion");
        listV.add("处领导意见");
        listK.add("bulingdaoOpinion");
        listV.add("部领导意见");
        listK.add("zzjl");
        listV.add("最终结论");

        return LeaderSupervisionUntil.exportExcelByListMap(listK, listV, dataList, "因公出国境申请管理");


    }


    /**
     * 因公出国境管理 导出
     **/
    public HSSFWorkbook jiweiApplyExport(LeaderSupervisionVo leaderSupervisionVo) {


        List<Map> dataList = leaderCommonQueryMapper.selectJiweiApply(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()).toArray()
           ,leaderSupervisionVo.getClshsftg()
        );


//  pua.id AS id,   # 业务id
//  pua.leader_batch_id as batchId, # 批次id
//  pua.SQZT   as applyStatus,    # 业务 流程状态
//  b.b0101 AS department,  # "部门名称"
//  a.a0101 AS userName, # 人员名称
//  pua.CGSJ AS abroadTime,  # 出国时间 == 出境日期
//  TIMESTAMPDIFF(DAY,now(),pua.CGSJ) as daynums, #  距离 出境天数
//  pua.HGSJ AS returnTime,  #  回国时间 == 入境日期
//  pua.SDGJ AS sdgj,  #  所到国境 == 目的地
//  pua.CFRW AS cfrw,  #  出访任务
//  "因公" AS businessType,  # 申请类型
//  pua.CGSPDW AS cgspdw, # 审批单位
//  IF (
//          a.A0104 NOT IN ('1', '2'),
//          '未知',
//          IF (a.A0104 = 1, '男', '女')
//  ) AS sex,  # 性别
//  DATE_FORMAT(mrp.BIRTH_DATE,'%Y.%m.%d') AS birthDate,   # 从登记备案库中 去的 人员出生日期
//  pua.POLITICAL_AFF AS politicalAffi,  # 政治面貌
//  pua.JOB AS job,   #职务
//  "暂无" AS applyTime,   # 申请日期
//  pua.SMDJ AS secretLevel,  # 涉密等级
//  "暂无" AS declassificaEndTime,  # 脱密结束日期
//  pua.SFLG AS sflg,  # 是否裸官
//  mrp.IDENTITY AS identity, #身份类别
//  pua.SFZYLD as sfzyld,  # 主要领导
//  pua.FMXX AS fmxx, # 负面信息
//  pua.SFBG as sfbg, # 是否变更
//  yesOrnO_take_advice(pua.id,pua.A0100,35) as  sfbczqjwyj,    # 是否需要征求纪委意见
//  pua.SCZQJWYJSJ as sczqjwyjsj , # 上次 征求 时间
//  case when pua.SFZQJWYJ =1 then '是' else '否' end as  sfzqjiwyj , # 是否已征求纪委意见
//  case when pua.JWJL =1 then '同意' when pua.JWJL = 2 then '不同意'  when pua.JWJL=3 then '不回复'  when pua.JWJL=4 then '反复'  else '' end
//  AS jwjl, # 纪委结论
//
//  case when pua.CLSHSFTG =1 then '通过' when pua.CLSHSFTG = 2 then '不通过' else '' end
//  AS clshsftg # 材料审核是否通过


        List listK = new ArrayList();
        List listV = new ArrayList();
        listK.add("num");
        listV.add("序号");
        listK.add("department");
        listV.add("工作单位");
        listK.add("userName");
        listV.add("姓名");
        listK.add("job");
        listV.add("职务");
        listK.add("businessType");
        listV.add("申请类型");
        // listK.add("sex");listV.add("性别");
        // listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
        // 少一列 备案号
        listK.add("abroadTime");
        listV.add("出境日期");
        listK.add("daynums");
        listV.add("距离出境天数");
        listK.add("returnTime");
        listV.add("入境日期");
        listK.add("sdgj");
        listV.add("目的地");
        listK.add("cfrw");
        listV.add("事由");  //  少一列 状态
        // listK.add("secretLevel");listV.add("涉密等级");
        // listK.add("declassificaEndTime");listV.add("脱密期结束时间");
        // listK.add("sflg");listV.add("是否裸官");
//  listK.add("identity");listV.add("身份类别");
//  listK.add("sfzyld");listV.add("主要领导");
        listK.add("fmxx");
        listV.add("负面信息");
        listK.add("sfbg");
        listV.add("是否变更");
        listK.add("sfbczqjwyj");
        listV.add("需要征求纪委意见");
        listK.add("sczqjwyjsj");
        listV.add("上次征求时间");
        listK.add("sfzqjiwyj");
        listV.add("是否已征求纪委意见");
        listK.add("jwjl");
        listV.add("纪委结论");
        listK.add("clshsftg");
        listV.add("材料审核结果");


        return LeaderSupervisionUntil.exportExcelByListMap(listK, listV, dataList, "因公出国境申请管理");


    }

    @Transactional(rollbackFor = Exception.class)
    public void test() {
        // 事务的隔离性 同一个 事务才不存在隔离， 对另一个事务有隔离
        String sql = "update student  set name ='333' ";
        String sql1 = "select * from student  ";

        SqlVo instance = SqlVo.getInstance(sql);
        selectMapper.update(instance);


        SqlVo instance1 = SqlVo.getInstance(sql1);
        List<LinkedHashMap<String, Object>> linkedHashMaps = selectMapper.select(instance1);

        System.out.println(linkedHashMaps);

    }

    /**
     * 纪委意见填写导出
     **/

    public HSSFWorkbook jiweiWriteApplyExport(LeaderSupervisionVo leaderSupervisionVo) {

        List<Map> dataList = leaderCommonQueryMapper.selectJiweiWriteApply(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()).toArray()

        );

//        pua.id AS id,   # 业务id
//        pua.leader_batch_id as batchId, # 批次id
//        pua.SQZT   as applyStatus,    # 业务 流程状态
//        b.b0101 AS department,  # "部门名称"
//        a.a0101 AS userName, # 人员名称
//        pua.CGSJ AS abroadTime,  # 出国时间 == 出境日期
//        TIMESTAMPDIFF(DAY,now(),pua.CGSJ) as daynums, #  距离 出境天数
//        pua.HGSJ AS returnTime,  #  回国时间 == 入境日期
//        pua.SDGJ AS sdgj,  #  所到国境 == 目的地
//        pua.CFRW AS cfrw,  #  出访任务
//        "因公" AS businessType,  # 申请类型
//        pua.CGSPDW AS cgspdw, # 审批单位
//        IF (
//                a.A0104 NOT IN ('1', '2'),
//                '未知',
//                IF (a.A0104 = 1, '男', '女')
//        ) AS sex,  # 性别
//        DATE_FORMAT(mrp.BIRTH_DATE,'%Y.%m.%d') AS birthDate,   # 从登记备案库中 去的 人员出生日期
//        pua.POLITICAL_AFF AS politicalAffi,  # 政治面貌
//        pua.JOB AS job,   #职务
//        "暂无" AS applyTime,   # 申请日期
//        pua.SMDJ AS secretLevel,  # 涉密等级
//        "暂无" AS declassificaEndTime,  # 脱密结束日期
//        pua.SFLG AS sflg,  # 是否裸官
//        mrp.IDENTITY AS identity, #身份类别
//        pua.SFZYLD as sfzyld,  # 主要领导
//        pua.FMXX AS fmxx, # 负面信息
//        pua.SFBG as sfbg, # 是否变更
//        case when pua.SFZQJWYJ =1 then '是' else '否' end as  sfzqjiwyj , # 是否已征求纪委意见,    # 是否需要征求纪委意见
//        pua.SCZQJWYJSJ as sczqjwyjsj, # 上次 征求 时间
//        case when pua.JWJL =1 then '通过' when pua.JWJL = 2 then '不通过'  when pua.JWJL=3 then '不回复'  when pua.JWJL=4 then '反复'  else '' end
//        AS jwjl, # 纪委意见 结论
//        case when jio.feedback_verdict = 1 then '通过' when jio.feedback_verdict = 2 then '不通过'  when jio.feedback_verdict=3 then '不回复' else '' END
//        as verbaljwjl, # 口头纪委意见
//        case when jio.official_feedback_verdict = 1 then '通过' when jio.official_feedback_verdict = 2 then '不通过'  when jio.official_feedback_verdict=3 then '不回复' else '' END
//        as officaljwjl # 书面纪委意见

        List listK = new ArrayList();
        List listV = new ArrayList();
        listK.add("num");
        listV.add("序号");
        listK.add("department");
        listV.add("工作单位");
        listK.add("userName");
        listV.add("姓名");
        listK.add("job");
        listV.add("职务");
        listK.add("jwjl");
        listV.add("纪委结论");
        listK.add("verbaljwjl");
        listV.add("口头意见");
        listK.add("officaljwjl");
        listV.add("书面意见");
        listK.add("businessType");
        listV.add("申请类型");
        listK.add("cgspdw");
        listV.add("审批单位");
        // listK.add("sex");listV.add("性别");
        // listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
        // 少一列 备案号
        listK.add("abroadTime");
        listV.add("出境日期");
        listK.add("daynums");
        listV.add("距离出境天数");
        listK.add("returnTime");
        listV.add("入境日期");
        listK.add("sdgj");
        listV.add("目的地");
        listK.add("cfrw");
        listV.add("任务/事由");  //  少一列 状态
        listK.add("secretLevel");
        listV.add("涉密等级");  //  少一列 状态
        // listK.add("secretLevel");listV.add("涉密等级");
        listK.add("declassificaEndTime");
        listV.add("脱密期结束时间");
        listK.add("sflg");
        listV.add("是否裸官");
        listK.add("identity");
        listV.add("身份类别");
        listK.add("sfzyld");
        listV.add("主要领导");
        listK.add("fmxx");
        listV.add("负面信息");
        listK.add("sfbg");
        listV.add("是否变更");
        listK.add("sfbczqjwyj");
        listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");

        listK.add("clshsftg");
        listV.add("材料审核结果");

        return LeaderSupervisionUntil.exportExcelByListMap(listK, listV, dataList, "记录意见导出");

    }

    public HSSFWorkbook makeCheckOpinionExport(AuditOpinionVo auditOpinionVo) {

        List<Map> dataList = null;
        if (auditOpinionVo.getBussinessType().equals("1")) {
            dataList = leaderCommonQueryMapper.selectAuditOpinionOmsPua(auditOpinionVo);
        } else if (auditOpinionVo.getBussinessType().equals("2")) {

            dataList = leaderCommonQueryMapper.selectAuditOpinionOmsPri(auditOpinionVo);
        } else if (auditOpinionVo.getBussinessType().equals("3")) {

            dataList = leaderCommonQueryMapper.selectAuditOpinionOmsPriDelay(auditOpinionVo);
        }

        List listK = new ArrayList();
        List listV = new ArrayList();

        if (auditOpinionVo.getBussinessType().equals("1")) {
//            pua.id AS id,   # 业务id
//            pua.leader_batch_id as batchId, # 批次id
//            pua.SQZT   as applyStatus,    # 业务 流程状态
//            b.b0101 AS department,  # "部门名称"
//            a.a0101 AS userName, # 人员名称
//            DATE_FORMAT(pua.CGSJ,'%Y.%m.%d') AS abroadTime,  # 出国时间 == 出境日期
//            DATE_FORMAT(pua.HGSJ,'%Y.%m.%d') AS returnTime,  #  回国时间 == 入境日期
//            pua.SDGJ AS sdgj,  #  所到国境 == 目的地
//            pua.CFRW AS cfrw,  #  出访任务
//            "因公" AS businessType,  # 申请类型
//            pua.CGSPDW AS cgspdw, # 审批单位
//            case when pua.CLSHSFTG =1 then '通过' when pua.CLSHSFTG = 2 then '不通过' else '' end
//                AS clshsftg, # 材料审核是否通过
//            case when pua.JWJL =1 then '通过' when pua.JWJL = 2 then '不通过'  when pua.JWJL=3 then '不回复'  else '' end
//                AS jwjl, # 纪委意见 结论
//            case when jio.feedback_verdict = 1 then '通过' when jio.feedback_verdict = 2 then '不通过'  when jio.feedback_verdict=3 then '不回复' else '' END
//                as verbaljwjl, # 口头纪委意见
//            case when jio.official_feedback_verdict = 1 then '通过' when jio.official_feedback_verdict = 2 then '不通过'  when jio.official_feedback_verdict=3 then '不回复' else '' END
//                as officaljwjl, # 书面纪委意见
//                    (case aba.APPROVAL_RESULT when 1 then '通过' when 2 then '不通过'  else  '暂无数据'  end )  as auditOpinion, # 干部监督处 意见
//                IF (
//                        a.A0104 NOT IN ('1', '2'),
//                        '未知',
//                        IF (a.A0104 = 1, '男', '女')
//                ) AS sex,  # 性别
//                DATE_FORMAT(mrp.BIRTH_DATE,'%Y.%m.%d') AS birthDate,   # 从登记备案库中 去的 人员出生日期
//                pua.POLITICAL_AFF AS politicalAffi,  # 政治面貌
//                pua.JOB AS job,   #职务
//                "暂无" AS applyTime,   # 申请日期
//                pua.SMDJ AS secretLevel,  # 涉密等级
//                "暂无" AS declassificaEndTime,  # 脱密结束日期
//                pua.SFLG AS sflg,  # 是否裸官
//                mrp.IDENTITY AS identity, #身份类别
//                pua.SFZYLD as sfzyld,  # 主要领导
//                pua.FMXX AS fmxx, # 负面信息
//                pua.SFBG as sfbg # 是否变更

            listK.add("num");
            listV.add("序号");
            listK.add("department");
            listV.add("工作单位");
            listK.add("userName");
            listV.add("姓名");
            listK.add("abroadTime");
            listV.add("出境日期");
            listK.add("returnTime");
            listV.add("入境日期");
            listK.add("sdgj");
            listV.add("目的地");
            listK.add("cfrw");
            listV.add("出访任务");  //  少一列 状态
            listK.add("cgspdw");
            listV.add("审批单位");
            listK.add("clshsftg");
            listV.add("材料审核结果");

            listK.add("jwjl");
            listV.add("纪委结论");
            listK.add("verbaljwjl");
            listV.add("口头意见");
            listK.add("officaljwjl");
            listV.add("书面意见");
//            listK.add("businessType");listV.add("申请类型");

            listK.add("sex");
            listV.add("性别");
            listK.add("birthDate");
            listV.add("出生日期");
            listK.add("politicalAffi");
            listV.add("政治面貌");
            listK.add("job");
            listV.add("职务");


//            listK.add("daynums");listV.add("距离出境天数");


            listK.add("cfrw");
            listV.add("任务/事由");  //  少一列 状态
            listK.add("secretLevel");
            listV.add("涉密等级");  //  少一列 状态
            // listK.add("secretLevel");listV.add("涉密等级");
            listK.add("declassificaEndTime");
            listV.add("脱密期结束时间");
            listK.add("sflg");
            listV.add("是否裸官");
            listK.add("identity");
            listV.add("身份类别");
            listK.add("sfzyld");
            listV.add("主要领导");
            listK.add("fmxx");
            listV.add("负面信息");
            listK.add("sfbg");
            listV.add("是否变更");
//            listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");


        } else if (auditOpinionVo.getBussinessType().equals("2")) {

            listK.add("num");
            listV.add("序号");
            listK.add("department");
            listV.add("工作单位");
            listK.add("userName");
            listV.add("姓名");
            listK.add("sex");
            listV.add("性别");
            listK.add("birthDate");
            listV.add("出生日期");
            listK.add("politicalAffi");
            listV.add("政治面貌");
            listK.add("health");
            listV.add("健康状况");
            listK.add("job");
            listV.add("职务");
            listK.add("abroadTime");
            listV.add("出境日期");
            listK.add("returnTime");
            listV.add("入境日期");
            listK.add("sdgj");
            listV.add("目的地");
            listK.add("cfrw");
            listV.add("出访任务");  //  少一列 状态
            listK.add("cgspdw");
            listV.add("审批单位");
            listK.add("clshsftg");
            listV.add("材料审核结果");

            listK.add("jwjl");
            listV.add("纪委结论");
            listK.add("verbaljwjl");
            listV.add("口头意见");
            listK.add("officaljwjl");
            listV.add("书面意见");
//            listK.add("businessType");listV.add("申请类型");


//            listK.add("daynums");listV.add("距离出境天数");


            listK.add("cfrw");
            listV.add("任务/事由");  //  少一列 状态
            listK.add("secretLevel");
            listV.add("涉密等级");  //  少一列 状态
            // listK.add("secretLevel");listV.add("涉密等级");
            listK.add("declassificaEndTime");
            listV.add("脱密期结束时间");
            listK.add("sflg");
            listV.add("是否裸官");
            listK.add("identity");
            listV.add("身份类别");
            listK.add("sfzyld");
            listV.add("主要领导");
            listK.add("fmxx");
            listV.add("负面信息");
//            listK.add("sfbg");listV.add("是否变更");
//            listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");

        } else if (auditOpinionVo.getBussinessType().equals("3")) {

            listK.add("num");
            listV.add("序号");
            listK.add("department");
            listV.add("工作单位");
            listK.add("userName");
            listV.add("姓名");
            listK.add("sex");
            listV.add("性别");
            listK.add("birthDate");
            listV.add("出生日期");
            listK.add("politicalAffi");
            listV.add("政治面貌");
            listK.add("health");
            listV.add("健康状况");
            listK.add("job");
            listV.add("职务");
            listK.add("abroadTime");
            listV.add("出境日期");
            listK.add("returnTime");
            listV.add("入境日期");
            listK.add("sdgj");
            listV.add("目的地");
            listK.add("cfrw");
            listV.add("出访任务");  //  少一列 状态
            listK.add("cgspdw");
            listV.add("审批单位");
            listK.add("clshsftg");
            listV.add("材料审核结果");

            listK.add("jwjl");
            listV.add("纪委结论");
            listK.add("verbaljwjl");
            listV.add("口头意见");
            listK.add("officaljwjl");
            listV.add("书面意见");
//            listK.add("businessType");listV.add("申请类型");


//            listK.add("daynums");listV.add("距离出境天数");


            listK.add("cfrw");
            listV.add("任务/事由");  //  少一列 状态
            listK.add("secretLevel");
            listV.add("涉密等级");  //  少一列 状态
            // listK.add("secretLevel");listV.add("涉密等级");
            listK.add("declassificaEndTime");
            listV.add("脱密期结束时间");
            listK.add("sflg");
            listV.add("是否裸官");
            listK.add("identity");
            listV.add("身份类别");
            listK.add("sfzyld");
            listV.add("主要领导");
            listK.add("fmxx");
            listV.add("负面信息");
//            listK.add("sfbg");listV.add("是否变更");
//            listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");

        }


        return LeaderSupervisionUntil.exportExcelByListMap(listK, listV, dataList, "记录意见导出");


    }

    /**
     * 人员备案表 导出
     **/

    public HSSFWorkbook putonRecordExport(LeaderSupervisionVo leaderSupervisionVo) {


        List<Map> dataList = leaderCommonQueryMapper.createPutOnRecordList(
                null,
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()).toArray()

        );

//        pua.id AS id,   # 业务id
//        pua.leader_batch_id as batchId, # 批次id
//        pua.SQZT   as applyStatus,    # 业务 流程状态
//        pua.PROCPERSON_ID as procpersonId , #登记备案人员id
//        "人员备案表" as fileType, # 模板类型
//        "28" as bussinessOccureStpet,
//        "制作备案表" as bussinessOccureStpetName,
//        "oms_pub_apply_cadres" as tableCode,
//        b.b0101 AS department,  # "部门名称"
//        a.a0101 AS userName, # 人员名称
//        DATE_FORMAT(pua.CGSJ,'%Y.%m.%d') AS abroadTime,  # 出国时间 == 出境日期
//        DATE_FORMAT(pua.HGSJ,'%Y.%m.%d') AS returnTime,  #  回国时间 == 入境日期
//        pua.SDGJ AS sdgj,  #  所到国境 == 目的地
//        pua.CFRW AS cfrw,  #  出访任务
//        pua.CFSY as cfsy,  #出访事由
//        mrp.DECRYPT_ENDDATE as decryptEndDate, # 脱密期 结束时间
//
//        "因公" AS businessType,  # 申请类型
//        pua.CGSPDW AS cgspdw, # 审批单位
//        IF (
//                a.A0104 NOT IN ('1', '2'),
//                '未知',
//                IF (a.A0104 = 1, '男', '女')
//        ) AS sex,  # 性别
//        DATE_FORMAT(mrp.BIRTH_DATE,'%Y.%m.%d') AS birthDate,   # 从登记备案库中 去的 人员出生日期
//        pua.POLITICAL_AFF AS politicalAffi,  # 政治面貌
//        pua.JOB AS job,   #职务
//        pua.BAH as bah, # 备案号
//        "暂无" AS applyTime,   # 申请日期
//        pua.SMDJ AS secretLevel,  # 涉密等级
//        "暂无" AS declassificaEndTime,  # 脱密结束日期
//        pua.SFLG AS sflg,  # 是否裸官
//        mrp.IDENTITY AS identity, #身份类别
//        pua.SFZYLD as sfzyld,  # 主要领导
//        pua.FMXX AS fmxx, # 负面信息
//        case pua.CLSHSFTG when 1 then '通过' when 2 then '不通过' else '暂无数据' end as clshsftg, # 材料审核是否通过
//        case when pua.SFZQJWYJ =1 then '是' else '否' end as  sfzqjiwyj,  # 是否已征求纪委意见
//
//        case when pua.JWJL =1 then '通过' when pua.JWJL = 2 then '不通过'  when pua.JWJL=3 then '不回复'  when pua.JWJL=4 then '反复'  else '' end
//        AS jwjl, # 纪委意见 结论
//        case when jio.feedback_verdict = 1 then '通过' when jio.feedback_verdict = 2 then '不通过'  when jio.feedback_verdict=3 then '不回复' else '' END
//        as verbaljwjl, # 口头纪委意见
//        case when jio.official_feedback_verdict = 1 then '通过' when jio.official_feedback_verdict = 2 then '不通过'  when jio.official_feedback_verdict=3 then '不回复' else '' END
//        as officaljwjl, # 书面纪委意见
//        opinionTemp.cadresupervisionOpinion , # 干部监督处意见
//        opinionTemp.chulingdaoOpinion, # 处领导意见
//        opinionTemp.bulingdaoOpinion,  # 部领导意见
//        CASE pua.ZZJL    when 1 then '通过'  when 2 then '不通过' else  '暂无数据' end as zzjl,  # 最终结论
//        pua.SFBG as sfbg # 是否变更

        List listK = new ArrayList();
        List listV = new ArrayList();
        listK.add("num");
        listV.add("序号");
        listK.add("department");
        listV.add("工作单位");
        listK.add("userName");
        listV.add("姓名");
        listK.add("sex");
        listV.add("性别");
        listK.add("birthDate");
        listV.add("出生日期");
        listK.add("politicalAffi");
        listV.add("政治面貌");  // 少一列 健康情况
        listK.add("job");
        listV.add("职务");
        listK.add("bah");
        listV.add("备案号");  // 少一列 健康情况
        listK.add("abroadTime");
        listV.add("出境日期");
        listK.add("returnTime");
        listV.add("入境日期");
        listK.add("sdgj");
        listV.add("目的地");
        listK.add("cfsy");
        listV.add("事由");
        listK.add("cgspdw");
        listV.add("审批单位");

        listK.add("jwjl");
        listV.add("纪委结论");
        listK.add("clshsftg");
        listV.add("材料审核");
        listK.add("cadresupervisionOpinion");
        listV.add("干部监督处意见");
        listK.add("chulingdaoOpinion");
        listV.add("处领导意见");
        listK.add("bulingdaoOpinion");
        listV.add("部领导意见");
        listK.add("zzjl");
        listV.add("最终结论");


//        listK.add("verbaljwjl");listV.add("口头意见");
//        listK.add("officaljwjl");listV.add("书面意见");
//        listK.add("businessType");listV.add("申请类型");

        // listK.add("sex");listV.add("性别");
        // listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
        // 少一列 备案号

//        listK.add("daynums");listV.add("距离出境天数");


//        listK.add("cfrw");listV.add("任务/事由");  //  少一列 状态
        listK.add("secretLevel");
        listV.add("涉密等级");  //  少一列 状态
        // listK.add("secretLevel");listV.add("涉密等级");
        listK.add("declassificaEndTime");
        listV.add("脱密期结束时间");
        listK.add("sflg");
        listV.add("裸官");
        listK.add("identity");
        listV.add("身份类别");
        listK.add("sfzyld");
        listV.add("主要领导");
        listK.add("fmxx");
        listV.add("负面信息");
//        listK.add("sfbg");listV.add("是否变更");
//        listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");


        return LeaderSupervisionUntil.exportExcelByListMap(listK, listV, dataList, "记录意见导出");


    }


    /**
     * 省管登记备案导出
     *
     * @param idStr
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public HSSFWorkbook exportRfInfo(String idStr) throws IOException {
        List<String> ids = null;
        List<OmsRegProcpersoninfoVO> regProcpersoninfos = null;

        OmsRegProcpersoninfoIPagParam procpersoninfoIPagParam=new OmsRegProcpersoninfoIPagParam();
        procpersoninfoIPagParam.setPageNum(1);
        procpersoninfoIPagParam.setPageSize(10000);
        if (!StringUtils.isBlank(idStr)) {
            ids = Arrays.asList(idStr.split(","));

            procpersoninfoIPagParam.setIds(ids);
            regProcpersoninfos = mrpinfoMapper.selectRegPersonInfoList(procpersoninfoIPagParam);
        }
        else {
            procpersoninfoIPagParam.setCheckStatus("0");
            regProcpersoninfos = mrpinfoMapper.selectRegPersonInfoList(procpersoninfoIPagParam);
        }

        B01 b01 = orgService.selectOrgByB0111(UserInfoUtil.getUserInfo().getOrgId());
        //处理批次数据，下载后改变状态
        OmsRegProcbatch batchinfo = dealDataRFByRfId(regProcpersoninfos,ids);
        List listK = new ArrayList();
        List listE = new ArrayList();
        List listV = new ArrayList();
        listK.add("num");
        listE.add("num");
        listV.add("序号");
        listK.add("surname");
        listE.add("surname");
        listV.add("中文姓");
        listK.add("name");
        listE.add("name");
        listV.add("中文名");
        listK.add("sexName");
        listE.add("sex");
        listV.add("性别");
        listK.add("birthDate");
        listE.add("birthDate");
        listV.add("出生日期");
        listK.add("idnumberGa");
        listE.add("idnumberGa");
        listV.add("身份证号");
        listK.add("registeResidence");
        listE.add("registeResidenceCode");
        listV.add("户口所在地");
        listK.add("inboundFlag");
        listE.add("inboundFlag");
        listV.add("入库标识");
        listK.add("workUnit");
        listE.add("workUnit");
        listV.add("工作单位");
        listK.add("postCode");
        listE.add("postCode");
        listV.add("职务(级)或职称");
        listK.add("personManager");
        listE.add("personManager");
        listV.add("人事主管单位");
        listK.add("organizationCode");
        listE.add("organizationCode");
        listV.add("报送单位组织机构代码");
        listK.add("b0101");
        listE.add("b0101");
        listV.add("报送单位名称");
        listK.add(batchinfo.getSubmitUcategory());
        listE.add(batchinfo.getSubmitUcategory());
        listV.add("报送单位类别");
        listK.add(batchinfo.getSubmitUcontacts());
        listE.add(batchinfo.getSubmitUcontacts());
        listV.add("报送单位联系人");
        listK.add(batchinfo.getSubmitPhone());
        listE.add(batchinfo.getSubmitPhone());
        listV.add("联系电话");
        listK.add(batchinfo.getBatchNo());
        listE.add(batchinfo.getBatchNo());
        listV.add("入库批号");
        return LeaderSupervisionUntil.exportRfInfoByListMap(listK,listE, listV, regProcpersoninfos,
                "表1（纸）", "表2（电子版）", batchinfo,b01);
    }

    private OmsRegProcbatch dealDataRFByRfId(List<OmsRegProcpersoninfoVO> rflist,List<String> ids) {

        if(ids==null){
            ids = new ArrayList<>();
            for (OmsRegProcpersoninfo personInfo:rflist
                 ) {
                ids.add(personInfo.getId());
            }
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("rf_id", ids);
        List<Object> regProcbatchPersons = procbatchPersonService.list(queryWrapper);
        HashMap<String, Object> hashMapRegProcbathPerson = OmsCommonUtil.CacheHasMap(regProcbatchPersons, "rfId");

        //查询批次相关信息
        OmsRegProcbatch batchinfo = orpbatchService.selectWbaByOrpbatch();
        if (batchinfo == null) throw new CustomMessageException("请先启动登记备案再下载。");

        List<OmsRegProcbatchPerson> adds = new ArrayList<>();
        List<String> dels = new ArrayList<>();
        for (OmsRegProcpersoninfo info : rflist) {
            //查询批次人员表中是否已存在该人员，若已存在执行更新，不新增
            OmsRegProcbatchPerson batchPerson = (OmsRegProcbatchPerson) hashMapRegProcbathPerson.get(info.getId());
            if (batchPerson != null) {
                dels.add(batchPerson.getId());
            }
            OmsRegProcbatchPerson batchperson1 = new OmsRegProcbatchPerson();
            //为批次人员表复制相同字段的数据
            BeanUtils.copyProperties(info, batchperson1);
            batchperson1.setId(UUIDGenerator.getPrimaryKey());
            batchperson1.setRfId(info.getId());
            batchperson1.setBatchId(batchinfo.getBatchNo());
            batchperson1.setSuccess("0");
            batchperson1.setErrorMsg("通过");
            adds.add(batchperson1);

            info.setRfStatus("1");
        }
        if (dels.size() > 0)
            procbatchPersonService.removeByIds(dels);
        if (adds.size() > 0) {
            procbatchPersonService.saveBatch(adds);
        }
        List<OmsRegProcpersoninfo> list=(List)rflist;
        mrpinfoService.updateBatchById(list);

        return batchinfo;
    }

    /**
     * 因私出国境申请管理
     **/
    public HSSFWorkbook exportAllOmsPriApplyManange(OmsPriApplyIPageParam omsPriApplyIPageParam) {

        List<Map> dataList = leaderCommonQueryMapper.selectPrivateApplyManager(omsPriApplyIPageParam);

//        SELECT
//        pra.id AS id, # 业务id
//
//        pra.leader_batch_id as batchId, # 批次id
//
//
//        b.b0101 AS department, 	# "部门名称"
//
//        concat(mrp.SURNAME,mrp.name) AS userName, # 人员名称
//
//        DATE_FORMAT(pra.ABROAD_TIME,'%Y.%m.%d') AS abroadTime, #出国时间 == 出境日期
//
//        DATE_FORMAT(pra.RETURN_TIME,'%Y.%m.%d') AS returnTime, #回国时间 == 入境日期
//
//        pra.GO_COUNTRY AS goCountry,  #出访国家 == 目的地
//
//        "无" AS cfrw,    #出访任务
//
//        '因私' AS businessType,  # 申请类型
//
//        "无" AS cgspdw,  # 审批单位
//        IF (
//                mrp.SEX NOT IN ('1', '2'),
//                '未知',
//                IF (mrp.SEX = 1, '男', '女')
//        ) AS sex,  # 性别
//
//        DATE_FORMAT(mrp.BIRTH_DATE,'%Y.%m.%d') AS birthDate,  # 从登记备案库中 去的 人员出生日期
//
//        pra.POLITICAL_OUTLOOK AS politicalAffi, # 政治面貌
//
//        pra.POSTRANK AS job, #职务
//
//        pra.APPLY_TIME AS applyTime, # 申请时间 == 申请日期
//
//        pra.CLASSIFICATION_LEVEL AS secretLevel, # 涉密等级
//
//        DATE_FORMAT(pra.DECLASSIFICA_ENDTIME,'%Y.%m.%d') AS declassificaEndTime, # 脱密期结束时间
//
//        pra.IS_LUOGUAN AS sflg, # 是否裸官
//
//        mrp.IDENTITY AS identity, #身份类别
//
//        pra.IS_LEADERS as sfzyld , # 主要领导
//
//        pra.NEGATIVE_INFO fmxx, # 负面信息
//        case pra.CLSHSFTG when 1 then '通过' when 2 then '不通过' else '暂无数据' end as clshsftg, # 材料审核是否通过
//        case when pra.JWJL =1 then '通过' when pra.JWJL = 2 then '不通过'  when pra.JWJL=3 then '不回复'  when pra.JWJL=4 then '反复'  else '' end
//        AS jwjl, # 纪委意见 结论
//        case when jio.feedback_verdict = 1 then '通过' when jio.feedback_verdict = 2 then '不通过'  when jio.feedback_verdict=3 then '不回复' else '' END
//        as verbaljwjl, # 口头纪委意见
//        case when jio.official_feedback_verdict = 1 then '通过' when jio.official_feedback_verdict = 2 then '不通过'  when jio.official_feedback_verdict=3 then '不回复' else '' END
//        as officaljwjl, # 书面纪委意见
//                -- 	case pra.JDCJL   when  1 then '通过' when 2 then '不通过' else '暂无数据' end as jdcjl , # 监督处审核意见
//        opinionTemp.cadresupervisionOpinion , # 干部监督处意见
//        opinionTemp.chulingdaoOpinion, # 处领导意见
//        opinionTemp.bulingdaoOpinion,  # 部领导意见
//        CASE pra.ZZJL    when 1 then '通过'  when 2 then '不通过' when 3 then '不回复' else  '暂无数据' end as zzjl  # 最终结论


        List listK = new ArrayList();
        List listV = new ArrayList();
        listK.add("num");
        listV.add("序号");
        listK.add("department");
        listV.add("单位");
        listK.add("userName");
        listV.add("姓名");
        listK.add("sex");
        listV.add("性别");
        listK.add("birthDate");
        listV.add("出生日期");
        listK.add("politicalAffi");
        listV.add("政治面貌");  // 少一列 健康情况
        listK.add("job");
        listV.add("职务");
//        listK.add("bah");listV.add("备案号");  // 少一列 审批号
        listK.add("abroadTime");
        listV.add("出境日期");
        listK.add("returnTime");
        listV.add("入境日期");
        listK.add("sdgj");
        listV.add("目的地");
        listK.add("cfsy");
        listV.add("事由");
        listK.add("applystatus");
        listV.add("状态");

        listK.add("secretLevel");
        listV.add("涉密等级");  //  少一列 状态
        // listK.add("secretLevel");listV.add("涉密等级");
        listK.add("declassificaEndTime");
        listV.add("脱密期结束时间");
        listK.add("sflg");
        listV.add("裸官");
        listK.add("identity");
        listV.add("身份类别");
        listK.add("sfzyld");
        listV.add("主要领导");
        listK.add("fmxx");
        listV.add("负面信息");

        listK.add("jwjl");
        listV.add("纪委结论");
        listK.add("clshsftg");
        listV.add("材料审核");
        listK.add("cadresupervisionOpinion");
        listV.add("干部监督处意见");
        listK.add("chulingdaoOpinion");
        listV.add("处领导意见");
        listK.add("bulingdaoOpinion");
        listV.add("部领导意见");
        listK.add("zzjl");
        listV.add("最终结论");


//        listK.add("verbaljwjl");listV.add("口头意见");
//        listK.add("officaljwjl");listV.add("书面意见");
//        listK.add("businessType");listV.add("申请类型");

        // listK.add("sex");listV.add("性别");
        // listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
        // 少一列 备案号

//        listK.add("daynums");listV.add("距离出境天数");


//        listK.add("cfrw");listV.add("任务/事由");  //  少一列 状态


        return LeaderSupervisionUntil.exportExcelByListMap(listK, listV, dataList, "因私出国境申请管理");


    }


    public HSSFWorkbook exportAllOmsPriDelayApplyManange(OmsPriApplyIPageParam omsPriApplyIPageParam) {

        List<Map> dataList = leaderCommonQueryMapper.selectPridelayApplyManager(omsPriApplyIPageParam);

//         prda.id AS id, # 业务id
//
//         prda.leader_batch_id as batchId, # 批次id
//
//         prda.APPLY_STATUS as applyStatus,
//           pra.HEALTH as health, # 健康状况
//
//                 b.b0101 AS department, 	# "部门名称"
//
//         concat(mrp.SURNAME,mrp.name) AS userName, # 人员名称
//
//         DATE_FORMAT(pra.ABROAD_TIME,'%Y.%m.%d') AS abroadTime, #出国时间 == 出境日期
//
//         DATE_FORMAT(pra.RETURN_TIME,'%Y.%m.%d') AS returnTime, #回国时间 == 入境日期
//
//         DATE_FORMAT(prda.ESTIMATE_RETURNTIME,'%Y.%m.%d') AS delayReturnTime  , #延期入境时间
//
//         prda.DELAY_REASON as delayReason  , #延期入境原因
//
//         pra.GO_COUNTRY AS goCountry,  #出访国家 == 目的地
//
//         pra.ABROAD_REASONS as abroadReasons, # 事由
//
//         "无" AS cfrw,    #出访任务
//
//         '延期' AS businessType,  # 申请类型
//
//         "无" AS cgspdw,  # 审批单位
//
//         IF (
//                 mrp.SEX NOT IN ('1', '2'),
//                 '未知',
//                 IF (mrp.SEX = 1, '男', '女')
//         ) AS sex,  # 性别
//
//         DATE_FORMAT(mrp.BIRTH_DATE,'%Y.%m.%d') AS birthDate,  # 从登记备案库中 去的 人员出生日期
//
//         pra.POLITICAL_OUTLOOK AS politicalAffi, # 政治面貌
//
//         pra.POSTRANK AS job, #职务
//
//         pra.HEALTH as health, # 健康状况
//
//         pra.APPLY_TIME AS applyTime, # 申请时间 == 申请日期
//
//         pra.CLASSIFICATION_LEVEL AS secretLevel, # 涉密等级
//
//         DATE_FORMAT(pra.DECLASSIFICA_ENDTIME,'%Y.%m.%d') AS declassificaEndTime, # 脱密期结束时间
//
//         pra.IS_LUOGUAN AS sflg, # 是否裸官
//
//         mrp.IDENTITY AS identity, #身份类别
//
//         pra.IS_LEADERS as sfzyld, # 主要领导
//
//         pra.NEGATIVE_INFO fmxx # 负面信息

        List listK = new ArrayList();
        List listV = new ArrayList();
        listK.add("num");
        listV.add("序号");
        listK.add("department");
        listV.add("单位");
        listK.add("userName");
        listV.add("姓名");
        listK.add("sex");
        listV.add("性别");
        listK.add("birthDate");
        listV.add("出生日期");
        listK.add("politicalAffi");
        listV.add("政治面貌");
        listK.add("health");
        listV.add("健康状况");
        listK.add("job");
        listV.add("职务");
        listK.add("delayReturnTime");
        listV.add("延期出境时间");
        listK.add("delayReason");
        listV.add("延期入境原因");
        listK.add("abroadTime");
        listV.add("出境日期");
        listK.add("returnTime");
        listV.add("入境日期");
        listK.add("sdgj");
        listV.add("目的地");
        listK.add("cfsy");
        listV.add("事由");
        listK.add("applystatus");
        listV.add("状态");


//        listK.add("bah");listV.add("备案号");  // 少一列 审批号


//         listK.add("secretLevel");listV.add("涉密等级");  //  少一列 状态
//         // listK.add("secretLevel");listV.add("涉密等级");
//         listK.add("declassificaEndTime");listV.add("脱密期结束时间");
//         listK.add("sflg");listV.add("裸官");
//         listK.add("identity");listV.add("身份类别");
//         listK.add("sfzyld");listV.add("主要领导");
//         listK.add("fmxx");listV.add("负面信息");
//
//         listK.add("jwjl");listV.add("纪委结论");
//         listK.add("clshsftg");listV.add("材料审核");
//         listK.add("cadresupervisionOpinion");listV.add("干部监督处意见");
//         listK.add("chulingdaoOpinion");listV.add("处领导意见");
//         listK.add("bulingdaoOpinion");listV.add("部领导意见");
//         listK.add("zzjl");listV.add("最终结论");

        return LeaderSupervisionUntil.exportExcelByListMap(listK, listV, dataList, "因私延期出国境申请管理");

    }


}
