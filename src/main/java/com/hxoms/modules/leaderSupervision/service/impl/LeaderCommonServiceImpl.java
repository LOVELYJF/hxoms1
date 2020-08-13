package com.hxoms.modules.leaderSupervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.leaderSupervision.Enum.BussinessApplyStatus;
import com.hxoms.modules.leaderSupervision.entity.OmsJiweiOpinion;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.mapper.OmsJiweiOpinionMapper;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.OmsBatchApplybusinessService;
import com.hxoms.modules.leaderSupervision.service.OmsLeaderBatchService;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.*;
import com.hxoms.modules.privateabroad.entity.OmsAbroadApproval;
import com.hxoms.modules.privateabroad.service.OmsAbroadApprovalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service("leaderCommonService")
public class LeaderCommonServiceImpl implements LeaderCommonService {



    @Autowired
    private LeaderCommonMapper leaderCommonQueryMapper;

    @Autowired
    private OmsLeaderBatchService  omsLeaderBatchService;  // 批次service
    @Autowired
    private OmsBatchApplybusinessService omsBatchApplybusinessService; //批次业务中间表service

    @Autowired
    private SelectMapper selectMapper;  // 通用 自定义sql
    @Autowired
    private OmsJiweiOpinionMapper omsJiweiOpinionMapper;  //记录纪委意见 Mapper
    @Autowired
    private OmsAbroadApprovalService omsAbroadApprovalService; // 审批表 service




    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional(readOnly=true)
    public PageInfo selectBusinessUser(LeaderSupervisionVo leaderSupervisionVo,int applyStatus) {


        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
        List<Map>   users = leaderCommonQueryMapper.selectBusinessUser(applyStatus,null,leaderSupervisionVo.getUserName(),leaderSupervisionVo.getBussinessType());

        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;

    }



    /**
     *  TODO 根据选择的人员 新建批次 页面  返回 批次号，和受理时间
     * */
    public Map  createBacthByUsers(){

        Map module = new LinkedHashMap();

//        List list = leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList());
//
//        LeaderSupervisionUntil.throwableByParam(
////                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()),
////                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessName()).collect(Collectors.toList())
//                leaderSupervisionVo
//        );

        // 查询当天最大的批次
        QueryWrapper<OmsLeaderBatch> leaderBatch_wrapper = new QueryWrapper<OmsLeaderBatch>();
        leaderBatch_wrapper.like("name", LeaderSupervisionUntil.selectorDateFormat(LeaderSupervisionUntil.leaderselectorFormatter));
        leaderBatch_wrapper.select("ifnull(max(name),'wu') as maxBatchCode ");
        Map<String, Object> map = omsLeaderBatchService.getMap(leaderBatch_wrapper);
        // 根据 所选择的人员 进行新建批次
        String batchName =  LeaderSupervisionUntil.factoryBatchCode(map.get("maxBatchCode").toString());

        module.put("batchName",batchName);
        module.put("acceptDate", LeaderSupervisionUntil.selectorDateFormat("yyyy.MM.dd"));

        return module;

    }






    /**
     *  TODO 保存批次 以及保存批次业务中间表  入口 : 业务受理
     * */
    @Transactional(rollbackFor = CustomMessageException.class)
    public void  saveBatch(LeaderSupervisionVo leaderSupervisionVo){

        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo.getBatchName(),leaderSupervisionVo.getAccpetDate(),

//                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()),
//                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessName()).collect(Collectors.toList())
                leaderSupervisionVo
        );
         // 保存批次表 (第一步)
        OmsLeaderBatch omsLeaderBatch = new OmsLeaderBatch();

        omsLeaderBatch.setId(UUIDGenerator.getPrimaryKey());
        omsLeaderBatch.setAcceptDate(leaderSupervisionVo.getAccpetDate());
        omsLeaderBatch.setName(leaderSupervisionVo.getBatchName());
        omsLeaderBatch.setCreateTime(new Date());
        omsLeaderBatch.setCreateUser(UserInfoUtil.getUserInfo().getUserName());
        // 修改 批次状态 为 业务受理
        omsLeaderBatch.setMasterStatus(String.valueOf(Constants.leader_business[0]));
        omsLeaderBatchService.save(omsLeaderBatch);
        //保存 批次 与 业务申请表 之间的关系
        saveBatchApply(leaderSupervisionVo,omsLeaderBatch.getId());
        // 修改 申请 业务 表 状态 (修改为业务受理) (第三步)
        // (这块的 新建 批次 如何 是从业务受理 从 征求纪委意见 入口中也可以新建批次)
//        updteBussinessApplyStatue(leaderSupervisionVo.getBussinessId(),leaderSupervisionVo.getBussinessName(),Constants.leader_businessName[0]);

       // 修改批次 状态 去 批次中 流程走的最快的 那个 业务申请 (第四步)

