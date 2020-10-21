package com.hxoms.modules.passportCard.exitEntryManage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.ExportExcelUtil;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.CerExitEntryInfo;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.CerExitEntryInfoExport;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.CerInfo;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo;
import com.hxoms.modules.passportCard.exitEntryManage.mapper.OmsCerExitEntryRepertoryMapper;
import com.hxoms.modules.passportCard.exitEntryManage.service.OmsExitEntryManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/17
 */
@Service
public class OmsExitEntryManageServiceImpl extends ServiceImpl<OmsCerExitEntryRepertoryMapper, OmsCerExitEntryRepertory> implements OmsExitEntryManageService {
   @Autowired
   private OmsCerExitEntryRepertoryMapper omsCerExitEntryRepertoryMapper;

    /**
     * @Desc: 证照信息管理-出入库记录-导出
     * @Author: wuqingfan
     * @Param: [ids]
     * @Return: excel
     * @Date: 2020/9/14
     */
    @Override
    public void exitEntryRecordExport(List<String> ids, HttpServletResponse response){
        if (ids == null || ids.size() < 1) {
            throw new CustomMessageException("操作失败！");
        }
        List<CerExitEntryInfoExport> getList=omsCerExitEntryRepertoryMapper.exitEntryRecordExport(ids);

        String[] headers="序号,单位,姓名,证件类型,证件号码,进出方式,存取日期,存取人,存取方式".split(",");
        ExportExcelUtil.exportNotTitleExcel("出入库记录",headers,(List) getList,response);
    }

    /**
     * @Desc: 查询证照出入库记录
     * @Author: wangyunquan
     * @Param: [pageBean, cerInfo]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.CerExitEntryInfo>
     * @Date: 2020/8/17
     */

    @Override
    public PageBean<CerExitEntryInfo> selectExitEntryRecord(PageBean pageBean, CerInfo cerInfo) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CerExitEntryInfo> pageInfo=new PageInfo<CerExitEntryInfo>(omsCerExitEntryRepertoryMapper.selectExitEntryRecord(cerInfo));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 查看签名
     * @Author: wangyunquan
     * @Param: [cerId]
     * @Return: java.util.List<com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo>
     * @Date: 2020/8/17
     */
    @Override
    public List<ExitEntrySignInfo> selectSignByGetId(String getId) {
        return omsCerExitEntryRepertoryMapper.selectSignById(getId);
    }
}
