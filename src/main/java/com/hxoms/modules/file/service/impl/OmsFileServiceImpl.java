package com.hxoms.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.file.OmsFileUtils;
import com.hxoms.common.utils.*;
import com.hxoms.modules.file.entity.*;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import com.hxoms.modules.file.entity.paramentity.SecretLevelAndFileType;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.file.mapper.OmsReplaceKeywordsMapper;
import com.hxoms.modules.file.service.OmsCreateFileService;
import com.hxoms.modules.file.service.OmsFileService;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.service.OmsPriDelayApplyService;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.entity.OtherPubApply;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.service.OmsPubApplyService;
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

    /**
     * @param procpersonId 备案人员id
     * @param applyId      业务主键
     * @description:返回需要生成的材料列表，自动根据模板设置返回通用或机构自定义模板，自动根据关键字替换生成材料， 重新根据业务数据获取需要的材料，将业务数据变化后不再需要的已经生成过的材料删除
     * @author:杨波
     * @date:2020-10-15 * @param tableCode 业务类型
     * @return:java.util.List<com.hxoms.modules.file.entity.OmsCreateFile>
     **/
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public List<OmsCreateFile> selectFileListByCode(String tableCode, String procpersonId, String applyId) {

        //取涉密等级、可用模板类型等信息
        SecretLevelAndFileType level = getSecretLevelAndFileType(tableCode, procpersonId, applyId);

        List<OmsFile> omsFiles = getTemplate(tableCode, procpersonId, applyId, 1,level);
        List<OmsCreateFile> omsCreateFiles = getCreateFiles(omsFiles, tableCode, applyId, 1);
        OmsPubApplyVO omsPubApply = null;

        if (Constants.oms_business[0].equals(tableCode)) {
            omsPubApply = omsPubApplyService.selectPubApplyById(applyId);
        }
        //没有生成时生成文件
        for (OmsFile omsFile : omsFiles) {
            OmsCreateFile omsCreateFile = omsCreateFiles.stream().filter((m) -> m.getFileId().equals(omsFile.getId())).findFirst().orElse(null);
            if (omsCreateFile != null) continue;

            omsCreateFile = createFile(omsFile, applyId);
            //替换关键词
            replaceFile(omsFile, applyId, tableCode, omsPubApply);
            if ("近三年出国（境）记录表".equals(omsFile.getFileShortname()) && omsFile.getTableCode().equals(Constants.oms_business[0])) {
                String frontContent = getFrontContent(applyId, "近三年出国（境）记录表");
                omsFile.setFrontContent(frontContent);
            } else if ("名单".equals(omsFile.getFileShortname()) && omsFile.getTableCode().equals(Constants.oms_business[4])) {
                String frontContent = getFrontContent(applyId, "名单");
                omsFile.setFrontContent(frontContent);
            }
            omsCreateFile.setFrontContent(omsFile.getFrontContent());
            omsCreateFile.setBankContent(omsFile.getBankContent());
            omsCreateFileMapper.insert(omsCreateFile);
            omsCreateFiles.add(omsCreateFile);
        }
        Collections.sort(omsCreateFiles);
        return omsCreateFiles;
    }

    /**
     * @param procpersonId 登记备案人员主键
     * @param applyId      业务表主键
     * @param isTemplate   是取模板还是取纸质材料，1、取模板用于生成材料，0、取纸质材料，用于打印材料清单 及自评
     * @param level        涉密等级
     * @description:根据登记备案人员、业务类型、材料类型获取当前可用材料模板列表
     * @author:杨波
     * @date:2020-10-20 * @param tableCode 业务类型（oms_pub_apply等）
     * @return:java.util.List<com.hxoms.modules.file.entity.OmsFile>
     **/
    private List<OmsFile> getTemplate(String tableCode, String procpersonId, String applyId,
                                      Integer isTemplate, SecretLevelAndFileType level) {

        if (StringUtils.isBlank(tableCode) || StringUtils.isBlank(procpersonId) ||
                StringUtils.isBlank(applyId)) {
            throw new CustomMessageException("参数错误");
        }

        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();

        //本单位自定义模板
        QueryWrapper<OmsFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TABLE_CODE", tableCode)
                .eq("IS_TEMPLATE", isTemplate)
                .eq("B0100", userInfo.getOrgId())
                .in("FILE_TYPE", level.getFileType())
                .orderByAsc("SORT_ID");

        List<OmsFile> omsFiles = omsFileMapper.selectList(queryWrapper);
        if (omsFiles == null) omsFiles = new ArrayList<>();


        //通用模板
        queryWrapper.clear();
        queryWrapper.eq("TABLE_CODE", tableCode)
                .eq("IS_TEMPLATE", isTemplate)
                .in("FILE_TYPE", level.getFileType())
                .and(wrapper -> wrapper.eq("B0100", "")
                        .or()
                        .isNull("B0100"))
                .orderByAsc("SORT_ID");

        List<OmsFile> omsFileSystem = omsFileMapper.selectList(queryWrapper);
        if (omsFileSystem == null) omsFileSystem = new ArrayList<>();


        //循环通用模板，判断通用模板设置，如果允许使用自定义模板时，查找该单位是否自定义了模板，没有时使用通用模板
        //不允许自定义时使用通用模板
        for (OmsFile omsfile : omsFileSystem) {
            //用户是否自定义该模板
            OmsFile omsSelfFile = omsFiles.stream().filter((m) -> m.getFileId() != null && m.getFileId().equals(omsfile.getId())).findFirst().orElse(null);

            //用户没有自定义该模板
            if (null == omsSelfFile) {
                omsFiles.add(omsfile);
            } else if (omsfile.getIsTymb() == 1) {
                omsFiles.remove(omsSelfFile);
                omsFiles.add(omsfile);
            }
        }
        return omsFiles;
    }

    private List<OmsCreateFile> getCreateFiles(List<OmsFile> omsFiles, String tableCode,
                                               String applyId, Integer isTemplate) {
        //生成文件
        QueryWrapper<OmsCreateFile> createFile = new QueryWrapper<>();
        createFile.eq("TABLE_CODE", tableCode)
                .eq("IS_TEMPLATE", isTemplate)
                .eq("APPLY_ID", applyId);
        List<OmsCreateFile> omsCreateFiles = omsCreateFileMapper.selectList(createFile);
        if (omsCreateFiles == null) omsCreateFiles = new ArrayList<>();

        //删除因为身份变化不再需要的文件
        for (OmsCreateFile omsCreateFile : omsCreateFiles
        ) {
            OmsFile omsFile = omsFiles.stream().filter((m) -> m.getId().equals(omsCreateFile.getFileId())).findFirst().orElse(null);
            if (omsFile == null) {
                QueryWrapper<OmsCreateFile> cfWrapper = new QueryWrapper<>();
                cfWrapper.eq("id", omsCreateFile.getId());
                omsCreateFileMapper.delete(cfWrapper);
                omsCreateFiles.remove(omsCreateFile);
            }
        }

        return omsCreateFiles;
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
        if ("近三年出国（境）记录表".equals(fileName)) {

            stringBuffer.append("<table cellpadding=\"0\" cellspacing=\"0\" border =\"1\" >");
            stringBuffer.append("<colgroup>");
            stringBuffer.append("<col width=\"115\" style=\"width:86.25pt;\"/>");
            stringBuffer.append("<col width=\"103\" style=\"width:77.25pt;\"/>");
            stringBuffer.append("<col width=\"118\" style=\"width:88.50pt;\"/>");
            stringBuffer.append("<col width=\"160\" style=\"width:120.00pt;\"/>");
            stringBuffer.append("<col width=\"140\" style=\"width:105.00pt;\"/>");
            stringBuffer.append("</colgroup><tbody><tr style=\"height:20.25pt;\" class=\"firstRow\">");
            stringBuffer.append("<td colspan=\"5\" class=\"et4\" width=\"477\" style=\"word-break: break-all;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            stringBuffer.append("<span style=\"font-size: 20px;\">&nbsp;<strong>" + omsPubApplyVO.getName() + "</strong><strong>干部近三年出访记录</strong></span>");
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
            for (OmsPubApplyVO o : pubApplyListBy3Year) {
                stringBuffer.append("<tr style=\"height:20.25pt;\">");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">" + o.getName() + "</td>");
                stringBuffer.append("<td class=\"et6\" width=\"67\" style=\"word-break: break-all;\">" + sdf1.format(o.getCgsj()) + "</td>");
                stringBuffer.append("<td class=\"et6\" width=\"15\" style=\"word-break: break-all;\">" + sdf1.format(o.getHgsj()) + "</td>");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">" + o.getSdgj() + "</td>");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">" + o.getCfrw() + "</td>");
                stringBuffer.append("</tr>");

            }
            //近3年因私出国记录
            for (OmsPriApplyVO o : omsPriPubApplies) {
                stringBuffer.append("<tr style=\"height:20.25pt;\">");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">" + o.getName() + "</td>");
                stringBuffer.append("<td class=\"et6\" width=\"67\" style=\"word-break: break-all;\">" + sdf1.format(o.getAbroadTime()) + "</td>");
                stringBuffer.append("<td class=\"et6\" width=\"15\" style=\"word-break: break-all;\">" + sdf1.format(o.getReturnTime()) + "</td>");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">" + o.getSdgj() + "</td>");
                stringBuffer.append("<td class=\"et6\" style=\"word-break: break-all;\">" + o.getAbroadReasons() + "</td>");
                stringBuffer.append("</tr>");
            }
        } else if ("名单".equals(fileName)) {
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
            if (omsPubApplyVOS != null && omsPubApplyVOS.size() > 0) {
                int i = 0;
                for (OmsPubApplyVO pubApplyVO : omsPubApplyVOS) {
                    i++;
                    stringBuffer.append("<tr>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">" + i + "<br/></td>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">" + pubApplyVO.getName() + "</td>");
                    if ("1".equals(pubApplyVO.getSex())) {
                        pubApplyVO.setSex("男");
                    } else if ("2".equals(pubApplyVO.getSex())) {
                        pubApplyVO.setSex("女");
                    }
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">" + pubApplyVO.getSex() + "</td>");
                    if (pubApplyVO.getBirthDate() != null) {
                        stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">" + sdf1.format(pubApplyVO.getBirthDate()) + "</td>");
                    } else {
                        stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\"></td>");
                    }
                    stringBuffer.append("<td width=\"112\" valign=\"top\" style=\"word-break: break-all;\">" + pubApplyVO.getB0101() + "</td>");
                    stringBuffer.append("<td width=\"64\" valign=\"top\" style=\"word-break: break-all;\">" + pubApplyVO.getZtnrzw() + "</td>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">" + pubApplyVO.getREGISTE_RESIDENCE() + "</td>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">" + "" + "</td>");
                    stringBuffer.append("<td width=\"88\" valign=\"top\" style=\"word-break: break-all;\">" + pubApplyVO.getBz() + "</td>");
                    stringBuffer.append("</tr>");
                }
            }
            stringBuffer.append("</tbody>");
            stringBuffer.append("</table><p><br/></p><p><br/></p>");
        }

        return stringBuffer.toString();
    }

    @Override
    public Map<String, Object> selectFileTemplate(String fileTemplateId) {
        if (StringUtils.isBlank(fileTemplateId)) {
            throw new CustomMessageException("参数错误");
        }
        Map<String, Object> result = new HashMap<>();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息

        //查询模板文件
        QueryWrapper<OmsFile> queryWrapperFile = new QueryWrapper<>();
        queryWrapperFile.eq("ID", fileTemplateId);
        OmsFile omsFile = omsFileMapper.selectOne(queryWrapperFile);

        if (omsFile.getIsTymb() == 0) {
            queryWrapperFile.clear();
            queryWrapperFile.eq("FILE_ID", fileTemplateId);
            queryWrapperFile.eq("B0100", userInfo.getOrgId());
            omsFile = omsFileMapper.selectOne(queryWrapperFile);
        }
        if (omsFile == null) {
            throw new CustomMessageException("文件不存在");
        }
        //查询关键字
        QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
        queryWrapperKeyword.eq("FILE_ID", omsFile.getFileId()==null ? omsFile.getId() : omsFile.getFileId());
        List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);

        result.put("omsFile", omsFile);
        result.put("omsReplaceKeywordList", omsReplaceKeywordList);

        return result;
    }

    /**
     * @param procpersonId 备案人员
     * @param applyId      业务主主键
     * @description:返回其它材料列表，其它材料也允许按非涉密、涉密、核心涉密、挂职来设置在哪种条件下需要
     * @author:杨波
     * @date:2020-10-15 * @param tableCode 业务类型（因公、因私等）
     * @return:java.util.List<com.hxoms.modules.file.entity.OtherMaterial>
     **/
    @Override
    public List<OtherMaterial> selectOtherMaterial(String tableCode, String procpersonId, String applyId) {
        //取涉密等级、可用模板类型等信息
        SecretLevelAndFileType level = getSecretLevelAndFileType(tableCode, procpersonId, applyId);

        List<OmsFile> omsFiles = getTemplate(tableCode, procpersonId, applyId, 0,level);
        List<OmsCreateFile> omsCreateFiles = getCreateFiles(omsFiles, tableCode, applyId, 0);

        List<OtherMaterial> oms = new ArrayList<>();
        for (OmsFile omsFile : omsFiles) {
            OtherMaterial om = new OtherMaterial();
            om.setId(omsFile.getId());
            om.setFileName(omsFile.getFileName());
            om.setSortId(omsFile.getSortId());
            om.setIsRequired(0);

            OmsCreateFile omsCreateFile = omsCreateFiles.stream().filter((m) -> m.getFileId().equals(omsFile.getId())).findFirst().orElse(null);
            if (omsCreateFile != null)
                om.setIsRequired(1);
            else if (omsFile.getIsDefault() == 1) {

                if (omsFile.getFileType().equals("1") ||
                        (omsFile.getFileType().equals("7") && level.getSecretLevel() == 3) ||
                        (omsFile.getFileType().equals("3") && level.getSecretLevel() > 0) ||
                        (omsFile.getFileType().equals("2") && level.getSecretLevel() == 0) ||
                        (omsFile.getFileType().equals("4") && level.getSecretLevel() > 0 &&
                                level.getDeclassificationEndDate() != null &&
                                level.getDeclassificationEndDate().after(new Date())) ||
                        (omsFile.getFileType().equals("5") && level.getIsLeader() != null &&
                                level.getIsLeader().equals("1")) ||
                        (omsFile.getFileType().equals("6") && level.getLqgz() != null &&
                                level.getLqgz().equals("1"))) {

                    om.setIsRequired(1);
                    omsCreateFile = createFile(omsFile, applyId);
                    omsCreateFileMapper.insert(omsCreateFile);
                }
            }
            oms.add(om);
        }
        Collections.sort(oms);
        return oms;
    }

    /**
     * @param applyId 业务主键
     * @description:根据模板文件创建用户文件，不自动生成模板内容
     * @author:杨波
     * @date:2020-10-15 * @param omsFile 模板文件
     * @return:com.hxoms.modules.file.entity.OmsCreateFile
     **/
    @Override
    public OmsCreateFile createFile(OmsFile omsFile, String applyId) {

        OmsCreateFile omsCreateFile = new OmsCreateFile();
        omsCreateFile.setId(UUIDGenerator.getPrimaryKey());
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
        omsCreateFile.setIsTemplate(omsFile.getIsTemplate());
        omsCreateFile.setCreateUser(UserInfoUtil.getUserId());
        omsCreateFile.setCreateTime(new Date());
        omsCreateFile.setIsTymb(omsFile.getIsTymb());
        return omsCreateFile;
    }

    /**
     * 功能描述: <br>
     * 〈通用模板查询〉
     *
     * @return
     * @Param: []
     * @Return: java.util.Map<java.lang.String                                                               ,                                                               java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/10/12 19:34
     */
    @Override
    public List<OmsTYMBVO> selectFileList() {
        //返回结果
        List<OmsTYMBVO> omsTYMBVOS = new ArrayList<>();
        /** 因公出国境 */
        OmsTYMBVO oms_pub_apply = new OmsTYMBVO();
        List<OmsMB> omsMB_pub = new ArrayList<>();
        oms_pub_apply.setType("因公出国境");
        /** 因私出国境 */
        OmsTYMBVO oms_pri_apply = new OmsTYMBVO();
        List<OmsMB> omsMB_pri = new ArrayList<>();
        oms_pri_apply.setType("因私出国境");
        /** 延期回国 */
        OmsTYMBVO oms_pri_delay_apply = new OmsTYMBVO();
        List<OmsMB> omsMB_pri_delay = new ArrayList<>();
        oms_pri_delay_apply.setType("延期回国");
        /** 注销证照 */
        OmsTYMBVO oms_cer_cancellate = new OmsTYMBVO();
        List<OmsMB> omsMB_cer_cancellate = new ArrayList<>();
        oms_cer_cancellate.setType("注销证照");
        /** 赴台批件 */
        OmsTYMBVO oms_ftpj_apply = new OmsTYMBVO();
        List<OmsMB> omsMB_ftpj = new ArrayList<>();
        oms_ftpj_apply.setType("赴台批件");
        /** 借出证照 */
        OmsTYMBVO oms_cer_cancellate_lending = new OmsTYMBVO();
        List<OmsMB> omsMB_cer_cancellate_lending = new ArrayList<>();
        oms_cer_cancellate_lending.setType("借出证照");
        /** 证照催缴 */
        OmsTYMBVO oms_cer_certificateCollect_cpd = new OmsTYMBVO();
        List<OmsMB> omsMB_cer_certificateCollect_cpd = new ArrayList<>();
        oms_cer_certificateCollect_cpd.setType("证照催缴");
        /** 撤销登记备案 */
        OmsTYMBVO oms_reg_revokeapply_hj = new OmsTYMBVO();
        List<OmsMB> omsMB_reg_revokeapply_hj = new ArrayList<>();
        oms_reg_revokeapply_hj.setType("撤销登记备案");
        /** */
        //查询文件
        QueryWrapper<OmsFile> queryWrapperFile = new QueryWrapper<>();
        queryWrapperFile.isNull("B0100");
        List<OmsFile> omsFiles = omsFileMapper.selectList(queryWrapperFile);
        if (omsFiles != null && omsFiles.size() > 0) {

            for (OmsFile file : omsFiles) {
                String tableCode = file.getTableCode();
                //因公出国境
                if ("oms_pub_apply".equals(tableCode) || "oms_pub_apply_cadres".equals(tableCode) || "oms_pub_apply_cadres_putoncreate".equals(tableCode)) {

                    //查询关键字
                    QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
                    queryWrapperKeyword.eq("TYPE", tableCode)
                            .eq("FILE_ID", file.getId());
                    List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
                    OmsMB omsMB = new OmsMB();
                    omsMB.setOmsReplaceKeywordList(omsReplaceKeywordList);
                    omsMB.setOmsFile(file);
                    omsMB_pub.add(omsMB);

                    //因私出国境
                } else if ("oms_pri_apply".equals(tableCode) || "oms_pri_apply_cadres".equals(tableCode)) {

                    //查询关键字
                    QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
                    queryWrapperKeyword.eq("TYPE", tableCode)
                            .eq("FILE_ID", file.getId());
                    List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
                    OmsMB omsMB = new OmsMB();
                    omsMB.setOmsReplaceKeywordList(omsReplaceKeywordList);
                    omsMB.setOmsFile(file);
                    omsMB_pri.add(omsMB);
                    //延期回国
                } else if ("oms_pri_delay_apply".equals(tableCode) || "oms_pri_delay_apply_cadres".equals(tableCode)) {

                    //查询关键字
                    QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
                    queryWrapperKeyword.eq("TYPE", tableCode)
                            .eq("FILE_ID", file.getId());
                    List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
                    OmsMB omsMB = new OmsMB();
                    omsMB.setOmsReplaceKeywordList(omsReplaceKeywordList);
                    omsMB.setOmsFile(file);
                    omsMB_pri_delay.add(omsMB);
                    //注销证照
                } else if ("oms_cer_cancellate".equals(tableCode) || "oms_cer_cancellate_approval".equals(tableCode) || "oms_cer_cancellate_letter".equals(tableCode)) {

                    //查询关键字
                    QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
                    queryWrapperKeyword.eq("TYPE", tableCode)
                            .eq("FILE_ID", file.getId());
                    List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
                    OmsMB omsMB = new OmsMB();
                    omsMB.setOmsReplaceKeywordList(omsReplaceKeywordList);
                    omsMB.setOmsFile(file);
                    omsMB_cer_cancellate.add(omsMB);
                    //赴台批件
                } else if ("oms_ftpj_apply".equals(tableCode)) {

                    //查询关键字
                    QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
                    queryWrapperKeyword.eq("TYPE", tableCode)
                            .eq("FILE_ID", file.getId());
                    List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
                    OmsMB omsMB = new OmsMB();
                    omsMB.setOmsReplaceKeywordList(omsReplaceKeywordList);
                    omsMB.setOmsFile(file);
                    omsMB_ftpj.add(omsMB);
                    //借出证照
                } else if ("oms_cer_cancellate_lending".equals(tableCode) || "oms_cer_cancellate_bill".equals(tableCode) || "oms_cer_cancellate_request".equals(tableCode) || "oms_cer_cancellate".equals(tableCode)) {

                    //查询关键字
                    QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
                    queryWrapperKeyword.eq("TYPE", tableCode)
                            .eq("FILE_ID", file.getId());
                    List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
                    OmsMB omsMB = new OmsMB();
                    omsMB.setOmsReplaceKeywordList(omsReplaceKeywordList);
                    omsMB.setOmsFile(file);
                    omsMB_cer_cancellate_lending.add(omsMB);
                    //证照催缴
                } else if ("oms_cer_certificateCollect_cpd".equals(tableCode) || "oms_cer_certificateCollect_bzh".equals(tableCode)) {

                    //查询关键字
                    QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
                    queryWrapperKeyword.eq("TYPE", tableCode)
                            .eq("FILE_ID", file.getId());
                    List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
                    OmsMB omsMB = new OmsMB();
                    omsMB.setOmsReplaceKeywordList(omsReplaceKeywordList);
                    omsMB.setOmsFile(file);
                    omsMB_cer_certificateCollect_cpd.add(omsMB);
                    //撤销登记备案
                } else if ("oms_reg_revokeapply_hj".equals(tableCode)) {

                    //查询关键字
                    QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
                    queryWrapperKeyword.eq("TYPE", tableCode)
                            .eq("FILE_ID", file.getId());
                    List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);
                    OmsMB omsMB = new OmsMB();
                    omsMB.setOmsReplaceKeywordList(omsReplaceKeywordList);
                    omsMB.setOmsFile(file);
                    omsMB_reg_revokeapply_hj.add(omsMB);
                }
            }
        }
        /** 因公出国境 */
        oms_pub_apply.setOmsMBS(omsMB_pub);
        omsTYMBVOS.add(oms_pub_apply);
        /** 因私出国境 */
        oms_pri_apply.setOmsMBS(omsMB_pri);
        omsTYMBVOS.add(oms_pri_apply);
        /** 延期回国 */
        oms_pri_delay_apply.setOmsMBS(omsMB_pri_delay);
        omsTYMBVOS.add(oms_pri_delay_apply);
        /** 注销证照 */
        oms_cer_cancellate.setOmsMBS(omsMB_cer_cancellate);
        omsTYMBVOS.add(oms_cer_cancellate);
        /** 赴台批件 */
        oms_ftpj_apply.setOmsMBS(omsMB_ftpj);
        omsTYMBVOS.add(oms_ftpj_apply);
        /** 借出证照 */
        oms_cer_cancellate_lending.setOmsMBS(omsMB_cer_cancellate_lending);
        omsTYMBVOS.add(oms_cer_cancellate_lending);
        /** 证照催缴 */
        oms_cer_certificateCollect_cpd.setOmsMBS(omsMB_cer_certificateCollect_cpd);
        omsTYMBVOS.add(oms_cer_certificateCollect_cpd);
        /** 撤销登记备案 */
        oms_reg_revokeapply_hj.setOmsMBS(omsMB_reg_revokeapply_hj);
        omsTYMBVOS.add(oms_reg_revokeapply_hj);
        return omsTYMBVOS;
    }

    /**
     * @param procpersonId 登记备案表主键
     * @param applyId      业务表主键
     * @description:根据业务类型取涉密等级，是因公、因私、因私延期回国就取它们的涉密等级，否则取登记备案人员的涉密等级
     * @author:杨波
     * @date:2020-10-20 * @param tableCode 业务类型（oms_pub_apply等）
     * @return:java.lang.Integer
     **/
    @Override
    public SecretLevelAndFileType getSecretLevelAndFileType(String tableCode, String procpersonId, String applyId) {

        SecretLevelAndFileType secretLevelAndFileType = new SecretLevelAndFileType();
        List<String> fileType = new ArrayList<>();
        fileType.add("1"); //全部人员都需要的文件
        int level = 0;

        //脱密时间
        Date ceclassificaEndtime = null;
        //是否主要领导
        String isLeaders = null;
        //
        String lqgz = null;

        if (tableCode.equals(Constants.oms_business[0])) {

            OmsPubApply omsPubApply = omsPubApplyMapper.selectById(applyId);
            isLeaders = omsPubApply.getSfzyld();
            if (!com.hxoms.common.util.StringUtils.isNullOrEmpty(omsPubApply.getSmdj()))
                level = Integer.parseInt(omsPubApply.getSmdj());

        } else if (tableCode.equals(Constants.oms_business[1])) {

            OmsPriApply omsPriApply = omsPriApplyMapper.selectById(applyId);
            ceclassificaEndtime = omsPriApply.getDeclassificaEndtime();
            isLeaders = omsPriApply.getIsLeaders();
            if (!com.hxoms.common.util.StringUtils.isNullOrEmpty(omsPriApply.getClassificationLevel()))
                level = Integer.parseInt(omsPriApply.getClassificationLevel());

        } else if (tableCode.equals(Constants.oms_business[2])) {

            OmsPriDelayApply omsPriDelayApply = omsPriDelayApplyService.selectDelayApplyById(applyId);
            OmsPriApply omsPriApply = omsPriApplyMapper.selectById(omsPriDelayApply.getApplyId());
            isLeaders = omsPriApply.getIsLeaders();
            ceclassificaEndtime = omsPriApply.getDeclassificaEndtime();
            if (!com.hxoms.common.util.StringUtils.isNullOrEmpty(omsPriApply.getClassificationLevel()))
                level = Integer.parseInt(omsPriApply.getClassificationLevel());

        }

        OmsRegProcpersoninfo omsRegProcpersoninfo = omsRegProcpersoninfoMapper.selectById(procpersonId);
        if (ceclassificaEndtime == null)
            ceclassificaEndtime = omsRegProcpersoninfo.getDecryptEnddate();
        if (isLeaders == null)
            isLeaders = omsRegProcpersoninfo.getMainLeader();
        lqgz = omsRegProcpersoninfo.getLqgz();
        if (level == 0 && com.hxoms.common.util.StringUtils.isNullOrEmpty(omsRegProcpersoninfo.getSecretLevel()))
            level = Integer.parseInt(omsRegProcpersoninfo.getSecretLevel());


        //涉密信息
        if (0 == level) {
            //非涉密
            fileType.add("2"); //非涉密使用的文件
        } else {
            fileType.add("3");//涉密人员使用的文件
            if (ceclassificaEndtime != null && ceclassificaEndtime.after(new Date())) {
                fileType.add("4");//在胶密期人员使用的人文件
            }
            if(level==3){
                fileType.add("7");
            }
        }

        //是否主要领导
        if (null != isLeaders && "1".equals(isLeaders)) {
            fileType.add("5"); //主要领导
        }
        if (lqgz != null && "1".equals(lqgz)) {
            fileType.add("6");//离琼挂职 要征求现单位意见
        }

        secretLevelAndFileType.setSecretLevel(level);
        secretLevelAndFileType.setFileType(fileType);
        secretLevelAndFileType.setDeclassificationEndDate(ceclassificaEndtime);
        secretLevelAndFileType.setIsLeader(isLeaders);
        secretLevelAndFileType.setLqgz(lqgz);
        return secretLevelAndFileType;
    }

    @Override
    public void downloadOmsFile(AbroadFileDestailParams abroadFileDestailParams) {
        if (StringUtils.isBlank(abroadFileDestailParams.getApplyID())
                || StringUtils.isBlank(abroadFileDestailParams.getFileId())
                || StringUtils.isBlank(abroadFileDestailParams.getIsEdit())
                || StringUtils.isBlank(abroadFileDestailParams.getTableCode())) {
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
        if (omsFile == null) {
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

        if (Constants.oms_business[1].equals(abroadFileDestailParams.getTableCode())) {
            //因私出国
            //查看
            OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(abroadFileDestailParams.getApplyID());
            // 替换关键词封装
            replaceKeywordsFile(omsPriApplyVO, omsReplaceKeywordList, keywords);
            srcPath += "yinsichuguo" + File.separator + omsFile.getFileName();
            destPath += "yinsichuguo" + File.separator + omsPriApplyVO.getId() + File.separator + omsFile.getFileName();
        } else if (Constants.oms_business[2].equals(abroadFileDestailParams.getTableCode())) {
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
        if (!status) {
            throw new CustomMessageException("获取文件失败");
        }
        //返回文件
        omsFileUtils.downloadFile(destPath, omsFile.getFileName().split("//.")[0]);
        //删除结果文件
        FileUtil.delete(destPath);
    }

    @Override
    public String saveTextOmsFile(OmsFile omsFile) {
        if (StringUtils.isBlank(omsFile.getId())) {
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        omsFile.setModifyTime(new Date());
        omsFile.setModifyUser(userInfo.getId());
        if (omsFileMapper.updateById(omsFile) < 1) {
            throw new CustomMessageException("操作失败");
        }
        return "操作成功";
    }

    @Override
    public Result saveOtherFile(String id, String applyId, Integer isRequired) {
        OmsFile omsFile = omsFileMapper.selectById(id);
        QueryWrapper<OmsCreateFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("APPLY_ID", applyId).eq("FILE_ID", id);
        List<OmsCreateFile> omsCreateFiles = omsCreateFileMapper.selectList(queryWrapper);
        OmsCreateFile omsCreateFile = null;
        if (omsCreateFiles != null && omsCreateFiles.size() > 0)
            omsCreateFile = omsCreateFiles.get(0);

        if (isRequired.equals(1) && omsCreateFile == null) {
            omsCreateFile = createFile(omsFile, applyId);
            omsCreateFileMapper.insert(omsCreateFile);
        } else if (isRequired.equals(0) && omsCreateFile != null) {
            omsCreateFileMapper.deleteById(omsCreateFile.getId());
        }
        return Result.success();
    }

    @Override
    public OmsCreateFile selectFileDestailNew(String fileId) {
        if (StringUtils.isBlank(fileId)) {
            throw new CustomMessageException("参数错误");
        }
        OmsCreateFile omsCreateFile = omsCreateFileMapper.selectById(fileId);
        OmsFile omsFile = omsFileMapper.selectById(omsCreateFile.getFileId());
        replaceFile(omsFile, omsCreateFile.getApplyId(), omsFile.getTableCode(), null);
        omsCreateFile.setFrontContent(omsFile.getFrontContent());
        omsCreateFile.setBankContent(omsFile.getBankContent());
        omsCreateFileMapper.updateById(omsCreateFile);
        return omsCreateFile;
    }


    /**
     * 替换关键词信息准备
     *
     * @param omsFile
     * @param applyId
     * @param tableCode
     */
    public void replaceFile(OmsFile omsFile, String applyId, String tableCode, OmsPubApplyVO omsPubApply) {
        //查询关键字
        QueryWrapper<OmsReplaceKeywords> queryWrapperKeyword = new QueryWrapper<>();
        queryWrapperKeyword.eq("TYPE", tableCode)
                .eq("FILE_ID", omsFile.getFileId() == null ? omsFile.getId() : omsFile.getFileId());
        List<OmsReplaceKeywords> omsReplaceKeywordList = omsReplaceKeywordsMapper.selectList(queryWrapperKeyword);

        //替换关键词
        if (!StringUtils.isBlank(omsFile.getRunSql())) {
            omsFile.setRunSql(omsFile.getRunSql().replace("@applyId", applyId));
        }

        FileReplaceVO fileReplaceVO = omsFileMapper.handleSql(omsFile.getRunSql());
        if (tableCode.equals(Constants.oms_business[0])) {
            if (omsPubApply == null) {
                omsPubApply = omsPubApplyService.selectPubApplyById(applyId);
            }
            //设置随同人员姓名
            String stName = null;
            //从团组预备案表查
            stName = omsPubApplyMapper.getStNameForGroup(applyId);
            if (StringUtils.isBlank(stName)) {
                //查询申请表
                if (omsPubApply.getPwh().startsWith("琼台赴字")) {
                    stName = omsPubApplyMapper.getStNameForTaiWanPub(applyId);
                } else
                    stName = omsPubApplyMapper.getStNameForPub(applyId);
                fileReplaceVO.setStName(stName);
            } else {
                fileReplaceVO.setStName(stName);
            }
            //获取当前登录人
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            fileReplaceVO.setNowUsername(userInfo.getUserName());
        }
        if ((tableCode.equals(Constants.oms_business[0])) && ("因公临时出国（境）人员备案表".equals(omsFile.getFileShortname()))) {
            fileReplaceVO = getFileReplaceVO(fileReplaceVO, applyId);
        }
        if (tableCode.equals(Constants.oms_business[4])) {
            //台办赴台批件
            //设置邀请单位 todo
            fileReplaceVO.setYqdw("");
        }
        // 替换关键词
        if (fileReplaceVO != null) {
            replaceKeywordsDestail(fileReplaceVO, omsReplaceKeywordList, omsFile);
        }
    }

    /**
     * 功能描述: <br>
     * 〈组装因公出国境关键字〉
     *
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
        if (!StringUtils.isBlank(secret_level) && !"0".equals(secret_level)) {
            omsPubApplyVO.setSfsmry("否");
        } else {
            omsPubApplyVO.setSfsmry("是");
        }
        fileReplaceVO.setSfsm(omsPubApplyVO.getSfsmry());
        if ("0".equals(secret_level)) {
            omsPubApplyVO.setSmdj("非涉密人员");
        } else if ("1".equals(secret_level)) {
            omsPubApplyVO.setSmdj("一般涉密人员");
        } else if ("2".equals(secret_level)) {
            omsPubApplyVO.setSmdj("重要涉密人员");
        } else if ("3".equals(secret_level)) {
            omsPubApplyVO.setSmdj("核心涉密人员");
        }
        OtherPubApply otherPubApply = omsPubApplyService.getOtherPubApply(omsPubApplyVO.getB0100(), omsPubApplyVO.getA0100(), omsPubApplyVO.getCgsj());
        List<OmsSmrOldInfoVO> omsSmrOldInfoVOS = otherPubApply.getOmsSmrOldInfoVOS();
        for (OmsSmrOldInfoVO o : omsSmrOldInfoVOS) {
            if ("0".equals(o.getSecretRelatedLevel())) {
                o.setSecretRelatedLevel("非涉密人员");
            } else if ("1".equals(o.getSecretRelatedLevel())) {
                o.setSecretRelatedLevel("一般涉密人员");
            } else if ("2".equals(o.getSecretRelatedLevel())) {
                o.setSecretRelatedLevel("重要涉密人员");
            } else if ("3".equals(o.getSecretRelatedLevel())) {
                o.setSecretRelatedLevel("核心涉密人员");
            }
        }
        fileReplaceVO.setSECRET_REVIEW_DATE(omsPubApplyVO.getSECRET_REVIEW_DATE());

        if (omsSmrOldInfoVOS != null && omsSmrOldInfoVOS.size() >= 2) {
            fileReplaceVO.setDwsmdj1(omsSmrOldInfoVOS.get(0).getSecretRelatedLevel());
            fileReplaceVO.setDwtmq1(omsSmrOldInfoVOS.get(0).getQrFinishDate());
            fileReplaceVO.setDwsmdj2(omsSmrOldInfoVOS.get(1).getSecretRelatedLevel());
            fileReplaceVO.setDwtmq2(omsSmrOldInfoVOS.get(1).getQrFinishDate());
        } else if (omsSmrOldInfoVOS != null && omsSmrOldInfoVOS.size() == 1) {
            fileReplaceVO.setDwsmdj1(omsSmrOldInfoVOS.get(0).getSecretRelatedLevel());
            fileReplaceVO.setDwtmq1(omsSmrOldInfoVOS.get(0).getQrFinishDate());
        }
        List<A36> a36List = otherPubApply.getA36List();
        StringBuffer stringBuffer = new StringBuffer();
        if (a36List != null && a36List.size() > 0) {
            stringBuffer.append("</tr>");
            stringBuffer.append("<tr style=\"height:198px\">");
            stringBuffer.append("<td rowspan=\"" + (a36List.size() + 1) +
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
            for (A36 a : a36List) {
                if ("1".equals(a.getIsAbroad())) {
                    a.setIsAbroad("是");
                } else {
                    a.setIsAbroad("否");
                }
                stringBuffer.append("<tr style=\"height:72px\">");
                if (a.getA3604a() == null) {
                    a.setA3604a("");
                }
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">" + a.getA3604a() + "</td>");
                if (a.getA3601() == null) {
                    a.setA3601("");
                }
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">" + a.getA3601() + "</td>");
                if (a.getA3607() == null) {
                    a.setA3607("");
                }
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">" + a.getA3607() + "</td>");
                if (a.getA3627() == null) {
                    a.setA3627("");
                }
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">" + a.getA3627() + "</td>");
                if (a.getA3611() == null) {
                    a.setA3611("");
                }
                stringBuffer.append("<td width=\"82\" style=\"word-break: break-all;\">" + a.getA3611() + "</td>");
                if (a.getA3611() == null) {
                    a.setA3611("");
                }
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">" + a.getA3611() + "</td>");
                if (a.getLivePlace() == null) {
                    a.setLivePlace("");
                }
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">" + a.getLivePlace() + "</td>");
                if (a.getIsAbroad() == null) {
                    a.setIsAbroad("");
                }
                stringBuffer.append("<td width=\"72\" style=\"word-break: break-all;\">" + a.getIsAbroad() + "</td>");
                stringBuffer.append("<td width=\"142\" style=\"\">无&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; □<br/>外国国籍&nbsp;&nbsp;&nbsp;&nbsp; □<br/>永久居留资格 □<br/>长期居留许可 □</td>");
                stringBuffer.append("</tr>");
            }
        } else {
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
     *
     * @param t
     * @param omsReplaceKeywordList
     * @param omsFile
     * @param <T>
     */
    private <T> void replaceKeywordsDestail(T t, List<OmsReplaceKeywords> omsReplaceKeywordList, OmsFile omsFile) {
        for (OmsReplaceKeywords omsReplaceKeywords : omsReplaceKeywordList) {
            //反射机制代替关键词
            Class clazz = t.getClass();
            try {
                Object value = clazz.getDeclaredMethod(omsReplaceKeywords.getReplaceField()).invoke(t);
                if (Date.class.isInstance(value) && value != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                    value = sdf.format(value);
                }
                if (!StringUtils.isBlank(omsFile.getFrontContent())) {
                    if (value != null) {
                        omsFile.setFrontContent(omsFile.getFrontContent().replace(omsReplaceKeywords.getKeyword(), value.toString()));
                    } else {
                        omsFile.setFrontContent(omsFile.getFrontContent().replace(omsReplaceKeywords.getKeyword(), ""));
                    }
                }
                if (!StringUtils.isBlank(omsFile.getBankContent())) {
                    if (value != null) {
                        omsFile.setBankContent(omsFile.getBankContent().replace(omsReplaceKeywords.getKeyword(), value.toString()));
                    } else {
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
     *
     * @param t
     * @param omsReplaceKeywordList
     * @param map
     * @param <T>
     */
    private <T> void replaceKeywordsFile(T t, List<OmsReplaceKeywords> omsReplaceKeywordList, Map<String, String> map) {
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
