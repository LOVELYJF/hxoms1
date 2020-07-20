package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcbatchMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcbatchPersonMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        List<OmsRegProcbatch> procbatchList = null;
        QueryWrapper<OmsRegProcbatch> queryWrapper = new QueryWrapper<OmsRegProcbatch>();
        //status 未备案0，已备案1，已确认2，
        queryWrapper.eq("STATUS","0");
        //查询是否有未备案批次
        int count = baseMapper.selectCount(queryWrapper);
        if (count > 0){
            procbatchList = baseMapper.selectList(queryWrapper);
            return procbatchList;
        }else{
            //创建批次
            regProcbatch.setId(UUIDGenerator.getPrimaryKey());
            //组织机构代码加yyyyMMdd
            regProcbatch.setBatchNo(regProcbatch.getBatchNo());
            regProcbatch.setCreateUser("");
            regProcbatch.setCreateDate(new Date());
            return baseMapper.insert(regProcbatch);
        }
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
    public ExcelModelORPinfo selectWbaByOrpbatch() {
        //查询待备案批次
        ExcelModelORPinfo regbatch = baseMapper.selectWbaByOrpbatch();
        return regbatch;
    }


}