      //  omsLeaderBatch.setMasterStatus(String.valueOf(Constants.leader_business[0]));



    }


    /**
     *
     * TODO 根据 选择 人员 选择 要纳入的批次
     *
     * **/

    public void leaderBatchAddApplyUser(LeaderSupervisionVo leaderSupervisionVo){


        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo.getLeaderBtachId(),
//                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()),
//                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessName()).collect(Collectors.toList())
                leaderSupervisionVo

        );
        //纳入的批次 所选择的人
        saveApplyBussinessByBatchId(leaderSupervisionVo, leaderSupervisionVo.getLeaderBtachId(),"leader_batch_id");





    }



    /**
     *  TODO 保存批次与业务申请表 之间的关系 (可以修改选择的人员)
     * */
    private void saveBatchApply(LeaderSupervisionVo leaderSupervisionVo,String batchId) {


        // 由于可以修改需要 解除以前的关系，以及 更具类型 回退状态

        relieveBatchIdAndRollbackStatus(batchId);

        //保存 业务和批次之间的关系
        saveApplyBussinessByBatchId(leaderSupervisionVo, batchId,"leader_batch_id");

    }

    // TODO 保存 业务申请 与 主表 之间的关系
    public void saveApplyBussinessByBatchId(LeaderSupervisionVo leaderSupervisionVo , String batchId,String masterTableId) {


        List<BussinessTypeAndIdVo> lists = leaderSupervisionVo.getBussinessTypeAndIdVos()  ;




        for(int i=0;i<lists.size();i++) {

            String bussinessType = LeaderSupervisionUntil.selectorBussinessTypeByName(lists.get(i).getBussinessName());

            String updateApplyStatusSql = getsavebatchIdbyApplybussiness(lists.get(i).getBussinessId(), bussinessType, batchId,masterTableId);

            log.info("保存 业务申请 表 与 主表 之间的 关系的 sql ="+updateApplyStatusSql);

            SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
            selectMapper.update(instance);

        }
    }

    //解除 与批次之间的关系， 回退状态

    public void relieveBatchIdAndRollbackStatus(String batchId) {


        for(BussinessApplyStatus applyStatus  : BussinessApplyStatus.values()){

            String relieveBatchIdAndRollbackStatusSql= " update ";


            relieveBatchIdAndRollbackStatusSql+="\t" +applyStatus.getTableName() + " set " + applyStatus.getApplySatus() +"\t"
                    +" = "+applyStatus.getLeaderNeedInitializeStatus() + "," +"  leader_batch_id ='' " + " where id =  '" +batchId+"'";

            SqlVo instance = SqlVo.getInstance(relieveBatchIdAndRollbackStatusSql);
            selectMapper.update(instance);


        }



    }


    // TODO 保存 业务申请 表 与 主表 之间的 关系
    private String getsavebatchIdbyApplybussiness(String bussinessId, String bussinessType, String batchId,String masterTableField) {

       String saveBatchIdgiveApplyBussiness = " update  " + bussinessType +" set "+masterTableField+" = '" + batchId + "' where  id = '" +bussinessId+"'";

       return  saveBatchIdgiveApplyBussiness;


    }

    // 修改 业务 流程表 的状态
    public void updteBussinessApplyStatue(List<BussinessTypeAndIdVo> bussinessTypeAndIdVos,String leaderStatusName) {


          for(int i=0;i<bussinessTypeAndIdVos.size();i++){

             String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(bussinessTypeAndIdVos.get(i).getBussinessName());


             String updateApplyStatusSql =   getUpdateStatusSql(bussinessTypeAndIdVos.get(i).getBussinessId(),bussinessType,leaderStatusName);

             log.info("修改业务 流程的 sql ="+updateApplyStatusSql);


            if(updateApplyStatusSql.length()>0){

                  SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
                  selectMapper.update(instance);


              }


          }



    }


    // 修改 业务 流程表 的状态
    public void updteBussinessApplyStatueAudit(List<BusinessTypeAndIdAndOnJobVo> businessTypeAndIdAndOnJobVos,String leaderStatusName) {


        for(int i=0;i<businessTypeAndIdAndOnJobVos.size();i++){

            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(businessTypeAndIdAndOnJobVos.get(i).getBussinessName());


            String updateApplyStatusSql =   getUpdateStatusSql(businessTypeAndIdAndOnJobVos.get(i).getBussinessId(),bussinessType,leaderStatusName);

            log.info("修改业务 流程的 sql ="+updateApplyStatusSql);


            if(updateApplyStatusSql.length()>0){

                SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
                selectMapper.update(instance);


            }


        }



    }


    public void updateBussinessApplyRecordOpinion(List<BussinessTypeAndIdVo> bussinessTypeAndIdVos,String opinion,String recordFlow){


        for(int i=0;i<bussinessTypeAndIdVos.size();i++){

            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(bussinessTypeAndIdVos.get(i).getBussinessName());


            String updatRecordOpinionSql =   getUpdateRecordOpinionSql(bussinessTypeAndIdVos.get(i).getBussinessId(),bussinessType,opinion,recordFlow);

            log.info("修改业务意见记录 流程的 sql ="+updatRecordOpinionSql);


            if(updatRecordOpinionSql.length()>0){

                SqlVo instance = SqlVo.getInstance(updatRecordOpinionSql);
                selectMapper.update(instance);


            }


        }



    }

    public void updateBussinessApplyRecordOpinionAudit(List<BusinessTypeAndIdAndOnJobVo> businessTypeAndIdAndOnJobVos,String opinion,String recordFlow){


        for(int i=0;i<businessTypeAndIdAndOnJobVos.size();i++){

            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(businessTypeAndIdAndOnJobVos.get(i).getBussinessName());


            String updatRecordOpinionSql =   getUpdateRecordOpinionSql(businessTypeAndIdAndOnJobVos.get(i).getBussinessId(),bussinessType,opinion,recordFlow);

            log.info("修改业务意见记录 流程的 sql ="+updatRecordOpinionSql);


            if(updatRecordOpinionSql.length()>0){

                SqlVo instance = SqlVo.getInstance(updatRecordOpinionSql);
                selectMapper.update(instance);


            }


        }



    }


    // TODO 材料审核 审批 人员名单

    @Override
    @Transactional(readOnly=true)
    public PageInfo selectMaterialReviewBusinessUser(LeaderSupervisionVo leaderSupervisionVo) {


        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
        List<Map>   users = leaderCommonQueryMapper.selectMaterialReviewBusinessUser();

        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;

    }




    // TODO  修改业务流程 记录 审批 意见
    public String getUpdateRecordOpinionSql(String busessId,String bussinesType,String opinion,String recordFlow){


        String updateSql = "update "+bussinesType;

        String setSql = " set  " ;

        String whereCondition = " where id = '" + busessId+"'";

        String realOpinion ="";

        // TODO 最终结论 后面覆盖 前面

        if("clshsftg".equals(recordFlow)){   // 材料审核是否通过

            setSql+= " CLSHSFTG " + " = " + opinion+", zzjl = " + opinion ;

            // FINAL_CONCLUSION

            return  updateSql+setSql+whereCondition;

        }else if("jwjl".equals(recordFlow)){  //  纪委结论

//            String  sql  = "SELECT " +
//                    " -- 1 同意  2 不同意  4 反复  3 不回复 " +
//                    "case when opinion in ('01','10','11','13','31') then 1 when opinion in ('20','02','22','23','32') then 2 when opinion in ('12','21')  then 4 else 3 end" +
//                    " " +
//                    "from( " +
//                    "select CONCAT(IFNULL(feedback_verdict,'0'),IFNULL(official_feedback_verdict,'0')) as opinion  from oms_jiwei_opinion  " +
//                    ")temp where id = " +busessId;
            String sql = "select ifnull(official_feedback_verdict,feedback_verdict) as opinion from oms_jiwei_opinion where id ="+busessId;
            SqlVo instance1 = SqlVo.getInstance(sql);
            List<LinkedHashMap<String, Object>> linkedHashMaps =  selectMapper.select(instance1);



            setSql+= " jwjl " + " = " + linkedHashMaps.get(0).get("opinion")+", zzjl = " + linkedHashMaps.get(0).get("opinion") ;


            return  updateSql+setSql+whereCondition;
        }else if("jdcjl".equals(recordFlow)){  // 监督处最终结论

            setSql+= " jdcjl " + " = " + opinion+", zzjl = " + opinion ;


            return  updateSql+setSql+whereCondition;
        }else{


            // 其它 流程步骤 修改 最终 结论

            setSql+= "zzjl = " + opinion;


            return  updateSql+setSql+whereCondition;
        }




    }



   // TODO  统一 修改 业务申请表状态
    public String getUpdateStatusSql(String busessId,String bussinesType,String leaderStatusName){

        String updateSql = "update "+bussinesType;

        String setSql = " set  " ;

        String whereCondition = " where id = '" + busessId+"'";


        for(BussinessApplyStatus applyStatus  : BussinessApplyStatus.values()){

            if(bussinesType.indexOf(applyStatus.getTableName())!=-1){

                String status =  applyStatus.getApplySatus();
               // 干部监督处的状态
                setSql+= status + "=" + Constants.leader_business[LeaderSupervisionUntil.getIndexByArray(Constants.leader_businessName,leaderStatusName)];

                break;


            }

        }

        return  updateSql+setSql+whereCondition;


    }


    // TODO  修改 业务 流程表 的状态  (对 以前 修改业务表 方法 进行 重写)
    /**
     *
     * @param  leaderStatusName 审批步骤
     *
     * @param  ispass  更具 是否通过 判断 该条 业务申请流程 是否 已办结
     *
     * import  只要是 数组参数 必须要保持  对应一致
     *
     *
     *
     * ****/

    public void updteBussinessApplyStatue(List<BusinessTypeAndIdAndOnJobVo> businessTypeAndIdAndOnJobVos, String leaderStatusName, String ispass) {


        for(int i=0;i<businessTypeAndIdAndOnJobVos.size();i++){

            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(businessTypeAndIdAndOnJobVos.get(i).getBussinessName());
            // 在职 状态
            String incumbencyStatus = businessTypeAndIdAndOnJobVos.get(i).getIncumbencyStatusArrays();

            String updateApplyStatusSql =   getUpdateStatusSql(businessTypeAndIdAndOnJobVos.get(i).getBussinessId(),bussinessType,leaderStatusName,incumbencyStatus,ispass);

            log.info("修改业务 流程的 sql ="+updateApplyStatusSql);


            if(updateApplyStatusSql.length()>0){

                SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
                selectMapper.update(instance);


            }


        }



    }


    public String getUpdateStatusSql(String busessId,String bussinesType,String leaderStatusName,String incumbencyStatus,String ispass){

        String updateSql = "update "+bussinesType;

        String setSql = " set  " ;

        String whereCondition = " where id = '" + busessId+"'";


        for(BussinessApplyStatus applyStatus  : BussinessApplyStatus.values()){

            if(bussinesType.indexOf(applyStatus.getTableName())!=-1){

                if("oms_pri_apply".equals(bussinesType)){

                    //TODO 因私 退休的处长审批通过，状态置为已办结，不通过的，部长审批
                    if(leaderStatusName.equals(Constants.leader_businessName[4])&&"3".equals(incumbencyStatus)&&"pass".equals(ispass)){

                        String status =  applyStatus.getApplySatus();
                        setSql+= status + "=" + Constants.leader_business[Constants.leader_business.length-1];

                        return  updateSql+setSql+whereCondition;


                    }
                    // TODO 因私 部长 审批 ，通过 状态 置为 已办结 因私干部监督处 流程 走完
                    else if(leaderStatusName.equals(Constants.leader_businessName[5])&&"pass".equals(ispass)){

                        String status =  applyStatus.getApplySatus();
                        setSql+= status + "=" + Constants.leader_business[Constants.leader_business.length-1];

                        return  updateSql+setSql+whereCondition;

                    }


                }

                String status =  applyStatus.getApplySatus();
                // 干部监督处的状态
                setSql+= status + "=" + Constants.leader_business[LeaderSupervisionUntil.getIndexByArray(Constants.leader_businessName,leaderStatusName)];

             //   break;
                return  updateSql+setSql+whereCondition;


            }

        }

     //   return  updateSql+setSql+whereCondition;

        return null;


    }






    /**
     *
     *
     *   征求意见，记录意见
     *
     *
     *
     *
     * */


    // TODO 征求 纪委意见 查询页面

    @Override
    @Transactional(readOnly=true)
    public PageInfo selectjiweiBusinessUser(LeaderSupervisionVo leaderSupervisionVo) {


        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
        List<Map>   users = leaderCommonQueryMapper.selectJiweiApply(null);

        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;

    }

    // TODO  征求 纪委意见 导出
    @Transactional(rollbackFor = CustomMessageException.class)
    public void  updateBussinessFiledsByJiweiExport(LeaderSupervisionVo leaderSupervisionVo){

        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo);

        updateBussinessFiledByJiWeiExport(leaderSupervisionVo.getBussinessTypeAndIdVos(), Constants.leader_businessName[2]);

        updteBussinessApplyStatue(leaderSupervisionVo.getBussinessTypeAndIdVos(), Constants.leader_businessName[2]);

    }


    // TODO 征求 记录纪委意见 查询页面

    @Override
    @Transactional(readOnly=true)
    public PageInfo selectjiweiWriteBusinessUser(LeaderSupervisionVo leaderSupervisionVo) {


        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
        List<Map>   users = leaderCommonQueryMapper.selectJiweiWriteApply();

        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;

    }


    public  void updateBussinessFiledByJiWeiExport(List<BussinessTypeAndIdVo> bussinessTypeAndIdVos,String leaderStatusName){


        for(int i=0;i<bussinessTypeAndIdVos.size();i++){

            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(bussinessTypeAndIdVos.get(i).getBussinessName());


            String updateSql = "update "+bussinessType;
            // 征求纪委 意见 时间 ，  选中导出的 就是 征求 过的 所以 该 字段 值 置为 1
            String setSql = " set SCZQJWYJSJ = DATE_FORMAT(now(),'%Y.%m.%d') , SFZQJWYJ = 1 " ;

            String whereCondition = " where id = '" + bussinessTypeAndIdVos.get(i).getBussinessId()+"'";

            log.info("sql ="+updateSql+setSql+whereCondition);

            SqlVo instance = SqlVo.getInstance(updateSql+setSql+whereCondition);
            selectMapper.update(instance);
        }



    }



    //  TODO 点击 纪委意见记录 触发的 事件
    @Transactional(rollbackFor = CustomMessageException.class)
    public void clickJieweiOpinion(OmsJiweiOpinionVo omsJiweiOpinionVo){

        LeaderSupervisionUntil.throwableByParam(omsJiweiOpinionVo);

        // 点击 这个 按就 就保存 纪委意见 表，以及 保存 纪委意见记录 与 业务表之间的关系
        for(int i=0;i<omsJiweiOpinionVo.getBussinessTypeAndIdVos().size();i++){

//            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(omsJiweiOpinionVo.getBussinessName()[i]);

//            String selectSql = " select  id as id   from oms_jiwei_opinion  where id = " +omsJiweiOpinionVo.getBussinessId()[i];
//
//            SqlVo instance = SqlVo.getInstance(selectSql);
//
//            List<LinkedHashMap<String, Object>> list = selectMapper.select(instance);

             OmsJiweiOpinion omsJiweiOpinion =  omsJiweiOpinionMapper.selectById(omsJiweiOpinionVo.getBussinessTypeAndIdVos().get(i).getBussinessId());

            if(omsJiweiOpinion==null){
                  // 如果 该条  业务申请 记录 的 纪委id 为空
                  OmsJiweiOpinion omsJiweiOpinion1 = new OmsJiweiOpinion();

                omsJiweiOpinion1.setId(omsJiweiOpinionVo.getBussinessTypeAndIdVos().get(i).getBussinessId());
                  omsJiweiOpinionMapper.insert(omsJiweiOpinion1);

            }else{

                log.info("纪委意见 记录已存在 ");

            }

        }



    }

    // TODO 保存  纪委意见 以及 保存 与申请业务的的关系
    @Transactional(rollbackFor = CustomMessageException.class)
    public void saveJieweiOpinion(OmsJiweiOpinionVo omsJiweiOpinionVo){

        LeaderSupervisionUntil.throwableByParam(omsJiweiOpinionVo);

        OmsJiweiOpinion omsJiweiOpinion = new OmsJiweiOpinion();
        if(omsJiweiOpinionVo.getFeedbackType()==String.valueOf(1)){
            // 口头 反馈
           // omsJiweiOpinion.setId(UUIDGenerator.getPrimaryKey());
            BeanUtils.copyProperties(omsJiweiOpinionVo, omsJiweiOpinion);
          //  omsJiweiOpinionMapper.insert(omsJiweiOpinion);
        }else if(omsJiweiOpinionVo.getFeedbackType()==String.valueOf(2)){
            // 书面 反馈

            omsJiweiOpinion.setOfficialFeedbackType("2");
            Class<?>  omsJiweiOpinionClass =   omsJiweiOpinion.getClass();

            Field[] fields =   omsJiweiOpinionClass.getDeclaredFields();

            for (int i = 0; i <fields.length ; i++) {

                Field field = fields[i];
                field.setAccessible(true);

                if(field.getName().contains("opinion")){

                    omsJiweiOpinion.setOfficialOpinion(omsJiweiOpinionVo.getOpinion()!=null?omsJiweiOpinionVo.getOpinion():"");

                }

                if(field.getName().toLowerCase().contains("feedbackType".toLowerCase())){

                    omsJiweiOpinion.setOfficialFeedbackVerdict(omsJiweiOpinionVo.getFeedbackVerdict());

                }

                if(field.getName().contains("remark")){

                    omsJiweiOpinion.setOfficialRemark(omsJiweiOpinionVo.getRemark());
                }



            }

        }

        UpdateWrapper<OmsJiweiOpinion> updateWrapper = new UpdateWrapper<>();

        updateWrapper.in("id",omsJiweiOpinionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()));
        // 修改 纪委意见 表 (第一步)
        omsJiweiOpinionMapper.update(omsJiweiOpinion, updateWrapper);


