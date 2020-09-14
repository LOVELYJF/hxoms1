package com.hxoms.modules.passportCard.exitEntryManage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.CerInfo;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/17
 */
public interface OmsExitEntryManageService extends IService<OmsCerExitEntryRepertory> {

    /**
     * @Desc: 证照信息管理-出入库记录-导出
     * @Author: wuqingfan
     * @Param: [ids]
     * @Return: excel
     * @Date: 2020/9/14
     */
    void exitEntryRecordExport(List<String> ids, HttpServletResponse response);

    /**
     * @Desc: 查询证照出入库记录
     * @Author: wangyunquan
     * @Param: [pageBean, cerInfo]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/17
     */
    PageBean selectExitEntryRecord(PageBean pageBean, CerInfo cerInfo);

    /**
     * @Desc: 查看签名
     * @Author: wangyunquan
     * @Param: [getId]
     * @Return: java.util.List<com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo>
     * @Date: 2020/8/17
     */
    List<ExitEntrySignInfo> selectSignByGetId(String getId);

}


