package com.hxoms.modules.omsmobilizingcadres.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre;
import com.hxoms.modules.omsmobilizingcadres.mapper.OmsMobilizingcadreMapper;
import com.hxoms.modules.omsmobilizingcadres.service.MobilizingcadreService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MobilizingcadreServiceImpl implements MobilizingcadreService {

    @Autowired
    OmsMobilizingcadreMapper mobilizingcadreMapper;

    /**
     * 功能描述: <br>
     * 〈添加调整期干部〉
     * @Param:
     * @Return:
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:09
     */
    @Override
    public void insertMobilizingCadre(OmsMobilizingcadre mobilizingCadre) {
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        if (mobilizingCadre == null){
            throw new CustomMessageException("调整期干部为空!");
        }
        if (loginUser == null){
            throw new CustomMessageException("当前登录用户为空!");
        }
        if (mobilizingCadre.getId() != null){
            throw new CustomMessageException("id重复!");
        }
        mobilizingCadre.setId(UUIDGenerator.getPrimaryKey());
        mobilizingCadre.setCreateDate(new Date());
        mobilizingCadre.setCreateUser(loginUser.getUserName());
        //添加时将状态更改为调整期
        mobilizingCadre.setStatus(String.valueOf(Constants.mobilizing_business[0]));
        mobilizingcadreMapper.insertSelective(mobilizingCadre);

    }

    /**
     * 功能描述: <br>
     * 〈根据ID删除调整期干部〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:15
     */
    @Override
    public void deleteMobilizingCadre(String id) {
        if (id == null){
            throw new CustomMessageException("参数为空!");
        }
        mobilizingcadreMapper.deleteByPrimaryKey(id);

    }

    /**
     * 功能描述: <br>
     * 〈通过人员主键查找调整期干部〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:23
     */
    @Override
    public OmsMobilizingcadre getMobilizingCadreByID(String id) {

        if (id == null){
            throw new CustomMessageException("参数为空!");
        }
        OmsMobilizingcadre omsMobilizingcadre = mobilizingcadreMapper.selectByPrimaryKey(id);
        return omsMobilizingcadre;
    }

    /**
     * 功能描述: <br>
     * 〈通过机构或者条件查找全部调整期干部列表〉
     * @Param: [orgIds, name, status]
     * @Return: java.util.List<com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre>
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:45
     */
    @Override
    public PageInfo getAllMobilizingCadre(Integer pageNum, Integer pageSize,List<String> orgIds, String name, String status) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (orgIds == null){
            //获取登录用户信息
            UserInfo loginUser = UserInfoUtil.getUserInfo();
            if (loginUser.getOrgId() != null ){
                orgIds.add(loginUser.getOrgId());
            }
        }
        PageHelper.startPage(pageNum, pageSize);   //设置传入页码，以及每页的大小
        List<LinkedHashMap<String, Object>> list = mobilizingcadreMapper.selectAllMobilizingCadre(orgIds,name,status);
        PageInfo info = new PageInfo(list);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈每天自动拉取干部信息库信息更改调整期状态〉
     * @Param: [a0100]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/23 11:22
     */
    @Override
    public void updateStatus(String a0100) {
        if (a0100 == null || a0100.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        //进入方法将状态更改为 调整完成
        String s = String.valueOf(Constants.mobilizing_business[1]);
        mobilizingcadreMapper.updateStatusByA0100(a0100,s);

    }

}
