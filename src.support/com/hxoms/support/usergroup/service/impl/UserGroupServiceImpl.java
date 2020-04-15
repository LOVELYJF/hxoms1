package com.hxoms.support.usergroup.service.impl;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.tree.TreeUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.support.role.entity.Role;
import com.hxoms.support.role.mapper.RoleMapper;
import com.hxoms.support.usergroup.entity.UserGroup;
import com.hxoms.support.usergroup.entity.UserGroupUser;
import com.hxoms.support.usergroup.mapper.UserGroupMapper;
import com.hxoms.support.usergroup.service.UserGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：张乾
 * @description：用户组service实现类
 * @createDate ： 2019/5/30 10:21
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询业务系统管理处室树
     *
     * @author sunqian
     * @date 2019/7/1 15:20
     */
    @Override
    public List<Tree> selectUserGroupTree(String id) {

        return TreeUtil.listToTreeJson(userGroupMapper.selectUserGroupTree(id));
    }

    @Override
    public void insertUserGroup(UserGroup userGroup) {
        if (userGroup == null) {
            throw new CustomMessageException("参数不能为空");
        }
        if (StringUtils.isBlank(userGroup.getUgName())) {
            throw new CustomMessageException("名称不能为空");
        }
        if (StringUtils.isBlank(userGroup.getOrgId())) {
            throw new CustomMessageException("所属机构不能为空");
        }
        userGroup.setId(UUIDGenerator.getPrimaryKey());
        userGroup.setModifyTime(new Date());
        userGroup.setModifyUser(UserInfoUtil.getUserInfo().getId());
        userGroupMapper.insertSelective(userGroup);
    }

    @Override
    public void updateUserGroup(UserGroup userGroup) {
        if (userGroup == null) {
            throw new CustomMessageException("参数不能为空");
        }
        if (StringUtils.isBlank(userGroup.getUgName())) {
            throw new CustomMessageException("用户组名称不能为空");
        }
        if (StringUtils.isBlank(userGroup.getId())) {
            throw new CustomMessageException("用户组id不能为空");
        }
        userGroup.setModifyTime(new Date());
        userGroup.setModifyUser(UserInfoUtil.getUserInfo().getId());
        userGroupMapper.updateByPrimaryKeySelective(userGroup);
    }

    @Override
    public void deleteUserGroup(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("用户组id不能为空");
        }
        //删除用户组
        userGroupMapper.deleteByPrimaryKey(id);
        //删除用户组角色关系
        userGroupMapper.deleteUserGroupRoles(id);
        //删除用户组用户关系
        userGroupMapper.deleteUserGroupUser(id);
    }

    @Override
    public List<Tree> selectUserGroupUserTree(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            throw new CustomMessageException("机构id不能为空");
        }
        List<Tree> list = userGroupMapper.selectUserGroupUserTree(orgId);
        return TreeUtil.listToTreeJson(list);
    }

    @Override
    public List<UserGroup> selectUserGroupList(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            throw new CustomMessageException("机构id不能为空");
        }
        return userGroupMapper.selectUserGroupList(orgId);
    }

    @Override
    public void assignUgIdForUser(String ugId, String userId) {
        if (StringUtils.isBlank(ugId)) {
            throw new CustomMessageException("用户组id不能为空");
        }
        if (StringUtils.isBlank(userId)) {
            throw new CustomMessageException("用户id不能为空");
        }
        //删除已分配的用户组
        userGroupMapper.deleteAssignUserGroup(userId);
        //重新分配用户组
        Map map = new HashMap<>();
        map.put("id", UUIDGenerator.getPrimaryKey());
        map.put("ugId", ugId);
        map.put("userId", userId);
        userGroupMapper.insertUgAndUser(map);
    }

    @Override
    public Map<String, Object> selectRoleWithUserGroupAndUser(String type, String id, String orgId) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(orgId)) {
            throw new CustomMessageException("机构id为空");
        }
        List<Role> assignRoleList = selectAssignRole(type, id);
        //获取所有的角色
        List<Role> roleList = roleMapper.selectRoleList(null, orgId);
        map.put("list", roleList);
        if (roleList == null || roleList.isEmpty()) {
            throw new CustomMessageException("当前机构下没有角色");
        } else if (assignRoleList == null || assignRoleList.isEmpty()) {
            return map;
        }
        List<String> list = new ArrayList<>();
        for (Role role : assignRoleList) {
            list.add(role.getId());
            if ("true".equals(role.getExtend())) {
                for (Role role1 : roleList) {
                    if (role.getId().equals(role1.getId())) {
                        role1.setExtend("true");
                        break;
                    }
                }
            }
        }
        map.put("checkId", list);
        return map;
    }

    public List<Role> selectAssignRole(String type, String id) {
        if (StringUtils.isBlank(type) || StringUtils.isBlank(id)) {
            throw new CustomMessageException("参数不能为空");
        }
        List<Role> roleList;
        //判断是用户组还是用户
        if ("user".equals(type)) {
            roleList = roleMapper.selectUserAssignRoles(id);
        } else if ("usergroup".equals(type)) {
            roleList = roleMapper.selectUserGroupAssignRoles(id);
        } else {
            throw new CustomMessageException("type类型不存在");
        }
        return roleList;
    }

    @Override
    public void assignRolesForUserOrUserGroup(String type, String id, List<String> roleIds) {
        if (StringUtils.isBlank(type) || StringUtils.isBlank(id)) {
            throw new CustomMessageException("类型或者id不能为空");
        }
        if ("user".equals(type)) {
            //先删除
            userGroupMapper.deleteAssignUserRoles(id);
            if (roleIds != null && !roleIds.isEmpty()) {
                userGroupMapper.assignRolesForUser(id, roleIds);
            }
        } else if ("usergroup".equals(type)) {
            //先删除
            userGroupMapper.deleteAssignUserGroupRoles(id);
            if (roleIds != null && !roleIds.isEmpty()) {
                userGroupMapper.assignRolesForUserGroup(id, roleIds);
            }
        } else {
            throw new CustomMessageException("授权类型不存在:[" + type + "]");
        }
    }
    /**
     * @description:通过ID获取用户组
     * @author:杨波
     * @date:2019-07-17
     *  * @param String id
     * @return:
     **/
    @Override
    public UserGroup selectByPrimaryKey(String id){
        return userGroupMapper.selectByPrimaryKey(id);
    }
    /**
     * @description:通过用户组ID，查找它的所有用户
     * @author:杨波
     * @date:2019-07-17
     *  * @param String ugid
     * @return:
     **/
    @Override
    public List<UserGroupUser> selectUserGroupUsers(String ugid){

        return userGroupMapper.selectUserGroupUsers(ugid);
    }

    @Override
    public UserGroupUser selectUserGroupUserByUserId(String userId) {
        return userGroupMapper.selectUserGroupUser(userId);
    }
}
