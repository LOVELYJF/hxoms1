package com.hxoms.modules.omsoperator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsoperator.entity.*;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorApprovalMapper;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorHandoverMapper;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorHandoverSubformMapper;
import com.hxoms.modules.omsoperator.service.OmsOperatorService;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.OmsCerGetTaskVO;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈经办人管理〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/7/16 20:07
 */
@Service
public class OmsOperatorServiceImpl implements OmsOperatorService {

    @Autowired
    private CfUserMapper cfUserMapper;
    @Autowired
    private OmsOperatorApprovalMapper operatorApprovalMapper;
    @Autowired
    private OmsOperatorHandoverMapper operatorHandoverMapper;
    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;
    @Autowired
    private OmsOperatorHandoverMapper omsOperatorHandoverMapper;
    @Autowired
    private OmsOperatorHandoverSubformMapper omsOperatorHandoverSubformMapper;
    /**
     * 功能描述: <br>
     * 〈保存经办人信息〉
     * @Param: [user]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/6 15:32
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String saveOperator(CfUser user) {
        //1、判断有没有选择经办人
        if (user == null) {
            throw new CustomMessageException("请先选择经办人!");
        }
        //提示信息
        String msc;
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        if (StringUilt.isStrOrnull(user.getUserId())) {
            //更新
            //修改人
            user.setModifyUser(loginUser.getId());
            //修改时间
            user.setModifyTime(new Date());
            cfUserMapper.updateByPrimaryKeySelective(user);
            msc="经办人未上报干部监督处，到经办人管理页面修改或上报!";
            return msc;
        } else {
            //新增经办人
            verify(user);
            //做保存业务，点击【保存】，校验通过后，将状态置为“注册”，提醒系统管理员：“经办人未上报干部监督处，到经办人管理页面修改或上报”。
            //设置ID
            user.setUserId(UUIDGenerator.getPrimaryKey());
            //设置初始密码
            user.setUserPassword(UUIDGenerator.encryptPwd(Constants.USER_PWD));
            //设置用户类型
            user.setUserType(Constants.USER_TYPES[6]);
            //设置用户状态
            user.setUserState(Constants.USER_STATUS[0]);
            //创建人
            user.setCreator(loginUser.getId());
            //创建时间
            user.setCreatetime(new Date());
            cfUserMapper.insertSelective(user);
            msc="经办人未上报干部监督处，到经办人管理页面修改或上报!";
            return msc;
        }
    }

    /**
     * 功能描述: <br>
     * 〈保存并上传经办人〉
     * @Param: [user]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/7 10:30
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String saveAndUploadOperator(CfUser user) {
        //1、判断有没有选择经办人
        if (user == null) {
            throw new CustomMessageException("请先选择经办人!");
        }
        String msc ="";
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        //创建审批对象
        OmsOperatorApproval  omsOperatorApproval = new OmsOperatorApproval();
        if (StringUilt.isStrOrnull(user.getUserId())) {
            //更新
            //修改人
            user.setModifyUser(loginUser.getId());
            //修改时间
            user.setModifyTime(new Date());
            //将状态置为"监督处审核"
            user.setUserState(Constants.USER_STATUS[3]);
            //更新经办人信息
            cfUserMapper.updateByPrimaryKeySelective(user);
        }else {
            //新增
            //校验
            verify(user);
            //设置ID
            user.setUserId(UUIDGenerator.getPrimaryKey());
            //设置初始密码
            user.setUserPassword(UUIDGenerator.encryptPwd(Constants.USER_PWD));
            //设置用户类型
            user.setUserType(Constants.USER_TYPES[6]);
            //将状态置为"监督处审核"
            user.setUserState(Constants.USER_STATUS[3]);
            //创建人
            user.setCreator(loginUser.getId());
            //创建时间
            user.setCreatetime(new Date());
            //新增经办人信息
            cfUserMapper.insertSelective(user);
        }
        OmsOperatorApproval omsOperatorApproval1 = operatorApprovalMapper.selectByUserId(user.getUserId());
        if(omsOperatorApproval1 == null){
            //经办人审批表主键、
            omsOperatorApproval.setId(UUIDGenerator.getPrimaryKey());
            // 经办人主键、
            omsOperatorApproval.setOperatorid(user.getUserId());
            //步骤名称（1.监督处审核 2.处领导审批）、
            omsOperatorApproval.setStepname("监督处审核");
            // 提交时间
            omsOperatorApproval.setSubmissiontime(new Date());
            // 提交人
            omsOperatorApproval.setSubmitter(loginUser.getId());
            //新增经办人审批信息
            operatorApprovalMapper.insertSelective(omsOperatorApproval);
        }
        return "请通知经办人持单位审批后的申请表及身份证到干部监督处办理审批手续";
    }

    /**
     * 功能描述: <br>
     * 〈根据UserID删除经办人〉
     * @Param: [userId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/7 19:48
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void deleteOperator(String userId) {
        if (StringUtils.isEmpty(userId)){
            throw new CustomMessageException("参数为空!");
        }
        cfUserMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 功能描述: <br>
     * 〈撤销经办人〉
     * @Param: [operatorId]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/8 14:41
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String revokeOperator(String operatorId) {
        String msc = "";
        if (StringUtils.isBlank(operatorId)) {
            throw new CustomMessageException("请先选择经办人!");
        }

        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        //查找原经办人
        CfUser user = cfUserMapper.selectByPrimaryKey(operatorId);
        String userState = user.getUserState();
        //用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
        if (userState.equals(Constants.USER_STATUS[1]) || userState.equals(Constants.USER_STATUS[7])){
            //状态为正常时操作显示：【撤消】、【审批信息】，将状态置为“待撤消”，系统通知经办人：“请到经办人交接页面办理交接手续”
           cfUserMapper.updateUserState(user.getUserId(),Constants.USER_STATUS[6]);
            msc ="请到经办人交接页面办理交接手续!";
        } else if (userState.equals(Constants.USER_STATUS[3])){
            //状态为征求意见时操作显示：【撤消】，此操作直接将状态置为“撤消”，通知干部监督处：“经办人XXX的申请已被XXX单位撤消”
            cfUserMapper.updateUserState(user.getUserId(),Constants.USER_STATUS[2]);
            //需要当前操作人ID(未设置)
            msc = "经办人"+user.getUserName()+"的申请已被"+loginUser.getOrgName()+"单位撤消!";
        }else if (userState.equals(Constants.USER_STATUS[4])){
            //状态为待审批时操作显示：【撤消】、【打印申请表】，此操作直接将状态置为“撤消”，通知干部监督处：“经办人XXX的申请已被XXX单位撤消”
            cfUserMapper.updateUserState(user.getUserId(),Constants.USER_STATUS[2]);
            //需要当前操作人ID(未设置)
            msc = "经办人"+user.getUserName()+"的申请已被"+loginUser.getOrgName()+"单位撤消!";
        }

        return msc;
    }

    /**
     * 功能描述: <br>
     * 〈经办人审批信息查询〉
     * @Param: [userId]
     * @Return: com.hxoms.modules.omsoperator.entity.OmsOperatorApproval
     * @Author: 李逍遥
     * @Date: 2020/5/11 9:16
     */
    @Override
    public OmsOperatorApproval getOperatorApprovalByUid(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new CustomMessageException("参数为空！");
        }
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(userId);
        return approval;
    }

    /**
     * 功能描述: <br>
     * 〈查询经办人交接记录〉
     * @Param: [userId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/5/11 11:44
     */
    @Override
    public List<OmsOperatorHandoverSubformVO>  getOperatorHandoverByUid(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new CustomMessageException("参数为空！");
        }
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS  = operatorHandoverMapper.selectByUserId(userId);
        return omsOperatorHandoverSubformVOS;
    }

   /**
    * 功能描述: <br>
    * 〈获取经办人列表〉
    * @Param: [pageNum, pageSize, keyWord, orgId, state]
    * @Return: com.github.pagehelper.PageInfo
    * @Author: 李逍遥
    * @Date: 2020/7/8 15:30
    */
    @Override
    public PageInfo getAllOperator(Integer pageNum, Integer pageSize, String keyWord,List<String> state) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //获取当前登录人
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        ArrayList<String> orgIds = new ArrayList<>();
        orgIds.add(loginUser.getOrgId());
        /** 设置传入页码，以及每页的大小  */
        PageHelper.startPage(pageNum, pageSize);
        List<CfUser> users = cfUserMapper.getOperatorApprovalList(keyWord,orgIds,Constants.USER_TYPES[6],state);
        PageInfo info = new PageInfo(users);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈经办人名单页面——获取经办人列表〉
     * @Param: [pageNum, pageSize, orgId, keyWord, state]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/14 16:11
     */
    @Override
    public PageInfo getOperatorList(Integer pageNum, Integer pageSize, List<String> orgId, String keyWord, List<String> state) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        /**  设置传入页码，以及每页的大小  */
        PageHelper.startPage(pageNum, pageSize);
        List<CfUser> users = cfUserMapper.getOperatorApprovalList(keyWord,orgId,Constants.USER_TYPES[6],state);
        PageInfo info = new PageInfo(users);
        return info;
    }
    /**
     * 功能描述: <br>
     * 〈经办人——基本数据流程统计〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/17 15:32
     */
    @Override
    public List<CountStatusResult> selectCountStatus(String orgId) {
        List<CountStatusResult> countStatusResults = operatorApprovalMapper.selectCountStatus(orgId);
        return countStatusResults;
    }

    /**
     * 功能描述: <br>
     * 〈经办人名单导出〉
     * @Param: [keyWord, orgId, state]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/17 19:31
     */
    @Override
    public void exportOperator(String keyWord, List<String> orgId, List<String> state, HttpServletResponse response) {

        //根据条件查询经办人名单
        List<CfUser> operatorList = cfUserMapper.getOperatorApprovalList(keyWord, orgId, Constants.USER_TYPES[6], state);
        if (operatorList == null || operatorList.size() < 1){
            throw new CustomMessageException("操作失败");
        }else {
            /** 开始导出 */
            //创建HSSFWorkbook对象(excel的文档对象)
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建文件样式对象
            HSSFCellStyle style = wb.createCellStyle();
            //获得字体对象
            HSSFFont font = wb.createFont();
            //建立新的sheet对象（excel的表单）
            HSSFSheet sheet=wb.createSheet("经办人名单");
            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row1=sheet.createRow(0);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell=row1.createCell(0);

            //设置标题字体大小
            font.setFontHeightInPoints((short) 16);
            //加粗
            font.setBold(true);
            // 左右居中 
            style.setAlignment(HorizontalAlignment.CENTER);
            // 上下居中 
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFont(font);
            cell.setCellStyle(style);
            //设置标题单元格内容
            cell.setCellValue("经办人名单");

            //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,9));
            //在sheet里创建第二行
            HSSFRow row2=sheet.createRow(2);
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("序号");
            row2.createCell(1).setCellValue("单位");
            row2.createCell(2).setCellValue("登录名");
            row2.createCell(3).setCellValue("姓名");
            row2.createCell(4).setCellValue("身份证号");
            row2.createCell(5).setCellValue("职务（级）");
            row2.createCell(6).setCellValue("联系方式");
            row2.createCell(7).setCellValue("政治面貌");
            row2.createCell(8).setCellValue("MAC地址");
            row2.createCell(9).setCellValue("状态");
            //在sheet里添加数据

            //创建文件样式对象
            HSSFCellStyle style1 = wb.createCellStyle();
            //获得字体对象
            HSSFFont font1 = wb.createFont();
            //设置单元格字体大小
            font1.setFontHeightInPoints((short) 12);
            //居左
            style1.setAlignment(HorizontalAlignment.LEFT);
            style1.setFont(font1);

            HSSFRow row = null;
            for(int i = 0; i < operatorList.size(); i++){
                row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(operatorList.get(i).getOrgName());
                row.createCell(2).setCellValue(operatorList.get(i).getUserCode());
                row.createCell(3).setCellValue(operatorList.get(i).getUserName());
                row.createCell(4).setCellValue(operatorList.get(i).getIdnumber());
                row.createCell(5).setCellValue(operatorList.get(i).getDuty());
                row.createCell(6).setCellValue((operatorList.get(i).getUserMobile()));
                row.createCell(7).setCellValue(operatorList.get(i).getPoliticalAffi());
                row.createCell(8).setCellValue(operatorList.get(i).getMac());
                row.createCell(9).setCellValue(Constants.USER_STATUSName[(Integer.valueOf(operatorList.get(i).getUserState()))]);

                //设置单元格字体大小
                for(int j = 0;j < 9;j++){
                    row.getCell(j).setCellStyle(style1);
                }
            }

            //输出Excel文件
            OutputStream output= null;
            try {
                output = response.getOutputStream();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "utf-8");

                wb.write(output);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 功能描述: <br>
     * 〈经办人交接页面展示〉
     * @Param: [operatorId]
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO>
     * @Author: 李逍遥
     * @Date: 2020/7/20 15:22
     */
    @Override
    public List<OmsOperatorHandoverSubformVO> getOperatorHandoverByOperatorId(String operatorId) {
        if (StringUtils.isBlank(operatorId)) {
            throw new CustomMessageException("参数为空！");
        }
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS  = operatorHandoverMapper.selectByOperatorId(operatorId,String.valueOf(Constants.handover_business[3]));
        return omsOperatorHandoverSubformVOS;
    }
    /**
     * 功能描述: <br>
     * 〈经办人交接页面下一步〉
     * @Param: [omsOperatorHandoverSubforms]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/20 16:05
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void nextByOperator(List<OmsOperatorHandoverSubform> omsOperatorHandoverSubforms) {
        String id = null;
        //跟新交接子表内容
        if (omsOperatorHandoverSubforms != null && omsOperatorHandoverSubforms.size() > 0){
            for (OmsOperatorHandoverSubform omsOperatorHandoverSubform:omsOperatorHandoverSubforms) {
                omsOperatorHandoverSubformMapper.updateByPrimaryKeySelective(omsOperatorHandoverSubform);
                id = omsOperatorHandoverSubform.getHandoverformid();
            }
        }
        //更新交接主表状态
        OmsOperatorHandover omsOperatorHandover = omsOperatorHandoverMapper.selectByPrimaryKey(id);
        if (omsOperatorHandover == null){
            throw new CustomMessageException("无交接记录");
        }
        omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[4]));
        omsOperatorHandoverMapper.updateByPrimaryKeySelective(omsOperatorHandover);
    }

    /**
     * 功能描述: <br>
     * 〈接手人确认页面展示〉
     * @Param: [handoverId]
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO>
     * @Author: 李逍遥
     * @Date: 2020/7/20 16:21
     */
    @Override
    public List<OmsOperatorHandoverSubformVO> getOperatorHandoverByhandoverId(String handoverId) {
        if (StringUtils.isBlank(handoverId)) {
            throw new CustomMessageException("参数为空！");
        }
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS  = operatorHandoverMapper.selectByHandoverId(handoverId,String.valueOf(Constants.handover_business[4]));
        return omsOperatorHandoverSubformVOS;

    }

    /**
     * 功能描述: <br>
     * 〈接手人确认页面下一步〉
     * @Param: [handoverformid]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/20 16:28
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void nextByHandover(String handoverformid) {
        if (StringUtils.isBlank(handoverformid)){
            throw new CustomMessageException("参数为空!");
        }
        //更新交接主表状态
        OmsOperatorHandover omsOperatorHandover = omsOperatorHandoverMapper.selectByPrimaryKey(handoverformid);
        if (omsOperatorHandover == null){
            throw new CustomMessageException("无交接记录");
        }
        omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[5]));
        omsOperatorHandoverMapper.updateByPrimaryKeySelective(omsOperatorHandover);
    }

    /**
     * 功能描述: <br>
     * 〈经办人交接流程统计〉
     * @Param: [orgId]
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/20 18:10
     */
    @Override
    public List<CountStatusResult> selectCountStatusByHandover(String orgId) {
        if (StringUtils.isBlank(orgId)){
            //获取当前登录人
            UserInfo loginUser = UserInfoUtil.getUserInfo();
            orgId = loginUser.getOrgId();
        }
        List<CountStatusResult> countStatusResults = operatorApprovalMapper.selectCountStatusByHandover(orgId);
        return countStatusResults;
    }

    /**
     * 功能描述: <br>
     * 〈经办人交接流程页面展示〉
     * @Param: [orgId]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/23 14:51
     */
    @Override
    public PageInfo getAllOperatorHandoverByOrgId(Integer pageNum, Integer pageSize,String orgId) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (StringUtils.isBlank(orgId)){
            //获取登录用户信息
            UserInfo loginUser = UserInfoUtil.getUserInfo();
            orgId = loginUser.getOrgId();
        }
        PageHelper.startPage(pageNum, pageSize);   //设置传入页码，以及每页的大小
        List<LinkedHashMap<String, Object>> list = operatorApprovalMapper.getAllOperatorHandoverByOrgId(orgId);
        PageInfo info = new PageInfo(list);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈通过b0100查询经办人〉
     * @Param: [b0100]
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Author: 李逍遥
     * @Date: 2020/8/14 10:47
     */
    @Override
    public List<CfUser> getOperatorByB0100(String b0100) {
        if (StringUtils.isBlank(b0100)){
            throw new CustomMessageException("参数为空!");
        }
        List<CfUser> users = cfUserMapper.getOperatorByB0100(b0100,Constants.USER_TYPES[6]);
        return users;
    }

    /**
     * 功能描述: <br>
     * 〈获取该撤销经办人未办结数据〉
     * @Param: [operatorId, handoverId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/9/10 8:58
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void getUnfinished(String operatorId, String handoverId) {
        if (StringUtils.isBlank(operatorId)) {
            throw new CustomMessageException("请先选择经办人!");
        }
        if (StringUtils.isBlank(handoverId)){
            throw new CustomMessageException("接手人不能为空!");
        }
        //查找原经办人
        CfUser user = cfUserMapper.selectByPrimaryKey(operatorId);
        /**证照领取*/
        List<OmsCerGetTaskVO> omsCerGetTasks = operatorHandoverMapper.selectOmsCerGetTaskByStatusAndName(user.getUserId(), "0");

        /**注销证照*/
        List<OmsCerCancellateLicense> omsCerCancellateLicenses = operatorHandoverMapper.selectCerCancellateLicenseByStatusAndName(user.getUserId(),"11");

        /**撤销登记备案*/
        List<OmsRegRevokeapply> omsRegRevokeapplies = operatorHandoverMapper.selectOmsRegRevokeapplyByStatusAndName(user.getUserId(), "2");

        /**因公出国境*/
        List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectPubAllyByStatusAndName(user.getUserId(), Constants.leader_business[9]);

        /**因私出国境*/
        List<OmsPriApplyVO> omsPriApplyVOS = operatorHandoverMapper.selectOmsPriApplyByStatusAndName(user.getUserId(), Constants.leader_business[9]);

        /** 延期回国*/
        List<OmsPriDelayVO> omsPriDelayVOS = operatorHandoverMapper.selectOmsPriDelayApplyByStatusAndName(user.getUserId(),Constants.leader_business[9]);

        if ((omsCerGetTasks != null && omsCerGetTasks.size() > 0)  || (omsCerCancellateLicenses != null && omsCerCancellateLicenses.size() > 0) ||
                (omsRegRevokeapplies != null && omsRegRevokeapplies.size() > 0  ) || (omsPubApplyVOS != null && omsPubApplyVOS.size() > 0 ) ||
                (omsPriApplyVOS != null && omsPriApplyVOS.size() > 0 ) || (omsPriDelayVOS != null && omsPriDelayVOS.size() > 0)){
            //往交接主表插入数据
            //首先查询该经办人及接手人是否已经有记录了
            OmsOperatorHandover omsOperatorHandovers =  omsOperatorHandoverMapper.selectByOperatorIdAndHandover(operatorId,handoverId);
            //往交接主表插入数据、
            String primaryKey = UUIDGenerator.getPrimaryKey();
            if (omsOperatorHandovers == null ){
                OmsOperatorHandover omsOperatorHandover = new OmsOperatorHandover();
                //主键
                omsOperatorHandover.setId(primaryKey);
                //接手人ID
                omsOperatorHandover.setHandoverid(handoverId);
                //经办人主键
                omsOperatorHandover.setOperatorid(operatorId);
                //交接时间
                omsOperatorHandover.setHandovertime(new Date());
                //交接状态
                omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[3]));
                omsOperatorHandoverMapper.insertSelective(omsOperatorHandover);

                //查询该经办人未完成的业务(往交接主表插入数据、查出数据后插入交接子表)
                /**证照领取*/
                if (omsCerGetTasks != null && omsCerGetTasks.size()>0) {
                    for (OmsCerGetTaskVO omsCerGetTask : omsCerGetTasks) {
                        Date date = new Date();
                        // 查出数据后插入交接子表
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                        //交接子表主键
                        omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                        //交接表主表主键
                        omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                        //业务主键
                        omsOperatorHandoverSubform.setBusinessid(omsCerGetTask.getId());
                        //业务类别
                        omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[0]);
                        //姓名
                        omsOperatorHandoverSubform.setName(omsCerGetTask.getName());
                        //身份证号
                        omsOperatorHandoverSubform.setIdcard(omsCerGetTask.getIdnumber());
                        //工作单位
                        omsOperatorHandoverSubform.setCompany(omsCerGetTask.getB0101());
                        //职务（级）
                        omsOperatorHandoverSubform.setDuty(omsCerGetTask.getPostrank());
                        //交接时间
                        omsOperatorHandoverSubform.setHandovertime(date);
                        //接手人ID
                        omsOperatorHandoverSubform.setHandoverid(handoverId);
                        //业务发生时间
                        omsOperatorHandoverSubform.setExitdate(omsCerGetTask.getGetTime());
                        //说明

                        omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                    }
                }
                /**注销证照*/
                if (omsCerCancellateLicenses != null && omsCerCancellateLicenses.size()>0) {
                    for (OmsCerCancellateLicense omsCerCancellateLicense : omsCerCancellateLicenses) {
                        Date date = new Date();
                        // 查出数据后插入交接子表
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                        //交接子表主键
                        omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                        //交接表主表主键
                        omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                        //业务主键
                        omsOperatorHandoverSubform.setBusinessid(omsCerCancellateLicense.getId());
                        //业务类别
                        omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[5]);
                        //姓名
                        omsOperatorHandoverSubform.setName(omsCerCancellateLicense.getName());
                        //性别
                        omsOperatorHandoverSubform.setSex(omsCerCancellateLicense.getSex());
                        //出生日期
                        omsOperatorHandoverSubform.setBirthday(omsCerCancellateLicense.getCsrq());
                        //身份证号
                        omsOperatorHandoverSubform.setIdcard(omsCerCancellateLicense.getZjhm());
                        //工作单位
                        omsOperatorHandoverSubform.setCompany(omsCerCancellateLicense.getWorkUnit());
                        //职务（级）
                        omsOperatorHandoverSubform.setDuty(omsCerCancellateLicense.getPost());
                        //交接时间
                        omsOperatorHandoverSubform.setHandovertime(date);
                        //接手人ID
                        omsOperatorHandoverSubform.setHandoverid(handoverId);
                        //业务发生时间
                        omsOperatorHandoverSubform.setExitdate(omsCerCancellateLicense.getCreateTime());
                        //说明
                        omsOperatorHandoverSubform.setSm(omsCerCancellateLicense.getZxsm());
                        omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                    }
                }

                /**因公出国境*/
                if (omsPubApplyVOS != null && omsPubApplyVOS.size()>0){
                    for (OmsPubApplyVO omsPubApplyVo:omsPubApplyVOS) {
                        Date date = new Date();
                        // 查出数据后插入交接子表
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                        //交接子表主键
                        omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                        //交接表主表主键
                        omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                        //业务主键
                        omsOperatorHandoverSubform.setBusinessid(omsPubApplyVo.getId());
                        //业务类别
                        omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[1]);
                        //人员主键
                        omsOperatorHandoverSubform.setA0100(omsPubApplyVo.getA0100());
                        //姓名
                        omsOperatorHandoverSubform.setName(omsPubApplyVo.getName());
                        //性别
                        omsOperatorHandoverSubform.setSex(omsPubApplyVo.getSex());
                        //出生日期
                        omsOperatorHandoverSubform.setBirthday(omsPubApplyVo.getBirthDate());
                        //身份证号
                        omsOperatorHandoverSubform.setIdcard(omsPubApplyVo.getIdnumber());
                        //政治面貌
                        omsOperatorHandoverSubform.setPoliticsstatus(omsPubApplyVo.getPoliticalAff());
                        //工作单位
                        omsOperatorHandoverSubform.setCompany(omsPubApplyVo.getB0101());
                        //职务（级）
                        omsOperatorHandoverSubform.setDuty(omsPubApplyVo.getJob());
                        //交接时间
                        omsOperatorHandoverSubform.setHandovertime(date);
                        //接手人ID
                        omsOperatorHandoverSubform.setHandoverid(handoverId);
                        //出国（境）时间
                        omsOperatorHandoverSubform.setExitdate(omsPubApplyVo.getCgsj());
                        //入境时间
                        omsOperatorHandoverSubform.setEntrydate(omsPubApplyVo.getHgsj());
                        //说明
                        omsOperatorHandoverSubform.setSm(omsPubApplyVo.getCfrw());
                        omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                    }
                }
                /**因私出国境*/
                if (omsPriApplyVOS != null && omsPriApplyVOS.size()>0){
                    for (OmsPriApplyVO omsPriApplyVO:omsPriApplyVOS) {
                        Date date = new Date();
                        // 查出数据后插入交接子表
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                        //交接子表主键
                        omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                        //交接表主表主键
                        omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                        //业务主键
                        omsOperatorHandoverSubform.setBusinessid(omsPriApplyVO.getId());
                        //业务类别
                        omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[2]);
                        //人员主键
                        omsOperatorHandoverSubform.setA0100(omsPriApplyVO.getProcpersonId());
                        //姓名
                        omsOperatorHandoverSubform.setName(omsPriApplyVO.getName());
                        //性别
                        omsOperatorHandoverSubform.setSex(omsPriApplyVO.getSex());
                        //出生日期
                        omsOperatorHandoverSubform.setBirthday(omsPriApplyVO.getBirthDate());
                        //身份证号
                        omsOperatorHandoverSubform.setIdcard(omsPriApplyVO.getIdnumber());
                        //政治面貌
                        omsOperatorHandoverSubform.setPoliticsstatus(omsPriApplyVO.getPoliticalOutlook());
                        //工作单位
                        omsOperatorHandoverSubform.setCompany(omsPriApplyVO.getB0101());
                        //职务（级）
                        omsOperatorHandoverSubform.setDuty(omsPriApplyVO.getPostrank());
                        //交接时间
                        omsOperatorHandoverSubform.setHandovertime(date);
                        //接手人ID
                        omsOperatorHandoverSubform.setHandoverid(handoverId);
                        //出国（境）时间
                        omsOperatorHandoverSubform.setExitdate(omsPriApplyVO.getAbroadTime());
                        //入境时间
                        omsOperatorHandoverSubform.setEntrydate(omsPriApplyVO.getReturnTime());
                        //说明
                        omsOperatorHandoverSubform.setSm(omsPriApplyVO.getAbroadReasons());
                        omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                    }
                }
                /**延期回国*/
                if (omsPriDelayVOS != null && omsPriDelayVOS.size()>0){
                    for (OmsPriDelayVO omsPriDelayVO:omsPriDelayVOS) {
                        Date date = new Date();
                        // 查出数据后插入交接子表
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                        //交接子表主键
                        omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                        //交接表主表主键
                        omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                        //业务主键
                        omsOperatorHandoverSubform.setBusinessid(omsPriDelayVO.getId());
                        //业务类别
                        omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[3]);
                        //人员主键
                        omsOperatorHandoverSubform.setA0100(omsPriDelayVO.getProcpersonId());
                        //姓名
                        omsOperatorHandoverSubform.setName(omsPriDelayVO.getName());
                        //性别
                        omsOperatorHandoverSubform.setSex(omsPriDelayVO.getSex());
                        //身份证号
                        omsOperatorHandoverSubform.setIdcard(omsPriDelayVO.getIdnumber());
                        //工作单位
                        omsOperatorHandoverSubform.setCompany(omsPriDelayVO.getB0101());
                        //职务（级）
                        omsOperatorHandoverSubform.setDuty(omsPriDelayVO.getPostrank());
                        //交接时间
                        omsOperatorHandoverSubform.setHandovertime(date);
                        //接手人ID
                        omsOperatorHandoverSubform.setHandoverid(handoverId);
                        //出国（境）时间
                        omsOperatorHandoverSubform.setExitdate(omsPriDelayVO.getApplyTime());
                        //入境时间
                        omsOperatorHandoverSubform.setEntrydate(omsPriDelayVO.getEstimateReturntime());
                        //说明
                        omsOperatorHandoverSubform.setSm(omsPriDelayVO.getDelayReason());
                        omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                    }
                }
                /**撤销登记备案*/
                if (omsRegRevokeapplies != null && omsRegRevokeapplies.size()>0) {
                    for (OmsRegRevokeapply omsRegRevokeapply : omsRegRevokeapplies) {
                        Date date = new Date();
                        // 查出数据后插入交接子表
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                        //交接子表主键
                        omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                        //交接表主表主键
                        omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                        //业务主键
                        omsOperatorHandoverSubform.setBusinessid(omsRegRevokeapply.getId());
                        //业务类别
                        omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[4]);
                        //人员主键
                        omsOperatorHandoverSubform.setA0100(omsRegRevokeapply.getA0100());
                        //姓名
                        omsOperatorHandoverSubform.setName(omsRegRevokeapply.getSurname()+omsRegRevokeapply.getName());
                        //性别
                        omsOperatorHandoverSubform.setSex(omsRegRevokeapply.getSex());
                        //身份证号
                        omsOperatorHandoverSubform.setIdcard(omsRegRevokeapply.getIdnumberGb());
                        //工作单位
                        omsOperatorHandoverSubform.setCompany(omsRegRevokeapply.getWorkUnit());
                        //职务（级）
                        omsOperatorHandoverSubform.setDuty(omsRegRevokeapply.getPost());
                        //交接时间
                        omsOperatorHandoverSubform.setHandovertime(date);
                        //接手人ID
                        omsOperatorHandoverSubform.setHandoverid(handoverId);
                        //业务发生时间
                        omsOperatorHandoverSubform.setExitdate(omsRegRevokeapply.getCreateDate());
                        //说明
                        omsOperatorHandoverSubform.setSm(omsRegRevokeapply.getApplyReason());
                        omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                    }
                }

            }else{
                //通过主表id及业务id查询子表 是否有记录，有则更新，无则插入
                //获取主表id
                String id = omsOperatorHandovers.getId();
                /**证照领取*/
                if (omsCerGetTasks != null && omsCerGetTasks.size()>0) {
                    for (OmsCerGetTaskVO omsCerGetTask : omsCerGetTasks) {
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = omsOperatorHandoverSubformMapper.getAllDataByHandoverId(id,omsCerGetTask.getId());
                        if (omsOperatorHandoverSubform == null){
                            // 查出数据后插入交接子表
                            omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                            //交接子表主键
                            omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                            //交接表主表主键
                            omsOperatorHandoverSubform.setHandoverformid(id);
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsCerGetTask.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[0]);
                            //姓名
                            omsOperatorHandoverSubform.setName(omsCerGetTask.getName());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsCerGetTask.getIdnumber());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsCerGetTask.getB0101());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsCerGetTask.getPostrank());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //业务发生时间
                            omsOperatorHandoverSubform.setExitdate(omsCerGetTask.getGetTime());
                            //说明

                            omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);

                        }else {
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsCerGetTask.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[0]);
                            //姓名
                            omsOperatorHandoverSubform.setName(omsCerGetTask.getName());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsCerGetTask.getIdnumber());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsCerGetTask.getB0101());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsCerGetTask.getPostrank());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //业务发生时间
                            omsOperatorHandoverSubform.setExitdate(omsCerGetTask.getGetTime());
                            //说明
                            omsOperatorHandoverSubformMapper.updateByPrimaryKeySelective(omsOperatorHandoverSubform);
                        }
                    }
                }

                /**注销证照*/
                if (omsCerCancellateLicenses != null && omsCerCancellateLicenses.size()>0) {
                    for (OmsCerCancellateLicense omsCerCancellateLicense : omsCerCancellateLicenses) {
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = omsOperatorHandoverSubformMapper.getAllDataByHandoverId(id,omsCerCancellateLicense.getId());
                        if (omsOperatorHandoverSubform == null){
                            // 查出数据后插入交接子表
                            omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                            //交接子表主键
                            omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                            //交接表主表主键
                            omsOperatorHandoverSubform.setHandoverformid(id);
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsCerCancellateLicense.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[5]);
                            //姓名
                            omsOperatorHandoverSubform.setName(omsCerCancellateLicense.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsCerCancellateLicense.getSex());
                            //出生日期
                            omsOperatorHandoverSubform.setBirthday(omsCerCancellateLicense.getCsrq());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsCerCancellateLicense.getZjhm());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsCerCancellateLicense.getWorkUnit());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsCerCancellateLicense.getPost());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //业务发生时间
                            omsOperatorHandoverSubform.setExitdate(omsCerCancellateLicense.getCreateTime());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsCerCancellateLicense.getZxsm());
                            omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                        }else {
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsCerCancellateLicense.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[5]);
                            //姓名
                            omsOperatorHandoverSubform.setName(omsCerCancellateLicense.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsCerCancellateLicense.getSex());
                            //出生日期
                            omsOperatorHandoverSubform.setBirthday(omsCerCancellateLicense.getCsrq());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsCerCancellateLicense.getZjhm());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsCerCancellateLicense.getWorkUnit());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsCerCancellateLicense.getPost());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //业务发生时间
                            omsOperatorHandoverSubform.setExitdate(omsCerCancellateLicense.getCreateTime());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsCerCancellateLicense.getZxsm());
                            omsOperatorHandoverSubformMapper.updateByPrimaryKeySelective(omsOperatorHandoverSubform);
                        }
                    }
                }

                /**因公出国境*/
                if (omsPubApplyVOS != null && omsPubApplyVOS.size()>0){
                    for (OmsPubApplyVO omsPubApplyVo:omsPubApplyVOS) {
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = omsOperatorHandoverSubformMapper.getAllDataByHandoverId(id,omsPubApplyVo.getId());
                        if (omsOperatorHandoverSubform == null){
                            // 查出数据后插入交接子表
                            omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                            //交接子表主键
                            omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                            //交接表主表主键
                            omsOperatorHandoverSubform.setHandoverformid(id);
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsPubApplyVo.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[1]);
                            //人员主键
                            omsOperatorHandoverSubform.setA0100(omsPubApplyVo.getA0100());
                            //姓名
                            omsOperatorHandoverSubform.setName(omsPubApplyVo.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsPubApplyVo.getSex());
                            //出生日期
                            omsOperatorHandoverSubform.setBirthday(omsPubApplyVo.getBirthDate());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsPubApplyVo.getIdnumber());
                            //政治面貌
                            omsOperatorHandoverSubform.setPoliticsstatus(omsPubApplyVo.getPoliticalAff());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsPubApplyVo.getB0101());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsPubApplyVo.getJob());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //出国（境）时间
                            omsOperatorHandoverSubform.setExitdate(omsPubApplyVo.getCgsj());
                            //入境时间
                            omsOperatorHandoverSubform.setEntrydate(omsPubApplyVo.getHgsj());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsPubApplyVo.getCfrw());
                            omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                        }else {
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsPubApplyVo.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[1]);
                            //人员主键
                            omsOperatorHandoverSubform.setA0100(omsPubApplyVo.getA0100());
                            //姓名
                            omsOperatorHandoverSubform.setName(omsPubApplyVo.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsPubApplyVo.getSex());
                            //出生日期
                            omsOperatorHandoverSubform.setBirthday(omsPubApplyVo.getBirthDate());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsPubApplyVo.getIdnumber());
                            //政治面貌
                            omsOperatorHandoverSubform.setPoliticsstatus(omsPubApplyVo.getPoliticalAff());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsPubApplyVo.getB0101());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsPubApplyVo.getJob());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //出国（境）时间
                            omsOperatorHandoverSubform.setExitdate(omsPubApplyVo.getCgsj());
                            //入境时间
                            omsOperatorHandoverSubform.setEntrydate(omsPubApplyVo.getHgsj());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsPubApplyVo.getCfrw());
                            omsOperatorHandoverSubformMapper.updateByPrimaryKeySelective(omsOperatorHandoverSubform);
                        }

                    }
                }

                /**因私出国境*/
                if (omsPriApplyVOS != null && omsPriApplyVOS.size()>0) {
                    for (OmsPriApplyVO omsPriApplyVO : omsPriApplyVOS) {
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = omsOperatorHandoverSubformMapper.getAllDataByHandoverId(id,omsPriApplyVO.getId());
                        if (omsOperatorHandoverSubform == null){
                            // 查出数据后插入交接子表
                            omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                            //交接子表主键
                            omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                            //交接表主表主键
                            omsOperatorHandoverSubform.setHandoverformid(id);
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsPriApplyVO.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[2]);
                            //人员主键
                            omsOperatorHandoverSubform.setA0100(omsPriApplyVO.getProcpersonId());
                            //姓名
                            omsOperatorHandoverSubform.setName(omsPriApplyVO.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsPriApplyVO.getSex());
                            //出生日期
                            omsOperatorHandoverSubform.setBirthday(omsPriApplyVO.getBirthDate());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsPriApplyVO.getIdnumber());
                            //政治面貌
                            omsOperatorHandoverSubform.setPoliticsstatus(omsPriApplyVO.getPoliticalOutlook());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsPriApplyVO.getB0101());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsPriApplyVO.getPostrank());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //出国（境）时间
                            omsOperatorHandoverSubform.setExitdate(omsPriApplyVO.getAbroadTime());
                            //入境时间
                            omsOperatorHandoverSubform.setEntrydate(omsPriApplyVO.getReturnTime());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsPriApplyVO.getAbroadReasons());
                            omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                        }else {
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsPriApplyVO.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[2]);
                            //人员主键
                            omsOperatorHandoverSubform.setA0100(omsPriApplyVO.getProcpersonId());
                            //姓名
                            omsOperatorHandoverSubform.setName(omsPriApplyVO.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsPriApplyVO.getSex());
                            //出生日期
                            omsOperatorHandoverSubform.setBirthday(omsPriApplyVO.getBirthDate());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsPriApplyVO.getIdnumber());
                            //政治面貌
                            omsOperatorHandoverSubform.setPoliticsstatus(omsPriApplyVO.getPoliticalOutlook());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsPriApplyVO.getB0101());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsPriApplyVO.getPostrank());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //出国（境）时间
                            omsOperatorHandoverSubform.setExitdate(omsPriApplyVO.getAbroadTime());
                            //入境时间
                            omsOperatorHandoverSubform.setEntrydate(omsPriApplyVO.getReturnTime());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsPriApplyVO.getAbroadReasons());
                            omsOperatorHandoverSubformMapper.updateByPrimaryKeySelective(omsOperatorHandoverSubform);
                        }

                    }
                }

                /**延期回国*/
                if (omsPriDelayVOS != null && omsPriDelayVOS.size()>0) {
                    for (OmsPriDelayVO omsPriDelayVO : omsPriDelayVOS) {
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = omsOperatorHandoverSubformMapper.getAllDataByHandoverId(id,omsPriDelayVO.getId());
                        if (omsOperatorHandoverSubform == null){
                            // 查出数据后插入交接子表
                            omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                            //交接子表主键
                            omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                            //交接表主表主键
                            omsOperatorHandoverSubform.setHandoverformid(id);
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsPriDelayVO.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[3]);
                            //人员主键
                            omsOperatorHandoverSubform.setA0100(omsPriDelayVO.getProcpersonId());
                            //姓名
                            omsOperatorHandoverSubform.setName(omsPriDelayVO.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsPriDelayVO.getSex());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsPriDelayVO.getIdnumber());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsPriDelayVO.getB0101());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsPriDelayVO.getPostrank());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //出国（境）时间
                            omsOperatorHandoverSubform.setExitdate(omsPriDelayVO.getApplyTime());
                            //入境时间
                            omsOperatorHandoverSubform.setEntrydate(omsPriDelayVO.getEstimateReturntime());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsPriDelayVO.getDelayReason());
                            omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                        }else {
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsPriDelayVO.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[3]);
                            //人员主键
                            omsOperatorHandoverSubform.setA0100(omsPriDelayVO.getProcpersonId());
                            //姓名
                            omsOperatorHandoverSubform.setName(omsPriDelayVO.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsPriDelayVO.getSex());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsPriDelayVO.getIdnumber());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsPriDelayVO.getB0101());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsPriDelayVO.getPostrank());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //出国（境）时间
                            omsOperatorHandoverSubform.setExitdate(omsPriDelayVO.getApplyTime());
                            //入境时间
                            omsOperatorHandoverSubform.setEntrydate(omsPriDelayVO.getEstimateReturntime());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsPriDelayVO.getDelayReason());
                            omsOperatorHandoverSubformMapper.updateByPrimaryKeySelective(omsOperatorHandoverSubform);
                        }
                    }
                }

                /**撤销登记备案*/
                if (omsRegRevokeapplies != null && omsRegRevokeapplies.size()>0) {
                    for (OmsRegRevokeapply omsRegRevokeapply : omsRegRevokeapplies) {
                        OmsOperatorHandoverSubform omsOperatorHandoverSubform = omsOperatorHandoverSubformMapper.getAllDataByHandoverId(id,omsRegRevokeapply.getId());
                        if (omsOperatorHandoverSubform == null){
                            // 查出数据后插入交接子表
                            omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                            //交接子表主键
                            omsOperatorHandoverSubform.setId(UUIDGenerator.getPrimaryKey());
                            //交接表主表主键
                            omsOperatorHandoverSubform.setHandoverformid(id);
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsRegRevokeapply.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[4]);
                            //人员主键
                            omsOperatorHandoverSubform.setA0100(omsRegRevokeapply.getA0100());
                            //姓名
                            omsOperatorHandoverSubform.setName(omsRegRevokeapply.getSurname()+omsRegRevokeapply.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsRegRevokeapply.getSex());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsRegRevokeapply.getIdnumberGb());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsRegRevokeapply.getWorkUnit());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsRegRevokeapply.getPost());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //业务发生时间
                            omsOperatorHandoverSubform.setExitdate(omsRegRevokeapply.getCreateDate());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsRegRevokeapply.getApplyReason());
                            omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
                        }else {
                            //业务主键
                            omsOperatorHandoverSubform.setBusinessid(omsRegRevokeapply.getId());
                            //业务类别
                            omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[4]);
                            //人员主键
                            omsOperatorHandoverSubform.setA0100(omsRegRevokeapply.getA0100());
                            //姓名
                            omsOperatorHandoverSubform.setName(omsRegRevokeapply.getSurname()+omsRegRevokeapply.getName());
                            //性别
                            omsOperatorHandoverSubform.setSex(omsRegRevokeapply.getSex());
                            //身份证号
                            omsOperatorHandoverSubform.setIdcard(omsRegRevokeapply.getIdnumberGb());
                            //工作单位
                            omsOperatorHandoverSubform.setCompany(omsRegRevokeapply.getWorkUnit());
                            //职务（级）
                            omsOperatorHandoverSubform.setDuty(omsRegRevokeapply.getPost());
                            //交接时间
                            omsOperatorHandoverSubform.setHandovertime(new Date());
                            //接手人ID
                            omsOperatorHandoverSubform.setHandoverid(handoverId);
                            //业务发生时间
                            omsOperatorHandoverSubform.setExitdate(omsRegRevokeapply.getCreateDate());
                            //说明
                            omsOperatorHandoverSubform.setSm(omsRegRevokeapply.getApplyReason());
                            omsOperatorHandoverSubformMapper.updateByPrimaryKeySelective(omsOperatorHandoverSubform);
                        }
                    }
                }
            }
        }
    }

    /**
     * 功能描述: <br>
     * 〈查询经办人经办业务列表〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/9/11 9:58
     */
    @Override
    public PageInfo getOperatorJBYW(OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam) {
        if (StringUtils.isBlank(omsOperatorJBYWQueryParam.getOperatorId())){
            throw new CustomMessageException("经办人ID为空!");
        }
        //分页
        PageUtil.pageHelp(omsOperatorJBYWQueryParam.getPageNum() == null ? 1 : omsOperatorJBYWQueryParam.getPageNum(),
                omsOperatorJBYWQueryParam.getPageSize() == null ? 10 : omsOperatorJBYWQueryParam.getPageSize());
        List<OmsOperatorJbywVO> omsOperatorJbywVOS = new ArrayList<>();
        //业务类别集合
        List<String> businessTypes = omsOperatorJBYWQueryParam.getBusinessType();
        /**证照领取*/
        List<OmsCerGetTaskVO> omsCerGetTasks = null;
        /**注销证照*/
        List<OmsCerCancellateLicense> omsCerCancellateLicenses = null;
        /**撤销登记备案*/
        List<OmsRegRevokeapply> omsRegRevokeapplies = null;
        /**因公出国境*/
        List<OmsPubApplyVO> omsPubApplyVOS = null;
        /**因私出国境*/
        List<OmsPriApplyVO> omsPriApplyVOS = null;
        /** 延期回国*/
        List<OmsPriDelayVO> omsPriDelayVOS = null;
        if (businessTypes == null || businessTypes.size() <= 0){
            //查询所有
            /**证照领取*/
            omsCerGetTasks = operatorHandoverMapper.selectOmsCerGetTaskByParam(omsOperatorJBYWQueryParam);

            /**注销证照*/
            omsCerCancellateLicenses = operatorHandoverMapper.selectCerCancellateLicenseByParam(omsOperatorJBYWQueryParam);

            /**撤销登记备案*/
            omsRegRevokeapplies = operatorHandoverMapper.selectOmsRegRevokeapplyByParam(omsOperatorJBYWQueryParam);

            /**因公出国境*/
            omsPubApplyVOS = omsPubApplyMapper.selectPubAllyByParam(omsOperatorJBYWQueryParam);

            /**因私出国境*/
            omsPriApplyVOS = operatorHandoverMapper.selectOmsPriApplyByParam(omsOperatorJBYWQueryParam);

            /** 延期回国*/
            omsPriDelayVOS = operatorHandoverMapper.selectOmsPriDelayApplyByParam(omsOperatorJBYWQueryParam);

        }else {
            //根据类型查找相关的数据
            for (String businessType:businessTypes) {
                if (businessType.equals(Constants.handover_type[0])){
                    /**证照领取*/
                    omsCerGetTasks = operatorHandoverMapper.selectOmsCerGetTaskByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[1])){
                    /**因公出国境*/
                    omsPubApplyVOS = omsPubApplyMapper.selectPubAllyByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[2])){
                    /**因私出国境*/
                    omsPriApplyVOS = operatorHandoverMapper.selectOmsPriApplyByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[3])){
                    /** 延期回国*/
                    omsPriDelayVOS = operatorHandoverMapper.selectOmsPriDelayApplyByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[4])){
                    /**撤销登记备案*/
                    omsRegRevokeapplies = operatorHandoverMapper.selectOmsRegRevokeapplyByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[5])){
                    /**注销证照*/
                    omsCerCancellateLicenses = operatorHandoverMapper.selectCerCancellateLicenseByParam(omsOperatorJBYWQueryParam);
                }
            }
        }
        /**证照领取*/
        if (omsCerGetTasks != null){
            for (OmsCerGetTaskVO omsCerGetTask : omsCerGetTasks) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType(Constants.handover_type[0]);
                //姓名
                omsOperatorJbywVO.setName(omsCerGetTask.getName());
                //性别
                omsOperatorJbywVO.setSex(omsCerGetTask.getSex());
                //工作单位
                omsOperatorJbywVO.setCompany(omsCerGetTask.getB0101());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsCerGetTask.getPostrank());
                //业务发生时间
                omsOperatorJbywVO.setStartTime(omsCerGetTask.getGetTime());
                //业务结束时间
                omsOperatorJbywVO.setEndTime(omsCerGetTask.getEndTime());
                //说明
                omsOperatorJbywVO.setSm(omsCerGetTask.getRemarks());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /**因公出国境*/
        if (omsPubApplyVOS != null){
            for (OmsPubApplyVO omsPubApplyVo:omsPubApplyVOS) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType(Constants.handover_type[1]);
                //姓名
                omsOperatorJbywVO.setName(omsPubApplyVo.getName());
                //性别
                omsOperatorJbywVO.setSex(omsPubApplyVo.getSex());
                //工作单位
                omsOperatorJbywVO.setCompany(omsPubApplyVo.getB0101());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsPubApplyVo.getJob());
                //出国（境）时间
                omsOperatorJbywVO.setStartTime(omsPubApplyVo.getCgsj());
                //入境时间
                omsOperatorJbywVO.setEndTime(omsPubApplyVo.getHgsj());
                //说明
                omsOperatorJbywVO.setSm(omsPubApplyVo.getCfrw());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /**因私出国境*/
        if (omsPriApplyVOS != null){
            for (OmsPriApplyVO omsPriApplyVO:omsPriApplyVOS) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType(Constants.handover_type[2]);
                //姓名
                omsOperatorJbywVO.setName(omsPriApplyVO.getName());
                //性别
                omsOperatorJbywVO.setSex(omsPriApplyVO.getSex());
                //工作单位
                omsOperatorJbywVO.setCompany(omsPriApplyVO.getB0101());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsPriApplyVO.getPostrank());
                //出国（境）时间
                omsOperatorJbywVO.setStartTime(omsPriApplyVO.getAbroadTime());
                //入境时间
                omsOperatorJbywVO.setEndTime(omsPriApplyVO.getReturnTime());
                //说明
                omsOperatorJbywVO.setSm(omsPriApplyVO.getAbroadReasons());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /** 延期回国*/
        if (omsPriDelayVOS != null){
            for (OmsPriDelayVO omsPriDelayVO:omsPriDelayVOS) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType(Constants.handover_type[3]);
                //姓名
                omsOperatorJbywVO.setName(omsPriDelayVO.getName());
                //性别
                omsOperatorJbywVO.setSex(omsPriDelayVO.getSex());
                //工作单位
                omsOperatorJbywVO.setCompany(omsPriDelayVO.getB0101());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsPriDelayVO.getPostrank());
                //出国（境）时间
                omsOperatorJbywVO.setStartTime(omsPriDelayVO.getApplyTime());
                //入境时间
                omsOperatorJbywVO.setEndTime(omsPriDelayVO.getEstimateReturntime());
                //说明
                omsOperatorJbywVO.setSm(omsPriDelayVO.getDelayReason());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /**撤销登记备案*/
        if (omsRegRevokeapplies != null){
            for (OmsRegRevokeapply omsRegRevokeapply : omsRegRevokeapplies) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType(Constants.handover_type[4]);
                //姓名
                omsOperatorJbywVO.setName(omsRegRevokeapply.getSurname()+omsRegRevokeapply.getName());
                //性别
                omsOperatorJbywVO.setSex(omsRegRevokeapply.getSex());
                //工作单位
                omsOperatorJbywVO.setCompany(omsRegRevokeapply.getWorkUnit());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsRegRevokeapply.getPost());
                //业务发生时间
                omsOperatorJbywVO.setStartTime(omsRegRevokeapply.getCreateDate());
                //业务结束时间

                //说明
                omsOperatorJbywVO.setSm(omsRegRevokeapply.getApplyReason());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /**注销证照*/
        if (omsCerCancellateLicenses != null){
            for (OmsCerCancellateLicense omsCerCancellateLicense : omsCerCancellateLicenses) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType(Constants.handover_type[5]);
                //姓名
                omsOperatorJbywVO.setName(omsCerCancellateLicense.getName());
                //性别
                omsOperatorJbywVO.setSex(omsCerCancellateLicense.getSex());
                //工作单位
                omsOperatorJbywVO.setCompany(omsCerCancellateLicense.getWorkUnit());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsCerCancellateLicense.getPost());
                //业务发生时间
                omsOperatorJbywVO.setStartTime(omsCerCancellateLicense.getCreateTime());
                //业务结束时间

                //说明
                omsOperatorJbywVO.setSm(omsCerCancellateLicense.getZxsm());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }


        PageInfo info = new PageInfo(omsOperatorJbywVOS);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈通过条件查询经办人交接记录〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/9/11 16:47
     */
    @Override
    public PageInfo getOperatorWBJYW(OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam) {
        if (StringUtils.isBlank(omsOperatorJBYWQueryParam.getOperatorId())){
            throw new CustomMessageException("经办人ID为空!");
        }
        //分页
        PageUtil.pageHelp(omsOperatorJBYWQueryParam.getPageNum() == null ? 1 : omsOperatorJBYWQueryParam.getPageNum(),
                omsOperatorJBYWQueryParam.getPageSize() == null ? 10 : omsOperatorJBYWQueryParam.getPageSize());
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS = operatorHandoverMapper.getOperatorWBJYW(omsOperatorJBYWQueryParam);
        PageInfo info = new PageInfo(omsOperatorHandoverSubformVOS);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈经办人经办业务导出〉
     * @Param: [omsOperatorJBYWQueryParam, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/9/12 9:51
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void exportOperatorByJBYW(OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam, HttpServletResponse response) {
        if (StringUtils.isBlank(omsOperatorJBYWQueryParam.getOperatorId())){
            throw new CustomMessageException("经办人ID为空!");
        }
        List<OmsOperatorJbywVO> omsOperatorJbywVOS = new ArrayList<>();
        //业务类别集合
        List<String> businessTypes = omsOperatorJBYWQueryParam.getBusinessType();
        /**证照领取*/
        List<OmsCerGetTaskVO> omsCerGetTasks = null;
        /**注销证照*/
        List<OmsCerCancellateLicense> omsCerCancellateLicenses = null;
        /**撤销登记备案*/
        List<OmsRegRevokeapply> omsRegRevokeapplies = null;
        /**因公出国境*/
        List<OmsPubApplyVO> omsPubApplyVOS = null;
        /**因私出国境*/
        List<OmsPriApplyVO> omsPriApplyVOS = null;
        /** 延期回国*/
        List<OmsPriDelayVO> omsPriDelayVOS = null;
        if (businessTypes == null || businessTypes.size() <= 0 ){
            //查询所有
            /**证照领取*/
            omsCerGetTasks = operatorHandoverMapper.selectOmsCerGetTaskByParam(omsOperatorJBYWQueryParam);

            /**注销证照*/
            omsCerCancellateLicenses = operatorHandoverMapper.selectCerCancellateLicenseByParam(omsOperatorJBYWQueryParam);

            /**撤销登记备案*/
            omsRegRevokeapplies = operatorHandoverMapper.selectOmsRegRevokeapplyByParam(omsOperatorJBYWQueryParam);

            /**因公出国境*/
            omsPubApplyVOS = omsPubApplyMapper.selectPubAllyByParam(omsOperatorJBYWQueryParam);

            /**因私出国境*/
            omsPriApplyVOS = operatorHandoverMapper.selectOmsPriApplyByParam(omsOperatorJBYWQueryParam);

            /** 延期回国*/
            omsPriDelayVOS = operatorHandoverMapper.selectOmsPriDelayApplyByParam(omsOperatorJBYWQueryParam);

        }else {
            //根据类型查找相关的数据
            for (String businessType:businessTypes) {
                if (businessType.equals(Constants.handover_type[0])){
                    /**证照领取*/
                    omsCerGetTasks = operatorHandoverMapper.selectOmsCerGetTaskByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[1])){
                    /**因公出国境*/
                    omsPubApplyVOS = omsPubApplyMapper.selectPubAllyByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[2])){
                    /**因私出国境*/
                    omsPriApplyVOS = operatorHandoverMapper.selectOmsPriApplyByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[3])){
                    /** 延期回国*/
                    omsPriDelayVOS = operatorHandoverMapper.selectOmsPriDelayApplyByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[4])){
                    /**撤销登记备案*/
                    omsRegRevokeapplies = operatorHandoverMapper.selectOmsRegRevokeapplyByParam(omsOperatorJBYWQueryParam);
                }
                if (businessType.equals(Constants.handover_type[5])){
                    /**注销证照*/
                    omsCerCancellateLicenses = operatorHandoverMapper.selectCerCancellateLicenseByParam(omsOperatorJBYWQueryParam);
                }
            }
        }
        /**证照领取*/
        if (omsCerGetTasks != null || omsCerGetTasks.size() <= 0){
            for (OmsCerGetTaskVO omsCerGetTask : omsCerGetTasks) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType("证照领取");
                //姓名
                omsOperatorJbywVO.setName(omsCerGetTask.getName());
                //性别
                if ("1".equals(omsCerGetTask.getSex())){
                    omsOperatorJbywVO.setSex("男");
                }else if ("2".equals(omsCerGetTask.getSex())){
                    omsOperatorJbywVO.setSex("女");
                }

                //工作单位
                omsOperatorJbywVO.setCompany(omsCerGetTask.getB0101());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsCerGetTask.getPostrank());
                //业务发生时间
                omsOperatorJbywVO.setStartTime(omsCerGetTask.getGetTime());
                //业务结束时间
                omsOperatorJbywVO.setEndTime(omsCerGetTask.getEndTime());
                //说明
                omsOperatorJbywVO.setSm(omsCerGetTask.getRemarks());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /**因公出国境*/
        if (omsPubApplyVOS != null){
            for (OmsPubApplyVO omsPubApplyVo:omsPubApplyVOS) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType("因公出国境");
                //姓名
                omsOperatorJbywVO.setName(omsPubApplyVo.getName());
                //性别
                if ("1".equals(omsPubApplyVo.getSex())){
                    omsOperatorJbywVO.setSex("男");
                }else if ("2".equals(omsPubApplyVo.getSex())){
                    omsOperatorJbywVO.setSex("女");
                }
                //工作单位
                omsOperatorJbywVO.setCompany(omsPubApplyVo.getB0101());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsPubApplyVo.getJob());
                //出国（境）时间
                omsOperatorJbywVO.setStartTime(omsPubApplyVo.getCgsj());
                //入境时间
                omsOperatorJbywVO.setEndTime(omsPubApplyVo.getHgsj());
                //说明
                omsOperatorJbywVO.setSm(omsPubApplyVo.getCfrw());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /**因私出国境*/
        if (omsPriApplyVOS != null){
            for (OmsPriApplyVO omsPriApplyVO:omsPriApplyVOS) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType("因私出国境");
                //姓名
                omsOperatorJbywVO.setName(omsPriApplyVO.getName());
                //性别
                if ("1".equals(omsPriApplyVO.getSex())){
                    omsOperatorJbywVO.setSex("男");
                }else if ("2".equals(omsPriApplyVO.getSex())){
                    omsOperatorJbywVO.setSex("女");
                }
                //工作单位
                omsOperatorJbywVO.setCompany(omsPriApplyVO.getB0101());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsPriApplyVO.getPostrank());
                //出国（境）时间
                omsOperatorJbywVO.setStartTime(omsPriApplyVO.getAbroadTime());
                //入境时间
                omsOperatorJbywVO.setEndTime(omsPriApplyVO.getReturnTime());
                //说明
                omsOperatorJbywVO.setSm(omsPriApplyVO.getAbroadReasons());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /** 延期回国*/
        if (omsPriDelayVOS != null){
            for (OmsPriDelayVO omsPriDelayVO:omsPriDelayVOS) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType("延期回国");
                //姓名
                omsOperatorJbywVO.setName(omsPriDelayVO.getName());
                //性别
                if ("1".equals(omsPriDelayVO.getSex())){
                    omsOperatorJbywVO.setSex("男");
                }else if ("2".equals(omsPriDelayVO.getSex())){
                    omsOperatorJbywVO.setSex("女");
                }
                //工作单位
                omsOperatorJbywVO.setCompany(omsPriDelayVO.getB0101());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsPriDelayVO.getPostrank());
                //出国（境）时间
                omsOperatorJbywVO.setStartTime(omsPriDelayVO.getApplyTime());
                //入境时间
                omsOperatorJbywVO.setEndTime(omsPriDelayVO.getEstimateReturntime());
                //说明
                omsOperatorJbywVO.setSm(omsPriDelayVO.getDelayReason());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /**撤销登记备案*/
        if (omsRegRevokeapplies != null){
            for (OmsRegRevokeapply omsRegRevokeapply : omsRegRevokeapplies) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType("撤销登记备案");
                //姓名
                omsOperatorJbywVO.setName(omsRegRevokeapply.getSurname()+omsRegRevokeapply.getName());
                //性别
                if ("1".equals(omsRegRevokeapply.getSex())){
                    omsOperatorJbywVO.setSex("男");
                }else if ("2".equals(omsRegRevokeapply.getSex())){
                    omsOperatorJbywVO.setSex("女");
                }
                //工作单位
                omsOperatorJbywVO.setCompany(omsRegRevokeapply.getWorkUnit());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsRegRevokeapply.getPost());
                //业务发生时间
                omsOperatorJbywVO.setStartTime(omsRegRevokeapply.getCreateDate());
                //业务结束时间

                //说明
                omsOperatorJbywVO.setSm(omsRegRevokeapply.getApplyReason());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        /**注销证照*/
        if (omsCerCancellateLicenses != null){
            for (OmsCerCancellateLicense omsCerCancellateLicense : omsCerCancellateLicenses) {
                OmsOperatorJbywVO omsOperatorJbywVO = new OmsOperatorJbywVO();
                //业务类别
                omsOperatorJbywVO.setBusinessType("注销证照");
                //姓名
                omsOperatorJbywVO.setName(omsCerCancellateLicense.getName());
                //性别
                if ("1".equals(omsCerCancellateLicense.getSex())){
                    omsOperatorJbywVO.setSex("男");
                }else if ("2".equals(omsCerCancellateLicense.getSex())){
                    omsOperatorJbywVO.setSex("女");
                }
                //工作单位
                omsOperatorJbywVO.setCompany(omsCerCancellateLicense.getWorkUnit());
                //职务（级）
                omsOperatorJbywVO.setDuty(omsCerCancellateLicense.getPost());
                //业务发生时间
                omsOperatorJbywVO.setStartTime(omsCerCancellateLicense.getCreateTime());
                //业务结束时间

                //说明
                omsOperatorJbywVO.setSm(omsCerCancellateLicense.getZxsm());
                omsOperatorJbywVOS.add(omsOperatorJbywVO);
            }
        }
        if (omsOperatorJbywVOS == null || omsOperatorJbywVOS.size() < 1){
            throw new CustomMessageException("操作失败");
        }else {
            /** 开始导出 */
            //创建HSSFWorkbook对象(excel的文档对象)
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建文件样式对象
            HSSFCellStyle style = wb.createCellStyle();
            //获得字体对象
            HSSFFont font = wb.createFont();
            //建立新的sheet对象（excel的表单）
            HSSFSheet sheet=wb.createSheet("经办人经办业务记录");
            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row1=sheet.createRow(0);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell=row1.createCell(0);
            //设置标题字体大小
            font.setFontHeightInPoints((short) 16);
            //加粗
            font.setBold(true);
            // 左右居中 
            style.setAlignment(HorizontalAlignment.CENTER);
            // 上下居中 
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFont(font);
            cell.setCellStyle(style);
            //设置标题单元格内容
            cell.setCellValue("经办人经办业务记录");
            //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,8));
            //在sheet里创建第二行
            HSSFRow row2=sheet.createRow(2);

            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("序号");
            row2.createCell(1).setCellValue("业务类别");
            row2.createCell(2).setCellValue("单位");
            row2.createCell(3).setCellValue("姓名");
            row2.createCell(4).setCellValue("性别");
            row2.createCell(5).setCellValue("职务（级）");
            row2.createCell(6).setCellValue("业务开始时间");
            row2.createCell(7).setCellValue("业务结束时间");
            row2.createCell(8).setCellValue("说明");
            //在sheet里添加数据
            //创建文件样式对象
            HSSFCellStyle style1 = wb.createCellStyle();

            //获得字体对象
            HSSFFont font1 = wb.createFont();
            //设置单元格字体大小
            font1.setFontHeightInPoints((short) 12);
            //居左
            style1.setAlignment(HorizontalAlignment.LEFT);
            style1.setFont(font1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            HSSFRow row = null;
            for(int i = 0; i < omsOperatorJbywVOS.size(); i++){
                row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(omsOperatorJbywVOS.get(i).getBusinessType());
                row.createCell(2).setCellValue(omsOperatorJbywVOS.get(i).getCompany());
                row.createCell(3).setCellValue(omsOperatorJbywVOS.get(i).getName());
                row.createCell(4).setCellValue(omsOperatorJbywVOS.get(i).getSex());
                row.createCell(5).setCellValue(omsOperatorJbywVOS.get(i).getDuty());
                Date startTime = omsOperatorJbywVOS.get(i).getStartTime();
                if (startTime != null){
                    row.createCell(6).setCellValue(sdf.format(startTime));
                }else {
                    row.createCell(6).setCellValue("");
                }
                Date endTime = omsOperatorJbywVOS.get(i).getEndTime();
                if (endTime != null){
                    row.createCell(7).setCellValue(sdf.format(endTime));
                }else {
                    row.createCell(7).setCellValue("");
                }
                row.createCell(8).setCellValue(omsOperatorJbywVOS.get(i).getSm());
                //设置单元格字体大小
                for(int j = 0;j < 9;j++){
                    row.getCell(j).setCellStyle(style1);
                }
            }
            //输出Excel文件
            OutputStream output= null;
            try {
                output = response.getOutputStream();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "utf-8");

                wb.write(output);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 功能描述: <br>
     * 〈经办人交接记录导出〉
     * @Param: [omsOperatorJBYWQueryParam, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/9/12 9:53
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void exportOperatorByJJJL(OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam, HttpServletResponse response) {
        if (StringUtils.isBlank(omsOperatorJBYWQueryParam.getOperatorId())){
            throw new CustomMessageException("经办人ID为空!");
        }
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS = operatorHandoverMapper.getOperatorWBJYW(omsOperatorJBYWQueryParam);

        if (omsOperatorHandoverSubformVOS == null || omsOperatorHandoverSubformVOS.size() < 1){
            throw new CustomMessageException("操作失败");
        }else {
            /** 开始导出 */
            //创建HSSFWorkbook对象(excel的文档对象)
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建文件样式对象
            HSSFCellStyle style = wb.createCellStyle();
            //获得字体对象
            HSSFFont font = wb.createFont();
            //建立新的sheet对象（excel的表单）
            HSSFSheet sheet=wb.createSheet("经办人交接记录");
            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row1=sheet.createRow(0);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell=row1.createCell(0);
            //设置标题字体大小
            font.setFontHeightInPoints((short) 16);
            //加粗
            font.setBold(true);
            // 左右居中 
            style.setAlignment(HorizontalAlignment.CENTER);
            // 上下居中 
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFont(font);
            cell.setCellStyle(style);
            //设置标题单元格内容
            cell.setCellValue("经办人交接记录");

            //副标题样式
            HSSFCellStyle style1 = wb.createCellStyle();
            HSSFFont font1 = wb.createFont();
            //字体加粗
            font1.setBold(false);
            //设置字体的大小
            font1.setFontHeightInPoints((short)12);
            //设置字体
            font1.setFontName("黑体");
            style1.setFont(font1);
            //在sheet里创建第二行
            HSSFRow row2=sheet.createRow(2);
            HSSFCell cell1 = row2.createCell(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cell1.setCellValue("交接时间："+sdf.format(omsOperatorHandoverSubformVOS.get(0).getJjTime())+"       接手人："+omsOperatorHandoverSubformVOS.get(0).getHandoverName());
            cell1.setCellStyle(style1);

            //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,9));
            sheet.addMergedRegion(new CellRangeAddress(2,2,0,9));
            //在sheet里创建第三行
            HSSFRow row3=sheet.createRow(3);
            //创建单元格并设置单元格内容
            row3.createCell(0).setCellValue("序号");
            row3.createCell(1).setCellValue("单位");
            row3.createCell(2).setCellValue("姓名");
            row3.createCell(3).setCellValue("性别");
            row3.createCell(4).setCellValue("职务（级）");
            row3.createCell(5).setCellValue("业务类别");
            row3.createCell(6).setCellValue("业务开始时间");
            row3.createCell(7).setCellValue("业务结束时间");
            row3.createCell(8).setCellValue("说明");
            row3.createCell(9).setCellValue("备注");
            //在sheet里添加数据

            //创建文件样式对象
            HSSFCellStyle style2 = wb.createCellStyle();
            //获得字体对象
            HSSFFont font2 = wb.createFont();
            //设置单元格字体大小
            font2.setFontHeightInPoints((short) 12);
            //居左
            style2.setAlignment(HorizontalAlignment.LEFT);
            style2.setFont(font2);

            HSSFRow row = null;
            for(int i = 0; i < omsOperatorHandoverSubformVOS.size(); i++){
                row = sheet.createRow(i + 4);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(omsOperatorHandoverSubformVOS.get(i).getCompany());
                row.createCell(2).setCellValue(omsOperatorHandoverSubformVOS.get(i).getName());
                if ("1".equals(omsOperatorHandoverSubformVOS.get(i).getSex())){
                    row.createCell(3).setCellValue("男");
                }else if ("2".equals(omsOperatorHandoverSubformVOS.get(i).getSex())){
                    row.createCell(3).setCellValue("女");
                }else {
                    row.createCell(3).setCellValue("");
                }

                row.createCell(4).setCellValue(omsOperatorHandoverSubformVOS.get(i).getDuty());
                String businesstype = omsOperatorHandoverSubformVOS.get(i).getBusinesstype();
                if (businesstype.equals(Constants.handover_type[0])){
                    row.createCell(5).setCellValue("证照领取");

                }else if (businesstype.equals(Constants.handover_type[1])){
                    row.createCell(5).setCellValue("因公出国");

                }else if (businesstype.equals(Constants.handover_type[2])){
                    row.createCell(5).setCellValue("因私出国");

                }else if (businesstype.equals(Constants.handover_type[3])){
                    row.createCell(5).setCellValue("延期回国");

                }else if (businesstype.equals(Constants.handover_type[4])){
                    row.createCell(5).setCellValue("撤销登记备案");

                }else if (businesstype.equals(Constants.handover_type[5])){
                    row.createCell(5).setCellValue("注销证照");

                }
                Date exitdate = omsOperatorHandoverSubformVOS.get(i).getExitdate();
                if (exitdate != null){
                    row.createCell(6).setCellValue(sdf.format(exitdate));
                }else {
                    row.createCell(6).setCellValue("");
                }
                Date entrydate = omsOperatorHandoverSubformVOS.get(i).getEntrydate();
                if (entrydate != null){
                    row.createCell(7).setCellValue(sdf.format(entrydate));
                }else {
                    row.createCell(7).setCellValue("");
                }
                row.createCell(8).setCellValue(omsOperatorHandoverSubformVOS.get(i).getSm());
                row.createCell(9).setCellValue(omsOperatorHandoverSubformVOS.get(i).getBz());
                //设置单元格字体大小
                for(int j = 0;j < 9;j++){
                    row.getCell(j).setCellStyle(style2);
                }
            }

            //输出Excel文件
            OutputStream output= null;
            try {
                output = response.getOutputStream();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "utf-8");

                wb.write(output);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 功能描述: <br>
     * 〈管理人员确定列表〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO>
     * @Author: 李逍遥
     * @Date: 2020/7/13 15:24
     */
    @Override
    public List<OmsOperatorHandoverSubformVO> getAllOperatorHandover() {
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        //通过机构id查询
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS = operatorHandoverMapper.getAllOperatorHandoverByOrgid(loginUser.getOrgId());
        return omsOperatorHandoverSubformVOS;
    }

    /**
     * 功能描述: <br>
     * 〈确认交接完成〉
     * @Param: [handoverformid]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/13 17:43
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void confirmDelivery(String handoverformid) {
        if (StringUtils.isBlank(handoverformid)){
            throw new CustomMessageException("参数为空!");
        }
        //获取当前登录人
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        OmsOperatorHandover omsOperatorHandover = operatorHandoverMapper.selectByPrimaryKey(handoverformid);
        //更改状态为完成
        omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[1]));
        operatorHandoverMapper.updateByPrimaryKeySelective(omsOperatorHandover);
        //更改经办人状态为撤销
        CfUser user = cfUserMapper.selectByPrimaryKey(omsOperatorHandover.getOperatorid());
        user.setUserState(Constants.USER_STATUS[2]);
        user.setModifyUser(loginUser.getId());
        user.setModifyTime(new Date());
        cfUserMapper.updateByPrimaryKeySelective(user);

    }

    /**
     * 功能描述: <br>
     * 〈中止交接〉
     * @Param: [handoverformid]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/13 18:02
     */
    @Override
    public void suspendHandover(String handoverformid) {
        if (StringUtils.isBlank(handoverformid)){
            throw new CustomMessageException("参数为空!");
        }
        //获取当前登录人
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        OmsOperatorHandover omsOperatorHandover = operatorHandoverMapper.selectByPrimaryKey(handoverformid);
        //更改状态为撤销
        omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[2]));
        operatorHandoverMapper.updateByPrimaryKeySelective(omsOperatorHandover);
        operatorHandoverMapper.deleteByPrimaryKey(omsOperatorHandover.getId());
        omsOperatorHandoverSubformMapper.deleteByPrimaryKey(omsOperatorHandover.getId());
        //更改经办人状态
        CfUser user = cfUserMapper.selectByPrimaryKey(omsOperatorHandover.getOperatorid());
       //将该经办人状态更改为正常
        user.setUserState(Constants.USER_STATUS[1]);
        user.setModifyUser(loginUser.getId());
        user.setModifyTime(new Date());
        cfUserMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 功能描述: <br>
     * 〈校验经办人〉
     * @Param: [user]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/7 14:32
     */
    private void verify(CfUser user) {

        //1、创建经办人时，要判断登录名是否重复，并且在在非撤消和拒绝状态
        CfUser selectUser = cfUserMapper.selectByUserCode(user.getUserCode());
        if(selectUser != null){
            //判断状态
            String userState = selectUser.getUserState();
            if (!userState.equals(Constants.USER_STATUS[2]) & !userState.equals(Constants.USER_STATUS[5])){
                throw new CustomMessageException("登录账号已经存在!");
            }
        }
        //2、一个机构下只允许有2个经办人,用户类型为 6经办人
        ArrayList<String> orgId = new ArrayList<>();
        orgId.add(user.getOrgId());
        List<CfUser> sysUserList = cfUserMapper.getSysUserListByParm(orgId,Constants.USER_TYPES[6],Constants.USER_STATUS[1]);
        if (sysUserList.size() >= 2){
            throw new CustomMessageException("您单位已有2名经办人，如须增加，请单独向干部监督处申请!");
        }
        ///3、检测该经办人是否在别的单位也注册成为了激活状态的经办人身份，如果有，提醒管理员：
        // “经办人XXX在XXX单位也是经办人，并处于正常办理业务状态，在XXX单位撤消他的经办人身份前，贵单位不能将其注册为经办人”
        //通过身份证号、用户类型、状态查找经办人
        CfUser operator = cfUserMapper.getOperatorByIdnumAndState(user.getIdnumber(),Constants.USER_TYPES[6],Constants.USER_STATUS[1]);
        if (operator != null && !user.getOrgId().equals(operator.getOrgId())){
            throw new CustomMessageException("经办人"+operator.getUserName()+"在"+operator.getOrgName()+"单位也是经办人，并处于正常办理业务状态，在"+operator.getOrgName()+"单位撤消他的经办人身份前，贵单位不能将其注册为经办人!");
        }
    }
}
