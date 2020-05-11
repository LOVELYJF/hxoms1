package com.hxoms.modules.sysUser.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: <br>
 * 〈系统设置-用户管理〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/4/28 16:56
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    public SysUserService sysUserService;

/**
 * 功能描述: <br>
 * 〈获取用户列表〉
 * @Param: [pageNum, pageSize, keyWord, orgId]
 * @Return: com.hxoms.common.utils.Result
 * @Author: 李逍遥
 * @Date: 2020/4/28 15:45
 */
    @RequestMapping("/getSysUserList")
    public Result getSysUserList(Integer pageNum, Integer pageSize, String keyWord, String orgId) {
        PageInfo pageInfo = sysUserService.getSysUserList(pageNum, pageSize, keyWord, orgId);
        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /**
     * 功能描述: <br>
     * 〈添加或修改用户〉
     * @Param: [user]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/4/28 15:46
     */
    @RequestMapping("/insertOrUpDateSysUser")
    public Result insertOrUpDateSysUser(CfUser user,CfUser loginUser) {
        sysUserService.InserOrUpdateSysUser(user,loginUser);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈按照登录名或者姓名查询用户〉
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/4/28 16:19
     */
    @RequestMapping("/getUserByCodeORName")
    public Result getUserByCodeORName(String keyWord){
        CfUser user = sysUserService.getUserByCodeORName(keyWord);
        return Result.success(user);
    }

    /**
     * 功能描述: <br>
     * 〈根据用户ID查询用户〉
     * @Param: [userId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/4/30 11:33
     */
    @RequestMapping("/selectSysUserByUserId")
    public Result selectSysUserByUserId(@RequestParam String userId) {
        CfUser user = sysUserService.selectSysUserByUserId(userId);
        return Result.success(user);
    }
    /**
     * 功能描述: <br>
     * 〈根据用户ID删除用户〉
     * @Param: [userId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/4/28 16:41
     */
    @RequestMapping("/deleteSysUser")
    public Result deleteSysUser(String userId){
        sysUserService.deleteSysUser(userId);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈修改密码〉
     * @Param: [userID, newPassword]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/4/28 17:18
     */
    @RequestMapping("/updatePassword")
    public Result updatePassword(String userId , String newPassword){
        sysUserService.updatePassword(userId , newPassword);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈根据用户ID，重置密码〉
     * @Param: [userId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/4/30 11:44
     */
    @RequestMapping("/resetPassword")
    public Result resetPassword(@RequestParam String userId) {
        sysUserService.resetPassword(userId);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈根据用户ID，更改用户状态〉
     * @Param: [userID, State]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/4/30 18:09
     */
    @RequestMapping("/updateUserState")
    public Result updateUserState(String userId , String state){
        sysUserService.updateUserState(userId , state );
        return Result.success();
    }
}
