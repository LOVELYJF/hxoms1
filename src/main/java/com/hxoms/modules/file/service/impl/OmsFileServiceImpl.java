package com.hxoms.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.file.OmsFileUtils;
import com.hxoms.common.utils.*;
import com.hxoms.modules.file.entity.*;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.file.mapper.OmsReplaceKeywordsMapper;
import com.hxoms.modules.file.service.OmsFileService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.service.OmsPriDelayApplyService;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.mapper.B01Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
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
    private OmsPriDelayApplyService omsPriDelayApplyService;
    @Autowired
    private OmsCreateFileMapper omsCreateFileMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersoninfoMapper;
    @Autowired
    private B01Mapper b01Mapper;

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public List<OmsFile> selectFileListByCode(String tableCode, String a0100) {
        if (StringUtils.isBlank(tableCode) || StringUtils.isBlank(a0100)){
            throw new CustomMessageException("参数错误");
        }
        List<String> fileType = new ArrayList<>();
        fileType.add("1"); //系统
        //涉密信息
        QueryWrapper<OmsRegProcpersoninfo> omsPersoninfo = new QueryWrapper<>();
        omsPersoninfo.eq("A0100", a0100)
                .eq("secret_level", "0");
        int sercetCount = omsRegProcpersoninfoMapper.selectCount(omsPersoninfo);
        if (sercetCount > 0){
            //非涉密
            fileType.add("2");
        }else{
            fileType.add("3");
            //TODO 涉密人员（原单位脱密期人员）
        }
        //是否主要领导
        omsPersoninfo.clear();
        omsPersoninfo.eq("A0100", a0100)
                .eq("MAIN_LEADER", "1");
        int omsPersoninfoCount = omsRegProcpersoninfoMapper.selectCount(omsPersoninfo);
        if (omsPersoninfoCount > 0){
            fileType.add("5"); //主要领导
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //查询机构信息
        B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());
        if (b01 == null){
            throw new CustomMessageException("数据异常");
        }
        QueryWrapper<OmsFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TABLE_CODE", tableCode)
                .eq("B0100", b01.getB0100())
                .in("FILE_TYPE",fileType)
                .orderByAsc("CREATE_TIME");
        List<OmsFile> omsFiles = omsFileMapper.selectList(queryWrapper);
        if (omsFiles == null || omsFiles.size() < 1) {
            //初始化机构文件
            queryWrapper.clear();
            queryWrapper.eq("TABLE_CODE", tableCode)
                    .in("FILE_TYPE",fileType)
                    .and(wrapper->wrapper.eq("B0100", "")
                            .or()
                            .isNull("B0100"))
                    .orderByAsc("CREATE_TIME");
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
                //复制文件
                if (Constants.oms_business[1].equals(tableCode)){
                    //因私出国
                    omsFileUtils.copyFolder("yinsichuguo", "yinsichuguo" + File.separator + b01.getB0100());
                } else if(Constants.oms_business[2].equals(tableCode)){
                    //延期回国
                    omsFileUtils.copyFolder("yanqihuiguo", "yanqihuiguo" + File.separator + b01.getB0100());
                }
                return omsFileSystem;
            }
        }
        return omsFiles;
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
            //查询sql
            queryWrapperFile.clear();
            queryWrapperFile.eq("ID", omsFile.getFileId())
                    .select("RUN_SQL");
            OmsFile omsFileSql = omsFileMapper.selectOne(queryWrapperFile);
            if (!StringUtils.isBlank(omsFileSql.getRunSql())){
                omsFileSql.setRunSql(omsFileSql.getRunSql().replaceAll("@applyId", abroadFileDestailParams.getApplyID()));
            }
            FileReplaceVO fileReplaceVO = omsFileMapper.handleSql(omsFileSql.getRunSql());
            // 替换关键词
            if (fileReplaceVO != null){
                replaceKeywordsDestail(fileReplaceVO, omsReplaceKeywordList, omsFile);
            }
            result.put("omsFile", omsFile);
            //查询生成的文件
            QueryWrapper<OmsCreateFile> createFile = new QueryWrapper<>();
            createFile.eq("FILE_ID", abroadFileDestailParams.getFileId())
                    .eq("TABLE_CODE", abroadFileDestailParams.getTableCode())
                    .eq("APPLY_ID", abroadFileDestailParams.getApplyID());
            OmsCreateFile omsCreateFile = omsCreateFileMapper.selectOne(createFile);
            result.put("omsCreateFile", omsCreateFile);
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
    public OmsFile selectFileDestailNew(String fileId) {
        if (StringUtils.isEmpty(fileId)){
            throw new CustomMessageException("参数错误");
        }
        return omsFileMapper.selectById(fileId);
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
                if (!StringUtils.isBlank(omsFile.getFrontContent()) && value != null){
                    omsFile.setFrontContent(omsFile.getFrontContent().replaceAll(omsReplaceKeywords.getKeyword(), value.toString()));
                }
                if (!StringUtils.isBlank(omsFile.getBankContent()) && value != null){
                    omsFile.setBankContent(omsFile.getBankContent().replaceAll(omsReplaceKeywords.getKeyword(), value.toString()));
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