//        omsJiweiOpinionMapper.update()

        // 保存 纪委意见 与 业务表 之间的 关系 (第二步)

//        saveApplyBussinessByBatchId(omsJiweiOpinionVo,omsJiweiOpinion.getId(),"jiwei_opinion_id");

        // 修改 业务申请 状态  （第三步） 修改 为 记录意见
        updteBussinessApplyStatue(omsJiweiOpinionVo.getBussinessTypeAndIdVos(), Constants.leader_businessName[3]);

        //在流程审批业务表 中记录纪委意见（第 三 点1 不）
        updateBussinessApplyRecordOpinion(omsJiweiOpinionVo.getBussinessTypeAndIdVos(),omsJiweiOpinionVo.getFeedbackVerdict(),"jwjl");

        //  修改 批次 状态 (第四步)

        // 4.1 根据 业务 id 获取 批次  次 方法 需要重构

//        String bussinessId = omsJiweiOpinionVo.getBussinessId()[0];
//
//        String bussinessName = omsJiweiOpinionVo.getBussinessName()[0];

        selectBatchIdAndisOrNotUpateBatchStatus(
                (String[])omsJiweiOpinionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                Constants.leader_business[3]);


    }

    // 更具 业务 流程 获取批次 并判断是否修改 批次 状态

    public void selectBatchIdAndisOrNotUpateBatchStatus(String[] bussinessId,int status){

//        String bussinessType = LeaderSupervisionUntil.selectorBussinessTypeByName(bussinessName);
//
//        String queryBatchIdSql     =   LeaderSupervisionUntil.getBatchIdByBuessinessId(bussinessId,bussinessType);
//
//        SqlVo instance = SqlVo.getInstance(queryBatchIdSql);
//
//        List<LinkedHashMap<String, Object>> lists = selectMapper.select(instance);

        List<Map> lists =   leaderCommonQueryMapper.selectLeaderBatchIdByBussinessIds(bussinessId);

        String batchId ="";
        if(lists!=null && lists.size()>0){
            for(Map tempMap : lists){


                batchId =   tempMap.get("batchid")!=null? tempMap.get("batchid").toString():"wu";
                if("wu".equals(batchId)){

                    throw new CustomMessageException("批次id为空，请仔细检查");
                }


                log.info("批次 id ="+batchId);

                // 修改批次状态
                selectMaxStatusBybatchId(batchId,status);

            }

        }



//        String batchId ="";
//
//        if(lists!=null && lists.size()>0){
//
//            batchId =   lists.get(0).get("batchId")!=null? lists.get(0).get("batchId").toString():"wu";
//
//        }
//
//        if("wu".equals(batchId)){
//
//            throw new CustomMessageException("批次id为空，请仔细检查");
//        }
//
//        log.info("批次 id ="+batchId);
//
//        // 修改批次状态
//        selectMaxStatusBybatchId(batchId,status);


    }



    // TODO 当 某个 申请流程 小于 当前批次 的最大流程 不作为
    public void selectMaxStatusBybatchId(String batchId,int status){


        int maxStatus =   leaderCommonQueryMapper.selectMaxStatusByBatchId(batchId);

        log.info("当前 批次的 最大 状态 status ="+maxStatus);

        // 如果 最大 流程 申请状态 不等于 已办结

        if(maxStatus!= Constants.leader_business[Constants.leader_business.length-1]){

            if(status>maxStatus){

                QueryWrapper<OmsLeaderBatch> queryWrapper = new QueryWrapper<OmsLeaderBatch>();
                queryWrapper.eq("id", batchId);

                OmsLeaderBatch omsLeaderBatch = new OmsLeaderBatch();

                omsLeaderBatch.setMasterStatus(String.valueOf(status));

                omsLeaderBatchService.update(omsLeaderBatch,queryWrapper);




            }



        }else{

            // 如何 最大 流程 等于 已办结

            // 第一步 需要判断 是不是 所以的流程 都等于已办结
        List<Map> listStatus =    leaderCommonQueryMapper.selectStatusByBatchId(batchId);

        if(listStatus.size()==1&&Integer.valueOf(listStatus.get(0).get("status").toString())== Constants.leader_business[Constants.leader_business.length-1]){



            QueryWrapper<OmsLeaderBatch> queryWrapper = new QueryWrapper<OmsLeaderBatch>();
            queryWrapper.eq("id", batchId);

            OmsLeaderBatch omsLeaderBatch = new OmsLeaderBatch();

            omsLeaderBatch.setMasterStatus(String.valueOf(Constants.leader_business[Constants.leader_business.length-1]));

            omsLeaderBatchService.update(omsLeaderBatch,queryWrapper);
            }else{


            // （当流程 含 已办结 但有不全等于 已办结）  获取 第二大 的流程
            QueryWrapper<OmsLeaderBatch> queryWrapper = new QueryWrapper<OmsLeaderBatch>();
            queryWrapper.eq("id", batchId);

            OmsLeaderBatch omsLeaderBatch = new OmsLeaderBatch();

            omsLeaderBatch.setMasterStatus(listStatus.get(1).get("status").toString());

            omsLeaderBatchService.update(omsLeaderBatch,queryWrapper);




        }

        }





    }


    /***  代办业务管理   ****/
    // TODO 删除 批次
    public void deleteLeaderBatch(OmsLeaderBatch omsLeaderBatch){

        LeaderSupervisionUntil.throwableByParam(omsLeaderBatch.getId());

        omsLeaderBatchService.removeById(omsLeaderBatch.getId());

        relieveBatchIdAndRollbackStatus(omsLeaderBatch.getId());

    }

    /**
     *
     *
     *   作出审核意见   1.生成呈批单，审核意见自动置为通过
     *                2.生成请示表，审核意见自动置为不通过
     *
     *
     *
     * */


    /**
     *
     * 审核意见 查询页面
     *
     * **/

    @Override
    @Transactional(readOnly=true)
    public PageInfo selectAuditOpinionBusinessUser(AuditOpinionVo auditOpinionVo) {


        PageUtil.pageHelp(auditOpinionVo.getPageNum(), auditOpinionVo.getPageSize());
        List<Map>   users = leaderCommonQueryMapper.selectAuditOpinion(auditOpinionVo);

        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;

    }


    /**
     *  TODO 生成呈批单
     *
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    public void createChengPiDan(LeaderSupervisionVo leaderSupervisionVo){

        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo);



        // 保存 审批记录 （第一步）  1.生成呈批单，审核意见自动置为通过
        saveAbroadApprovalByBussinessId(leaderSupervisionVo.getBussinessTypeAndIdVos(),"通过", Constants.leader_businessName[3], Constants.leader_business[3],null);
//        omsAbroadApprovalService
        //  修改 业务流程状态 (第二步) 修改 为 干部监督处
        updteBussinessApplyStatue(leaderSupervisionVo.getBussinessTypeAndIdVos(), Constants.leader_businessName[3]);

        // 修改 业务流程申请 最终结论

        updateBussinessApplyRecordOpinion(leaderSupervisionVo.getBussinessTypeAndIdVos(),"1",null);

        //修改 批次状态 (第三步)
//        String bussinessId = auditOpinionVo.getBussinessId()[0];
//
//        String bussinessName = auditOpinionVo.getBussinessName()[0];
        selectBatchIdAndisOrNotUpateBatchStatus(
                (String[]) leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                Constants.leader_business[3]);

    }



    public void saveAbroadApprovalByBussinessId(List<BussinessTypeAndIdVo>  bussinessTypeAndIdVos, String pass,String stepName,int stepCode,String reson) {

        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();

        for(int i=0;i<bussinessTypeAndIdVos.size();i++){

            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(bussinessTypeAndIdVos.get(i).getBussinessName());

            OmsAbroadApproval omsAbroadApproval = new OmsAbroadApproval();
            omsAbroadApproval.setApplyId(bussinessTypeAndIdVos.get(i).getBussinessId()); // 业务流程 Id
            omsAbroadApproval.setStepCode(stepCode);      //  步骤编码
            omsAbroadApproval.setStepName(stepName);      //  步骤名称
            omsAbroadApproval.setType(bussinessType);  // 业务流程 类型(因公   因私  延期出国)
            if(StringUilt.stringIsNullOrEmpty(reson)){

                omsAbroadApproval.setApprovalAdvice(reson); //  审批意见
            }else{
                omsAbroadApproval.setApprovalAdvice(pass);  //  审批意见
            }

            omsAbroadApproval.setApprovalResult(pass); //  审批结论
            omsAbroadApproval.setApprovalUser(userInfo.getId());  // 审批人
            omsAbroadApproval.setApprovalTime(new Date());  // 审批时间
            omsAbroadApprovalService.insertOmsAbroadApproval(omsAbroadApproval);


        }



    }



    public void saveAbroadApprovalByBussinessIdByAudit(List<BusinessTypeAndIdAndOnJobVo> businessTypeAndIdAndOnJobVos, String pass,String stepName,int stepCode,String reson) {

        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();

        for(int i=0;i<businessTypeAndIdAndOnJobVos.size();i++){

            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(businessTypeAndIdAndOnJobVos.get(i).getBussinessName());

            OmsAbroadApproval omsAbroadApproval = new OmsAbroadApproval();
            omsAbroadApproval.setApplyId(businessTypeAndIdAndOnJobVos .get(i).getBussinessId()); // 业务流程 Id
            omsAbroadApproval.setStepCode(stepCode);      //  步骤编码
            omsAbroadApproval.setStepName(stepName);      //  步骤名称
            omsAbroadApproval.setType(bussinessType);  // 业务流程 类型(因公   因私  延期出国)
            if(StringUilt.stringIsNullOrEmpty(reson)){

                omsAbroadApproval.setApprovalAdvice(reson); //  审批意见
            }else{
                omsAbroadApproval.setApprovalAdvice(pass);  //  审批意见
            }

            omsAbroadApproval.setApprovalResult(pass); //  审批结论
            omsAbroadApproval.setApprovalUser(userInfo.getId());  // 审批人
            omsAbroadApproval.setApprovalTime(new Date());  // 审批时间
            omsAbroadApprovalService.insertOmsAbroadApproval(omsAbroadApproval);


        }



    }


    /**
     *
     *  TODO 生成 请示表
     *
     * **/
    @Transactional(rollbackFor = CustomMessageException.class)
    public void createInstructionsTable(LeaderSupervisionVo leaderSupervisionVo){

        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo);



        // 保存 审批记录 （第一步）  1.生成请示表，审核意见自动置为通过
        saveAbroadApprovalByBussinessId(leaderSupervisionVo.getBussinessTypeAndIdVos(),"不通过", Constants.leader_businessName[3], Constants.leader_business[3],null);
