package com.hxoms.modules.leaderSupervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.entity.paramentity.SendMessageParam;
import com.hxoms.message.message.service.MessageService;
import com.hxoms.message.msguser.entity.MsgUser;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.file.service.OmsCreateFileService;
import com.hxoms.modules.file.service.impl.OmsFileServiceImpl;
import com.hxoms.modules.leaderSupervision.Enum.BussinessApplyStatus;
import com.hxoms.modules.leaderSupervision.entity.AttachmentAskforjiwei;
import com.hxoms.modules.leaderSupervision.entity.OmsAttachment;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.entity.OmsPutonrecords;
import com.hxoms.modules.leaderSupervision.mapper.*;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.until.FileTypeConvertUtil;
import com.hxoms.modules.leaderSupervision.until.HtmlUtils;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.BusinessTypeAndIdAndOnJobVo;
import com.hxoms.modules.leaderSupervision.vo.BussinessTypeAndIdVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.mapper.B01Mapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
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
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @authore:wjf
 * @data 2020/7/29 10:30  对 干部监督 业务 一些的 细节处理
 * @Description:
 ***/
@Service("leaderDetailProcessingService")
public class LeaderDetailProcessingServiceImpl implements LeaderDetailProcessingService {

    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;

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
    private OmsCreateFileMapper omsCreateFileMapper;

    @Autowired
    private OmsFileServiceImpl omsFileServiceImpl;
    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersoninfoMapper;

    @Autowired
    private OmsPutonrecordsMapper omsPutonrecordsMapper;



    @Value("${omsAttachment.baseDir}")
    private String attachmentPath;

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    @Value("${file.ueditorRealImgUrl}")
    private String ueditorRealImgUrl;

    public String getUeditorRealImgUrl() {
        return ueditorRealImgUrl;
    }

    public void setUeditorRealImgUrl(String ueditorRealImgUrl) {
        this.ueditorRealImgUrl = ueditorRealImgUrl;
    }

    @Value("${htmlTopdf.pdfName}")
    private String pdfName;

    private final Logger log = LoggerFactory.getLogger(getClass());


    // 业务 处理 材料审核 的 下一步 触发的事件
    public void materialReviewNextStep(String applyId, String tableCode,String clshsftgOpinion) {

        // TODO 保存自评

        //修改 业务流程 材料审核 以及 修改流程状态  查询的 时候 少了个 自评 的 来源 经办人，还是 干部监督处
        List<Map> lists = leaderCommonMapper.selectMaterialReview(applyId);
        String updateBussinessCheck = "";
        if (lists.size() == 0 || lists == null) {
            throw new CustomMessageException("数据异常，请 联系开发人员");
            // 通过
        } else if (lists.size() == 1 && lists.get(0).get("result") == "1") {

            updateBussinessCheck=  getUpdateRecordOpinionSql(applyId,tableCode,"1","clshsftg",clshsftgOpinion);

        }else{
            // 不通过
            updateBussinessCheck=  getUpdateRecordOpinionSql(applyId,tableCode,"2","clshsftg",clshsftgOpinion);

        }

        if (updateBussinessCheck != null && updateBussinessCheck.length() > 0) {
            SqlVo instance = SqlVo.getInstance(updateBussinessCheck);
            selectMapper.update(instance);
        }

        // 查询当前流程状态
        int currentStatus = leaderCommonService.selectBussinessStatus(applyId, tableCode, null);
        //Constants.leader_business[1]
        if (currentStatus < Constants.emPrivateGoAbroad.征求意见.getIndex()) {

            // 修改 流程 状态 置为 征求纪委意见
            getUpdateStatusSql(applyId, tableCode, Constants.emPrivateGoAbroad.征求意见.getName(), "materialReviewNextStep");

        }

        // 修改批次状态
        List<String> bussinessIds = new ArrayList<>();
        bussinessIds.add(applyId);
        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(bussinessIds, Constants.emPrivateGoAbroad.征求意见.getIndex());

    }

    // 干部监督处 材料审核 给经办人 发送消息 通知经办人重新递交备案函

