package com.hxoms.modules.passportCard.exitEntryManage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/17
 */
public interface OmsExitEntryManageService extends IService<OmsCerExitEntryRepertory> {

    /**
     * @Desc: 查询证照出入库记录
     * @Author: wangyunquan
     * @Param: [pageBean, omsCerExitEntryRepertory]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/17
     */
    PageBean selectExitEntryRecord(PageBean pageBean, OmsCerExitEntryRepertory omsCerExitEntryRepertory);
}
