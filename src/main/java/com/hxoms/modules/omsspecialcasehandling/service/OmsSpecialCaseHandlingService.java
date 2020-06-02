package com.hxoms.modules.omsspecialcasehandling.service;

import com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling;

import java.util.Map;

public interface OmsSpecialCaseHandlingService {
    /**
     * 功能描述: <br>
     * 〈新增特殊情况〉
     * @Param: [specialcasehandling]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:51
     */
    void insertSpecialCase(OmsSpecialcasehandling specialcasehandling);

    /**
     * 功能描述: <br>
     * 〈通过姓名查询〉
     * @Param: [name]
     * @Return: com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:59
     */
    OmsSpecialcasehandling getSpecialCaseByName(String name);

    /**
     * 功能描述: <br>
     * 〈修改特殊情况人员〉
     * @Param: [omsSpecialcasehandling]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/2 11:05
     */
    void updateSpecialCase(OmsSpecialcasehandling omsSpecialcasehandling);

    /**
     * 功能描述: <br>
     * 〈通过ID删除特殊情况人员〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/2 11:11
     */
    void deleteSpecialCaseById(String id);

    /**
     * 功能描述: <br>
     * 〈查询列表〉
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/6/2 11:16
     */
    Map<String, Object> getAllSpecialCase();

}