    /**
     * param : 参数 applyId  业务 id , tableCode 业务 类型(因公，因私，延期)
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    public void sendMessageToAgent(String applyId, String tableCode) {

        // 查询 经办人
        List<Map> receiveUserList = leaderCommonMapper.selectAgent(applyId, tableCode);

        //查询 发送内容
        List<Map> listMessage = leaderCommonMapper.selectSendAgentMessage(applyId);

        try {
            preAndRecMessage(listMessage.get(0) != null ? listMessage.get(0).get("message").toString() : "", Constants.PERSONAL, receiveUserList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 修改流程状态， 将 流程 置为 初始状态
        String updateSql = getUpdateStatusSql(applyId, tableCode, "1", "sendMessageToAgent");
        SqlVo instance = SqlVo.getInstance(updateSql);
        selectMapper.update(instance);

        //TODO 当 把 流程 状态 置为 初始状态 需要 解除 他与 批次之间的 关系吗

    }

    public void preAndRecMessage(String msgContent, String receiveUserType, List<Map> receiveUserList) throws Exception {
        // 查询经办人
        if (receiveUserList == null || receiveUserList.size() == 0)
            throw new CustomMessageException("经办人，未查到！");
        SendMessageParam sendMessageParam = new SendMessageParam();
        //设置信息内容
        Message message = new Message();
        message.setContent(msgContent);
        sendMessageParam.setMessage(message);
        //设置接收人
        List<MsgUser> msgUserList = new ArrayList<>();
        for (Map receiveUser : receiveUserList) {
            MsgUser msgUser = new MsgUser();
            msgUser.setReceiveUserId(receiveUser.get("id").toString());
            msgUser.setReceiveUsername(receiveUser.get("userName").toString());
            msgUserList.add(msgUser);
        }
        Map<String, List<MsgUser>> msgUserMap = new HashedMap();
        msgUserMap.put(receiveUserType, msgUserList);
        sendMessageParam.setMsgUserMap(msgUserMap);
        //发送信息
        messageService.sendMessage(sendMessageParam);
    }


    // TODO  统一 修改 业务申请表状态
    public String getUpdateStatusSql(String busessId, String bussinesType, String leaderStatusName, String methodName) {

        String updateSql = "update " + bussinesType;
        String setSql = " set  ";
        String whereCondition = " where id = '" + busessId + "'";

        for (BussinessApplyStatus applyStatus : BussinessApplyStatus.values()) {

            if (bussinesType.indexOf(applyStatus.getTableName()) != -1) {
                String status = applyStatus.getApplySatus();
                if ("sendMessageToAgent".equals(methodName)) {
                    setSql += status + "= '" + leaderStatusName + "'";
                } else {
                    // 干部监督处的状态
                    setSql += status + "=" + Constants.emPrivateGoAbroad.valueOf(leaderStatusName);
                }
                break;
            }
        }
        return updateSql + setSql + whereCondition;
    }

    // TODO  修改业务流程 记录 审批 意见
    public String getUpdateRecordOpinionSql(String busessId, String bussinesType, String opinion, String recordFlow,String clshsftgOpinion) {

        String updateSql = "update " + bussinesType;
        String setSql = " set  ";
        String whereCondition = " where id = " + busessId;

        // TODO 最终结论 后面覆盖 前面
        if ("clshsftg".equals(recordFlow)) {   // 材料审核是否通过

            if(!StringUilt.stringIsNullOrEmpty(clshsftgOpinion)){

                setSql+= " CLSHSFTG " + " = " + opinion+", zzjl = " + opinion +", clshsftg_Opinion= '"+clshsftgOpinion+"'";

            }else{
                setSql+= " CLSHSFTG " + " = " + opinion+", zzjl = " + opinion ;

            }
            return updateSql + setSql + whereCondition;
        } else if ("jwjl".equals(recordFlow)) {  //  纪委结论

            String sql = "select ifnull(official_feedback_verdict,feedback_verdict) as opinion from oms_jiwei_opinion where id =" + busessId;
            SqlVo instance1 = SqlVo.getInstance(sql);
            List<LinkedHashMap<String, Object>> linkedHashMaps = selectMapper.select(instance1);

            setSql += " jwjl " + " = " + linkedHashMaps.get(0).get("opinion") + ", zzjl = " + linkedHashMaps.get(0).get("opinion");

            return updateSql + setSql + whereCondition;
        } else if ("jdcjl".equals(recordFlow)) {  // 监督处最终结论

            setSql += " jdcjl " + " = " + opinion + ", zzjl = " + opinion;

            return updateSql + setSql + whereCondition;
        } else {

            // 其它 流程步骤 修改 最终 结论
            setSql += "zzjl = " + opinion;
            return updateSql + setSql + whereCondition;
        }
    }


    public List<Map> getApplicationType() {
        return leaderCommonMapper.selectgetApplicationType();
    }

    public PageInfo selectOmsLeaderBatch(LeaderSupervisionVo leaderSupervisionVo) {

        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
        List<Map> lists = leaderCommonMapper.selectLeaderBatch();
        PageInfo pageInfo = new PageInfo(lists);
        return pageInfo;
    }

    public void updateLeaderBatch(OmsLeaderBatch omsLeaderBatch) {

        LeaderSupervisionUntil.throwableByParam(omsLeaderBatch.getId());

        omsLeaderBatchMapper.updateById(omsLeaderBatch);
    }

    public List<Map> selectLeaderBatchStatus() {
        List lists = new ArrayList();

        for (Constants.emPrivateGoAbroad goAbroad : Constants.emPrivateGoAbroad.values()) {
            if (goAbroad.getIndex() < 20) continue;
            Map map = new HashMap();
            map.put("code", goAbroad.getIndex());
            map.put("name", goAbroad.getName());

            lists.add(map);
        }
        return lists;
    }

    public List<Map> selectMaterialStatus() {

        List lists = new ArrayList();
        Map map = new HashMap();
        map.put("code", "all");
        map.put("name", "材料审核");
        Map map1 = new HashMap();
        map1.put("code", "1");
        map1.put("name", "已审核");
        Map map2 = new HashMap();
        map2.put("code", "2");
        map2.put("name", "未审核");

        lists.add(map);
        lists.add(map1);
        lists.add(map2);
        return lists;
    }

    /**
     * 文件上传
     *
     * @param files
     * @throws Exception
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    public void fileUpload(@RequestParam("file") MultipartFile[] files, String[] leaderBatchIds, String bussinessType, int bussinessOccureStpet, String bussinessOccureStpetName, HttpServletRequest request) {

        if (files == null || files.length <= 0) {
            throw new CustomMessageException("没有选择上传的文件，请仔细检查");

        }

        if (leaderBatchIds == null || files.length <= 0) {

            throw new CustomMessageException("没有选择关联的批次，请仔细检查");
        }

        //文件命名
        //保存时的文件名
        for (int i = 0; i < files.length; i++) {
            //保存文件到本地文件，并保存路径到数据库
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            String fileName = df.format(calendar.getTime()) + files[i].getOriginalFilename();
            log.info("文件的文件名为:" + fileName);

            if (!files[i].getOriginalFilename().endsWith(".pdf")) {

                throw new CustomMessageException(files[i].getOriginalFilename() + "此文件的格式不正确，请上传pdf格式文件");
            }

            //保存文件的绝对路径
//            String filePath = request.getSession().getServletContext().getRealPath("static/");
            String filePath = attachmentPath + File.separator + "static" + File.separator;
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

    // 保存 备案附件 和人员的关系
    private void saveAttachmentByPutonRecord(String applyId, String pdfFilePath, int bussinessOccureStpet, String bussinessOccureStpetName) {


        LambdaQueryWrapper<OmsAttachment> lambdaQueryWrapper = Wrappers.<OmsAttachment>lambdaQuery();

        lambdaQueryWrapper.eq(OmsAttachment::getBussinessid, applyId)
                .eq(OmsAttachment::getBussinessOccureStpet, bussinessOccureStpet)
                .eq(OmsAttachment::getBussinessOccureStpetName, bussinessOccureStpetName);

        int rows = omsAttachmentMapper.delete(lambdaQueryWrapper);

        log.info("删除的条数" + rows);


        // 保存 附件 表
        OmsAttachment omsAttachment = new OmsAttachment();
        omsAttachment.setId(UUIDGenerator.getPrimaryKey());
        omsAttachment.setBussinessid(applyId);
        omsAttachment.setName(pdfFilePath.split("\\.")[pdfFilePath.split("\\.").length - 1]);
        omsAttachment.setType("pdf");
        omsAttachment.setUrl(pdfFilePath);
        omsAttachment.setBussinessOccureStpet(bussinessOccureStpet + "");
        omsAttachment.setBussinessOccureStpetName(bussinessOccureStpetName);
        omsAttachment.setCreateTime(new Date());
        omsAttachment.setCreateUser(UserInfoUtil.getUserInfo().getId());
        omsAttachmentMapper.insert(omsAttachment);

    }

    private void saveAttachmentAndAskforJiwei(MultipartFile file, String[] leaderBatchIds, int bussinessOccureStpet, String bussinessOccureStpetName, int i, String fileName, String url) {
        // 保存 附件 表
        OmsAttachment omsAttachment = new OmsAttachment();
        omsAttachment.setId(UUIDGenerator.getPrimaryKey());
        omsAttachment.setSize(String.valueOf(file.getSize()));
        omsAttachment.setName(fileName);
        omsAttachment.setType(file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length - 1]);
        omsAttachment.setUrl(url);
        omsAttachment.setBussinessOccureStpet(bussinessOccureStpet + "");
        omsAttachment.setBussinessOccureStpetName(bussinessOccureStpetName);
        omsAttachment.setCreateTime(new Date());
        omsAttachment.setCreateUser(UserInfoUtil.getUserInfo().getId());
        omsAttachmentMapper.insert(omsAttachment);
        // 保存 附件业务 的中间表
        for (int j = 0; j < leaderBatchIds.length; j++) {

            AttachmentAskforjiwei attachmentAskforjiwei = new AttachmentAskforjiwei();
            attachmentAskforjiwei.setId(UUIDGenerator.getPrimaryKey());
            attachmentAskforjiwei.setAttachmentid(omsAttachment.getId());
            attachmentAskforjiwei.setLeaderBatchId(leaderBatchIds[i]);
            attachmentAskforjiweiMapper.insert(attachmentAskforjiwei);

        }
    }

    public List<Map> selectAttachmentList(String[] leaderBatchIds) {

        List<Map> lists = leaderCommonMapper.selectAttachmentList(leaderBatchIds);

        return lists;
    }

    public Map downloadAttachmentById(String bId) {

        Map map = new HashMap();

        OmsAttachment omsAttachment = omsAttachmentMapper.getAttachmentByBussinessId(bId);
        try {
            map.put("array", LeaderSupervisionUntil.downloadFile(omsAttachment.getUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("fileName", omsAttachment.getName());
        return map;
    }

    /**
     * 生成审批单
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    public List<Map> makeApprovalFor(LeaderSupervisionVo leaderSupervisionVo) {

        // 对 参数进行校验
        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo);
        // 保存 审批记录  系统自动根据材料审核结果、纪委意见判断生成呈批单（通过）还是请示表（未通过），
        // 判断依据：材料审核和纪委意见均通过的为通过，否则为不通过。

        List<Map> lists = leaderCommonDetailMapper.selectJiweiOpinionAndMaterialsCheckOpinion(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()).toArray(),
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessName()).collect(Collectors.toList()).get(0)

        );
        Set<String> passList = new HashSet<>();
        Set<String> notpassList = new HashSet<>();

        if (lists != null && lists.size() > 0) {

            for (Map map : lists) {
                String materialsCheck = (String) map.get("materialsCheck");
                String jiweiopion = (String) map.get("jiweiopion");
                String id = (String) map.get("id");
                if ("1".equals(materialsCheck) && "1".equals(jiweiopion)) {

                    passList.add(id);
                    map.put("fileType", "呈批单");
                    map.put("pass", "通过");
                } else {
                    notpassList.add(id);
                    map.put("fileType", "请示表");
                    map.put("pass", "不通过");
                }
            }
        }

        createOmsFileAndomsCreateFile(lists);
        return lists;
    }

    /**
     * 生成人员备案表
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    public List<Map> createPutOnRecord(LeaderSupervisionVo leaderSupervisionVo) {

        // 对 参数进行校验
        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo);
        // 保存 审批记录  系统自动根据材料审核结果、纪委意见判断生成呈批单（通过）还是请示表（未通过），
        // 判断依据：材料审核和纪委意见均通过的为通过，否则为不通过。

        List<Map> lists = leaderCommonDetailMapper.selectCreatePutOnRecord(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()).toArray()

        );

        createOmsFileAndomsCreateFile(lists);
        return lists;
    }

    public void createOmsFileAndomsCreateFile(List<Map> lists) {

        if (lists != null && lists.size() > 0) {

            for (Map map : lists) {

                String tableCode = (String) map.get("tableCode");
                String fileShortname = (String) map.get("fileType");
                String applyId = (String) map.get("id");

//              //登录用户信息
                UserInfo userInfo = UserInfoUtil.getUserInfo();//查询机构信息
                B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());
                if (b01 == null) {
                    throw new CustomMessageException("数据异常");
                }
                QueryWrapper<OmsFile> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("TABLE_CODE", tableCode)
                        .eq("B0100", b01.getB0100())
                        .eq("FILE_SHORTNAME", fileShortname)
                        .orderByAsc("SORT_ID");
                List<OmsFile> omsFiles = omsFileMapper.selectList(queryWrapper);
                if (omsFiles == null || omsFiles.size() < 1) {
                    //初始化机构文件
                    queryWrapper.clear();
                    queryWrapper.eq("TABLE_CODE", tableCode)
                            .eq("FILE_SHORTNAME", fileShortname)
                            .and(wrapper -> wrapper.eq("B0100", "")
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
                            .eq("FILE_SHORTNAME", fileShortname)
                            .orderByAsc("SORT_ID");
                    omsFiles = omsFileMapper.selectList(queryWrapper);
                }

                map.put("omsFiles", omsFiles);
                //生成文件
                if (!StringUtils.isBlank(applyId)) {
                    QueryWrapper<OmsCreateFile> createFile = new QueryWrapper<>();
                    createFile.eq("TABLE_CODE", tableCode)
                            .eq("FILE_SHORTNAME",fileShortname)
                            .eq("APPLY_ID", applyId);
                    int count = omsCreateFileMapper.selectCount(createFile);
                    //没有生成时生成文件
                    if (count < 1) {
                        for (OmsFile omsFile : omsFiles) {
                            OmsCreateFile omsCreateFile = omsFileServiceImpl.createFile(omsFile, applyId);

                            //替换关键词
                            omsFileServiceImpl.replaceFile(omsFile, applyId, tableCode,null);
                            omsCreateFile.setFrontContent(omsFile.getFrontContent());
                            omsCreateFile.setBankContent(omsFile.getBankContent());
                            omsCreateFileMapper.insert(omsCreateFile);
                            map.put("omsCreateFile",omsCreateFile);
                        }
                    }else{

                        map.put("omsCreateFile",omsCreateFileMapper.selectList(createFile).get(0));


                    }
                }
            }
        } else {
            throw new CustomMessageException("参数 为空，请仔细检查");
        }
    }


    @Override
    @Transactional(rollbackFor = CustomMessageException.class)
    public OmsCreateFile insertOrUpdatePutOnRecordFile(OmsCreateFile omsCreateFile, String userName) {

//        LeaderSupervisionUntil.throwableByParam(bussinessId,type,pass);


        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if (StringUtils.isBlank(omsCreateFile.getId())) {
            //新增
            omsCreateFile.setId(UUIDGenerator.getPrimaryKey());
            omsCreateFile.setCreateUser(userInfo.getId());
            omsCreateFile.setIsSealhandle("0");
            omsCreateFile.setCreateTime(new Date());
            if (omsCreateFileMapper.insert(omsCreateFile) < 1) {
                throw new CustomMessageException("操作失败");
            }
        } else {
            //修改
            omsCreateFile.setModifyTime(new Date());
            omsCreateFile.setModifyUser(userInfo.getId());
            if (omsCreateFileMapper.updateById(omsCreateFile) < 1) {
                throw new CustomMessageException("操作失败");
            }
        }

        List<BussinessTypeAndIdVo> bussinessTypeAndIdVosNum1 = new ArrayList<>();

        BussinessTypeAndIdVo bussinessTypeAndIdVo = new BussinessTypeAndIdVo();
        bussinessTypeAndIdVo.setBussinessId(omsCreateFile.getApplyId());
        bussinessTypeAndIdVo.setBussinessName("因公");
        bussinessTypeAndIdVosNum1.add(bussinessTypeAndIdVo);

        String pdfFilePath = getPdfByHtml(omsCreateFile, userName);

        //由于在某种未知的 情况 会产生多个备案表 保留最新的

        LeaderSupervisionUntil.deleteFileById(omsCreateFile.getApplyId() + userName + pdfName + ".pdf", attachmentPath + File.separator + "static");

        saveAttachmentByPutonRecord(omsCreateFile.getApplyId(), pdfFilePath, Constants.emPrivateGoAbroad.制作备案表.getIndex(), Constants.emPrivateGoAbroad.制作备案表.getName());

        //  (1) 保存 审批记录(通过)
        leaderCommonService.saveAbroadApprovalByBussinessId(bussinessTypeAndIdVosNum1, "通过", Constants.emPrivateGoAbroad.制作备案表.getName(), Constants.emPrivateGoAbroad.制作备案表.getIndex(), null);//Constants.leader_businessName[8]
        //        // (2) 修改流程状态
        leaderCommonService.updteBussinessApplyStatue(bussinessTypeAndIdVosNum1, Constants.emPrivateGoAbroad.制作备案表.getName());

        leaderCommonService.updateBussinessApplyRecordOpinion(bussinessTypeAndIdVosNum1, "1", null);

        // 修改 批次状态
        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(
                bussinessTypeAndIdVosNum1.stream().map(s -> s.getBussinessId()).collect(Collectors.toList()),
                Constants.emPrivateGoAbroad.已办结.getIndex());
//

        //更新因公出国境备案号（1：已备案，0：未备案，默认为0）
        OmsPubApply omsPubApply = omsPubApplyMapper.selectById(omsCreateFile.getApplyId());
        omsPubApply.setBah("1");
        omsPubApplyMapper.updateById(omsPubApply);
        return omsCreateFile;
    }

    private String getPdfByHtml(OmsCreateFile omsCreateFile, String userName) {
        //解析 html img 的src 标签


        String contentStr = HtmlUtils.replaceTag(omsCreateFile.getFrontContent(), "src", ueditorRealImgUrl);

        // 要转换的 html
        String htmlstr = LeaderSupervisionUntil.prefixPdfStyle + contentStr + LeaderSupervisionUntil.suffixPdfStyle;

        String newHtmlStr = htmlstr.replaceAll("<br>", "<br/>");
        // 生成 pdf的路径 +名称
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String fileName = df.format(calendar.getTime()) + omsCreateFile.getApplyId() + userName + pdfName + ".pdf";
        log.info("文件的文件名为:" + fileName);


        String filePath = attachmentPath + File.separator + "static" + File.separator;
        try {
            FileTypeConvertUtil.html2pdf(newHtmlStr, filePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath + fileName;

    }


    @Override
    @Transactional(rollbackFor = CustomMessageException.class)
    public OmsCreateFile insertOrUpadateCreateFileAndUpdateStaus(OmsCreateFile omsCreateFile, String bussinessId, String type, String pass) {

        LeaderSupervisionUntil.throwableByParam(bussinessId, type, pass);

        String opinion = "";
        if ("通过".equals(pass)) {
            opinion = "1";
        } else {
            opinion = "2";
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if (StringUtils.isBlank(omsCreateFile.getId())) {
            //新增
            omsCreateFile.setId(UUIDGenerator.getPrimaryKey());
            omsCreateFile.setCreateUser(userInfo.getId());
            omsCreateFile.setIsSealhandle("0");
            omsCreateFile.setCreateTime(new Date());
            if (omsCreateFileMapper.insert(omsCreateFile) < 1) {
                throw new CustomMessageException("操作失败");
            }
        } else {
            //修改
            omsCreateFile.setModifyTime(new Date());
            omsCreateFile.setModifyUser(userInfo.getId());
            if (omsCreateFileMapper.updateById(omsCreateFile) < 1) {
                throw new CustomMessageException("操作失败");
            }
        }

        List<BussinessTypeAndIdVo> bussinessTypeAndIdVosNum1 = new ArrayList<>();

        BussinessTypeAndIdVo bussinessTypeAndIdVo = new BussinessTypeAndIdVo();
        bussinessTypeAndIdVo.setBussinessId(bussinessId);
        bussinessTypeAndIdVo.setBussinessName(type);
        bussinessTypeAndIdVosNum1.add(bussinessTypeAndIdVo);

        //  (1) 保存 审批记录(通过)
        leaderCommonService.saveAbroadApprovalByBussinessId(bussinessTypeAndIdVosNum1, pass, Constants.emPrivateGoAbroad.做出审核意见.getName(), Constants.emPrivateGoAbroad.做出审核意见.getIndex(), null);//Constants.leader_businessName[3]
        //不通过

        leaderCommonService.updteBussinessApplyStatue(bussinessTypeAndIdVosNum1, Constants.emPrivateGoAbroad.处领导审批.getName());//Constants.leader_businessName[4]
        // 修改 业务流程申请 最终结论 (通过)
        leaderCommonService.updateBussinessApplyRecordOpinion(bussinessTypeAndIdVosNum1, opinion, null);
        // 修改 业务流程申请 最终结论 (不通过)
        // 修改 批次状态
        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(
                bussinessTypeAndIdVosNum1.stream().map(s -> s.getBussinessId()).collect(Collectors.toList()),
                Constants.emPrivateGoAbroad.处领导审批.getIndex());

        return omsCreateFile;
    }

    // 处长批量审批
    @Transactional(rollbackFor = CustomMessageException.class)
    public void chuzhangAbroadApprovalBatch(LeaderSupervisionVo leaderSupervisionVo) {

        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo);

        List listPass = leaderSupervisionVo.getBussinessTypeAndIdVos().stream().filter((BussinessTypeAndIdVo b) -> "通过".equals(b.getCadresupervisionOpinion())).collect(Collectors.toList());
        List listNoPass = leaderSupervisionVo.getBussinessTypeAndIdVos().stream().filter((BussinessTypeAndIdVo b) -> "不通过".equals(b.getCadresupervisionOpinion())).collect(Collectors.toList());

        //  (1) 保存 审批记录(通过)
        leaderCommonService.saveAbroadApprovalByBussinessId(listPass, "通过", Constants.emPrivateGoAbroad.处领导审批.getName(), Constants.emPrivateGoAbroad.处领导审批.getIndex(), null);
        //不通过
        leaderCommonService.saveAbroadApprovalByBussinessId(listNoPass, "不通过", Constants.emPrivateGoAbroad.处领导审批.getName(), Constants.emPrivateGoAbroad.处领导审批.getIndex(), null);

        //  修改 业务流程状态 (第二步) 修改 为  处领导审批
        newUpdteBussinessApplyStatue(leaderSupervisionVo.getBussinessTypeAndIdVos(), Constants.emPrivateGoAbroad.处领导审批.getName());
        // 修改 业务流程申请 最终结论
        leaderCommonService.updateBussinessApplyRecordOpinion(listPass, "1", null);

        leaderCommonService.updateBussinessApplyRecordOpinion(listNoPass, "2", null);

        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()),
                Constants.emPrivateGoAbroad.部领导审批.getIndex());

        //更新 《登记备案人员信息表》中纪委不回复意见人员字段
        updateProcpersoninfoByjiwei(leaderSupervisionVo.getBussinessTypeAndIdVos());
    }

    public void newUpdteBussinessApplyStatue(List<BussinessTypeAndIdVo> businessTypeAndIdAndOnJobVos, String leaderStatusName) {

        for (int i = 0; i < businessTypeAndIdAndOnJobVos.size(); i++) {

            String ispass = businessTypeAndIdAndOnJobVos.get(i).getCadresupervisionOpinion();
            String incumbencyStatus = businessTypeAndIdAndOnJobVos.get(i).getIncumbencyStatus();
            String bussinessType = LeaderSupervisionUntil.selectorBussinessTypeByName(businessTypeAndIdAndOnJobVos.get(i).getBussinessName());

            String updateApplyStatusSql = getUpdateStatusSql(businessTypeAndIdAndOnJobVos.get(i).getBussinessId(), bussinessType, leaderStatusName, incumbencyStatus, ispass);

            log.info("修改业务 流程的 sql =" + updateApplyStatusSql);

            if (updateApplyStatusSql.length() > 0) {
                SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
                selectMapper.update(instance);
            }
        }
    }

    public String getUpdateStatusSql(String busessId, String bussinesType, String leaderStatusName, String incumbencyStatus, String ispass) {

        String updateSql = "update " + bussinesType;
        String setSql = " set  ";
        String whereCondition = " where id = '" + busessId + "'";

        for (BussinessApplyStatus applyStatus : BussinessApplyStatus.values()) {

            if (bussinesType.indexOf(applyStatus.getTableName()) != -1) {

                if ("oms_pri_apply".equals(bussinesType)) {

                    //TODO 因私 退休的处长审批通过，状态置为已办结，不通过的，部长审批
                    if (leaderStatusName.equals(Constants.emPrivateGoAbroad.处领导审批.getName()) &&
                            "3".equals(incumbencyStatus) && "通过".equals(ispass)) {

                        String status = applyStatus.getApplySatus();
                        setSql += status + "=" + Constants.emPrivateGoAbroad.已办结.getIndex();

                        return updateSql + setSql + whereCondition;
                    }
                    // TODO 因私 部长 审批 ，通过 状态 置为 已办结 因私干部监督处 流程 走完
                    else if (leaderStatusName.equals(Constants.emPrivateGoAbroad.部领导审批.getName()) && "通过".equals(ispass)) {

                        String status = applyStatus.getApplySatus();
                        setSql += status + "=" + Constants.emPrivateGoAbroad.已办结.getIndex();

                        return updateSql + setSql + whereCondition;
                    }

                    String status = applyStatus.getApplySatus();
                    // 干部监督处的状态
                    setSql += status + "=" + Constants.emPrivateGoAbroad.valueOf(leaderStatusName).getIndex() + 1;

                    //   break;
                    return updateSql + setSql + whereCondition;

                    // TODO  因私延期回国，处长审批后结束
                } else if ("oms_pri_delay_apply".equals(bussinesType)) {

                    String status = applyStatus.getApplySatus();
                    setSql += status + "=" + Constants.emPrivateGoAbroad.已办结.getIndex();

                    return updateSql + setSql + whereCondition;
                    // TODO   因公备案，同意的处长签字后结束，不同意的由分管部长签
                } else if ("oms_pub_apply".equals(bussinesType)) {

                    String status = applyStatus.getApplySatus();

                    // 同意到  上传批文流程
                    if ("通过".equals(ispass)) {
                        setSql += status + "=" + Constants.emPrivateGoAbroad.核实批件.getIndex();

                        return updateSql + setSql + whereCondition;
                    }
                    // 不同意 到 部长审批
                    else if ("不通过".equals(ispass)) {

                        setSql += status + "=" + Constants.emPrivateGoAbroad.部领导审批.getIndex();
                        return updateSql + setSql + whereCondition;
                    }
                }
            }
        }

        return null;
    }

    // 更新 登记备案表

    public void updateProcpersoninfoByjiwei(List<BussinessTypeAndIdVo> businessTypeAndIdAndOnJobVos) {

        for (BussinessTypeAndIdVo bussinessTypeAndIdVo : businessTypeAndIdAndOnJobVos) {

            String jwjl = bussinessTypeAndIdVo.getJwjl();
            String procpersonId = bussinessTypeAndIdVo.getProcpersonId();

            if ("不回复".equals(jwjl)) {

                OmsRegProcpersoninfo omsRegProcpersoninfo = new OmsRegProcpersoninfo();

                omsRegProcpersoninfo.setId(procpersonId);
                omsRegProcpersoninfo.setReplyopinion("是");

                omsRegProcpersoninfoMapper.updateById(omsRegProcpersoninfo);
            } else {
                // 如果纪委不回复意见人员字段值是“是”，本次回复了意见，将纪委不回复意见人员字段更新为“否”。
                UpdateWrapper<OmsRegProcpersoninfo> queryWrapper = new UpdateWrapper<OmsRegProcpersoninfo>();
                queryWrapper.eq("id", procpersonId);
                queryWrapper.eq("REPLYOPINION", "是");

                OmsRegProcpersoninfo omsRegProcpersoninfo = new OmsRegProcpersoninfo();


                omsRegProcpersoninfo.setReplyopinion("否");

                omsRegProcpersoninfoMapper.update(omsRegProcpersoninfo, queryWrapper);


            }


        }


    }


    // 更新 登记备案表(部长)

    public void updateProcpersoninfoByjiweiBybuzhang(List<BusinessTypeAndIdAndOnJobVo> businessTypeAndIdAndOnJobVos) {

        for (BusinessTypeAndIdAndOnJobVo bussinessTypeAndIdVo : businessTypeAndIdAndOnJobVos) {

            String jwjl = bussinessTypeAndIdVo.getJwjl();
            String procpersonId = bussinessTypeAndIdVo.getProcpersonId();

            if ("不回复".equals(jwjl)) {

                OmsRegProcpersoninfo omsRegProcpersoninfo = new OmsRegProcpersoninfo();

                omsRegProcpersoninfo.setId(procpersonId);
                omsRegProcpersoninfo.setReplyopinion("是");

                omsRegProcpersoninfoMapper.updateById(omsRegProcpersoninfo);
            } else {
                // 如果纪委不回复意见人员字段值是“是”，本次回复了意见，将纪委不回复意见人员字段更新为“否”。
                UpdateWrapper<OmsRegProcpersoninfo> queryWrapper = new UpdateWrapper<OmsRegProcpersoninfo>();
                queryWrapper.eq("id", procpersonId);
                queryWrapper.eq("REPLYOPINION", "是");

                OmsRegProcpersoninfo omsRegProcpersoninfo = new OmsRegProcpersoninfo();


                omsRegProcpersoninfo.setReplyopinion("否");

                omsRegProcpersoninfoMapper.update(omsRegProcpersoninfo, queryWrapper);


            }


        }


    }


    /**
     * 保存 单条处长审批记录
     **/
    @Transactional(rollbackFor = CustomMessageException.class)
    public void saveChuZhangOneApproveRecord(AuditOpinionVo auditOpinionVo) {

        LeaderSupervisionUntil.throwableByParam(auditOpinionVo.getBusId()
                , auditOpinionVo.getBusName()
                , auditOpinionVo.getIspass()
                , auditOpinionVo.getReason()
                , auditOpinionVo.getIncumbencyStatus()
        );

        List<BussinessTypeAndIdVo> listPass = new ArrayList<>();

        BussinessTypeAndIdVo bussinessTypeAndIdVo = new BussinessTypeAndIdVo();
        bussinessTypeAndIdVo.setBussinessId(auditOpinionVo.getBusId());
        bussinessTypeAndIdVo.setBussinessName(auditOpinionVo.getBusName());
        bussinessTypeAndIdVo.setIncumbencyStatus(auditOpinionVo.getIncumbencyStatus());
        bussinessTypeAndIdVo.setJwjl(auditOpinionVo.getJwjl());
        listPass.add(bussinessTypeAndIdVo);
        if ("pass".equals(auditOpinionVo.getIspass())) {

            leaderCommonService.saveAbroadApprovalByBussinessId(listPass, "通过", Constants.emPrivateGoAbroad.处领导审批.getName(), Constants.emPrivateGoAbroad.处领导审批.getIndex(), auditOpinionVo.getReason());
            // 修改 业务流程申请 最终结论
            leaderCommonService.updateBussinessApplyRecordOpinion(listPass, "1", null);

        } else if ("nopass".equals(auditOpinionVo.getIspass())) {

            leaderCommonService.saveAbroadApprovalByBussinessId(listPass, "不通过", Constants.emPrivateGoAbroad.处领导审批.getName(), Constants.emPrivateGoAbroad.处领导审批.getIndex(), auditOpinionVo.getReason());
            leaderCommonService.updateBussinessApplyRecordOpinion(listPass, "2", null);
        }

        //  修改 业务流程状态 (第二步) 修改 为  处领导审批
        newUpdteBussinessApplyStatueByChuzhangOne(listPass, Constants.emPrivateGoAbroad.部领导审批.getName(), auditOpinionVo.getIspass());

        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(
                listPass.stream().map(s -> s.getBussinessId()).collect(Collectors.toList()),
                Constants.emPrivateGoAbroad.部领导审批.getIndex());

        //更新 《登记备案人员信息表》中纪委不回复意见人员字段
        updateProcpersoninfoByjiwei(listPass);

    }

