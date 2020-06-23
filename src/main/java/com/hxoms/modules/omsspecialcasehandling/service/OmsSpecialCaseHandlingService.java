package com.hxoms.modules.omsspecialcasehandling.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling;

import java.util.List;
import java.util.Map;

public interface OmsSpecialCaseHandlingService {
    /**
     * 功能描述: <br>
     * 〈新增或修改特殊情况〉
     * @Param: [specialcasehandling]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:51
     */
    void insertOrUpdateSpecialCase(OmsSpecialcasehandling specialCaseHandling);

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
    PageInfo getAllSpecialCase(Integer pageNum, Integer pageSize, String keyWord);

}
