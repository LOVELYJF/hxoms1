package com.hxoms.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.file.OmsFileUtils;
import com.hxoms.common.utils.*;
import com.hxoms.modules.file.entity.FileReplaceVO;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.OmsReplaceKeywords;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.file.mapper.OmsReplaceKeywordsMapper;
import com.hxoms.modules.file.service.OmsCreateFileService;
import com.hxoms.modules.file.service.OmsFileService;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.service.OmsPriDelayApplyService;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.entity.OtherPubApply;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.service.OmsPubApplyService;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.mapper.B01Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsFileServiceImpl implements OmsFileService {

    @Autowired
    private OmsFileUtils omsFileUtils;
    @Autowired
    private OmsFileMapper omsFileMapper;
    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;
    @Autowired
    private OmsReplaceKeywordsMapper omsReplaceKeywordsMapper;
    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;
    @Autowired
    private OmsPubApplyService omsPubApplyService;
    @Autowired
    private OmsPriDelayApplyService omsPriDelayApplyService;
    @Autowired
    private OmsCreateFileMapper omsCreateFileMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersoninfoMapper;
    @Autowired
    private B01Mapper b01Mapper;
    @Autowired
    private OmsCreateFileService omsCreateFileService;
    @Autowired
    private OmsSmrOldInfoMapper omsSmrOldInfoMapper;


    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public List<OmsFile> selectFileListByCode(String tableCode, String procpersonId, String applyId) {
        if (StringUtils.isBlank(tableCode) || StringUtils.isBlank(procpersonId)){
            throw new CustomMessageException("参数错误");
        }
        List<String> fileType = new ArrayList<>();
        fileType.add("1"); //系统
        if(Constants.oms_business[1].equals(tableCode)){
            //涉密信息
            OmsRegProcpersoninfo omsRegProcpersoninfo = omsRegProcpersoninfoMapper.selectById(procpersonId);
            if ("0".equals(omsRegProcpersoninfo.getSecretLevel())){
                //非涉密
                fileType.add("2");
            }else{
                fileType.add("3");
                //涉密人员（原单位脱密期人员）
                QueryWrapper<OmsSmrOldInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("A0100", procpersonId)
                        .ne("B0100", omsRegProcpersoninfo.getRfB0000());
                int omsS = omsSmrOldInfoMapper.selectCount(queryWrapper);
                if (omsS > 0){
                    fileType.add("4");
                }
            }
            //是否主要领导
            if ("1".equals(omsRegProcpersoninfo.getMainLeader())){
                fileType.add("5"); //主要领导
            }
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());



//        //测试用的单位主键
//        UserInfo userInfo = UserInfoUtil.getUserInfo();
//        userInfo.setId("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        B01 b01 = new B01();
//        b01.setB0100("cd3ffb59-d5ba-1038-bdaa-c2ae22a0bcce");




        if (b01 == null){
            throw new CustomMessageException("数据异常");
        }
        QueryWrapper<OmsFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TABLE_CODE", tableCode)
                .eq("B0100", b01.getB0100())
                .in("FILE_TYPE",fileType)
                .orderByAsc("SORT_ID");
        List<OmsFile> omsFiles = omsFileMapper.selectList(queryWrapper);
        if (omsFiles == null || omsFiles.size() < 1) {
            //初始化机构文件
            queryWrapper.clear();
            queryWrapper.eq("TABLE_CODE", tableCode)
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
                    int count = omsFileMapper.insert(omsfile);
                    if(count < 1){
                        throw new CustomMessageException("插入新的文件信息出错");
                    }
                }
                //复制文件
                if (Constants.oms_business[1].equals(tableCode)){
                    //因私出国
                    omsFileUtils.copyFolder("yinsichuguo", "yinsichuguo" + File.separator + b01.getB0100());
                } else if(Constants.oms_business[2].equals(tableCode)){
                    //延期回国
                    omsFileUtils.copyFolder("yanqihuiguo", "yanqihuiguo" + File.separator + b01.getB0100());
                }
            }
            //重新查询
            queryWrapper.clear();
            queryWrapper.eq("TABLE_CODE", tableCode)
                    .eq("B0100", b01.getB0100())
                    .in("FILE_TYPE",fileType)
                    .orderByAsc("SORT_ID");
            omsFiles = omsFileMapper.selectList(queryWrapper);
        }
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
                    replaceFile(omsFile, applyId, tableCode);