//        omsAbroadApprovalService
        //  修改 业务流程状态 (第二步) 修改 为 干部监督处
        updteBussinessApplyStatue(leaderSupervisionVo.getBussinessTypeAndIdVos(), Constants.leader_businessName[3]);

        // 修改 业务流程申请 最终结论

        updateBussinessApplyRecordOpinion(leaderSupervisionVo.getBussinessTypeAndIdVos(),"2",null);

        //修改 批次状态 (第三步)

        selectBatchIdAndisOrNotUpateBatchStatus(
                (String[]) leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),

                Constants.leader_business[3]);



    }


  /**
   *
   *
   *
   *  处领导审批
   *
   *
   *
   *
   *
   *
   *
   * **/


  // TODO 处领导 审批 查询 页面

  @Override
  @Transactional(readOnly=true)
  public PageInfo selectChuZhangBusinessUser(LeaderSupervisionVo leaderSupervisionVo) {


      PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
      List<Map>   users = leaderCommonQueryMapper.selectChuZhangApprover(leaderSupervisionVo);

      PageInfo pageInfo = new PageInfo(users);
      return pageInfo;

  }




    // TODO 处领导审批 批量 审批
    @Transactional(rollbackFor = CustomMessageException.class)
  public void  chuzhangAbroadApprovalBatch(AuditOpinionVo auditOpinionVo){

      LeaderSupervisionUntil.throwableByParam(auditOpinionVo,auditOpinionVo.getIspass());



      // 通过
      if("pass".equals(auditOpinionVo.getIspass())){

          // 保存 审批记录 （第一步）   处领导审批
          saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"通过", Constants.leader_businessName[4], Constants.leader_business[4],null);
//        omsAbroadApprovalService
          //  修改 业务流程状态 (第二步) 修改 为  处领导审批
          updteBussinessApplyStatue(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), Constants.leader_businessName[4],auditOpinionVo.getIspass());

          // 修改 业务流程申请 最终结论

          updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"1",null);


          //修改 批次状态 (第三步)
