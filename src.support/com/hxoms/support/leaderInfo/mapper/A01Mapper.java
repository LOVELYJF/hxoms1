package com.hxoms.support.leaderInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.support.leaderInfo.entity.A01;
import com.hxoms.support.leaderInfo.entity.A01WithBLOBs;
import com.hxoms.support.leaderInfo.util.LowerKeyMap;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface A01Mapper extends BaseMapper<A01> {
    int deleteByPrimaryKey(String a0100);

    int insert(A01WithBLOBs record);

    int insertSelective(A01WithBLOBs record);

    int updateByPrimaryKeySelective(A01WithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(A01WithBLOBs record);

    int updateByPrimaryKey(A01 record);

    /**
     * @desc: 查询所有信息
     * @author: sundeng
     * @date: 2019/7/26
     */
    List<LinkedHashMap<String, Object>> selectAllInfo();

    /**
     * @desc: 根据机构ID查询
     * @author: sundeng
     * @date: 2019/7/26
     */
    List<LinkedHashMap<String, Object>> selectByOrgId(String orgId);

    /**
     * @desc: 查询数据信息集
     * @author: sundeng
     * @date: 2019/7/26
     */
    List<LowerKeyMap<String, Object>>  selectLeaderInfoData(@Param("tablecode") String tablecode,@Param("id") String id);

    /**
     * @desc: 查询家庭成员
     * @author: sundeng
     * @date: 2019/7/29
     * @param id
     */
    List<Map<String,Object>> selectFamilyMember(String id);

    /**
     * @desc: 查询信息列
     * @author: sundeng
     * @date: 2019/7/30
     */
    List<Map<String,Object>> selectInfoLine();

    /**
     * @desc: 查询表信息
     * @author: sundeng
     * @date: 2019/8/1
     */
    List<Map<String,Object>> selectTableInfo(String tablecode);

    /**
     * @desc: 查询表列名
     * @author: sundeng
     * @date: 2019/8/1
     */
    List<Map<String,Object>> selectTableCol(String tablecode);
    
    /**
     * @desc: 导出任免表家庭成员情况
     * @author: sundeng
     * @date: 2019/8/28
     */
    List<Map<String,Object>> selectFamily(String id);

    /**
     * 功能描述: <br>
     * 〈根据关键字/姓氏查询结果〉
     * @Param: [name]
     * @Return: java.util.List<java.util.LinkedHashMap<java.lang.String,java.lang.Object>>
     * @Author: 李逍遥
     * @Date: 2020/4/29 11:42
     */
    List<LinkedHashMap<String, Object>> selectAllInfoByName(String name);


    /**
     * <b>查询人员政治面貌</b>
     * @param a0100
     * @author:luoshuai
     * @date 2020/5/15 14:05
     * @return
     */
    List<Map<String,Object>> selectPiliticalAffi(String a0100);


    /**
     * <b>查询人员信息</b>
     * @param a0100
     * @return
     */
    List<Map<String,Object>> selectPersonInfo(String a0100);
}