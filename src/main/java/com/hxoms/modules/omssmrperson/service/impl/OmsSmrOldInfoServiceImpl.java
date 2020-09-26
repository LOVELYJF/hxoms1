package com.hxoms.modules.omssmrperson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OmsSmrOldInfoServiceImpl extends ServiceImpl<OmsSmrOldInfoMapper, OmsSmrOldInfo> implements OmsSmrOldInfoService {

    @Autowired
    OmsSmrOldInfoMapper smrOldInfoMapper;

    @Override
    public PageInfo<OmsSmrOldInfoVO> getSmrOldInfoById(Integer pageNum, Integer pageSize, String id) {
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

    /**
     * 获取脱密期确认列表
     */
    @Override
    public Map<String, Object> getConfirmPeriodList() {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<OmsSmrOldInfoVO> list = smrOldInfoMapper.getConfirmPeriodList(UserInfoUtil.getUserInfo().getOrgId(), "", "");
        resultMap.put("result", list);
        return resultMap;
    }

    @Override
    public Map<String, Object> getSmrMaintainList() {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<OmsSmrOldInfoVO> list = smrOldInfoMapper.getSmrMaintainList(UserInfoUtil.getUserInfo().getOrgId(), "", "");
        resultMap.put("result", list);
        return resultMap;
    }

    @Override
    public Result updateSmrOldInfo(List<OmsSmrOldInfoVO> smrOldInfos) {
        Calendar calc = Calendar.getInstance();
        List<OmsSmrOldInfo> updates = new ArrayList<>();
        String staffs = "";
        for (OmsSmrOldInfoVO smrOldInfo : smrOldInfos
        ) {
            if(smrOldInfo.getStartDate()!=null&&smrOldInfo.getQrStartDate()!=null){
                calc.setTime(smrOldInfo.getQrStartDate());
                calc.add(Calendar.DATE, 40);
                if (calc.getTime().before(smrOldInfo.getStartDate())) {
                    staffs += smrOldInfo.getA0101() + ",";
                } else {
                    smrOldInfo.setSfqr("1");
                    updates.add(smrOldInfo);
                }
            }
            else{
                updates.add(smrOldInfo);
            }
        }
        if (updates.size() > 0)
            this.updateBatchById(updates);

        if (!"".equals(staffs))
            return Result.error("以下人员的确定脱密开始时间早于脱密时间40天以上，系统不于保存:" + staffs);

        return Result.success("保存成功！");
    }
}
