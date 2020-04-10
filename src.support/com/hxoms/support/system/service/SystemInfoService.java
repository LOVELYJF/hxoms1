package com.hxoms.support.system.service;

import com.hxoms.common.tree.Tree;
import com.hxoms.support.system.entity.SystemInfo;

import java.util.List;

public interface SystemInfoService {

    List<Tree> selectSystemInfoTree();

    /**
     * @Author sunqian
     * @Desc 查询子节点
     * @Date 2019/6/18 14:17
     * @param systemInfo
     */
    List<SystemInfo> selectKidsSysInfo(SystemInfo systemInfo);

    /**
     * @Author sunqian
     * @Desc 新增
     * @Date 2019/6/18 14:41
     */
    void insertSystemInfo(SystemInfo systemInfo);

    /**
     * @Author sunqian
     * @Desc 更新
     * @Date 2019/6/18 14:57
     */
    void updateSystemInfo(SystemInfo systemInfo);

    /**
     * @Author sunqian
     * @Desc 删除
     * @Date 2019/6/18 15:01
     */
    void deleteSystemInfo(SystemInfo systemInfo);

    /**
     * @Author sunqian
     * @Desc 根据id查询
     * @Date 2019/6/18 15:17
     */
    SystemInfo selectSysInfoByPrimaryKey(String id);

    /**
     * 获取登录用户所具有的系统权限
     * 
     * @author sunqian
     * @date 2019/8/22 11:23
     */
    List<SystemInfo> selectCurrUserGrantSystemInfo();
}
