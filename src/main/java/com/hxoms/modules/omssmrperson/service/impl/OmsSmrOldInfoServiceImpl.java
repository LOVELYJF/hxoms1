package com.hxoms.modules.omssmrperson.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class OmsSmrOldInfoServiceImpl extends ServiceImpl<OmsSmrOldInfoMapper, OmsSmrOldInfo> implements OmsSmrOldInfoService {


    @Override
    public IPage<OmsSmrOldInfo> getSmrOldInfoById(String A0100) throws ParseException {
        if(!StringUtils.isBlank(A0100)){
            return baseMapper.getSmrOldInfoList(A0100);
        }else{
            return null;
        }
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
