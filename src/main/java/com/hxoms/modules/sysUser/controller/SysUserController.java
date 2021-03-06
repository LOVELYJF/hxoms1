package com.hxoms.modules.sysUser.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
 * @Param: [pageNum, pageSize, keyWord（用户名或者登陆名）, orgId（机构ID集合）]
 * @Return: com.hxoms.common.utils.Result
 * @Author: 李逍遥
 * @Date: 2020/4/28 15:45
 */
    @PostMapping("/getSysUserList")
    public Result getSysUserList(Integer pageNum, Integer pageSize, String keyWord, @RequestParam(value ="orgId",required = false) List<String> orgId) {
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
    @PostMapping("/insertOrUpDateSysUser")
    public Result insertOrUpDateSysUser(CfUser user) {
        sysUserService.InserOrUpdateSysUser(user);
        return Result.success();
    }
   /**
    * 功能描述: <br>
    * 〈按照登录名或者姓名查询用户〉
    * @Param: [keyWord]
    * @Return: com.hxoms.common.utils.Result
    * @Author: 李逍遥
    * @Date: 2020/5/20 17:25
    */
    @GetMapping("/getUserByCodeORName")
    public Result getUserByCodeORName(String keyWord){
        List<CfUser> user = sysUserService.getUserByCodeORName(keyWord);
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
    @GetMapping("/selectSysUserByUserId")
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
    @PostMapping("/deleteSysUser")
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
    @PostMapping("/updatePassword")
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
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestParam(value ="userId",required = false) List<String> userId) {
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
    @PostMapping("/updateUserState")
    public Result updateUserState(String userId , String state){
        sysUserService.updateUserState(userId , state);
        return Result.success();
    }
}
