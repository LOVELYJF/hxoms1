package com.hxoms.modules.omssmrperson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OmsSmrOldInfoServiceImpl extends ServiceImpl<OmsSmrOldInfoMapper, OmsSmrOldInfo> implements OmsSmrOldInfoService {


    @Override
    public PageInfo<OmsSmrOldInfo> getSmrOldInfoById(Integer pageNum, Integer pageSize,String id){
        List<OmsSmrOldInfo> resultList = baseMapper.getSmrOldInfoList(id);
        PageUtil.pageHelp(pageNum, pageSize);
        PageInfo<OmsSmrOldInfo> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    @Override
    public Object insert(OmsSmrOldInfo smrOldInfo) {
        return baseMapper.insert(smrOldInfo);
    }

    @Override
    public Object update(OmsSmrOldInfo smrOldInfo) {
        return null;
    }

    @Override
    public Object delete(String id) {
        return null;
    }

}
