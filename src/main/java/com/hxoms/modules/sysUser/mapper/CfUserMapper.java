package com.hxoms.modules.sysUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.sysUser.entity.CfUser;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CfUserMapper extends BaseMapper<CfUser> {
    /**
     * 功能描述: <br>
     * 〈获取用户列表〉
     * @Param: [keyWord, orgId]
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Author: 李逍遥
     * @Date: 2020/4/30 10:02
     */
    List<CfUser> getSysUserList(@Param("userName") String keyWord, @Param("orgId") List<String> orgId);
    /**
     * 功能描述: <br>
     * 〈新增用户〉
     * @Param: [user]
     * @Return: int
     * @Author: 李逍遥
     * @Date: 2020/4/30 10:12
     */
    int insert(CfUser user);
    /**
     * 功能描述: <br>
     * 〈更新用户〉
     * @Param: [user]
     * @Return: int
     * @Author: 李逍遥
     * @Date: 2020/4/30 10:12
     */
    int updateByPrimaryKey(CfUser user);
    int updateByPrimaryKeySelective(CfUser user);
    int insertSelective(CfUser record);
    /**
     * 功能描述: <br>
     * 〈删除用户〉
     * @Param: [userId]
     * @Return: int
     * @Author: 李逍遥
     * @Date: 2020/4/30 10:12
     */
    int deleteByPrimaryKey(String userId);

    /**
     * 功能描述: <br>
     * 〈根据用户名或登录名查找用户〉
     * @Param: [keyWord]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/4/30 10:43
     */
    List<CfUser> selectByPrimaryKey(@Param("userName") String keyWord);
    /**
     * 功能描述: <br>
     * 〈查询密码〉
     * @Param:
     * @Return:
     * @Author: 李逍遥
     * @Date: 2020/4/30 10:55
     */
    String selectPassword(String userId);

    /**
     * 功能描述: <br>
     * 〈修改密码〉
     * @Param: [userID, password]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/30 11:07
     */
    void updatePassword(@Param("userId") String userId, @Param("password") String password);

    /**
     * 功能描述: <br>
     * 〈通过用户ID查询用户〉
     * @Param: [userId]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/4/30 14:35
     */
    CfUser selectSysUserByUserId(String userId);

    /**
     * 功能描述: <br>
     * 〈通过登录名查询用户〉
     * @Param: [userCode]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/4/30 14:31
     */
    CfUser selectByUserCode(String userCode);

    /**
     * 功能描述: <br>
     * 〈更新用户状态〉
     * @Param: [userId, state]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/4/30 18:19
     */
    void updateUserState(String userId, String state);

    /**
     * 功能描述: <br>
     * 〈获取某个机构下的经办人〉
     * @Param: [orgId, userType]
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Author: 李逍遥
     * @Date: 2020/5/7 16:09
     */
    List<CfUser> getSysUserListByParm(List<String> orgId, String userType);

    /**
     * 功能描述: <br>
     * 〈通过身份证号、用户类型、状态,查找经办人〉
     * @Param: [idnumber, userType, state]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/5/7 16:10
     */
    CfUser getOperatorByIdnumAndState(String idnumber, String userType, String state);

    /**
     * 功能描述: <br>
     * 〈通过姓名或者状态列表查询经办人列表〉
     * @Param: [name, state]
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Author: 李逍遥
     * @Date: 2020/5/7 16:11
     */
    List<CfUser> getOperatorByNameOrState(String name, List<String> state, List<String> orgIds);

    /**
     * 功能描述: <br>
     * 〈查询经办人审批列表〉
     * @Param: [keyWord, orgId]
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Author: 李逍遥
     * @Date: 2020/5/13 10:56
     */
    List<CfUser> getOperatorApprovalList(@Param("userName")String keyWord, @Param("orgIds") List<String> orgIds, @Param("userType") String userType, @Param("states") List<String> states);


    /**
     * 功能描述: <br>
     * 〈通过身份证读取设备匹配经办人〉
     * @Param: [idcard, states, userType]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/5/15 10:06
     */
    CfUser getOperatorApprovalByIdCard(@Param("idCard")String idCard,@Param("states") String states,@Param("userType") String userType);
}