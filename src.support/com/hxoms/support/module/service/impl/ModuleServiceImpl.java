package com.hxoms.support.module.service.impl;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.tree.TreeUtil;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.support.module.entity.Module;
import com.hxoms.support.module.entity.ModuleTree;
import com.hxoms.support.module.mapper.ModuleMapper;
import com.hxoms.support.module.service.ModuleService;
import com.hxoms.support.user.entity.User;
import com.hxoms.support.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: 模块管理CRUD
 * @Author: sunqian
 * @CreateDate: 2019/5/17 18:35
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Tree> selectModuleTree(UserInfo userInfo) {
        List<Tree> list = moduleMapper.selectModuleTree();
        return TreeUtil.listToTreeJson(list);
    }

    @Override
    public void insertModule(Module module) {
        if (module == null) {
            throw new CustomMessageException("参数为空");
        }
        if (StringUtils.isBlank(module.getMuName())) {
            throw new CustomMessageException("名称不能为空");
        }
        if (StringUtils.isBlank(module.getMuCode())) {
            throw new CustomMessageException("编码不能为空");
        }
        //校验编码是否存在
        Module valModule = moduleMapper.selectModuleByCode(module.getMuCode());
        if (valModule != null) {
            throw new CustomMessageException("该编码已经存在");
        }
        module.setMuId(UUIDGenerator.getPrimaryKey());
        module.setModifyUser(UserInfoUtil.getUserInfo().getId());
        module.setModifyTime(new Date());
        moduleMapper.insertSelective(module);
    }

    @Override
    public List<Module> selectModulesByPid(String pId) {
        if (pId == null) {
            throw new CustomMessageException("参数pId不能为空");
        }
        List<Module> moduleList = moduleMapper.selectModuleList();
        if (moduleList != null && !moduleList.isEmpty()) {
            Iterator<Module> iterator = moduleList.iterator();
            while (iterator.hasNext()) {
                Module next = iterator.next();
                if (!pId.equals(next.getMuPid())) {
                    iterator.remove();
                }
            }
        }
        return moduleList == null ? new ArrayList<>() : moduleList;
    }

    @Override
    public Module selectModuleById(String id) {
        return moduleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateModule(Module module) {
        if (module == null) {
            throw new CustomMessageException("参数为空");
        }
        if (StringUtils.isBlank(module.getMuId())) {
            throw new CustomMessageException("id不能为空");
        }
        moduleMapper.updateByPrimaryKeySelective(module);
    }

    @Override
    public void deleteModuleById(String id) {
        if (id == null) {
            throw new CustomMessageException("参数id不能为空");
        }
        //判断是否有子节点
        List<Module> modules = selectModulesByPid(id);
        if (modules == null || modules.isEmpty()) {
            moduleMapper.deleteByPrimaryKey(id);
        } else {
            throw new CustomMessageException("有子节点，不能删除");
        }
    }

    @Override
    public List<Module> selectCurrGrantModule(String userId) {
        if (userId == null) {
            userId = UserInfoUtil.getUserInfo().getId();
        }
        //获取当前登录用户
        User user = userMapper.selectByPrimaryKey(userId);
        List<Module> currModuleList = null;
        if (Constants.USER_TYPES[0].equals(user.getUserType())) {
            //管理员权限
            currModuleList = moduleMapper.selectOrgGrantModule(user.getOrgId());
        } else {
            //普通用户权限
            currModuleList = moduleMapper.selectUserGrantModules(user.getId());
        }
        return currModuleList == null ? new ArrayList<>() : currModuleList;
    }

    @Override
    public Map<String, Object> selectGrantModuleTree(String userId) {
        Map<String, Object> map = new HashMap<>();
        List<Module> moduleList = selectCurrGrantModule(userId);
        List<Tree> moduleTrees = new ArrayList<>();
        if (moduleList != null && !moduleList.isEmpty()) {
            for (Module module : moduleList) {
                ModuleTree mt = new ModuleTree();
                mt.setId(module.getMuId());
                mt.setUrl(module.getUrl());
                mt.setLabel(module.getMuName());
                mt.setpId(module.getMuPid());
                mt.setIcon(module.getMuIcon());
                moduleTrees.add(mt);
            }
        }
        map.put("moduleTree", TreeUtil.listToTreeJson(moduleTrees));
        map.put("moduleList",moduleList);
        return map;
    }

    @Override
    public List<Module> selectRouterList() {
        return moduleMapper.selectRouterList();
    }
}
