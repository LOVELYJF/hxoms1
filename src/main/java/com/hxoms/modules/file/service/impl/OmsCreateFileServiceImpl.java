package com.hxoms.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.service.OmsCreateFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @desc: 生成材料
 * @author: lijing
 * @date: 2020-06-12
 */
@Service
public class OmsCreateFileServiceImpl implements OmsCreateFileService {
    @Autowired
    private OmsCreateFileMapper omsCreateFileMapper;

    @Override
    public List<OmsCreateFile> selectCreateFileList(String tableCode, String applyId) {
        if (StringUtils.isBlank(tableCode) || StringUtils.isBlank(applyId)){
            throw new CustomMessageException("参数错误");
        }
        QueryWrapper<OmsCreateFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("ID","FILE_ID","APPLY_ID","IS_EDIT",
                "IS_SEALHANDLE","CHECK_ADVICES","SEAL_DESC","FILE_NAME",
                "FILE_SHORTNAME","FRONT_CONTENT","BANK_CONTENT","PRINT_NUM")
                .eq("TABLE_CODE", tableCode)
                .eq("APPLY_ID", applyId)
                .eq("ISFILE_LIST", "1")
                .orderByAsc("SORT_ID");
        return omsCreateFileMapper.selectList(queryWrapper);
    }

    @Override
    public OmsCreateFile InsertOrUpdate(OmsCreateFile omsCreateFile) {
        if(StringUilt.stringIsNullOrEmpty(omsCreateFile.getId())){
            omsCreateFile.setId(UUIDGenerator.getPrimaryKey());
            omsCreateFile.setCreateTime(new Date());
            omsCreateFile.setCreateUser(UserInfoUtil.getUserId());
            if(omsCreateFileMapper.insert(omsCreateFile)<1){
                throw new CustomMessageException("新增材料失败！");
            }
        }
        else{
            omsCreateFile.setModifyTime(new Date());
            omsCreateFile.setModifyUser(UserInfoUtil.getUserId());
            if(omsCreateFileMapper.updateById(omsCreateFile)<1){
                throw new CustomMessageException("修改材料失败！");
            }
        }
        return omsCreateFile;
    }

    @Override
    public String deleteCreateFile(String tableCode, String applyId) {
        if (StringUtils.isBlank(tableCode) || StringUtils.isBlank(applyId)){
            throw new CustomMessageException("参数错误");
        }
        QueryWrapper<OmsCreateFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TABLE_CODE", tableCode)
                .eq("APPLY_ID", applyId);
        int status = omsCreateFileMapper.delete(queryWrapper);
        if (status < 0){
            throw new CustomMessageException("删除失败");
        }
        return "删除成功";
    }
}
