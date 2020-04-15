package com.hxoms.support.leadertype.service.impl;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.support.inforesource.entity.DataTable;
import com.hxoms.support.inforesource.mapper.DataTableMapper;
import com.hxoms.support.leadertype.mapper.LeaderTypeMapper;
import com.hxoms.support.leadertype.service.LeaderTypeService;
import com.hxoms.support.user.entity.User;
import com.hxoms.support.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 干部类别相关
 *
 * @author sunqian
 * @date 2019/8/22 9:22
 */
@Service
public class LeaderTypeServiceImpl implements LeaderTypeService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LeaderTypeMapper leaderTypeMapper;
    @Autowired
    private DataTableMapper dataTableMapper;


    @Override
    public List<DataTable> selectGrantLeaderTypeInfo(String leaderTypeId) {
        if (StringUtils.isEmpty(leaderTypeId)) {
            throw new CustomMessageException("干部类别参数为空");
        }
        // 查询当前用户
        User user = userMapper.selectByPrimaryKey(UserInfoUtil.getUserInfo().getId());
        // 查询当前用户所属机构的信息集,根据干部类别id
        List<DataTable> dataTableList;
        // 判断用户是普通用户还是管理员
        if (Constants.USER_TYPES[0].equals(user.getUserType())) {
            // 管理员直接返回机构所拥有的信息集权限
            dataTableList = dataTableMapper.selectGrantLeaderTypeInfo(user.getOrgId(), leaderTypeId);
        } else {
            // 普通用户需要查询用户角色下的信息集权限和机构的取交集
            dataTableList = dataTableMapper.selectUserGrantLeaderTypeInfo(user.getId(), leaderTypeId);
        }
        return dataTableList == null ? new ArrayList<>() : dataTableList;
    }
}
