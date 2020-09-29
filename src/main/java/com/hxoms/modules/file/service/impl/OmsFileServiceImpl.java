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
        B01 b01 = b01Mapper.selectOrgByB0100(userInfo.getOrgId());
//        //测试用的单位主键
//        userInfo.setOrgId("00000000000000000000000000");
//        UserInfo userInfo = UserInfoUtil.getUserInfo();
//        userInfo.setId("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        B01 b01 = new B01();
//        b01.setB0100("cd3ffb59-d5ba-1038-bdaa-c2ae22a0bcce");
//        if (b01 == null){
//            throw new CustomMessageException("数据异常");
//        }
        QueryWrapper<OmsFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TABLE_CODE", tableCode)
                .eq("B0100", userInfo.getOrgId())
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
                    omsfile.setB0100(userInfo.getOrgId());
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
                    omsFileUtils.copyFolder("yinsichuguo", "yinsichuguo" + File.separator + userInfo.getOrgId());
                } else if(Constants.oms_business[2].equals(tableCode)){
                    //延期回国
                    omsFileUtils.copyFolder("yanqihuiguo", "yanqihuiguo" + File.separator + userInfo.getOrgId());
                }
            }
            //重新查询
            queryWrapper.clear();
            queryWrapper.eq("TABLE_CODE", tableCode)
                    .eq("B0100",userInfo.getOrgId())
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
                   if ("近三年出国（境）记录表".equals(omsFile.getFileShortname())&& omsFile.getTableCode().equals(Constants.oms_business[0])){
                        String frontContent = getFrontContent(applyId,"近三年出国（境）记录表");
                        omsFile.setFrontContent(frontContent);
                    }else if ("名单".equals(omsFile.getFileShortname()) && omsFile.getTableCode().equals(Constants.oms_business[4])){
                        String frontContent = getFrontContent(applyId,"名单");
                        omsFile.setFrontContent(frontContent);
                    }
                    omsCreateFile.setFrontContent(omsFile.getFrontContent());
                    omsCreateFile.setBankContent(omsFile.getBankContent());
                    omsCreateFileService.insertOrUpdate(omsCreateFile);
                }
            }
        }
        return omsFiles;
    }

    private String getFrontContent(String applyId, String fileName) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
        OmsPubApplyQueryParam omsPubApplyQueryParam = new OmsPubApplyQueryParam();
        omsPubApplyQueryParam.setApplyId(applyId);
        List<OmsPubApplyVO> pubAppListByCondition = omsPubApplyMapper.getPubAppListByCondition(omsPubApplyQueryParam);
        OmsPubApplyVO omsPubApplyVO = pubAppListByCondition.get(0);
        //获取台办赴台批件名单
        List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectPubApplyListByPwh(omsPubApplyVO.getPwh());
        //获取近三年该干部因公出国境记录
        List<OmsPubApplyVO> pubApplyListBy3Year = omsPubApplyMapper.getPubApplyListBy3Year(omsPubApplyVO.getA0100());
        //获取近三年该干部因私出国境记录
        List<OmsPriApplyVO> omsPriPubApplies = omsPriApplyMapper.selectPriListBy3Year(omsPubApplyVO.getA0100());
        StringBuffer stringBuffer = new StringBuffer();
        if ("近三年出国（境）记录表".equals(fileName)){

            stringBuffer.append("<table cellpadding=\"0\" cellspacing=\"0\" border =\"1\" >");
            stringBuffer.append("<colgroup>");
            stringBuffer.append("<col width=\"115\" style=\"width:86.25pt;\"/>");
            stringBuffer.append("<col width=\"103\" style=\"width:77.25pt;\"/>");
            stringBuffer.append("<col width=\"118\" style=\"width:88.50pt;\"/>");
            stringBuffer.append("<col width=\"160\" style=\"width:120.00pt;\"/>");
            stringBuffer.append("<col width=\"140\" style=\"width:105.00pt;\"/>");
            stringBuffer.append("</colgroup><tbody><tr style=\"height:20.25pt;\" class=\"firstRow\">");
            stringBuffer.append("<td colspan=\"5\" class=\"et4\" width=\"477\" style=\"word-break: break-all;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            stringBuffer.append("<span style=\"font-size: 20px;\">&nbsp;<strong>"+omsPubApplyVO.getName()+"</strong><strong>干部近三年出访记录</strong></span>");
            stringBuffer.append("</td>");
            stringBuffer.append("</tr>");
            stringBuffer.append("<tr style=\"height:18.75pt;\">");
            stringBuffer.append("<td class=\"et5\" style=\"\">出访人员</td>");
            stringBuffer.append("<td class=\"et5\" width=\"67\">出访时间</td>");
            stringBuffer.append("<td class=\"et5\" width=\"15\">回国时间</td>");
            stringBuffer.append("<td class=\"et5\">出访国家或地区</td>");
            stringBuffer.append("<td class=\"et5\">出访事由</td>");
            stringBuffer.append("</tr>");

            //近3年因公出国记录
            for (OmsPubApplyVO o:pubApplyListBy3Year) {
                stringBuffer.append("<tr style=\"height:20.25pt;\">");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">"+o.getName()+"</td>");
                stringBuffer.append("<td class=\"et6\" width=\"67\" style=\"word-break: break-all;\">"+sdf1.format(o.getCgsj())+"</td>");
                stringBuffer.append("<td class=\"et6\" width=\"15\" style=\"word-break: break-all;\">"+sdf1.format(o.getHgsj())+"</td>");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">"+o.getSdgj()+"</td>");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">"+o.getCfrw()+"</td>");
                stringBuffer.append("</tr>");

            }
            //近3年因私出国记录
            for (OmsPriApplyVO o:omsPriPubApplies) {
                stringBuffer.append("<tr style=\"height:20.25pt;\">");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">"+o.getName()+"</td>");
                stringBuffer.append("<td class=\"et6\" width=\"67\" style=\"word-break: break-all;\">"+sdf1.format(o.getAbroadTime())+"</td>");
                stringBuffer.append("<td class=\"et6\" width=\"15\" style=\"word-break: break-all;\">"+sdf1.format(o.getReturnTime())+"</td>");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">"+o.getSdgj()+"</td>");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">"+o.getAbroadReasons()+"</td>");
                stringBuffer.append("</tr>");
            }
        }
        else if ("名单".equals(fileName)){
            stringBuffer.append("<table cellpadding=\"0\" cellspacing=\"0\">");
            stringBuffer.append("<tbody>");
            stringBuffer.append("<tr class=\"firstRow\">");
            stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">序号</td>");
            stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">姓名</td>");
            stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">性别</td>");
            stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">出生日期</td>");
            stringBuffer.append("<td width=\"112\" valign=\"top\" style=\"word-break: break-all;\">工作单位及职务</td>");
            stringBuffer.append("<td width=\"64\" valign=\"top\" style=\"word-break: break-all;\">赴台身份<br/></td>");
            stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">户口所在地<br/></td>");
            stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">证照保管单位</td>");
            stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">备注<br/></td>");
            stringBuffer.append("</tr>");
            //赴台批件名单
            if (omsPubApplyVOS != null && omsPubApplyVOS.size() >0){
                int i =0;
                for (OmsPubApplyVO pubApplyVO:omsPubApplyVOS) {
                    i++;
                    stringBuffer.append("<tr>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">"+i+"<br/></td>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">"+pubApplyVO.getName()+"</td>");
                    if ("1".equals(pubApplyVO.getSex())){
                        pubApplyVO.setSex("男");
                    }else if ("2".equals(pubApplyVO.getSex())){
                        pubApplyVO.setSex("女");
                    }
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">"+pubApplyVO.getSex()+"</td>");
                    if (pubApplyVO.getBirthDate() != null){
                        stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">"+sdf1.format(pubApplyVO.getBirthDate())+"</td>");
                    }else {
                        stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\"></td>");
                    }
                    stringBuffer.append("<td width=\"112\" valign=\"top\" style=\"word-break: break-all;\">"+pubApplyVO.getB0101()+"</td>");
                    stringBuffer.append("<td width=\"64\" valign=\"top\" style=\"word-break: break-all;\">"+pubApplyVO.getZtnrzw()+"</td>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">"+pubApplyVO.getREGISTE_RESIDENCE()+"</td>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">"+""+"</td>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">"+pubApplyVO.getBz()+"</td>");
                    stringBuffer.append("</tr>");
                }
            }
            stringBuffer.append("</tbody>");
            stringBuffer.append("</table><p><br/></p><p><br/></p>");
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
        
        B01 b01 = b01Mapper.selectOrgByB0100(userInfo.getOrgId());


