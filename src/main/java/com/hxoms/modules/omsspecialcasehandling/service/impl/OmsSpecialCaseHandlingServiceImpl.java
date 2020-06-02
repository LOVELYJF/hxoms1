package com.hxoms.modules.omsspecialcasehandling.service.impl;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling;
import com.hxoms.modules.omsspecialcasehandling.mapper.OmsSpecialcasehandlingMapper;
import com.hxoms.modules.omsspecialcasehandling.service.OmsSpecialCaseHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OmsSpecialCaseHandlingServiceImpl implements OmsSpecialCaseHandlingService {
    @Autowired
    private OmsSpecialcasehandlingMapper specialcasehandlingMapper;
    /**
     * 功能描述: <br>
     * 〈新增特殊情况〉
     * @Param: [specialcasehandling]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:52
     */
    @Override
    public void insertSpecialCase(OmsSpecialcasehandling specialcasehandling) {
        if (specialcasehandling == null){
            throw new CustomMessageException("参数为空!");
        }
        specialcasehandling.setId(UUIDGenerator.getPrimaryKey());
        specialcasehandlingMapper.insertSelective(specialcasehandling);
    }

    /**
     * 功能描述: <br>
     * 〈通过姓名查询〉
     * @Param: [name]
     * @Return: com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:59
     */
    @Override
    public OmsSpecialcasehandling getSpecialCaseByName(String name) {
        if (name == null || name == ""){
            throw new  CustomMessageException("参数为空!");
        }
        OmsSpecialcasehandling omsSpecialcasehandling = specialcasehandlingMapper.selectByPrimaryKey(name);
        return omsSpecialcasehandling;
    }

    /**
     * 功能描述: <br>
     * 〈修改特殊情况人员〉
     * @Param: [omsSpecialcasehandling]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/2 11:06
     */
    @Override
    public void updateSpecialCase(OmsSpecialcasehandling omsSpecialcasehandling) {
        if(omsSpecialcasehandling == null || omsSpecialcasehandling.getId() == null){
            throw new CustomMessageException("参数为空！");
        }
        specialcasehandlingMapper.updateByPrimaryKeySelective(omsSpecialcasehandling);
    }

    @Override
    public void deleteSpecialCaseById(String id) {
        if (id == null || id == ""){
            throw new CustomMessageException("参数为空!");
        }
        specialcasehandlingMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> getAllSpecialCase() {
        //结果map
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> list = specialcasehandlingMapper.getAllSpecialCase();
        PageInfo info1 = new PageInfo(list);
        resultMap.put("info",info1);
        return resultMap;
    }
}