//          String bussinessId = auditOpinionVo.getBussinessId()[0];
//
//          String bussinessName = auditOpinionVo.getBussinessName()[0];
          selectBatchIdAndisOrNotUpateBatchStatus(
                  (String[]) auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),

                  Constants.leader_business[4]);


      }else if("nopass".equals(auditOpinionVo.getIspass())){
      // 不通过

          // 保存 审批记录 （第一步）   处领导审批
          saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"通过", Constants.leader_businessName[4], Constants.leader_business[4],null);
//        omsAbroadApprovalService
          //  修改 业务流程状态 (第二步) 修改 为  处领导审批
          updteBussinessApplyStatueAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),Constants.leader_businessName[4]);


          // 修改 业务流程申请 最终结论

          updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"2",null);

          //修改 批次状态 (第三步)
//          String bussinessId = auditOpinionVo.getBussinessId()[0];
//
//          String bussinessName = auditOpinionVo.getBussinessName()[0];
          selectBatchIdAndisOrNotUpateBatchStatus(
                  (String[]) auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                  Constants.leader_business[4]);



      }



  }

    // TODO 进入 处领导审批 逐条 审批 页面
    public Map goInChuZhangApplyPage(AuditOpinionVo auditOpinionVo){

        LeaderSupervisionUntil.throwableByParam(auditOpinionVo.getBusId(),auditOpinionVo.getBusName());



        String bussinessId = auditOpinionVo.getBusId();

        String busName  = auditOpinionVo.getBusName();


        String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(busName);

        String selectSql="";

        if("oms_pub_apply".equals(bussinessType)){
                             // 是否涉密人员
              selectSql = "select  "

                    + " '因公' as businessType "
                    + " mrp.INCUMBENCY_STATUS as incumbencyStatus "
                    + " SFSMRY as sfsmry "+

                    // 纪委意见 是否反复
                    "\t ( select if(CONCAT(feedback_verdict,official_feedback_verdict) in (12,21) ,1,2) as opinionsRepeatedly from oms_jiwei_opinion jiw  where jiw.id = '"+bussinessId+"')"
                    // 未征求纪委意见
                    +"\t SFZQJWYJ as sfzqjwyj"
                    // 是否重要领导
                    +  " \t IS_LEADERS as isLeaders"
                    // 纪委结论
                    +" JWJL as jwjl "
                    // 材料审核结论
                    + " CLSHSFTG as clshsftg "
                    + " from oms_pub_apply  pua " +
                      " left join  oms_reg_procpersoninfo mrp ON pua.A0100 = mrp.a0100 " +
                      "where id = '"+bussinessId+"'";


        }else if("oms_pri_apply".equals(bussinessType)){

             selectSql = "select  " +
                      " '因私' as businessType "
                     + "  mrp.INCUMBENCY_STATUS as incumbencyStatus "
                     // 纪委意见 是否反复
                     + "\t ( select if(CONCAT(feedback_verdict,official_feedback_verdict) in (12,21) ,1,2) as opinionsRepeatedly from oms_jiwei_opinion jiw  where jiw.id = '"+bussinessId+"')"
                    // 未征求纪委意见
                    +"\t pra.SFZQJWYJ as sfzqjwyj"
                    // 是否重要领导
                    +  " \t pra.IS_LEADERS as isLeaders"
                    // 纪委结论
                    +" pra.JWJL as jwjl "
                    // 材料审核结论
                    + " pra.CLSHSFTG as clshsftg "
                    + " from oms_pri_apply pra" +
                      "  left join  oms_reg_procpersoninfo mrp ON pra.A0100 = mrp.a0100   " +
                     "where id =  '" + bussinessId+"'";
                    ;


        }else if("oms_pri_delay_apply".equals(bussinessType)){

             selectSql = " select  " +
                     " '延期' as businessType "
                     + "  mrp.INCUMBENCY_STATUS as incumbencyStatus "
                     + "\t ( select if(CONCAT(feedback_verdict,official_feedback_verdict) in (12,21) ,1,2) as opinionsRepeatedly from oms_jiwei_opinion jiw  where jiw.id = '"+bussinessId+"')"
                    // 未征求纪委意见
                    +"\t pda.SFZQJWYJ as sfzqjwyj"
                    // 是否重要领导
                    +  " \t pda.IS_LEADERS as isLeaders"
                    // 纪委结论
                    +" pda.JWJL as jwjl "
                    // 材料审核结论
                    + " pda.CLSHSFTG as clshsftg " +
                    "   from  oms_pri_delay_apply pda left join oms_pri_apply pra on pda.APPLY_ID = pra.id "
                    +  "  left join  oms_reg_procpersoninfo mrp ON pra.A0100 = mrp.a0100 "
                    + "  where pda.id = '" + bussinessId+"'" ;
        }



        SqlVo instance = SqlVo.getInstance(selectSql);
        List<LinkedHashMap<String, Object>> linkedHashMap = selectMapper.select(instance);

        return  linkedHashMap!=null &&linkedHashMap.size()==1? linkedHashMap.get(0) : null;

    }


  // TODO 处领导审批 逐条 审批
  public void chuzhangAbroadApprovalOneByOne(AuditOpinionVo auditOpinionVo){


      LeaderSupervisionUntil.throwableByParam(auditOpinionVo.getBusId(),auditOpinionVo.getBusName(),auditOpinionVo.getIspass());

      // 通过
      if("pass".equals(auditOpinionVo.getIspass())){

          // 保存 审批记录 （第一步）   处领导审批
          List<BussinessTypeAndIdVo> bussinessTypeAndIdVos =  new ArrayList<BussinessTypeAndIdVo>();
          BussinessTypeAndIdVo bussinessTypeAndIdVo = new BussinessTypeAndIdVo();
          bussinessTypeAndIdVo.setBussinessId(auditOpinionVo.getBusId());
          bussinessTypeAndIdVo.setBussinessName(auditOpinionVo.getBusName());
          bussinessTypeAndIdVos.add(bussinessTypeAndIdVo);
          saveAbroadApprovalByBussinessId(bussinessTypeAndIdVos,"通过", Constants.leader_businessName[4], Constants.leader_business[4],null);
//        omsAbroadApprovalService
          //  修改 业务流程状态 (第二步) 修改 为  处领导审批
          updteBussinessApplyStatue(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), Constants.leader_businessName[4],auditOpinionVo.getIspass());

          // 修改 业务流程申请 最终结论

          updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"1",null);


          //修改 批次状态 (第三步)