//        //测试用的单位主键
//          userInfo.setOrgId("00000000000000000000000000");
//        UserInfo userInfo = UserInfoUtil.getUserInfo();
//        userInfo.setId("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        B01 b01 = new B01();
//        b01.setB0100("cd3ffb59-d5ba-1038-bdaa-c2ae22a0bcce");
//


        //查询文件
        QueryWrapper<OmsFile> queryWrapperFile = new QueryWrapper<>();
        queryWrapperFile.eq("TABLE_CODE", abroadFileDestailParams.getTableCode())
                .eq("B0100", userInfo.getOrgId())
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
        if ((tableCode.equals(Constants.oms_business[0])) && ("因公临时出国（境）人员备案表".equals(omsFile.getFileShortname()))){
            fileReplaceVO=getFileReplaceVO(fileReplaceVO,applyId);
        }
        if (tableCode.equals(Constants.oms_business[4])){
            //台办赴台批件
            //设置邀请单位 todo
            fileReplaceVO.setYqdw("");
        }
        // 替换关键词
        if (fileReplaceVO != null){
            replaceKeywordsDestail(fileReplaceVO, omsReplaceKeywordList, omsFile);
        }
    }

    /**
     * 功能描述: <br>
     * 〈组装因公出国境关键字〉
     * @Param: [fileReplaceVO, applyId]
     * @Return: com.hxoms.modules.file.entity.FileReplaceVO
     * @Author: 李逍遥
     * @Date: 2020/9/29 16:44
     */
    private FileReplaceVO getFileReplaceVO(FileReplaceVO fileReplaceVO, String applyId) {

        OmsPubApplyQueryParam omsPubApplyQueryParam = new OmsPubApplyQueryParam();
        omsPubApplyQueryParam.setApplyId(applyId);
        List<OmsPubApplyVO> pubAppListByCondition = omsPubApplyMapper.getPubAppListByCondition(omsPubApplyQueryParam);
        OmsPubApplyVO omsPubApplyVO = pubAppListByCondition.get(0);
            String secret_level = omsPubApplyVO.getSECRET_LEVEL();
            if (!StringUtils.isBlank(secret_level) && !"0".equals(secret_level)){
                omsPubApplyVO.setSfsmry("否");
            }else {
                omsPubApplyVO.setSfsmry("是");
            }
            fileReplaceVO.setSfsm(omsPubApplyVO.getSfsmry());
            if ("0".equals(secret_level)){
                omsPubApplyVO.setSmdj("非涉密人员");
            }else if ("1".equals(secret_level)){
                omsPubApplyVO.setSmdj("一般涉密人员");
            }else if ("2".equals(secret_level)){
                omsPubApplyVO.setSmdj("重要涉密人员");
            }else if ("3".equals(secret_level)){
                omsPubApplyVO.setSmdj("核心涉密人员");
            }
        fileReplaceVO.setSecretLevel(omsPubApplyVO.getSmdj());
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
            fileReplaceVO.setName(omsPubApplyVO.getName());
            fileReplaceVO.setSECRET_REVIEW_DATE(omsPubApplyVO.getSECRET_REVIEW_DATE());

            if (omsSmrOldInfoVOS != null && omsSmrOldInfoVOS.size()>= 2){
                fileReplaceVO.setDwsmdj1(omsSmrOldInfoVOS.get(0).getSecretRelatedLevel());
                fileReplaceVO.setDwtmq1(omsSmrOldInfoVOS.get(0).getQrFinishDate());
                fileReplaceVO.setDwsmdj2(omsSmrOldInfoVOS.get(1).getSecretRelatedLevel());
                fileReplaceVO.setDwtmq2(omsSmrOldInfoVOS.get(1).getQrFinishDate());
            }else if(omsSmrOldInfoVOS != null && omsSmrOldInfoVOS.size() == 1) {
                fileReplaceVO.setDwsmdj1(omsSmrOldInfoVOS.get(0).getSecretRelatedLevel());
                fileReplaceVO.setDwtmq1(omsSmrOldInfoVOS.get(0).getQrFinishDate());
            }
            List<A36> a36List = otherPubApply.getA36List();
        StringBuffer stringBuffer = new StringBuffer();
        if (a36List != null && a36List.size() >0){
                stringBuffer.append("</tr>");
                stringBuffer.append("<tr style=\"height:198px\">");
                stringBuffer.append("<td rowspan=\"" +(a36List.size()+1)+
                        "\" width=\"72\" style=\"\">家庭主要成员情况</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">称谓</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">姓名</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">年龄</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">政治面貌</td>");
                stringBuffer.append("<td width=\"82\" style=\"\">工作单位</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">工作单位及职务</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">居住地</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">是否在国（境）外</td>");
                stringBuffer.append("<td width=\"64\" style=\"\">是否取得外国国籍、境外长期或永久居留权</td>");
                stringBuffer.append("</tr>");
                for (A36 a:a36List) {
                    if ("1".equals(a.getIsAbroad())){
                        a.setIsAbroad("是");
                    }else {
                        a.setIsAbroad("否");
                    }
                    stringBuffer.append("<tr style=\"height:72px\">");
                    stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">"+a.getA3604a()+"</td>");
                    stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">"+a.getA3601()+"</td>");
                    stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">"+a.getA3607()+"</td>");
                    stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">"+a.getA3627()+"</td>");
                    stringBuffer.append("<td width=\"82\" style=\"word-break: break-all;\">"+a.getA3611()+"</td>");
                    stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">"+a.getA3611()+"</td>");
                    stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">"+a.getLivePlace()+"</td>");
                    stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">"+a.getIsAbroad()+"</td>");
                    stringBuffer.append("<td width=\"142\" style=\"\">无&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; □<br/>外国国籍&nbsp;&nbsp;&nbsp;&nbsp; □<br/>永久居留资格 □<br/>长期居留许可 □</td>");
                    stringBuffer.append("</tr>");
                }
            }else {
                stringBuffer.append("</tr>");
                stringBuffer.append("<tr style=\"height:198px\">");
                stringBuffer.append("<td rowspan=\"1\" width=\"72\" style=\"\">家庭主要成员情况</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">称谓</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">姓名</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">年龄</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">政治面貌</td>");
                stringBuffer.append("<td width=\"82\" style=\"\">工作单位</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">工作单位及职务</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">居住地</td>");
                stringBuffer.append("<td width=\"72\" style=\"\">是否在国（境）外</td>");
                stringBuffer.append("<td width=\"64\" style=\"\">是否取得外国国籍、境外长期或永久居留权</td>");
                stringBuffer.append("</tr>");
                stringBuffer.append("<tr style=\"height:72px\">");
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\"></td>");
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\"></td>");
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\"></td>");
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\"></td>");
                stringBuffer.append("<td width=\"82\" style=\"word-break: break-all;\"></td>");
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\"></td>");
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\"></td>");
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\"></td>");
                stringBuffer.append("<td width=\"142\" style=\"\">无&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; □<br/>外国国籍&nbsp;&nbsp;&nbsp;&nbsp; □<br/>永久居留资格 □<br/>长期居留许可 □</td>");
                stringBuffer.append("</tr>");
            }
            fileReplaceVO.setJtcy(stringBuffer.toString());
            fileReplaceVO.setZtdw(omsPubApplyVO.getZtdw());
            fileReplaceVO.setZtnrzw(omsPubApplyVO.getZtnrzw());
            fileReplaceVO.setCgspdw(omsPubApplyVO.getCgspdw());

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
            fileReplaceVO.setZjcfjl(omsPubApplyVO.getZjcgqk());
        return fileReplaceVO;
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
