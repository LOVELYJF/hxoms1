package com.hxoms.modules.leaderSupervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.entity.paramentity.SendMessageParam;
import com.hxoms.message.message.service.MessageService;
import com.hxoms.message.msguser.entity.MsgUser;
import com.hxoms.modules.file.entity.FileReplaceVO;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.OmsReplaceKeywords;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.file.service.OmsCreateFileService;
import com.hxoms.modules.file.service.impl.OmsFileServiceImpl;
import com.hxoms.modules.leaderSupervision.Enum.BussinessApplyStatus;
import com.hxoms.modules.leaderSupervision.entity.AttachmentAskforjiwei;
import com.hxoms.modules.leaderSupervision.entity.OmsAttachment;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.mapper.*;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.BussinessTypeAndIdVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.mapper.B01Mapper;
import com.hxoms.support.user.entity.User;
import dm.jdbc.dbaccess.Const;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @authore:wjf
 * @data 2020/7/29 10:30  对 干部监督 业务 一些的 细节处理
 *   没设好 应该 写个 AOP 统一处理这些问题
 * @Description:
 ***/
@Service("leaderDetailProcessingService")
public class LeaderDetailProcessingServiceImpl implements LeaderDetailProcessingService {


    @Autowired
    private MessageService messageService;

    @Autowired
    private LeaderCommonMapper leaderCommonMapper;

    @Autowired
    private SelectMapper selectMapper;  // 通用 自定义sql
    @Autowired
    private OmsLeaderBatchMapper omsLeaderBatchMapper;  // 干部 监督处批次 mapper
    @Autowired
    private OmsAttachmentMapper omsAttachmentMapper;
    @Autowired
    private AttachmentAskforjiweiMapper attachmentAskforjiweiMapper;
    @Autowired
    private LeaderCommonServiceImpl leaderCommonService;
    @Autowired
    private LeaderCommonDetailMapper leaderCommonDetailMapper;
    @Autowired
    private OmsFileMapper omsFileMapper;
    @Autowired
    private B01Mapper b01Mapper;
    @Autowired
    private OmsCreateFileService omsCreateFileService;
    @Autowired
    private OmsCreateFileMapper omsCreateFileMapper;

    @Autowired
    private OmsFileServiceImpl omsFileServiceImpl;


