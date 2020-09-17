package com.hxoms.modules.leaderSupervision.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /** 因公出国境管理 导出 **/
    public HSSFWorkbook pubApplyMangerExport(){

     List<Map> dataList =  leaderCommonQueryMapper.selectPulicApplyManager();


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
     listK.add("num");listV.add("序号");
     listK.add("department");listV.add("单位");
     listK.add("userName");listV.add("姓名");
     listK.add("sex");listV.add("性别");
     listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
     listK.add("job");listV.add("职务");               // 少一列 备案号
     listK.add("abroadTime");listV.add("出境日期");
     listK.add("returnTime");listV.add("出境日期");
     listK.add("sdgj");listV.add("目的地");
     listK.add("cfrw");listV.add("事由");  //  少一列 状态
     listK.add("secretLevel");listV.add("涉密等级");
     listK.add("declassificaEndTime");listV.add("脱密期结束时间");
     listK.add("sflg");listV.add("是否裸官");
     listK.add("identity");listV.add("身份类别");
     listK.add("sfzyld");listV.add("主要领导");
     listK.add("fmxx");listV.add("负面信息");
     listK.add("clshsftg");listV.add("材料审核");
     listK.add("cadresupervisionOpinion");listV.add("干部监督处意见");
     listK.add("chulingdaoOpinion");listV.add("处领导意见");
     listK.add("bulingdaoOpinion");listV.add("部领导意见");
     listK.add("zzjl");listV.add("最终结论");

     return LeaderSupervisionUntil.exportExcelByListMap(listK,listV,dataList,"因公出国境申请管理");


    }



 /** 因公出国境管理 导出 **/
 public HSSFWorkbook  jiweiApplyExport(LeaderSupervisionVo leaderSupervisionVo){



  List<Map> dataList =  leaderCommonQueryMapper.selectJiweiApply(
          leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray()

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
  listK.add("num");listV.add("序号");
  listK.add("department");listV.add("工作单位");
  listK.add("userName");listV.add("姓名");
  listK.add("job");listV.add("职务");
  listK.add("businessType");listV.add("申请类型");
 // listK.add("sex");listV.add("性别");
 // listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
            // 少一列 备案号
  listK.add("abroadTime");listV.add("出境日期");
  listK.add("daynums");listV.add("距离出境天数");
  listK.add("returnTime");listV.add("入境日期");
  listK.add("sdgj");listV.add("目的地");
  listK.add("cfrw");listV.add("事由");  //  少一列 状态
 // listK.add("secretLevel");listV.add("涉密等级");
 // listK.add("declassificaEndTime");listV.add("脱密期结束时间");
 // listK.add("sflg");listV.add("是否裸官");
//  listK.add("identity");listV.add("身份类别");
//  listK.add("sfzyld");listV.add("主要领导");
  listK.add("fmxx");listV.add("负面信息");
  listK.add("sfbg");listV.add("是否变更");
  listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
  listK.add("sczqjwyjsj");listV.add("上次征求时间");
  listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");
  listK.add("jwjl");listV.add("纪委结论");
  listK.add("clshsftg");listV.add("材料审核结果");



  return LeaderSupervisionUntil.exportExcelByListMap(listK,listV,dataList,"因公出国境申请管理");


 }

     @Transactional(rollbackFor = Exception.class)
    public void  test(){
     // 事务的隔离性 同一个 事务才不存在隔离， 对另一个事务有隔离
     String sql = "update student  set name ='333' ";
     String sql1 = "select * from student  ";

      SqlVo instance = SqlVo.getInstance(sql);
      selectMapper.update(instance);


      SqlVo instance1 = SqlVo.getInstance(sql1);
      List<LinkedHashMap<String, Object>> linkedHashMaps =  selectMapper.select(instance1);

      System.out.println(linkedHashMaps);

    }

    /**
     *  纪委意见填写导出
     * **/

    public HSSFWorkbook  jiweiWriteApplyExport(LeaderSupervisionVo leaderSupervisionVo){

        List<Map> dataList =  leaderCommonQueryMapper.selectJiweiWriteApply(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray()

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
        listK.add("num");listV.add("序号");
        listK.add("department");listV.add("工作单位");
        listK.add("userName");listV.add("姓名");
        listK.add("job");listV.add("职务");
        listK.add("jwjl");listV.add("纪委结论");
        listK.add("verbaljwjl");listV.add("口头意见");
        listK.add("officaljwjl");listV.add("书面意见");
        listK.add("businessType");listV.add("申请类型");
        listK.add("cgspdw");listV.add("审批单位");
        // listK.add("sex");listV.add("性别");
        // listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
        // 少一列 备案号
        listK.add("abroadTime");listV.add("出境日期");
        listK.add("daynums");listV.add("距离出境天数");
        listK.add("returnTime");listV.add("入境日期");
        listK.add("sdgj");listV.add("目的地");
        listK.add("cfrw");listV.add("任务/事由");  //  少一列 状态
        listK.add("secretLevel");listV.add("涉密等级");  //  少一列 状态
        // listK.add("secretLevel");listV.add("涉密等级");
         listK.add("declassificaEndTime");listV.add("脱密期结束时间");
         listK.add("sflg");listV.add("是否裸官");
       listK.add("identity");listV.add("身份类别");
       listK.add("sfzyld");listV.add("主要领导");
        listK.add("fmxx");listV.add("负面信息");
        listK.add("sfbg");listV.add("是否变更");
        listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");

        listK.add("clshsftg");listV.add("材料审核结果");

        return LeaderSupervisionUntil.exportExcelByListMap(listK,listV,dataList,"记录意见导出");

    }

    public HSSFWorkbook makeCheckOpinionExport(AuditOpinionVo auditOpinionVo){

        List<Map>   dataList=null;
        if(auditOpinionVo.getBussinessType().equals("1")){
            dataList = leaderCommonQueryMapper.selectAuditOpinionOmsPua(auditOpinionVo);
        }else if(auditOpinionVo.getBussinessType().equals("2")){

            dataList = leaderCommonQueryMapper.selectAuditOpinionOmsPri(auditOpinionVo);
        }else if(auditOpinionVo.getBussinessType().equals("3")){

            dataList = leaderCommonQueryMapper.selectAuditOpinionOmsPriDelay(auditOpinionVo);
        }

        List listK = new ArrayList();
        List listV = new ArrayList();

        if(auditOpinionVo.getBussinessType().equals("1")){
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

            listK.add("num");listV.add("序号");
            listK.add("department");listV.add("工作单位");
            listK.add("userName");listV.add("姓名");
            listK.add("abroadTime");listV.add("出境日期");
            listK.add("returnTime");listV.add("入境日期");
            listK.add("sdgj");listV.add("目的地");
            listK.add("cfrw");listV.add("出访任务");  //  少一列 状态
            listK.add("cgspdw");listV.add("审批单位");
            listK.add("clshsftg");listV.add("材料审核结果");

            listK.add("jwjl");listV.add("纪委结论");
            listK.add("verbaljwjl");listV.add("口头意见");
            listK.add("officaljwjl");listV.add("书面意见");
//            listK.add("businessType");listV.add("申请类型");

             listK.add("sex");listV.add("性别");
             listK.add("birthDate");listV.add("出生日期");
             listK.add("politicalAffi");listV.add("政治面貌");
            listK.add("job");listV.add("职务");


//            listK.add("daynums");listV.add("距离出境天数");


            listK.add("cfrw");listV.add("任务/事由");  //  少一列 状态
            listK.add("secretLevel");listV.add("涉密等级");  //  少一列 状态
            // listK.add("secretLevel");listV.add("涉密等级");
            listK.add("declassificaEndTime");listV.add("脱密期结束时间");
            listK.add("sflg");listV.add("是否裸官");
            listK.add("identity");listV.add("身份类别");
            listK.add("sfzyld");listV.add("主要领导");
            listK.add("fmxx");listV.add("负面信息");
            listK.add("sfbg");listV.add("是否变更");
//            listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");


        }else if(auditOpinionVo.getBussinessType().equals("2")){

            listK.add("num");listV.add("序号");
            listK.add("department");listV.add("工作单位");
            listK.add("userName");listV.add("姓名");
            listK.add("sex");listV.add("性别");
            listK.add("birthDate");listV.add("出生日期");
            listK.add("politicalAffi");listV.add("政治面貌");
            listK.add("health");listV.add("健康状况");
            listK.add("job");listV.add("职务");
            listK.add("abroadTime");listV.add("出境日期");
            listK.add("returnTime");listV.add("入境日期");
            listK.add("sdgj");listV.add("目的地");
            listK.add("cfrw");listV.add("出访任务");  //  少一列 状态
            listK.add("cgspdw");listV.add("审批单位");
            listK.add("clshsftg");listV.add("材料审核结果");

            listK.add("jwjl");listV.add("纪委结论");
            listK.add("verbaljwjl");listV.add("口头意见");
            listK.add("officaljwjl");listV.add("书面意见");
//            listK.add("businessType");listV.add("申请类型");




//            listK.add("daynums");listV.add("距离出境天数");


            listK.add("cfrw");listV.add("任务/事由");  //  少一列 状态
            listK.add("secretLevel");listV.add("涉密等级");  //  少一列 状态
            // listK.add("secretLevel");listV.add("涉密等级");
            listK.add("declassificaEndTime");listV.add("脱密期结束时间");
            listK.add("sflg");listV.add("是否裸官");
            listK.add("identity");listV.add("身份类别");
            listK.add("sfzyld");listV.add("主要领导");
            listK.add("fmxx");listV.add("负面信息");
//            listK.add("sfbg");listV.add("是否变更");
//            listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");

         }
        else if(auditOpinionVo.getBussinessType().equals("3")){

            listK.add("num");listV.add("序号");
            listK.add("department");listV.add("工作单位");
            listK.add("userName");listV.add("姓名");
            listK.add("sex");listV.add("性别");
            listK.add("birthDate");listV.add("出生日期");
            listK.add("politicalAffi");listV.add("政治面貌");
            listK.add("health");listV.add("健康状况");
            listK.add("job");listV.add("职务");
            listK.add("abroadTime");listV.add("出境日期");
            listK.add("returnTime");listV.add("入境日期");
            listK.add("sdgj");listV.add("目的地");
            listK.add("cfrw");listV.add("出访任务");  //  少一列 状态
            listK.add("cgspdw");listV.add("审批单位");
            listK.add("clshsftg");listV.add("材料审核结果");

            listK.add("jwjl");listV.add("纪委结论");
            listK.add("verbaljwjl");listV.add("口头意见");
            listK.add("officaljwjl");listV.add("书面意见");
//            listK.add("businessType");listV.add("申请类型");




//            listK.add("daynums");listV.add("距离出境天数");


            listK.add("cfrw");listV.add("任务/事由");  //  少一列 状态
            listK.add("secretLevel");listV.add("涉密等级");  //  少一列 状态
            // listK.add("secretLevel");listV.add("涉密等级");
            listK.add("declassificaEndTime");listV.add("脱密期结束时间");
            listK.add("sflg");listV.add("是否裸官");
            listK.add("identity");listV.add("身份类别");
            listK.add("sfzyld");listV.add("主要领导");
            listK.add("fmxx");listV.add("负面信息");
//            listK.add("sfbg");listV.add("是否变更");
//            listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");

    }


    return LeaderSupervisionUntil.exportExcelByListMap(listK,listV,dataList,"记录意见导出");


    }

    /**人员备案表 导出 **/

    public HSSFWorkbook putonRecordExport(LeaderSupervisionVo leaderSupervisionVo){


        List<Map> dataList =  leaderCommonQueryMapper.createPutOnRecordList(
                null,
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray()

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
        listK.add("num");listV.add("序号");
        listK.add("department");listV.add("工作单位");
        listK.add("userName");listV.add("姓名");
        listK.add("sex");listV.add("性别");
        listK.add("birthDate");listV.add("出生日期");
        listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
        listK.add("job");listV.add("职务");
        listK.add("bah");listV.add("备案号");  // 少一列 健康情况
        listK.add("abroadTime");listV.add("出境日期");
        listK.add("returnTime");listV.add("入境日期");
        listK.add("sdgj");listV.add("目的地");
        listK.add("cfsy");listV.add("事由");
        listK.add("cgspdw");listV.add("审批单位");

        listK.add("jwjl");listV.add("纪委结论");
        listK.add("clshsftg");listV.add("材料审核");
        listK.add("cadresupervisionOpinion");listV.add("干部监督处意见");
        listK.add("chulingdaoOpinion");listV.add("处领导意见");
        listK.add("bulingdaoOpinion");listV.add("部领导意见");
        listK.add("zzjl");listV.add("最终结论");


//        listK.add("verbaljwjl");listV.add("口头意见");
//        listK.add("officaljwjl");listV.add("书面意见");
//        listK.add("businessType");listV.add("申请类型");

        // listK.add("sex");listV.add("性别");
        // listK.add("politicalAffi");listV.add("政治面貌");  // 少一列 健康情况
        // 少一列 备案号

//        listK.add("daynums");listV.add("距离出境天数");


//        listK.add("cfrw");listV.add("任务/事由");  //  少一列 状态
        listK.add("secretLevel");listV.add("涉密等级");  //  少一列 状态
        // listK.add("secretLevel");listV.add("涉密等级");
        listK.add("declassificaEndTime");listV.add("脱密期结束时间");
        listK.add("sflg");listV.add("裸官");
        listK.add("identity");listV.add("身份类别");
        listK.add("sfzyld");listV.add("主要领导");
        listK.add("fmxx");listV.add("负面信息");
//        listK.add("sfbg");listV.add("是否变更");
//        listK.add("sfbczqjwyj");listV.add("需要征求纪委意见");
//        listK.add("sczqjwyjsj");listV.add("上次征求时间");
//        listK.add("sfzqjiwyj");listV.add("是否已征求纪委意见");



        return LeaderSupervisionUntil.exportExcelByListMap(listK,listV,dataList,"记录意见导出");


    }


    /**
     * 省管登记备案导出
     * @param idStr
     * @return
     */
    public HSSFWorkbook exportRfInfo(String idStr) {
        List<String> ids = null;
        if (!StringUtils.isBlank(idStr)){
            ids = Arrays.asList(idStr.split(","));
        }

        //处理批次数据，下载后改变状态
        dealDataRFByRfId(idStr);
        List<Map> dataList  = mrpinfoMapper.selectRegInfoListById(ids);
        List listK = new ArrayList();
        List listV = new ArrayList();
        listK.add("num");listV.add("序号");
        listK.add("surname");listV.add("中文姓");
        listK.add("name");listV.add("中文名");
        listK.add("sex");listV.add("性别");
        listK.add("birthDateGb");listV.add("出生日期");
        listK.add("idnumberGb");listV.add("身份证号");
        listK.add("registeResidence");listV.add("户口所在地");
        listK.add("inboundFlag");listV.add("入库标识");
        listK.add("workUnit");listV.add("工作单位");
        listK.add("post");listV.add("职务(级)或职称");
        listK.add("personManager");listV.add("人事主管单位");
        listK.add("暂无数据");listV.add("报送单位组织机构代码");
        listK.add("暂无数据");listV.add("报送单位名称");
        listK.add("暂无数据");listV.add("报送单位类别");
        listK.add("暂无数据");listV.add("报送单位联系人");
        listK.add("暂无数据");listV.add("联系电话");
        listK.add("暂无数据");listV.add("入库批号");

        return LeaderSupervisionUntil.exportRfInfoByListMap(listK,listV,dataList,"表1（纸）","表1（电子）");
    }

    private void dealDataRFByRfId(String idStr) {
        List<String> ids = null;
        if (!StringUtils.isBlank(idStr)){
            ids = Arrays.asList(idStr.split(","));
        }
        List<OmsRegProcbatchPerson> orpbplist = new ArrayList<>();
        //查询登记备案信息根据备案id
        List<OmsRegProcpersoninfo> rflist = mrpinfoMapper.selectListById(ids);
        //查询批次相关信息
        OmsRegProcbatch batchinfo = orpbatchService.selectWbaByOrpbatch();
        if (batchinfo!=null){
            for (int i = 0; i < rflist.size(); i++) {
                OmsRegProcpersoninfo info = rflist.get(i);
                OmsRegProcbatchPerson batchperson = new OmsRegProcbatchPerson();
                //为批次人员表复制相同字段的数据
                BeanUtils.copyProperties(rflist, batchperson);
                batchperson.setId(UUIDGenerator.getPrimaryKey());
                batchperson.setRfId(info.getId());
                batchperson.setBatchId(batchinfo.getBatchNo());
                orpbplist.add(batchperson);
            }
            int con = orpbatchService.batchinsertInfo(orpbplist);
            if (con > 0) {
                //修改批次表备案状态0未备案，1已备案，2已确认
                batchinfo.setStatus("1");
                int con1 = orpbatchService.updateOrpbatch(batchinfo);
                if (con1 > 0) {
                    mrpinfoService.updateRegProcpersoninfo(idStr);
                }
            }
        }else{
            throw new CustomMessageException("请先启动登记备案再下载。");
        }

    }


}
