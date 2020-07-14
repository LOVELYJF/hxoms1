package com.hxoms.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
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
        queryWrapper.select("ID","FILE_ID","APPLY_ID","IS_EDIT", "IS_SEALHANDLE","CHECK_ADVICES","SEAL_DESC","FILE_NAME","FILE_SHORTNAME")
                .eq("TABLE_CODE", tableCode)
                .eq("APPLY_ID", applyId);
        return omsCreateFileMapper.selectList(queryWrapper);
    }

    @Override
    public OmsCreateFile insertOrUpdate(OmsCreateFile omsCreateFile) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if (StringUtils.isBlank(omsCreateFile.getId())){
            //新增
            omsCreateFile.setId(UUIDGenerator.getPrimaryKey());
            omsCreateFile.setCreateUser(userInfo.getId());
            omsCreateFile.setIsSealhandle("0");
            omsCreateFile.setCreateTime(new Date());
            if (omsCreateFileMapper.insert(omsCreateFile) < 1){
                throw new CustomMessageException("操作失败");
            }
        }else{
            //修改
            omsCreateFile.setModifyTime(new Date());
            omsCreateFile.setModifyUser(userInfo.getId());
            if (omsCreateFileMapper.updateById(omsCreateFile) < 1){
                throw new CustomMessageException("操作失败");
            }
        }
        return omsCreateFile;
    }
}
