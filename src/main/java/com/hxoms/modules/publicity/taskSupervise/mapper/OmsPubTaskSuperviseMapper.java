package com.hxoms.modules.publicity.taskSupervise.mapper;

import com.hxoms.modules.publicity.taskSupervise.entity.UrgeParameterVO;
import com.hxoms.modules.publicity.taskSupervise.entity.ZtDwPersionQuery;
import com.hxoms.modules.publicity.taskSupervise.entity.ZtDwPersionVO;
import com.hxoms.modules.publicity.taskSupervise.entity.ZtDwTreeVO;
import com.hxoms.support.user.entity.User;

import java.util.List;

public interface OmsPubTaskSuperviseMapper{

    /**
     * @Desc: 查询经办人所在单位的团组
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.ZtDwVO>
     * @Date: 2020/6/19
     */
    List<ZtDwTreeVO> selectZtDwApplyList(String id);

    /**
     * @Desc: 查询团组人员
     * @Author: wangyunquan
     * @Param: [id, year, ztDwName]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.ZtDwPersionVO>
     * @Date: 2020/6/23
     */
    List<ZtDwPersionVO> selectZtDwPersonAll(String id, String year, String ztDwName);

    /**
     * @Desc: 查询团组人员
     * @Author: wangyunquan
     * @Param: [ztDwPersionQuery]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.ZtDwPersionVO>
     * @Date: 2020/6/23
     */
    List<ZtDwPersionVO> selectZtDwPersonByQuaAll(ZtDwPersionQuery ztDwPersionQuery);
    /**
     * @Desc: 查询办理催办所需参数
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: com.hxoms.modules.publicity.entity.UrgeParameterVO
     * @Date: 2020/6/29
     */
    UrgeParameterVO selectById(String id);
    /**
     * @Desc: 查询用户
     * @Author: wangyunquan
     * @Param: [orgId, userType]
     * @Return: java.util.List<com.hxoms.support.user.entity.User>
     * @Date: 2020/6/29
     */
    List<User> selectUserByQua(String orgId, String userType);
}