    @Value("${omsAttachment.baseDir}")
    private String attachmentPath;

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());



    // 业务 处理 材料审核 的 下一步 触发的事件
    public void materialReviewNextStep(String applyId,String tableCode){

     // TODO 保存自评

    //修改 业务流程 材料审核 以及 修改流程状态  查询的 时候 少了个 自评 的 来源 经办人，还是 干部监督处
      List<Map> lists =  leaderCommonMapper.selectMaterialReview(applyId);

      String updateBussinessCheck ="";

     if(lists.size()==0||lists==null ){

      throw new CustomMessageException("数据异常，请 联系开发人员");

       // 通过
     }else if(lists.size()==1 &&lists.get(0).get("result")=="1"){

         updateBussinessCheck=  getUpdateRecordOpinionSql(applyId,tableCode,"1","clshsftg");

     }else{
       // 不通过
         updateBussinessCheck=  getUpdateRecordOpinionSql(applyId,tableCode,"2","clshsftg");

     }

     if(updateBussinessCheck!=null && updateBussinessCheck.length()>0){

         SqlVo instance = SqlVo.getInstance(updateBussinessCheck);
         selectMapper.update(instance);

     }

     // 修改 流程 状态 置为 征求纪委意见
        getUpdateStatusSql(applyId,tableCode,Constants.leader_businessName[1],"materialReviewNextStep");
      // 修改批次状态
        List<String> bussinessIds = new ArrayList<>();
        bussinessIds.add(applyId);
        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(bussinessIds,Constants.leader_business[1]);

    }

    // 干部监督处 材料审核 给经办人 发送消息 通知经办人重新递交备案函
    /**
     * param : 参数 applyId  业务 id , tableCode 业务 类型(因公，因私，延期)
     *
     * */
    @Transactional(rollbackFor = CustomMessageException.class)
    public void sendMessageToAgent(String applyId,String tableCode){

        // 查询 经办人
        List<Map> receiveUserList=leaderCommonMapper.selectAgent(applyId,tableCode);

        //查询 发送内容
       List<Map> listMessage = leaderCommonMapper.selectSendAgentMessage(applyId);

        try {
            preAndRecMessage(listMessage.get(0)!=null?listMessage.get(0).get("message").toString():"",Constants.PERSONAL,receiveUserList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 修改流程状态， 将 流程 置为 初始状态

        String updateSql =   getUpdateStatusSql(applyId,tableCode,"1","sendMessageToAgent");

        SqlVo instance = SqlVo.getInstance(updateSql);
        selectMapper.update(instance);

        //TODO 当 把 流程 状态 置为 初始状态 需要 解除 他与 批次之间的 关系吗



    }



    public void preAndRecMessage(String msgContent,String receiveUserType, List<Map> receiveUserList) throws Exception{
        // 查询经办人
        if(receiveUserList==null||receiveUserList.size()==0)
            throw new CustomMessageException("经办人，未查到！");
        SendMessageParam sendMessageParam=new SendMessageParam();
        //设置信息内容
        Message message=new Message();
        message.setContent(msgContent);
        sendMessageParam.setMessage(message);
        //设置接收人
        List<MsgUser> msgUserList=new ArrayList<>();
        for (Map receiveUser:receiveUserList) {
            MsgUser msgUser=new MsgUser();
            msgUser.setReceiveUserId(receiveUser.get("id").toString());
            msgUser.setReceiveUsername(receiveUser.get("userName").toString());
            msgUserList.add(msgUser);
        }
        Map<String,List<MsgUser>> msgUserMap=new HashedMap();
        msgUserMap.put(receiveUserType,msgUserList);
        sendMessageParam.setMsgUserMap(msgUserMap);
        //发送信息
        messageService.sendMessage(sendMessageParam);
    }


    // TODO  统一 修改 业务申请表状态
    public String getUpdateStatusSql(String busessId,String bussinesType,String leaderStatusName, String methodName){

        String updateSql = "update "+bussinesType;

        String setSql = " set  " ;

        String whereCondition = " where id = " + busessId;


        for(BussinessApplyStatus applyStatus  : BussinessApplyStatus.values()){

            if(bussinesType.indexOf(applyStatus.getTableName())!=-1){

                String status =  applyStatus.getApplySatus();

                if("sendMessageToAgent".equals(methodName)){

                    setSql+= status + "= ‘" + leaderStatusName+"'";
                }else{

                    // 干部监督处的状态
                    setSql+= status + "=" + Constants.leader_business[LeaderSupervisionUntil.getIndexByArray(Constants.leader_businessName,leaderStatusName)];
                }


                break;


            }

        }

        return  updateSql+setSql+whereCondition;


    }



    // TODO  修改业务流程 记录 审批 意见
    public String getUpdateRecordOpinionSql(String busessId,String bussinesType,String opinion,String recordFlow){


        String updateSql = "update "+bussinesType;

        String setSql = " set  " ;

        String whereCondition = " where id = " + busessId;

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


    public List<Map> getApplicationType(){

        return leaderCommonMapper.selectgetApplicationType();


    }

    public PageInfo selectOmsLeaderBatch(LeaderSupervisionVo leaderSupervisionVo){

        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());

        List<Map> lists =   leaderCommonMapper.selectLeaderBatch();

        PageInfo pageInfo = new PageInfo(lists);
        return pageInfo;

    }

    public void updateLeaderBatch(OmsLeaderBatch omsLeaderBatch){

        LeaderSupervisionUntil.throwableByParam(omsLeaderBatch.getId());


        omsLeaderBatchMapper.updateById(omsLeaderBatch);

    }

    public List<Map> selectLeaderBatchStatus(){

       List lists = new ArrayList();

       int len = Constants.leader_business.length;

       for(int i=0;i<len;i++){

           Map map = new HashMap();

           map.put("code",Constants.leader_business[i]);
           map.put("name", Constants.leader_businessName[i]);

           lists.add(map);
       }
       return  lists;
    }

    public  List<Map> selectMaterialStatus(){


        List lists = new ArrayList();

        Map map = new HashMap();
        map.put("code","all");
        map.put("name","材料审核");
        Map map1 = new HashMap();
        map1.put("code","1");
        map1.put("name","已审核");
        Map map2 = new HashMap();
        map2.put("code","2");
        map2.put("name","未审核");

        lists.add(map); lists.add(map1); lists.add(map2);
        return  lists;



    }









    /**
     * 文件上传
     * @param files
     * @throws Exception
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    public void fileUpload(@RequestParam("file") MultipartFile[] files, String[] leaderBatchIds,String bussinessType,int bussinessOccureStpet,String bussinessOccureStpetName,  HttpServletRequest request) {

         if(files ==null || files.length<=0){
             throw new CustomMessageException("没有选择上传的文件，请仔细检查");

         }

         if(leaderBatchIds == null || files.length<=0){

             throw new CustomMessageException("没有选择关联的批次，请仔细检查");
         }

        //文件命名
        //保存时的文件名
        for(int i=0;i<files.length;i++) {
            //保存文件到本地文件，并保存路径到数据库
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            String fileName = df.format(calendar.getTime()) + files[i].getOriginalFilename();
            log.info("文件的文件名为:" + fileName);

            if(!files[i].getOriginalFilename().endsWith(".pdf")){

                throw new CustomMessageException(files[i].getOriginalFilename()+"此文件的格式不正确，请上传pdf格式文件");
            }

            //保存文件的绝对路径
//            String filePath = request.getSession().getServletContext().getRealPath("static/");
            String filePath = attachmentPath+File.separator+"static"+File.separator;
            log.info("文件的绝对路径:" + filePath);
            log.info(attachmentPath);

            try {
                //上传文件
                LeaderSupervisionUntil.uploadFile(files[i].getBytes(), filePath, fileName);
                //保存到数据库代码，存入路径以及文件名称
                saveAttachmentAndAskforJiwei(files[i], leaderBatchIds, bussinessOccureStpet, bussinessOccureStpetName, i, fileName, filePath + fileName);

            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                throw new CustomMessageException("文件上传失败");
            }
        }
    }

    private void saveAttachmentAndAskforJiwei(MultipartFile file, String[] leaderBatchIds, int bussinessOccureStpet, String bussinessOccureStpetName, int i, String fileName, String url) {
        // 保存 附件 表
        OmsAttachment omsAttachment = new OmsAttachment();
        omsAttachment.setId(UUIDGenerator.getPrimaryKey());
        omsAttachment.setSize(String.valueOf(file.getSize()));
        omsAttachment.setName(fileName);
        omsAttachment.setType(file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length-1]);
        omsAttachment.setUrl(url);
        omsAttachment.setBussinessOccureStpet(bussinessOccureStpet+"");
        omsAttachment.setBussinessOccureStpetName(bussinessOccureStpetName);
        omsAttachment.setCreateTime(new Date());
        omsAttachment.setCreateUser(UserInfoUtil.getUserInfo().getId());
        omsAttachmentMapper.insert(omsAttachment);
        // 保存 附件业务 的中间表
        for(int j=0;j<leaderBatchIds.length;j++){

            AttachmentAskforjiwei attachmentAskforjiwei = new AttachmentAskforjiwei();
            attachmentAskforjiwei.setId(UUIDGenerator.getPrimaryKey());
            attachmentAskforjiwei.setAttachmentid(omsAttachment.getId());
            attachmentAskforjiwei.setLeaderBatchId(leaderBatchIds[i]);
            attachmentAskforjiweiMapper.insert(attachmentAskforjiwei);

        }
    }

    public List<Map> selectAttachmentList(String[] leaderBatchIds){

       List<Map> lists =  leaderCommonMapper.selectAttachmentList(leaderBatchIds);

       return lists;
    }

    public Map downloadAttachmentById(String id){

        Map map = new HashMap();

      OmsAttachment omsAttachment =   omsAttachmentMapper.selectById(id);
        try {
            map.put("array",LeaderSupervisionUntil.downloadFile(omsAttachment.getUrl())) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("fileName",omsAttachment.getName());
        return map;

    }

    /**
     *
     * 生成审批单
     * */
    @Transactional(rollbackFor = CustomMessageException.class)
    public List<Map> makeApprovalFor(LeaderSupervisionVo leaderSupervisionVo){

        // 对 参数进行校验
        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo);
       // 保存 审批记录  系统自动根据材料审核结果、纪委意见判断生成呈批单（通过）还是请示表（未通过），
        // 判断依据：材料审核和纪委意见均通过的为通过，否则为不通过。

       List<Map> lists =  leaderCommonDetailMapper.selectJiweiOpinionAndMaterialsCheckOpinion(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessName()).collect(Collectors.toList()).get(0)

                );
       Set<String> passList = new HashSet<>();
       Set<String> notpassList = new HashSet<>();

       if(lists!=null && lists.size()>0){

           for (Map map:lists) {
              String materialsCheck =  (String)map.get("materialsCheck");
              String jiweiopion  = (String)map.get("jiweiopion");
              String id = (String) map.get("id");
               // 1 代表 通过
//              if(materialsCheck.equals("1")&&jiweiopion.equals("1")){
              if("1".equals(materialsCheck)&&"1".equals(jiweiopion)){

                  passList.add(id);

                  map.put("fileType","呈批单");

              }else{

                  notpassList.add(id);
                  map.put("fileType","请示表");

              }


           }

       }
        // 通过 集合
        List<BussinessTypeAndIdVo>  bussinessTypeAndIdVosNum1 =    leaderSupervisionVo.getBussinessTypeAndIdVos().stream().filter((BussinessTypeAndIdVo m) ->passList.contains(m.getBussinessId())).collect(Collectors.toList());
       // 不通过 集合
        List<BussinessTypeAndIdVo>  bussinessTypeAndIdVosNum2 =    leaderSupervisionVo.getBussinessTypeAndIdVos().stream().filter((BussinessTypeAndIdVo m) ->notpassList.contains(m.getBussinessId())).collect(Collectors.toList());

        //  (1) 保存 审批记录(通过)
        leaderCommonService.saveAbroadApprovalByBussinessId(bussinessTypeAndIdVosNum1,"通过", Constants.leader_businessName[3], Constants.leader_business[3],null);
        //不通过
        leaderCommonService.saveAbroadApprovalByBussinessId(bussinessTypeAndIdVosNum2,"不通过", Constants.leader_businessName[3], Constants.leader_business[3],null);
        // (2) 修改流程状态
        leaderCommonService.updteBussinessApplyStatue(leaderSupervisionVo.getBussinessTypeAndIdVos(), Constants.leader_businessName[4]);
        // 修改 业务流程申请 最终结论 (通过)
        leaderCommonService.updateBussinessApplyRecordOpinion(bussinessTypeAndIdVosNum1,"1",null);
        // 修改 业务流程申请 最终结论 (不通过)
        leaderCommonService.updateBussinessApplyRecordOpinion(bussinessTypeAndIdVosNum2,"1",null);
        // 修改 批次状态
        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()),
                Constants.leader_business[4]);

        createOmsFileAndomsCreateFile(lists);

        return lists;

    }

    public void createOmsFileAndomsCreateFile(List<Map> lists){

       if(lists!=null && lists.size()>0){

          for(Map map : lists){

              String tableCode =(String)map.get("tableCode");
              String fileShortname = (String)map.get("fileType");
              String applyId  = (String) map.get("id");

//              //登录用户信息
             UserInfo userInfo = UserInfoUtil.getUserInfo();//查询机构信息
             B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());
             if (b01 == null){
                  throw new CustomMessageException("数据异常");
              }
              QueryWrapper<OmsFile> queryWrapper = new QueryWrapper<>();
              queryWrapper.eq("TABLE_CODE", tableCode)
                     .eq("B0100", b01.getB0100())
                      .eq("FILE_SHORTNAME",fileShortname)
                      .orderByAsc("SORT_ID");
              List<OmsFile> omsFiles = omsFileMapper.selectList(queryWrapper);
              if (omsFiles == null || omsFiles.size() < 1) {
                  //初始化机构文件
                  queryWrapper.clear();
                  queryWrapper.eq("TABLE_CODE", tableCode)
                          .eq("FILE_SHORTNAME",fileShortname)
                          .and(wrapper->wrapper.eq("B0100", "")
                                  .or()
                                  .isNull("B0100"))
                          .orderByAsc("SORT_ID");
                  List<OmsFile> omsFileSystem = omsFileMapper.selectList(queryWrapper);
                  if (omsFileSystem != null && omsFileSystem.size() > 0) {
                      //插入
                      for (OmsFile omsfile : omsFileSystem) {
                          omsfile.setFileId(omsfile.getId());
                          omsfile.setId(UUIDGenerator.getPrimaryKey());
                          omsfile.setB0100(b01.getB0100());
                          omsfile.setCreateUser(userInfo.getId());
                          omsfile.setCreateTime(new Date());
                          omsFileMapper.insert(omsfile);
                      }

                  }
                  queryWrapper.clear();
                  //重新查询
                  queryWrapper.eq("TABLE_CODE", tableCode)
                          .eq("B0100", b01.getB0100())
                          .eq("FILE_SHORTNAME",fileShortname)
                          .orderByAsc("SORT_ID");
                  omsFiles = omsFileMapper.selectList(queryWrapper);


              }

              map.put("omsFiles",omsFiles);
              //生成文件
              if (!StringUtils.isBlank(applyId)){
                  QueryWrapper<OmsCreateFile> createFile = new QueryWrapper<>();
                  createFile.eq("TABLE_CODE", tableCode)
                          .eq("APPLY_ID", applyId);
                  int count = omsCreateFileMapper.selectCount(createFile);
                  //没有生成时生成文件
                  if (count < 1){
                      for (OmsFile omsFile : omsFiles){
                          OmsCreateFile omsCreateFile = new OmsCreateFile();
                          omsCreateFile.setFileId(omsFile.getId());
                          omsCreateFile.setApplyId(applyId);
                          omsCreateFile.setFileName(omsFile.getFileName());
                          omsCreateFile.setFileShortname(omsFile.getFileShortname());
                          omsCreateFile.setFileType(omsFile.getFileType());
                          omsCreateFile.setTableCode(omsFile.getTableCode());
                          omsCreateFile.setIsEdit(omsFile.getIsEdit());
                          omsCreateFile.setSealDesc(omsFile.getSealDesc());
                          omsCreateFile.setIsfileList(omsFile.getIsfileList());
                          omsCreateFile.setSortId(omsFile.getSortId());
                          omsCreateFile.setPrintNum(omsFile.getPrintNum());
                          //替换关键词
                          omsFileServiceImpl.replaceFile(omsFile, applyId, tableCode);
                          omsCreateFile.setFrontContent(omsFile.getFrontContent());
                          omsCreateFile.setBankContent(omsFile.getBankContent());
                          omsCreateFileService.insertOrUpdate(omsCreateFile);
                      }
                  }
              }


          }
       }else{

           throw new CustomMessageException("参数 为空，请仔细检查");
       }



    }









}
