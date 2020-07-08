package com.hxoms.modules.sysUser.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.sysUser.entity.CfUser;

import java.util.List;


public interface SysUserService {
    /**
     * 功能描述: <br>
     * 〈查询用户列表〉
     * @Param: [pageNum, pageSize, keyWord, orgId]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/4/28 16:07
     */
    PageInfo getSysUserList(Integer pageNum, Integer pageSize, String keyWord, List<String> orgId);

    /**
     * 功能描述: <br>
     * 〈添加或修改用户〉
     * @Param: [user]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/28 16:07
     */
    void InserOrUpdateSysUser(CfUser user);

    /**
     * 功能描述: <br>
     * 〈按照登录名或者姓名查询用户〉
     * @Param: [keyWord]
     * @Return: com.hxoms.modules.sysUser.entity.OmsSysUser
     * @Author: 李逍遥
     * @Date: 2020/4/28 16:35
     */
    List<CfUser> getUserByCodeORName(String keyWord);

    /**
     * 功能描述: <br>
     * 〈根据用户ID删除用户〉
     * @Param: [userId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/28 16:43
     */
    void deleteSysUser(String userId);

    /**
     * 功能描述: <br>
     * 〈修改密码〉
     * @Param: [userID, newPassword]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/28 17:18
     */
    void updatePassword(String userID, String newPassword);

    /**
     * 功能描述: <br>
     * 〈根据用户ID查询用户〉
     * @Param: [userId]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/4/30 11:35
     */
    CfUser selectSysUserByUserId(String userId);

    /**
     * 功能描述: <br>
     * 〈根据用户ID，重置密码〉
     * @Param: [userId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/30 11:46
     */
    void resetPassword(List<String> userId);

    /**
     * 功能描述: <br>
     * 〈根据用户ID，更新用户状态〉
     * @Param: [userId, state]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/30 18:12
     */
    void updateUserState(String userId, String state);
}