//          String bussinessId = auditOpinionVo.getBussinessId()[0];
//
//          String bussinessName = auditOpinionVo.getBussinessName()[0];
          selectBatchIdAndisOrNotUpateBatchStatus(
                  (String[]) auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                  Constants.leader_business[4]);


      }else if("nopass".equals(auditOpinionVo.getIspass())){
          // 不通过

          // 保存 审批记录 （第一步）   处领导审批
          saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"通过", Constants.leader_businessName[4], Constants.leader_business[4],null);
//        omsAbroadApprovalService
          //  修改 业务流程状态 (第二步) 修改 为  处领导审批
          updteBussinessApplyStatueAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), Constants.leader_businessName[4]);


          // 修改 业务流程申请 最终结论

          updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"2",null);

          //修改 批次状态 (第三步)
//          String bussinessId = auditOpinionVo.getBussinessId()[0];
//
//          String bussinessName = auditOpinionVo.getBussinessName()[0];
          selectBatchIdAndisOrNotUpateBatchStatus(
                  (String[]) auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                  Constants.leader_business[4]);



      }




  }

    /**
     *
     *
     *
     *  部领导审批
     *
     *
     *
     *
     *
     * **/

    // TODO 部领导 审批 查询 页面

    @Override
    @Transactional(readOnly=true)
    public PageInfo selectBuZhangBusinessUser(LeaderSupervisionVo leaderSupervisionVo) {


        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
        List<Map>   users = leaderCommonQueryMapper.selectChuZhangApprover(leaderSupervisionVo);

        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;

    }



   // TODO 部领导 批量  审批
   @Transactional(rollbackFor = CustomMessageException.class)
  public  void buzhangAbroadApproval(AuditOpinionVo auditOpinionVo){


      LeaderSupervisionUntil.throwableByParam(auditOpinionVo,auditOpinionVo.getIspass());



      // 通过
      if("pass".equals(auditOpinionVo.getIspass())){

          // 保存 审批记录 （第一步）   部领导审批
          saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"通过", Constants.leader_businessName[5], Constants.leader_business[5],auditOpinionVo.getReason());
//        omsAbroadApprovalService
          //  修改 业务流程状态 (第二步) 修改 为  部领导审批
          updteBussinessApplyStatue(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), Constants.leader_businessName[5],auditOpinionVo.getIspass());

          // 修改 业务流程申请 最终结论

          updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"1",null);


          //修改 批次状态 (第三步)
