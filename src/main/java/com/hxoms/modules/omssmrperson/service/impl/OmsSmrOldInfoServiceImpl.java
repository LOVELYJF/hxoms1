package com.hxoms.modules.omssmrperson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OmsSmrOldInfoServiceImpl extends ServiceImpl<OmsSmrOldInfoMapper, OmsSmrOldInfo> implements OmsSmrOldInfoService {

    @Autowired
    OmsSmrOldInfoMapper smrOldInfoMapper;

    @Override
    public PageInfo<OmsSmrOldInfoVO> getSmrOldInfoById(Integer pageNum, Integer pageSize,String id){
        List<OmsSmrOldInfoVO> resultList = baseMapper.getSmrOldInfoList(id);
        PageUtil.pageHelp(pageNum, pageSize);

        PageInfo<OmsSmrOldInfoVO> pageInfo = new PageInfo(resultList);
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

    /** 获取脱密期确认列表 */
    @Override
    public Map<String, Object> getConfirmPeriodList() {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<OmsSmrOldInfo> list = smrOldInfoMapper.getConfirmPeriodList();
        resultMap.put("result", list);
        return resultMap;
    }
}
