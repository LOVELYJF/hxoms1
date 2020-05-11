package com.hxoms.modules.sysUser.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import com.hxoms.modules.sysUser.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private CfUserMapper cfUserMapper;

     /**
      * 功能描述: <br>
      * 〈查询用户列表〉
      * @Param: [pageNum, pageSize, keyWord (用户名或者登陆名), orgId]
      * @Return: com.github.pagehelper.PageInfo
      * @Author: 李逍遥
      * @Date: 2020/4/28 16:08
      */
    @Override
    public PageInfo getSysUserList(Integer pageNum, Integer pageSize, String keyWord, String orgId) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);   //设置传入页码，以及每页的大小
        List<CfUser> users = cfUserMapper.getSysUserList(keyWord, orgId);
        PageInfo info = new PageInfo(users);
        return info;
    }

   /**
    * 功能描述: <br>
    * 〈添加或修改用户〉
    * @Param: [user]
    * @Return: void
    * @Author: 李逍遥
    * @Date: 2020/4/28 16:08
    */
    @Override
    public void InserOrUpdateSysUser(CfUser user,CfUser loginUser) {
        if (user == null) {
            throw new CustomMessageException("用户不能为空!");
        }
        if (loginUser == null){
            throw new CustomMessageException("登录用户不能为空!");
        }
        if (StringUilt.isStrOrnull(user.getUserId())) {
            //更新用户（不用设置密码）
            //修改用户（未设置，1、从前端传过来 2、后台获取目前登录人员角色，根据角色创建状态）
            user.setModifyUser(loginUser.getUserName());
            //修改时间
            user.setModifyTime(new Date());
            cfUserMapper.updateByPrimaryKeySelective(user);

        } else {
            //新增用户（给定初始密码）
            //创建用户时，要判断登录名是否重复，并且在在非撤消和拒绝状态
            CfUser selectUser = cfUserMapper.selectByUserCode(user.getUserCode());
            if(selectUser != null){
                //判断状态
                String userState = selectUser.getUserState();
                if (userState.equals("2") || userState.equals("5")){
                    throw new CustomMessageException("登录名重复，请重新输入!");
                }
            }
            //设置ID
            user.setUserId(UUIDGenerator.getPrimaryKey());
            //设置初始密码
            user.setUserPassword(UUIDGenerator.encryptPwd(Constants.USER_PWD));
            //设置用户类型（未设置，从前端传或者手动设置（比较麻烦），1超级管理员、2系统管理员、3安全审计管理员、4安全保密管理员、5监督处工作人员、6经办人、7相关处室、8其他）

            //设置状态（未设置，1、从前端传过来 2、后台获取目前登录人员角色，根据角色创建状态）

            //创建人（未设置，1、从前端传过来 2、后台获取当前登录人，）
            user.setCreator(loginUser.getUserName());
            //创建时间
            user.setCreatetime(new Date());
            cfUserMapper.insertSelective(user);

        }

    }

    /**
     * 功能描述: <br>
     * 〈按照登录名或者姓名查询用户〉
     * @Param: [keyWord]
     * @Return: com.hxoms.modules.sysUser.entity.OmsSysUser
     * @Author: 李逍遥
     * @Date: 2020/4/28 16:36
     */
    @Override
    public CfUser getUserByCodeORName(String keyWord) {
        if (StringUtils.isEmpty(keyWord)) {
            throw new CustomMessageException("参数为空!");
        }
       CfUser user = cfUserMapper.selectByPrimaryKey(keyWord);
        if (user == null) {
            throw new CustomMessageException("用户不存在!");
        }

        return user;
    }

    /**
     * 功能描述: <br>
     * 〈根据用户ID删除用户〉
     * @Param: [userId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/28 16:44
     */
    @Override
    public void deleteSysUser(String userId) {
        if (StringUtils.isEmpty(userId)){
            throw new CustomMessageException("参数为空!");
        }
        cfUserMapper.deleteByPrimaryKey(userId);

    }

    /**
     * 功能描述: <br>
     * 〈修改密码〉
     * @Param: [userID, newPassword]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/28 17:19
     */
    @Override
    public void updatePassword(String userId, String newPassword) {

        if (StringUilt.stringIsNullOrEmpty(userId)) {
            throw new CustomMessageException("用户ID为空!");
        }
        if (StringUilt.stringIsNullOrEmpty(newPassword)) {
            throw new CustomMessageException("新密码为空!");
        }
        String selectPassword = cfUserMapper.selectPassword(userId);
        String password = UUIDGenerator.encryptPwd(newPassword);
        if (selectPassword.equals(password)){
            throw new CustomMessageException("新密码和原密码相同!");
        }
        //待确认，是否需要添加修改人及修改时间

        cfUserMapper.updatePassword(userId,password);
    }

    /**
     * 功能描述: <br>
     * 〈根据用户ID查询用户〉
     * @Param: [userId]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/4/30 11:35
     */
    @Override
    public CfUser selectSysUserByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new CustomMessageException("参数为空!");
        }
        CfUser user = cfUserMapper.selectSysUserByUserId(userId);
        if (user == null) {
            throw new CustomMessageException("用户不存在!");
        }
        return user;
    }

    /**
     * 功能描述: <br>
     * 〈根据用户ID，重置密码〉
     * @Param: [userId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/30 11:47
     */
    @Override
    public void resetPassword(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new CustomMessageException("参数为空!");
        }
        CfUser user = cfUserMapper.selectSysUserByUserId(userId);
        if (user == null) {
            throw new CustomMessageException("用户不存在!");
        }
        //待确认，是否需要添加修改用户及修改时间

        //重置密码
        String password = UUIDGenerator.encryptPwd(Constants.USER_PWD);
        cfUserMapper.updatePassword(userId,password);

    }

    /**
     * 功能描述: <br>
     * 〈根据用户ID，更新用户状态〉
     * @Param: [userId, state]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/30 18:13
     */
    @Override
    public void updateUserState(String userId, String state) {
        if (StringUilt.stringIsNullOrEmpty(userId)) {
            throw new CustomMessageException("用户ID为空!");
        }
        if (StringUilt.stringIsNullOrEmpty(state)) {
            throw new CustomMessageException("状态码为空!");
        }
        cfUserMapper.updateUserState(userId,state);
    }
}