//          String bussinessId = auditOpinionVo.getBussinessId()[0];
//
//          String bussinessName = auditOpinionVo.getBussinessName()[0];
          selectBatchIdAndisOrNotUpateBatchStatus(
                  (String[]) auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                  Constants.leader_business[5]);


      }else if("nopass".equals(auditOpinionVo.getIspass())){
          // 不通过

          // 保存 审批记录 （第一步）   部领导审批
          saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"通过", Constants.leader_businessName[5], Constants.leader_business[5],auditOpinionVo.getReason());
//        omsAbroadApprovalService
          //  修改 业务流程状态 (第二步) 修改 为  部领导审批
          updteBussinessApplyStatueAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), Constants.leader_businessName[5]);


          // 修改 业务流程申请 最终结论

          updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"2",null);

          //修改 批次状态 (第三步)

          selectBatchIdAndisOrNotUpateBatchStatus(
                  (String[]) auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                  Constants.leader_business[5]
          );



      }






  }




    /**
     *
     *
     *
     *  批件核实
     *
     *
     *
     *
     *
     * **/

    @Override
    @Transactional(readOnly=true)
    public PageInfo selectInstructionsVerify(LeaderSupervisionVo leaderSupervisionVo) {


        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
        List<Map>   users = leaderCommonQueryMapper.selectInstructionsVerify(leaderSupervisionVo);

        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;

    }


    /*** 批件核实审批页面 ***/
    @Transactional(rollbackFor = CustomMessageException.class)
    public void InstructionsVerifyApproval(AuditOpinionVo auditOpinionVo){

        LeaderSupervisionUntil.throwableByParam(auditOpinionVo,auditOpinionVo.getIspass());


        // 通过
        if("pass".equals(auditOpinionVo.getIspass())){

            // 保存 审批记录 （第一步）  批件核实
            saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"通过", Constants.leader_businessName[6], Constants.leader_business[6],null);
//        omsAbroadApprovalService
            //  修改 业务流程状态 (第二步) 修改 为  部领导审批
            updteBussinessApplyStatue(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), Constants.leader_businessName[6],auditOpinionVo.getIspass());

            // 修改 业务流程申请 最终结论

            updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"1",null);


            //修改 批次状态 (第三步)
//            String bussinessId = auditOpinionVo.getBussinessId()[0];
//
//            String bussinessName = auditOpinionVo.getBussinessName()[0];
            selectBatchIdAndisOrNotUpateBatchStatus(
                    (String[]) auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                    Constants.leader_business[6]);


        }else if("nopass".equals(auditOpinionVo.getIspass())){
            // 不通过

            // 保存 审批记录 （第一步）   部领导审批
            saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"通过", Constants.leader_businessName[6], Constants.leader_business[6],null);
//        omsAbroadApprovalService
            //  修改 业务流程状态 (第二步) 修改 为  部领导审批
            updteBussinessApplyStatueAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), Constants.leader_businessName[6]);


            // 修改 业务流程申请 最终结论

            updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(),"2",null);

            //修改 批次状态 (第三步)
//            String bussinessId = auditOpinionVo.getBussinessId()[0];
//
//            String bussinessName = auditOpinionVo.getBussinessName()[0];
            selectBatchIdAndisOrNotUpateBatchStatus(
                    (String[]) auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                    Constants.leader_business[6]);



        }


    }




































}
