package com.hxoms.modules.leaderSupervision.service.impl;

import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
 public HSSFWorkbook jiweiApplyExport(LeaderSupervisionVo leaderSupervisionVo){



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


}