    public void newUpdteBussinessApplyStatueByChuzhangOne(List<BussinessTypeAndIdVo> businessTypeAndIdAndOnJobVos, String leaderStatusName, String ispass) {

        for (int i = 0; i < businessTypeAndIdAndOnJobVos.size(); i++) {

            String bussinessType = LeaderSupervisionUntil.selectorBussinessTypeByName(businessTypeAndIdAndOnJobVos.get(i).getBussinessName());
            String incumbencyStatus = businessTypeAndIdAndOnJobVos.get(i).getIncumbencyStatus();

            if ("pass".equals(ispass)) {
                ispass = "通过";
            } else if ("nopass".equals(ispass)) {
                ispass = "不通过";
            }

            String updateApplyStatusSql = getUpdateStatusSql(businessTypeAndIdAndOnJobVos.get(i).getBussinessId(), bussinessType, leaderStatusName, incumbencyStatus, ispass);
            log.info("修改业务 流程的 sql =" + updateApplyStatusSql);

            if (updateApplyStatusSql.length() > 0) {
                SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
                selectMapper.update(instance);
            }
        }
    }

    /**
     * 保存部长审批记录
     **/
    @Transactional(rollbackFor = CustomMessageException.class)
    public void saveBuZhangApprover(AuditOpinionVo auditOpinionVo) {

        LeaderSupervisionUntil.throwableByParam(
                auditOpinionVo,
                auditOpinionVo.getIspass(),
                auditOpinionVo.getReason()
        );

        //保存审批记录
        if ("pass".equals(auditOpinionVo.getIspass())) {

            //  (1) 保存 审批记录(通过)
            leaderCommonService.saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), "通过", Constants.emPrivateGoAbroad.部领导审批.getName(), Constants.emPrivateGoAbroad.部领导审批.getIndex(), auditOpinionVo.getReason());
            // 修改最终结论
            leaderCommonService.updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), "1", null);
        } else if ("nopass".equals(auditOpinionVo.getIspass())) {

            leaderCommonService.saveAbroadApprovalByBussinessIdByAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), "不通过", Constants.emPrivateGoAbroad.部领导审批.getName(), Constants.emPrivateGoAbroad.部领导审批.getIndex(), auditOpinionVo.getReason());
            leaderCommonService.updateBussinessApplyRecordOpinionAudit(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), "2", null);
        }


        //  修改 业务流程状态 (第二步) 修改 为  处领导审批
        newUpdteBussinessApplyStatueBybuzhang(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos(), Constants.emPrivateGoAbroad.部领导审批.getName(), auditOpinionVo.getIspass());

        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(
                auditOpinionVo.getBusinessTypeAndIdAndOnJobVos().stream().map(s -> s.getBussinessId()).collect(Collectors.toList()),
                Constants.emPrivateGoAbroad.核实批件.getIndex());

        //更新 《登记备案人员信息表》中纪委不回复意见人员字段
        updateProcpersoninfoByjiweiBybuzhang(auditOpinionVo.getBusinessTypeAndIdAndOnJobVos());

    }

    public void newUpdteBussinessApplyStatueBybuzhang(List<BusinessTypeAndIdAndOnJobVo> businessTypeAndIdAndOnJobVos, String leaderStatusName, String ispass) {

        for (int i = 0; i < businessTypeAndIdAndOnJobVos.size(); i++) {

            String bussinessType = LeaderSupervisionUntil.selectorBussinessTypeByName(businessTypeAndIdAndOnJobVos.get(i).getBussinessName());
            String updateApplyStatusSql = getUpdateStatusSqlByBuzhang(businessTypeAndIdAndOnJobVos.get(i).getBussinessId(), bussinessType, leaderStatusName, ispass);
            log.info("修改业务 流程的 sql =" + updateApplyStatusSql);

            if (updateApplyStatusSql.length() > 0) {

                SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
                selectMapper.update(instance);
            }
        }
    }

    public String getUpdateStatusSqlByBuzhang(String busessId, String bussinesType, String leaderStatusName, String ispass) {

        String updateSql = "update " + bussinesType;
        String setSql = " set  ";
        String whereCondition = " where id = '" + busessId + "'";

        for (BussinessApplyStatus applyStatus : BussinessApplyStatus.values()) {

            if (bussinesType.indexOf(applyStatus.getTableName()) != -1) {
                if ("oms_pri_apply".equals(bussinesType)) {

                    // TODO 因私 到 部长 审批 流程结束 ，状态 置为 已办结 因私干部监督处 流程 走完
                    if (leaderStatusName.equals(Constants.emPrivateGoAbroad.部领导审批.getName())) {

                        String status = applyStatus.getApplySatus();
                        setSql += status + "=" + Constants.emPrivateGoAbroad.已办结.getIndex();
                        return updateSql + setSql + whereCondition;
                    }

                } else if ("oms_pub_apply".equals(bussinesType)) {

                    String status = applyStatus.getApplySatus();
                    // 同意到  上传批文流程
                    if ("pass".equals(ispass)) {
                        setSql += status + "=" + Constants.emPrivateGoAbroad.核实批件.getIndex();
                        return updateSql + setSql + whereCondition;
                    }
                    // 不同意 到 部长审批 因公流程 完结
                    else if ("nopass".equals(ispass)) {

                        setSql += status + "=" + Constants.emPrivateGoAbroad.已办结.getIndex();
                        return updateSql + setSql + whereCondition;
                    }
                }
            }
        }
        return null;
    }

    public List<Map> selectGroupConditions() {

        return leaderCommonMapper.selectGroupConditions();
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo createPutOnRecordList(LeaderSupervisionVo leaderSupervisionVo, Object[] bussinessIds) {

        PageUtil.pageHelp(leaderSupervisionVo.getPageNum(), leaderSupervisionVo.getPageSize());
        List<Map> lists = leaderCommonMapper.createPutOnRecordList(leaderSupervisionVo, null);
        PageInfo pageInfo = new PageInfo(lists);
        return pageInfo;
    }

    /**
     * 批量下载 备案表
     **/
    public Map batchDownloadPutOnRecord(LeaderSupervisionVo leaderSupervisionVo) {

        Map maps = new HashMap();
        List<BussinessTypeAndIdVo> lists = leaderSupervisionVo.getBussinessTypeAndIdVos();
        byte[] fileDateByte = null;
        if (lists != null) {
            // 当只有一个pdf 现在本附件
            if (lists.size() > 0 && lists.size() == 1) {

                BussinessTypeAndIdVo bussinessTypeAndIdVo = lists.get(0);

                String bussinessId = bussinessTypeAndIdVo.getBussinessId();
                String bussinessOccureStpet = bussinessTypeAndIdVo.getBussinessOccureStpet();
                String bussinessOccureStpetName = bussinessTypeAndIdVo.getBussinessOccureStpetName();

                QueryWrapper<OmsAttachment> queryWrapper = new QueryWrapper<>();

                queryWrapper.eq("bussinessId", bussinessId);
                queryWrapper.eq("bussiness_occure_stpet", bussinessOccureStpet);
                queryWrapper.eq("bussiness_occure_stpet_name", bussinessOccureStpetName);

                List<OmsAttachment> attList = omsAttachmentMapper.selectList(queryWrapper);

                if (attList != null && attList.size() == 1) {
                    // 获取文件路径
                    String fileUrl = attList.get(0).getUrl();
                    try {
                        fileDateByte = LeaderSupervisionUntil.downloadFile(fileUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    maps.put("fileName", attList.get(0).getName());
                    maps.put("array", fileDateByte);
                    return maps;

                } else {
                    throw new CustomMessageException("备案表不存在,请仔细检查");
                }
            }
            // 当有多个附件 显示压缩包
            else if (lists.size() > 0 && lists.size() > 1) {
                // 文件集合
                List<File> fileList = new ArrayList<>();
                String zipFilePullPath = attachmentPath + File.separator + "static" + File.separator;
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                Calendar calendar = Calendar.getInstance();
                String fileName = df.format(calendar.getTime()) + ".zip";
                log.info("文件的文件名为:" + fileName);
                for (int i = 0; i < lists.size(); i++) {
                    BussinessTypeAndIdVo bussinessTypeAndIdVo = lists.get(0);
                    String bussinessId = bussinessTypeAndIdVo.getBussinessId();
                    String bussinessOccureStpet = bussinessTypeAndIdVo.getBussinessOccureStpet();
                    String bussinessOccureStpetName = bussinessTypeAndIdVo.getBussinessOccureStpetName();

                    QueryWrapper<OmsAttachment> queryWrapper = new QueryWrapper<>();

                    queryWrapper.eq("bussinessId", bussinessId);
                    queryWrapper.eq("bussiness_occure_stpet", bussinessOccureStpet);
                    queryWrapper.eq("bussiness_occure_stpet_name", bussinessOccureStpetName);

                    List<OmsAttachment> attList = omsAttachmentMapper.selectList(queryWrapper);

                    if (attList != null && attList.size() == 1) {

                        String fileUrl = attList.get(0).getUrl();

                        File inputFile = new File(FilenameUtils.normalize(fileUrl));
                        //判断文件是否存在
                        if (inputFile.exists() && inputFile.isFile()) {
                            fileList.add(inputFile);
                        }
                    } else {
                        throw new CustomMessageException("一个人员出现多个备案表,请仔细检查");
                    }
                }

                ZIPUtils.zipFiles(fileList, new File(zipFilePullPath + fileName));
                //zip文件下载
                try {
                    fileDateByte = LeaderSupervisionUntil.downloadFile(zipFilePullPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //删除zip文件
                FileUtil.deleteFile(zipFilePullPath + fileName);

                maps.put("fileName", fileName);
                maps.put("array", fileDateByte);
                return maps;
            }
        } else {
            throw new CustomMessageException("参数错误，请仔细检查");
        }
        return null;
    }
    private String  getPdfByHtmlByChenpiDan(OmsCreateFile omsCreateFile) {
        //解析 html img 的src 标签


        String contentStr = HtmlUtils.replaceTag(omsCreateFile.getFrontContent(),"src",ueditorRealImgUrl);

        // 要转换的 html
        String htmlstr =LeaderSupervisionUntil.prefixPdfStyle +contentStr+LeaderSupervisionUntil.suffixPdfStyle;

        String newHtmlStr = htmlstr.replaceAll("<br>","<br/>");
        // 生成 pdf的路径 +名称
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String fileName = df.format(calendar.getTime())+omsCreateFile.getApplyId() +pdfName+".pdf" ;
        log.info("文件的文件名为:" + fileName);


        String filePath = attachmentPath+File.separator+"static"+File.separator;
        try {
            FileTypeConvertUtil.html2pdf(newHtmlStr,filePath+fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath+fileName;

    }


    // 保存 备案表记录
    public void saveOmsPutonrecords(){

        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        OmsPutonrecords omsPutonrecords = new OmsPutonrecords();
        omsPutonrecords.setId(UUIDGenerator.getPrimaryKey());
        omsPutonrecords.setOrgName(userInfo.getOrgName());
        omsPutonrecords.setCreateDate(new Date());
        omsPutonrecords.setUserName(userInfo.getUserName());
        omsPutonrecordsMapper.insert(omsPutonrecords);

    }
}
