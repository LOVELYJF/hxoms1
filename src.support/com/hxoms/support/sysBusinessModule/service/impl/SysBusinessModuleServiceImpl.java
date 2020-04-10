package com.hxoms.support.sysBusinessModule.service.impl;

import com.hxoms.common.tree.Tree;
import com.hxoms.common.tree.TreeUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.support.sysBusinessModule.entity.SysBusinessModule;
import com.hxoms.support.sysBusinessModule.mapper.SysBusinessModuleMapper;
import com.hxoms.support.sysBusinessModule.service.SysBusinessModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysBusinessModuleServiceImpl implements SysBusinessModuleService {

    @Autowired
    private SysBusinessModuleMapper sysBusinessModuleMapper;

    @Override
    public List<SysBusinessModule> selectSysBusinessModuleByPid(String pid) {
        List<SysBusinessModule> sysBusinessModules = sysBusinessModuleMapper.selectSysBusinessModuleByPid(pid);
        return sysBusinessModules;
    }

    @Override
    public SysBusinessModule selectSysBusinessModuleById(String id) {
        SysBusinessModule sysBusinessModule = sysBusinessModuleMapper.selectByPrimaryKey(id);
        return sysBusinessModule;
    }

    @Override
    public void insertSysBusinessModule(SysBusinessModule sysBusinessModule) {
        sysBusinessModule.setId(UUIDGenerator.getPrimaryKey());
        sysBusinessModule.setModifyTime(new Date());
        sysBusinessModule.setModifyUser(UserInfoUtil.getUserInfo().getId());
        sysBusinessModuleMapper.insertSelective(sysBusinessModule);
    }

    @Override
    public void updateSysBusinessModule(SysBusinessModule sysBusinessModule) {
        sysBusinessModule.setModifyTime(new Date());
        sysBusinessModule.setModifyUser(UserInfoUtil.getUserInfo().getId());
        sysBusinessModuleMapper.updateByPrimaryKeySelective(sysBusinessModule);
    }

    @Override
    public void deleteSysBusinessModule(String id) {
        sysBusinessModuleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Tree> selectSysBusinessModuleTree(String systemId) {
        List<Tree> trees = TreeUtil.listToTreeJson(sysBusinessModuleMapper.selectTree(systemId));
        return trees;
    }

    @Override
    public void updateSortSysBusinessModule(String ids[]) {
        sysBusinessModuleMapper.updateSortSysBusinessModule(ids);
    }


}
