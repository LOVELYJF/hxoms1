package com.hxoms.modules.passportCard.exitEntryManage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo;
import com.hxoms.modules.passportCard.exitEntryManage.mapper.OmsCerExitEntryRepertoryMapper;
import com.hxoms.modules.passportCard.exitEntryManage.service.OmsExitEntryManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/17
 */
public class OmsExitEntryManageServiceImpl extends ServiceImpl<OmsCerExitEntryRepertoryMapper, OmsCerExitEntryRepertory> implements OmsExitEntryManageService {
   @Autowired
   private OmsCerExitEntryRepertoryMapper omsCerExitEntryRepertoryMapper;


    /**
     * @Desc: 查询证照出入库记录
     * @Author: wangyunquan
     * @Param: [pageBean, omsCerExitEntryRepertory]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/17
     */
    @Override
    public PageBean selectExitEntryRecord(PageBean pageBean, OmsCerExitEntryRepertory omsCerExitEntryRepertory) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<OmsCerExitEntryRepertory> pageInfo=new PageInfo<OmsCerExitEntryRepertory>(omsCerExitEntryRepertoryMapper.selectExitEntryRecord(omsCerExitEntryRepertory));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 查看签名
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: java.util.List<com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo>
     * @Date: 2020/8/17
     */
    @Override
    public List<ExitEntrySignInfo> selectSignById(String id) {
        return omsCerExitEntryRepertoryMapper.selectSignById(id);
    }
}