//                    if ("因公临时出国（境）人员备案表".equals(omsFile.getFileShortname())&& omsFile.getTableCode().equals(Constants.oms_business[0])){
//                        String frontContent = getFrontContent(applyId,"因公临时出国（境）人员备案表");
//                        omsFile.setFrontContent(frontContent);
//                    }else if ("近三年出国（境）记录表".equals(omsFile.getFileShortname())&& omsFile.getTableCode().equals(Constants.oms_business[0])){
//
//                    }


                    omsCreateFile.setFrontContent(omsFile.getFrontContent());
                    omsCreateFile.setBankContent(omsFile.getBankContent());
                    omsCreateFileService.insertOrUpdate(omsCreateFile);
                }
            }
        }
        return omsFiles;
    }

    private String getFrontContent(String applyId, String fileName) {
        //获取近三年该干部因公出国境记录
        //获取近三年该干部因私出国境记录

        OmsPubApplyQueryParam omsPubApplyQueryParam = new OmsPubApplyQueryParam();
        omsPubApplyQueryParam.setApplyId(applyId);
        List<OmsPubApplyVO> pubAppListByCondition = omsPubApplyMapper.getPubAppListByCondition(omsPubApplyQueryParam);
        OmsPubApplyVO omsPubApplyVO = pubAppListByCondition.get(0);
        StringBuffer stringBuffer = new StringBuffer();
        if ("因公临时出国（境）人员备案表".equals(fileName)){
            if ("1".equals(omsPubApplyVO.getSex())){
                omsPubApplyVO.setSex("男");
            }else if ("2".equals(omsPubApplyVO.getSex())){
                omsPubApplyVO.setSex("女");
            }
            String secret_level = omsPubApplyVO.getSECRET_LEVEL();
            if (!StringUtils.isBlank(secret_level) && !"0".equals(secret_level)){
                omsPubApplyVO.setSfsmry("是");
            }else {
                omsPubApplyVO.setSfsmry("否");
            }
            if ("0".equals(secret_level)){
                omsPubApplyVO.setSmdj("非涉密人员");
            }else if ("1".equals(secret_level)){
                omsPubApplyVO.setSmdj("一般涉密人员");
            }else if ("2".equals(secret_level)){
                omsPubApplyVO.setSmdj("重要涉密人员");
            }else if ("3".equals(secret_level)){
                omsPubApplyVO.setSmdj("核心涉密人员");
            }
            OtherPubApply otherPubApply = omsPubApplyService.getOtherPubApply(omsPubApplyVO.getB0100(), omsPubApplyVO.getA0100(), omsPubApplyVO.getCgsj());
            List<OmsSmrOldInfoVO> omsSmrOldInfoVOS = otherPubApply.getOmsSmrOldInfoVOS();

            for (OmsSmrOldInfoVO o:omsSmrOldInfoVOS) {
                if ("0".equals(o.getSecretRelatedLevel())){
                    o.setSecretRelatedLevel("非涉密人员");
                }else if ("1".equals(o.getSecretRelatedLevel())){
                    o.setSecretRelatedLevel("一般涉密人员");
                }else if ("2".equals(o.getSecretRelatedLevel())){
                    o.setSecretRelatedLevel("重要涉密人员");
                }else if ("3".equals(o.getSecretRelatedLevel())){
                    o.setSecretRelatedLevel("核心涉密人员");
                }

            }
            stringBuffer.append("<table cellpadding=\"0\" cellspacing=\"0\">\n");
            stringBuffer.append("        <colgroup>\n");
            stringBuffer.append("            <col width=\"72\" style=\"width:72px\" span=\"5\"/>\n");
            stringBuffer.append("            <col width=\"82\" style=\"width:82px\"/>\n" );
            stringBuffer.append("            <col width=\"72\" style=\"width:72px\" span=\"3\"/>\n");
            stringBuffer.append("            <col width=\"142\" style=\"width:142px\"/>\n");
            stringBuffer.append("        </colgroup>\n");
            stringBuffer.append("        <tbody>\n");
            stringBuffer.append("        <tr style=\"height:54px\" class=\"firstRow\">\n");
            stringBuffer.append("            <td colspan=\"10\" width=\"874\" style=\"\">\n");
            stringBuffer.append("                <span style=\"font-size: 20px;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span>\n");
            stringBuffer.append("                <span style=\"font-size: 36px;\">&nbsp; \n");
            stringBuffer.append("                    <span style=\"font-size: 24px;\">&nbsp;因公临时出国人员备案表&nbsp;</span>\n");
            stringBuffer.append("                </span>\n");
            stringBuffer.append("                <span style=\"font-size: 20px;\"><br/></span>\n");
            stringBuffer.append("            </td>\n");
            stringBuffer.append("        </tr>\n");
            stringBuffer.append("        <tr style=\"height:35px\">\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">姓名</td>\n");
            stringBuffer.append("            <td colspan=\"2\" width=\"144\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getName()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">性别</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+omsPubApplyVO.getSex()+"</td>\n");
            stringBuffer.append("            <td width=\"82\" style=\"\">出生年月</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+omsPubApplyVO.getBirthDate()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">政治面貌</td>\n");
            stringBuffer.append("            <td colspan=\"2\" width=\"155\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getPoliticalAff()+"</td>\n");
            stringBuffer.append("        </tr>\n");
            stringBuffer.append("        <tr style=\"height:90px\">\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">工作单位</td>\n");
            stringBuffer.append("            <td colspan=\"2\" width=\"144\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getB0101()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">职务</td>\n");
            stringBuffer.append("            <td colspan=\"3\" width=\"226\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getJob()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">健康状况（选项：健康/不健康)</td>\n");
            stringBuffer.append("            <td colspan=\"2\" width=\"155\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getHealth()+"</td>\n");
            stringBuffer.append("        </tr>\n");
            stringBuffer.append("        <tr style=\"height:90px\">\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">是否为涉密人员</td>\n");
            stringBuffer.append("            <td colspan=\"2\" width=\"144\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getSfsmry()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">涉密等级（核心、重要、一般）</td>\n");
            stringBuffer.append("            <td colspan=\"3\" width=\"226\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getSmdj()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">核心涉密人员年审</td>\n");
            stringBuffer.append("            <td colspan=\"2\" width=\"155\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getSECRET_REVIEW_DATE()+"</td>\n");
            stringBuffer.append("        </tr><tr style=\"height:90px\">\n");
            stringBuffer.append("            <td rowspan=\"2\" width=\"72\" style=\"\">脱密期信息</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">在某一单位原为核心、重要、一般</td>\n");
            stringBuffer.append("            <td colspan=\"3\" width=\"216\" style=\"word-break: break-all;\">"+omsSmrOldInfoVOS.get(0).getSecretRelatedLevel()+"</td>\n");
            stringBuffer.append("            <td width=\"82\" style=\"\">脱密期(至年月)</td>\n");
            stringBuffer.append("            <td colspan=\"4\" width=\"335\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsSmrOldInfoVOS.get(0).getQrFinishDate()+"</td>\n");
            stringBuffer.append("        </tr><tr style=\"height:90px\">\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">在某二单位原为核心、重要、一般</td>\n");
            stringBuffer.append("            <td colspan=\"3\" width=\"216\" style=\"word-break: break-all;\">"+omsSmrOldInfoVOS.get(1).getSecretRelatedLevel()+"</td>\n");
            stringBuffer.append("            <td width=\"82\" style=\"\">脱密期(至年月)</td>\n");
            stringBuffer.append("            <td colspan=\"4\" width=\"358\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsSmrOldInfoVOS.get(1).getQrFinishDate()+"</td>\n");
            stringBuffer.append("        </tr>\n");
            stringBuffer.append("        <tr style=\"height:198px\">\n");
            stringBuffer.append("            <td rowspan=\"5\" width=\"72\" style=\"\">家庭主要成员情况</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">称谓</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">姓名</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">年龄</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">政治面貌</td>\n");
            stringBuffer.append("            <td width=\"82\" style=\"\">工作单位</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">工作单位及职务(自动提取任免表)</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">居住地(手动填写)</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">是否在国（境）外（来自于干部监督处个人有关事项报告中的信息）</td>\n");
            stringBuffer.append("            <td width=\"64\" style=\"\">是否取得外国国籍、境外长期或永久居留权（来自于干部监督处个人有关事项报告中的信息）</td>\n");
            stringBuffer.append("        </tr>\n");
            List<A36> a36List = otherPubApply.getA36List();
            for (A36 a:a36List) {
                if ("1".equals(a.getIsAbroad())){
                    a.setIsAbroad("是");
                }else {
                    a.setIsAbroad("否");
                }
                stringBuffer.append("        <tr style=\"height:72px\">\n");
                stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+a.getA3604a()+"</td>\n");
                stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+a.getA3601()+"</td>\n");
                stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+a.getA3607()+"</td>\n");
                stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+a.getA3627()+"</td>\n");
                stringBuffer.append("            <td width=\"82\" style=\"word-break: break-all;\">"+a.getA3611()+"</td>\n");
                stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+a.getA3611()+"</td>\n");
                stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+a.getLivePlace()+"</td>\n");
                stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+a.getIsAbroad()+"</td>\n");
                stringBuffer.append("            <td width=\"142\" style=\"\">无&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; □<br/>外国国籍&nbsp;&nbsp;&nbsp;&nbsp; □<br/>永久居留资格 □<br/>长期居留许可 □</td>\n");
                stringBuffer.append("        </tr>\n");
            }
            stringBuffer.append("        <tr style=\"height:198px\">\n");
            stringBuffer.append("            <td colspan=\"2\" width=\"144\" style=\"\">组团单位(省内单位勾选，省外单位为手工录入)</td>\n");
            stringBuffer.append("            <td colspan=\"4\" width=\"298\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getZtdw()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">在团组中拟任职务（默认为团长、副团长、团员、其他（手工录入）</td>\n");
            stringBuffer.append("            <td colspan=\"3\" width=\"245\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getZtnrzw()+"</td>\n");
            stringBuffer.append("        </tr>\n");
            stringBuffer.append("        <tr style=\"height:82px\">\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">于</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+omsPubApplyVO.getCgsj()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">至</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">"+omsPubApplyVO.getHgsj()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">赴</td>\n");
            stringBuffer.append("            <td width=\"82\" style=\"word-break: break-all;\">"+omsPubApplyVO.getSdgj()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"word-break: break-all;\">停留"+omsPubApplyVO.getTlsj()+"天</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">开展</td>\n");
            stringBuffer.append("            <td colspan=\"2\" width=\"155\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getCfrw()+"</td>\n");
            stringBuffer.append("        </tr>\n");
            stringBuffer.append("        <tr style=\"height:140px\">\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">出国任务审批单位</td>\n");
            stringBuffer.append("            <td colspan=\"5\" width=\"370\" style=\"border-right: 1px solid rgb(0, 0, 0); word-break: break-all;\">"+omsPubApplyVO.getCgspdw()+"</td>\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">文号</td>\n");
            stringBuffer.append("            <td colspan=\"3\" width=\"245\" style=\"word-break: break-all;\">"+omsPubApplyVO.getPwh()+"</td>\n");
            stringBuffer.append("        </tr>\n");
            stringBuffer.append("        <tr style=\"height:144px\">\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">最近一次因公出国时间、所赴国家（地区）及任务</td>\n");
            //获取最近一次出国情况
            List<OmsPubApply> latestInfoList = omsPubApplyMapper.selectPubAbroadLatestInfo(omsPubApplyVO.getA0100());
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
                omsPubApplyVO.setZjcgqk(sb.toString());
            }
            stringBuffer.append("            <td colspan=\"9\" width=\"786\" style=\"word-break: break-all;\">"+omsPubApplyVO.getZjcgqk()+"</td>\n");
            stringBuffer.append("        </tr><tr style=\"height:74px\">\n");
            stringBuffer.append("            <td width=\"72\" style=\"\">人员派出单位意见</td>\n");
            stringBuffer.append("            <td colspan=\"9\" width=\"786\" style=\"\">负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 单位盖章<br/>年月日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 年月日</td></tr><tr style=\"height:31px\"><td width=\"72\" style=\"\">说明</td><td colspan=\"9\" width=\"786\" style=\"\">本表由因公临时出国人员所在单位填写，按照干部管理权限，报组织人事部门备案，并抄报外事审批部门。</td></tr></tbody></table><p><br/></p>\n");

        }else if ("近三年出国（境）记录表".equals(fileName)){

        String s = "<table cellpadding=\"0\" cellspacing=\"0\">\n" +
                "        <colgroup>\n" +
                "            <col width=\"115\" style=\"width:86.25pt;\"/>\n" +
                "            <col width=\"103\" style=\"width:77.25pt;\"/>\n" +
                "            <col width=\"118\" style=\"width:88.50pt;\"/>\n" +
                "            <col width=\"160\" style=\"width:120.00pt;\"/>\n" +
                "            <col width=\"140\" style=\"width:105.00pt;\"/>\n" +
                "        </colgroup><tbody><tr style=\"height:20.25pt;\" class=\"firstRow\">\n" +
                "        <td colspan=\"5\" class=\"et4\" width=\"477\" style=\"word-break: break-all;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\n" +
                "            <span style=\"font-size: 20px;\">&nbsp;<strong>XX</strong><strong>干部近三年出访记录</strong></span>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr style=\"height:18.75pt;\">\n" +
                "        <td class=\"et5\" style=\"\">出访人员</td>\n" +
                "        <td class=\"et5\" width=\"67\">出访时间</td>\n" +
                "        <td class=\"et5\" width=\"15\">回国时间</td>\n" +
                "        <td class=\"et5\">出访国家或地区</td>\n" +
                "        <td class=\"et5\">出访事由</td>\n" +
                "    </tr>\n" +
                "    <tr style=\"height:20.25pt;\">\n" +
                "        <td class=\"et6\" style=\"word-break: break-all;\">李</td>\n" +
                "        <td class=\"et6\" width=\"67\" style=\"word-break: break-all;\">李</td>\n" +
                "        <td class=\"et6\" width=\"15\" style=\"word-break: break-all;\">李</td>\n" +
                "        <td class=\"et6\" style=\"word-break: break-all;\">李</td>\n" +
                "        <td class=\"et6\" style=\"word-break: break-all;\">李</td>\n" +
                "    </tr>\n" +
                "    </tbody></table><p><br/></p>";


        }

    return stringBuffer.toString();
    }

    @Override
    public Map<String, Object> selectFileDestail(AbroadFileDestailParams abroadFileDestailParams) {
        if (StringUtils.isBlank(abroadFileDestailParams.getApplyID())
                || StringUtils.isBlank(abroadFileDestailParams.getFileId())
                || StringUtils.isBlank(abroadFileDestailParams.getIsEdit())
                || StringUtils.isBlank(abroadFileDestailParams.getTableCode())){
            throw new CustomMessageException("参数错误");
        }
        Map<String, Object> result = new HashMap<>();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());


//        //测试用的单位主键
//        UserInfo userInfo = UserInfoUtil.getUserInfo();
//        userInfo.setId("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        B01 b01 = new B01();
//        b01.setB0100("cd3ffb59-d5ba-1038-bdaa-c2ae22a0bcce");
//


        //查询文件
        QueryWrapper<OmsFile> queryWrapperFile = new QueryWrapper<>();
        queryWrapperFile.eq("TABLE_CODE", abroadFileDestailParams.getTableCode())
                .eq("B0100", b01.getB0100())
                .eq("ID", abroadFileDestailParams.getFileId());
        OmsFile omsFile = omsFileMapper.selectOne(queryWrapperFile);
        if (omsFile == null){
            throw new CustomMessageException("文件不存在");
        }
        //查询关键字
        QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
        queryWrapperKeyword.eq("TYPE", abroadFileDestailParams.getTableCode())
                .eq("FILE_ID", omsFile.getFileId());
        List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);

        if ("1".equals(abroadFileDestailParams.getIsEdit())){
            //编辑
            result.put("omsFile", omsFile);
            result.put("omsReplaceKeywordList", omsReplaceKeywordList);
        } else if("0".equals(abroadFileDestailParams.getIsEdit())){
            //查询生成的文件
            QueryWrapper<OmsCreateFile> createFile = new QueryWrapper<>();
            createFile.eq("FILE_ID", abroadFileDestailParams.getFileId())
                    .eq("TABLE_CODE", abroadFileDestailParams.getTableCode())
                    .eq("APPLY_ID", abroadFileDestailParams.getApplyID());
            OmsCreateFile omsCreateFile = omsCreateFileMapper.selectOne(createFile);
            result.put("omsCreateFile", omsCreateFile);
            //如果生成文件为空，查询文件
            if (omsCreateFile == null){
                //查询sql
                queryWrapperFile.clear();
                queryWrapperFile.eq("ID", omsFile.getFileId())
                        .select("RUN_SQL");
                OmsFile omsFileSql = omsFileMapper.selectOne(queryWrapperFile);
                if (!StringUtils.isBlank(omsFileSql.getRunSql())){
                    omsFileSql.setRunSql(omsFileSql.getRunSql().replace("@applyId", abroadFileDestailParams.getApplyID()));
                }
                FileReplaceVO fileReplaceVO = omsFileMapper.handleSql(omsFileSql.getRunSql());
                // 替换关键词
                if (fileReplaceVO != null){
                    replaceKeywordsDestail(fileReplaceVO, omsReplaceKeywordList, omsFile);
                }
            }
            result.put("omsFile", omsFile);
        }
        return result;
    }

    @Override
    public void downloadOmsFile(AbroadFileDestailParams abroadFileDestailParams) {
        if (StringUtils.isBlank(abroadFileDestailParams.getApplyID())
                || StringUtils.isBlank(abroadFileDestailParams.getFileId())
                || StringUtils.isBlank(abroadFileDestailParams.getIsEdit())
                || StringUtils.isBlank(abroadFileDestailParams.getTableCode())){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询文件
        QueryWrapper<OmsFile> queryWrapperFile = new QueryWrapper<>();
        queryWrapperFile.eq("TABLE_CODE", abroadFileDestailParams.getTableCode())
                .eq("ID", abroadFileDestailParams.getFileId())
                .eq("B0100", userInfo.getOrgId());
        OmsFile omsFile = omsFileMapper.selectOne(queryWrapperFile);
        if (omsFile == null){
            throw new CustomMessageException("文件不存在");
        }
        /**查询所需的信息**/
        //查询关键字
        Map<String, String> keywords = new HashMap<>();
        QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
        queryWrapperKeyword.eq("TYPE", abroadFileDestailParams.getTableCode())
                .eq("FILE_ID", omsFile.getFileId());
        List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
        String srcPath = omsFileUtils.getBaseDir() + File.separator;
        String destPath = omsFileUtils.getBaseDir() + File.separator;

        if (Constants.oms_business[1].equals(abroadFileDestailParams.getTableCode())){
            //因私出国
            //查看
            OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(abroadFileDestailParams.getApplyID());
            // 替换关键词封装
            replaceKeywordsFile(omsPriApplyVO, omsReplaceKeywordList, keywords);
            srcPath += "yinsichuguo" + File.separator + omsFile.getFileName();
            destPath += "yinsichuguo" + File.separator + omsPriApplyVO.getId() + File.separator + omsFile.getFileName();
        }else if(Constants.oms_business[2].equals(abroadFileDestailParams.getTableCode())){
            //延期回国
            //查看
            OmsPriDelayApplyVO omsPriDelayApplyVO = omsPriDelayApplyService.selectDelayApplyById(abroadFileDestailParams.getApplyID());
            // 替换关键词封装
            replaceKeywordsFile(omsPriDelayApplyVO, omsReplaceKeywordList, keywords);
            srcPath += "yanqihuiguo" + File.separator + omsFile.getFileName();
            destPath += "yanqihuiguo" + File.separator + omsPriDelayApplyVO.getId() + File.separator + omsFile.getFileName();
        }
        //替换关键词
        boolean status = omsFileUtils.replaceAndGenerateWord(srcPath, destPath, keywords);
        if (!status){
            throw new CustomMessageException("获取文件失败");
        }
        //返回文件
        omsFileUtils.downloadFile(destPath, omsFile.getFileName().split("//.")[0]);
        //删除结果文件
        FileUtil.delete(destPath);
    }

    @Override
    public String saveTextOmsFile(OmsFile omsFile) {
        if (StringUtils.isBlank(omsFile.getId())){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        omsFile.setModifyTime(new Date());
        omsFile.setModifyUser(userInfo.getId());
        if (omsFileMapper.updateById(omsFile) < 1){
            throw new CustomMessageException("操作失败");
        }
        return "操作成功";
    }

    @Override
    public OmsFile selectFileDestailNew(String fileId, String applyId, String tableCode) {
        if (StringUtils.isEmpty(fileId) || StringUtils.isBlank(applyId)){
            throw new CustomMessageException("参数错误");
        }
        OmsFile omsFile = omsFileMapper.selectById(fileId);
        replaceFile(omsFile, applyId, tableCode);
        return omsFile;
    }

    /**
     * 替换关键词信息准备
     * @param omsFile
     * @param applyId
     * @param tableCode
     */
    public void replaceFile(OmsFile omsFile, String applyId, String tableCode){
        //查询关键字
        QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
        queryWrapperKeyword.eq("TYPE", tableCode)
                .eq("FILE_ID", omsFile.getFileId());
        List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);

        //替换关键词
        if (!StringUtils.isBlank(omsFile.getRunSql())){
            omsFile.setRunSql(omsFile.getRunSql().replace("@applyId", applyId));
        }

        FileReplaceVO fileReplaceVO = omsFileMapper.handleSql(omsFile.getRunSql());
        if (tableCode.equals(Constants.oms_business[0])){
            //设置随同人员姓名
            String stName = null;
            //从团组预备案表查
            stName = omsPubApplyMapper.getStNameForGroup(applyId);
            if (StringUtils.isBlank(stName)){
                //查询申请表
                stName  = omsPubApplyMapper.getStNameForPub(applyId);
                fileReplaceVO.setStName(stName);
            }else {
                fileReplaceVO.setStName(stName);
            }
            //获取当前登录人
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            fileReplaceVO.setNowUsername(userInfo.getUserName());
        }
        // 替换关键词
        if (fileReplaceVO != null){
            replaceKeywordsDestail(fileReplaceVO, omsReplaceKeywordList, omsFile);
        }
    }

    /**
     * 关键词替换（非文件）
     * @param t
     * @param omsReplaceKeywordList
     * @param omsFile
     * @param <T>
     */
    private <T> void replaceKeywordsDestail(T t, List<OmsReplaceKeywords> omsReplaceKeywordList, OmsFile omsFile){
        for (OmsReplaceKeywords omsReplaceKeywords : omsReplaceKeywordList) {
            //反射机制代替关键词
            Class clazz = t.getClass();
            try {
                Object value = clazz.getDeclaredMethod(omsReplaceKeywords.getReplaceField()).invoke(t);
                if (Date.class.isInstance(value) && value != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                    value = sdf.format(value);
                }
                if (!StringUtils.isBlank(omsFile.getFrontContent())){
                    if (value != null){
                        omsFile.setFrontContent(omsFile.getFrontContent().replace(omsReplaceKeywords.getKeyword(), value.toString()));
                    }else{
                        omsFile.setFrontContent(omsFile.getFrontContent().replace(omsReplaceKeywords.getKeyword(), ""));
                    }
                }
                if (!StringUtils.isBlank(omsFile.getBankContent())){
                    if (value != null){
                        omsFile.setBankContent(omsFile.getBankContent().replace(omsReplaceKeywords.getKeyword(), value.toString()));
                    }else{
                        omsFile.setBankContent(omsFile.getBankContent().replace(omsReplaceKeywords.getKeyword(), ""));
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new CustomMessageException("数据异常");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new CustomMessageException("数据异常");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new CustomMessageException("数据异常");
            }
        }
    }

    /**
     * 关键词替换（文件）
     * @param t
     * @param omsReplaceKeywordList
     * @param map
     * @param <T>
     */
    private <T> void replaceKeywordsFile(T t, List<OmsReplaceKeywords> omsReplaceKeywordList, Map<String, String> map){
        for (OmsReplaceKeywords omsReplaceKeywords : omsReplaceKeywordList) {
            //反射机制代替关键词
            Class clazz = t.getClass();
            try {
                String value = (String) clazz.getDeclaredMethod(omsReplaceKeywords.getReplaceField()).invoke(t);
                map.put(omsReplaceKeywords.getKeyword(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new CustomMessageException("数据异常");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new CustomMessageException("数据异常");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new CustomMessageException("数据异常");
            }
        }
    }
}
