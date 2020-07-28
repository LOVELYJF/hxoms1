package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcbatchMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcbatchPersonMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OmsRegProcbatchServiceImpl extends ServiceImpl<OmsRegProcbatchMapper, OmsRegProcbatch> implements OmsRegProcbatchService {


    @Autowired
    private OmsRegProcbatchPersonMapper regbatchPersonMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper regpersonInfoMapper;


    /**
     * 启动登记备案
     * @param regProcbatch
     * @return
     */
    @Override
    public Object startOmsReg(OmsRegProcbatch regProcbatch) {
        QueryWrapper<OmsRegProcbatch> queryWrapper = new QueryWrapper<OmsRegProcbatch>();
        Map<String, Object> map = new HashMap<String, Object>();
        //status 未备案0，已备案1，已确认2，
        queryWrapper.eq("STATUS","0");
        //查询是否有未备案批次
        int count = baseMapper.selectCount(queryWrapper);
        if (count > 0){
            throw new CustomMessageException("已经存在未确定备案完成的批次不能启动新的批次");
        }else{
            UserInfo user = UserInfoUtil.getUserInfo();
            regProcbatch.setRfUcontacts(user.getUserName());
            regProcbatch.setRfUphone(user.getUserMobile());
            return regProcbatch;
        }

    }

    @Override
    public Object insertProcbatch(OmsRegProcbatch regProcbatch) {
        UserInfo user = UserInfoUtil.getUserInfo();
        //创建批次
        regProcbatch.setId(UUIDGenerator.getPrimaryKey());
        //组织机构代码加yyyyMMdd
        String rfB0000 = regProcbatch.getRfB0000();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String day = sdf.format(new Date());
        regProcbatch.setBatchNo(rfB0000+day);
        regProcbatch.setSubmitTime(new Date());
        regProcbatch.setCreateUser(user.getId());
        regProcbatch.setCreateDate(new Date());
        return baseMapper.insert(regProcbatch);

    }

    @Override
    public int batchinsertInfo(List<OmsRegProcbatchPerson> orpbplist) {
        int con=0;
        //批量添加的方法
        int count = 30;
        int n1 = orpbplist.size() / count;
        int n2 = orpbplist.size() % count;
        if (n2 > 0) {
            n1 = n1 + 1;
        }
        for (int j = 0; j < n1; j++) {
            if ((j + 1) * count > orpbplist.size()) {
                //批量保存
                con = regbatchPersonMapper.batchinsertInfo(orpbplist.subList(j * count, orpbplist.size()));
            } else {
                con = regbatchPersonMapper.batchinsertInfo(orpbplist.subList(j * count, (j + 1) * count));
            }
        }
        return con;
    }

    @Override
    public int updateOrpbatch(OmsRegProcbatch batchinfo) {
        return baseMapper.updateById(batchinfo);
    }


    @Override
    public Object determineRegFinish() {
        QueryWrapper<OmsRegProcbatch> queryWrapper = new QueryWrapper<OmsRegProcbatch>();
        //状态：待备案
        queryWrapper.eq("status","0");

        //查询待备案批次
        OmsRegProcbatch regbatch = baseMapper.selectOne(queryWrapper);
        String batchId = regbatch.getId();

        //根据批次id查询该批次对应的人员a0100
        List<String> a0100s = regbatchPersonMapper.selectA0100s(batchId);
        QueryWrapper<OmsRegProcpersoninfo> personInfoWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        personInfoWrapper.in("A0100",a0100s);

        OmsRegProcpersoninfo omsreginfo = new OmsRegProcpersoninfo();
        //入库标识  新增U  修改I  撤消D
        omsreginfo.setInboundFlag("I");
        //备案状态  0未备案，1已备案，2已确认
        omsreginfo.setRfStatus("0");
        //验收状态  1已验收，0待验收
        omsreginfo.setCheckStatus("0");
        omsreginfo.setModifyTime(new Date());
        omsreginfo.setModifyUser("测试");

        //根据a0100s修改人员备案状态
        int con = regpersonInfoMapper.update(omsreginfo,personInfoWrapper);
        return con;
    }

    @Override
    public List<String> getHistoryBatch() {
        return baseMapper.getHistoryBatch();
    }

    @Override
    public OmsRegProcbatch selectWbaByOrpbatch() {
        //查询待备案批次
        OmsRegProcbatch regbatch = baseMapper.selectWbaByOrpbatch();
        return regbatch;
    }



}
