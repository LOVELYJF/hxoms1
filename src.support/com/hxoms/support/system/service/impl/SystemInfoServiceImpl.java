package com.hxoms.support.system.service.impl;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.tree.TreeUtil;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.support.system.entity.SystemInfo;
import com.hxoms.support.system.mapper.SystemInfoMapper;
import com.hxoms.support.system.service.SystemInfoService;
import com.hxoms.support.user.entity.User;
import com.hxoms.support.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Desc:
 * @Author：孙登
 * @CreateDate: 2019/6/12 16:47
 */
@Service
public class SystemInfoServiceImpl implements SystemInfoService {

    @Autowired
    private SystemInfoMapper systemInfoMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Tree> selectSystemInfoTree() {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<Tree> list = systemInfoMapper.selectSystemInfoTree();
        return TreeUtil.listToTreeJson(list);
    }

    @Override
    public List<SystemInfo> selectKidsSysInfo(SystemInfo systemInfo) {
        return systemInfoMapper.selectKidsSysInfo(systemInfo.getId());
    }

    @Override
    public void insertSystemInfo(SystemInfo systemInfo) {
        if (systemInfo == null) {
            throw new CustomMessageException("参数为空");
        }
        systemInfo.setId(UUIDGenerator.getPrimaryKey());
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        systemInfo.setModifyUser(userInfo.getId());
        if (systemInfo.getOrderIndex() == null) {
            systemInfo.setOrderIndex(systemInfoMapper.selectMaxIndex());
        }
        systemInfo.setModifyTime(new Date());
        systemInfoMapper.insert(systemInfo);
    }

    @Override
    public void updateSystemInfo(SystemInfo systemInfo) {
        if (systemInfo == null) {
            throw new CustomMessageException("参数为空");
        }
        systemInfo.setModifyTime(new Date());
        systemInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());
        systemInfoMapper.updateByPrimaryKeySelective(systemInfo);
    }

    @Override
    public void deleteSystemInfo(SystemInfo systemInfo) {
        if (systemInfo == null || systemInfo.getId() == null) {
            throw new CustomMessageException("参数为空");
        }
        //判断是否有子节点
        List<SystemInfo> kidList = systemInfoMapper.selectKidsSysInfo(systemInfo.getId());
        if (kidList == null || kidList.isEmpty()) {
            systemInfoMapper.deleteByPrimaryKey(systemInfo.getId());
        } else {
            throw new CustomMessageException("存在子节点,不可以删除");
        }
    }

    @Override
    public SystemInfo selectSysInfoByPrimaryKey(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("id为空");
        }
        return systemInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemInfo> selectCurrUserGrantSystemInfo() {
        User user = userMapper.selectByPrimaryKey(UserInfoUtil.getUserInfo().getId());
        //查询用户所属机构的系统权限
        List<SystemInfo> currUserSystemInfoList = systemInfoMapper.selectOrgGrantSystemInfo(user.getOrgId());
        //判断用户是管理员还是普通用户;管理员直接返回,普通用户要重新查询和机构的权限取交集
        if (!Constants.USER_TYPES[0].equals(user.getUserType())) {
            currUserSystemInfoList = systemInfoMapper.selectUserGrantSystemInfo(user.getId());
        }
        return currUserSystemInfoList == null ? new ArrayList<>() : currUserSystemInfoList;
    }
}
