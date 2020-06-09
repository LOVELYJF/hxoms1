package com.hxoms.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.file.OmsFileUtils;
import com.hxoms.common.utils.FileUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.file.entity.*;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.file.mapper.OmsReplaceKeywordsMapper;
import com.hxoms.modules.file.mapper.OmsSealhandleRecordsMapper;
import com.hxoms.modules.file.service.OmsFileService;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.service.OmsPriDelayApplyService;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
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
    private OmsSealhandleRecordsMapper omsSealhandleRecordsMapper;

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public List<OmsFile> selectFileListByCode(String tableCode) {
        if (StringUtils.isBlank(tableCode)){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        QueryWrapper<OmsFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TABLE_CODE", tableCode)
                .eq("B0100", userInfo.getOrgId())
                .orderByAsc("CREATE_TIME");
        List<OmsFile> omsFiles = omsFileMapper.selectList(queryWrapper);
        if (omsFiles == null || omsFiles.size() < 1) {
            //初始化机构文件
            queryWrapper.clear();
            queryWrapper.eq("TABLE_CODE", tableCode)
            .eq("B0100", "")
            .or()
            .isNull("B0100");
            List<OmsFile> omsFileSystem = omsFileMapper.selectList(queryWrapper);
            if (omsFileSystem != null && omsFileSystem.size() > 0) {
                //插入
                for (OmsFile omsfile : omsFileSystem) {
                    omsfile.setFileId(omsfile.getId());
                    omsfile.setId(UUIDGenerator.getPrimaryKey());
                    omsfile.setB0100(userInfo.getOrgId());
                    omsfile.setCreateUser(userInfo.getId());
                    omsfile.setCreateTime(new Date());
                    omsFileMapper.insert(omsfile);
                }
                //复制文件
                if ("oms_pri_apply".equals(tableCode)){
                    //因私出国
                    omsFileUtils.copyFolder("yinsichuguo", "yinsichuguo" + File.separator + userInfo.getOrgId());
                } else if("oms_pri_delay_apply".equals(tableCode)){
                    //延期回国
                    omsFileUtils.copyFolder("yanqihuiguo", "yanqihuiguo" + File.separator + userInfo.getOrgId());
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
            //因私出国
            if ("oms_pri_apply".equals(abroadFileDestailParams.getTableCode())){
                //查看
                OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(abroadFileDestailParams.getApplyID());
                // 替换关键词
                replaceKeywordsDestail(omsPriApplyVO, omsReplaceKeywordList, omsFile);
            }else if("oms_pri_delay_apply".equals(abroadFileDestailParams.getTableCode())){
                //查看
                OmsPriDelayApplyVO omsPriDelayApplyVO = omsPriDelayApplyService.selectDelayApplyById(abroadFileDestailParams.getApplyID());
                // 替换关键词
                replaceKeywordsDestail(omsPriDelayApplyVO, omsReplaceKeywordList, omsFile);
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

        if ("oms_pri_apply".equals(abroadFileDestailParams.getTableCode())){
            //因私出国
            //查看
            OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(abroadFileDestailParams.getApplyID());
            // 替换关键词封装
            replaceKeywordsFile(omsPriApplyVO, omsReplaceKeywordList, keywords);
            srcPath += "yinsichuguo" + File.separator + omsFile.getFileName();
            destPath += "yinsichuguo" + File.separator + omsPriApplyVO.getId() + File.separator + omsFile.getFileName();
        }else if("oms_pri_delay_apply".equals(abroadFileDestailParams.getTableCode())){
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
        omsFile.setModifyUser(userInfo.getId());
        omsFile.setModifyTime(new Date());
        if (omsFileMapper.updateById(omsFile) < 1){
            throw new CustomMessageException("操作失败");
        }
        return "操作成功";
    }

    @Override
    public List<OmsFileVO> selectSealHandleList(String tableCode, String applyId) {
        if (StringUtils.isBlank(tableCode) || StringUtils.isEmpty(applyId)){
            throw new CustomMessageException("参数错误");
        }
        Map<String, String> params = new HashMap<>();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        params.put("tableCode", tableCode);
        params.put("applyId", applyId);
        params.put("b0100", userInfo.getOrgId());
        return omsFileMapper.selectSealHandleList(params);
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String saveSealHandle(List<OmsSealhandleRecordsVO> omsSealhandleRecordsVOS) {
        for (OmsSealhandleRecordsVO item : omsSealhandleRecordsVOS) {
            QueryWrapper<OmsSealhandleRecords> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("APPLY_ID", item.getApplyId())
                    .eq("FILE_ID", item.getFileId());
            if ("0".equals(item.getIsHandle())){
                omsSealhandleRecordsMapper.delete(queryWrapper);
            }else{
                //没有则插入
                if (omsSealhandleRecordsMapper.selectCount(queryWrapper) < 1){
                    OmsSealhandleRecords omsSealhandleRecords = new OmsSealhandleRecords();
                    omsSealhandleRecords.setId(UUIDGenerator.getPrimaryKey());
                    omsSealhandleRecords.setApplyId(item.getApplyId());
                    omsSealhandleRecords.setFileId(item.getFileId());
                    if(omsSealhandleRecordsMapper.insert(omsSealhandleRecords) < 1){
                        throw new CustomMessageException("操作失败");
                    }
                }
            }
        }
        return "操作成功";
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
                String value = (String) clazz.getDeclaredMethod(omsReplaceKeywords.getReplaceField()).invoke(t);
                omsFile.getFrontContent().replaceAll(omsReplaceKeywords.getKeyword(), value);
                omsFile.getBankContent().replaceAll(omsReplaceKeywords.getKeyword(), value);
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
