package com.hxoms.modules.passportCard.initialise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.ExportExcelUtil;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.enums.LicenceIdentityEnum;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.enums.CjDataSourceEnum;
import com.hxoms.modules.passportCard.certificateCollect.entity.enums.CjStatusEnum;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import com.hxoms.modules.passportCard.deviceInteraction.service.OmsDeviceInteractionService;
import com.hxoms.modules.passportCard.initialise.entity.*;
import com.hxoms.modules.passportCard.initialise.entity.enums.*;
import com.hxoms.modules.passportCard.initialise.entity.exportExcel.ExportExceptionCer;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.initialise.mapper.OmsCerConuterNumberMapper;
import com.hxoms.modules.passportCard.initialise.mapper.OmsCerImportBatchMapper;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import com.hxoms.modules.passportCard.initialise.service.OmsCerExitEntryImportManageService;
import com.hxoms.modules.passportCard.initialise.service.OmsCerImportManageService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CfCertificateServiceImpl extends ServiceImpl<CfCertificateMapper,CfCertificate> implements CfCertificateService {

    @Autowired
    private CfCertificateMapper cfCertificateMapper;

    @Autowired
    private OmsEntryexitRecordService omsEntryexitRecordService;

    @Autowired
    private CfCertificateCollectionService cfCertificateCollectionService;
    @Autowired
    private SysDictItemMapper sysDictItemMapper;

    @Autowired
    private OmsCerImportBatchMapper omsCerImportBatchMapper;

    @Autowired
    private OmsCerImportManageService omsCerImportManageService;

    @Autowired
    private OmsCerExitEntryImportManageService omsCerExitEntryImportManageService;

    @Autowired
    private OmsRegProcpersonInfoService omsRegProcpersonInfoService;

    @Autowired
    private OmsCerConuterNumberMapper omsCerConuterNumberMapper;

    @Autowired
    private OmsDeviceInteractionService omsDeviceInteractionService;
    /**
     * @Desc: 初始化证照，导出存疑证照统计-导出证照查询
     * @Author: wuqingfan
     * @Param: [ids]
     * @Return: excel
     * @Date: 2020/9/10
     */
    @Override
    public void exportExceptionCerForOmsId(List<String> ids, HttpServletResponse response) {
     /*   if (ids == null || ids.size() < 1) {
            throw new CustomMessageException("操作失败！");
        }*/
        List<ExportExceptionCer> getList = cfCertificateMapper.exportExceptionCerForOmsId(ids);
        if (getList.size()>0){
            getList.forEach(p -> p.setExitAndEntryDate(ExportExcelUtil.getDateStr(3)));
        }
        String[] headers="序号,姓名,,身份证号,性别(必填项)1：男；2女；,出生日期（必填项）19880101,出入境起始时间（20050101），最大查询5年的出入境记录".split(",");
        ExportExcelUtil.exportNotTitleExcel("存疑证照统计",headers,(List) getList,"yyyyMMdd",response);
    }

    /**
     * @Desc: 初始化证照，导出存疑证照统计-导出证照查询
     * @Author: wuqingfan
     * @Param: [ids]
     * @Return: excel
     * @Date: 2020/9/10
     */
    @Override
    public void exportExceptionCer(List<String> ids, HttpServletResponse response) {
        if (ids == null || ids.size() < 1) {
            throw new CustomMessageException("操作失败！");
        }
        List<ExportExceptionCer> getList = cfCertificateMapper.exportExceptionCer(ids);
        if (getList.size()>0){
           getList.forEach(p -> p.setExitAndEntryDate(ExportExcelUtil.getDateStr(3)));
        }
        String[] headers="序号,姓名,,身份证号,性别(必填项)1：男；2女；,出生日期（必填项）19880101,出入境起始时间（20050101），最大查询5年的出入境记录".split(",");
        ExportExcelUtil.exportNotTitleExcel("存疑证照统计",headers,(List) getList,"yyyyMMdd",response);
    }


    /**
     * @Desc: 初始化证照，导出未上缴证照统计
     * @Author: wuqingfan
     * @Param: [ids]
     * @Return: List<ExportNotProvicdeCer>
     * @Date: 2020/9/10
     */
    @Override
    public void exportNotProvicdeCer(List<String> ids, HttpServletResponse response){
        if (ids==null||ids.size()<1){
            throw new CustomMessageException("操作失败！");
        }
        List getList =cfCertificateMapper.exportNotProvicdeCer(ids);
        String[] headers="序号,姓名,性别,单位,任职状态,职务,证照类型,证件号码,有效期至,管理单位,出生日期,签发单位,签发日期,出生地".split(",");
        ExportExcelUtil.exportNotTitleExcel("未上缴证照统计",headers,getList,response);
    }


    @Override
    public PageInfo<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam) {


        /*if (cfCertificatePageParam.getPageNum() == null){
            cfCertificatePageParam.setPageNum(0);
        }
        if(cfCertificatePageParam.getPageSize() ==null){
            cfCertificatePageParam.setPageSize(cfCertificateList.size());
        }*/

        PageHelper.startPage(cfCertificatePageParam.getPageNum()==null?0 : cfCertificatePageParam.getPageNum(),
                            cfCertificatePageParam.getPageSize()==null?10 : cfCertificatePageParam.getPageSize());
        List<CfCertificate> cfCertificateList =cfCertificateMapper.selectCfCertificateIPage(cfCertificatePageParam);

        PageInfo<CfCertificate> pageInfo = new PageInfo(cfCertificateList);

        return pageInfo;
    }


 
   /**
    * @Desc: 导入公安的证照信息
    * @Author: wangyunquan
    * @Param: [multipartFile,year]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/7/24
    */
    @Override
    public PageBean<ImportInterface> excelToDB(MultipartFile multipartFile,String year) throws Exception {
        if (multipartFile==null || multipartFile.getSize() <= 0 ) {
           throw new CustomMessageException("参数不正确");
        }
        //读取Excel数据
        readExcel(multipartFile.getInputStream(),multipartFile.getOriginalFilename(),year);
        return selectAllCertificate(new PageBean());
    }

    /**
     * @Desc: 查询所有证照
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/4
     */
    @Override
    public PageBean<ImportInterface> selectAllCertificate(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<ImportInterface> pageInfo= new PageInfo<ImportInterface>(cfCertificateMapper.selectAllCertificate());
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 查询证件个数
     * @Author: wangyunquan
     * @Param: []
     * @Return: com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CerTotalCount
     * @Date: 2020/10/19
     */
    @Override
    public CerTotalCount selectCerCount() {
        return cfCertificateMapper.selectCerCount();
    }

    /**
     * @Desc: 验证证照信息
     * @Author: wangyunquan
     * @Param: [validateCerInfoParam]
     * @Return: com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate
     * @Date: 2020/8/4
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CfCertificateValidate validateCerInfo(ValidateCerInfo validateCerInfo) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        CfCertificate cfCertificate=new CfCertificate();
        BeanUtils.copyProperties(validateCerInfo,cfCertificate);
        if(cfCertificate ==null|| cfCertificate.getZjlx()==null||StringUtils.isBlank(cfCertificate.getZjhm()))
            throw new CustomMessageException("参数不正确");
        //通过证件类型、证件号码查询
        CfCertificate certificateGa=cfCertificateMapper.selectCertificateInfo(cfCertificate);
        //数据库存在证照，则验证证照
        if(certificateGa!=null){
            //证照验证
            validateCerInfo(certificateGa,cfCertificate,true);
            certificateGa.setSaveStatus(SaveStatusEnum.YQC.getCode());
            certificateGa.setXplx(cfCertificate.getXplx());
            certificateGa.setZjxs(cfCertificate.getZjxs());
            certificateGa.setUpdater(userInfo.getId());
            certificateGa.setUpdateTime(new Date());
            //设置存储方式
            setStoreMode(certificateGa,userInfo);
            if(cfCertificateMapper.updateById(certificateGa)==0)
                throw new CustomMessageException("证照验证保存失败！");
            certificateGa=cfCertificateMapper.selectById(certificateGa.getId());
        }
        //获取备案人员信息
        List<RegProcpersoninfo> regProcpersoninfoList=cfCertificateMapper.selectRegPerson(certificateGa!=null?certificateGa.getOmsId():null,cfCertificate.getName(),cfCertificate.getCsrq());
        //判断是否需要做新增处理
        if(certificateGa==null&&regProcpersoninfoList.size()==1){
            RegProcpersoninfo regProcpersoninfo = regProcpersoninfoList.get(0);
            cfCertificate.setOmsId(regProcpersoninfo.getId());
            cfCertificate.setA0100(regProcpersoninfo.getA0100());
            cfCertificate.setA0184(regProcpersoninfo.getIdnumberGb());
            insertCertificate(cfCertificate);
        }
        CfCertificateValidate cfCertificateValidate=new CfCertificateValidate();
        if(certificateGa!=null){
            //验证通过，修改人员证件持有情况
            if(CardStatusEnum.YYZ.getCode().equals(certificateGa.getCardStatus())&&regProcpersoninfoList.size()==1){
                RegProcpersoninfo regProcpersoninfo = regProcpersoninfoList.get(0);
                OmsRegProcpersoninfo omsRegProcpersoninfo=new OmsRegProcpersoninfo();
                omsRegProcpersoninfo.setId(regProcpersoninfo.getId());
                omsRegProcpersoninfo.setLicenceIdentity(PubUtils.calLicenceIdentity(regProcpersoninfo.getLicenceIdentity(),certificateGa.getZjlx()));
                if(!omsRegProcpersonInfoService.updateById(omsRegProcpersoninfo))
                    throw new CustomMessageException("登记备案信息更新失败！");
            }
            ValidateCerInfo validateCerInfoReturn = new ValidateCerInfo();
            BeanUtils.copyProperties(certificateGa,validateCerInfoReturn);
            cfCertificateValidate.setValidateCerInfo(validateCerInfoReturn);
        }
        cfCertificateValidate.setRegProcpersoninfoList(regProcpersoninfoList);
        return cfCertificateValidate;
    }


    /**
     * @Desc: 保存证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: void
     * @Date: 2020/8/5
     */
    @Override
    public void insertCertificate(CfCertificate cfCertificate) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        if(cfCertificate==null)
            throw new CustomMessageException("参数不能为空，请核实！");
        if(StringUtils.isBlank(cfCertificate.getOmsId()))
            throw new CustomMessageException("未关联登记备案人员，请核实！");
        cfCertificate.setId(UUIDGenerator.getPrimaryKey());
        cfCertificate.setPy(PingYinUtil.getFirstSpell(cfCertificate.getName()));
        //已取出
        cfCertificate.setSaveStatus(SaveStatusEnum.YQC.getCode());
        //待验证
        cfCertificate.setCardStatus(CardStatusEnum.DYZ.getCode());
        //异常原因不能修改，耶稣说的。
        cfCertificate.setExceptionMessage("公安无对应证照数据");
        cfCertificate.setUpdater(userInfo.getId());
        cfCertificate.setUpdateTime(new Date());
        if(cfCertificateMapper.insert(cfCertificate)==0)
            throw new CustomMessageException("保存失败！");
    }

    /**
     * @Desc: 未上缴证照统计
     * @Author: wangyunquan
     * @Param: [pageBean,year]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/7
     */
    @Override
    public PageBean<CfCertificateInfo> selectNotProvicdeCer(PageBean pageBean,String year) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CfCertificateInfo> pageInfo=new PageInfo<CfCertificateInfo>(cfCertificateMapper.selectNotProvicdeCer(year));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 已上缴未入库统计
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/7
     */
    @Override
    public PageBean<CfCertificateInfo> selectProNotstorCer(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CfCertificateInfo> pageInfo=new PageInfo<CfCertificateInfo>(cfCertificateMapper.selectProNotstorCer());
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 存疑证照统计
     * @Author: wangyunquan
     * @Param: [pageBean，year]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/7
     */
    @Override
    public PageBean<CfCertificateInfo> selectExceptionCer(PageBean pageBean,String year) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CfCertificateInfo> pageInfo=new PageInfo<CfCertificateInfo>(cfCertificateMapper.selectExceptionCer(year));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 生成催缴任务
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: void
     * @Date: 2020/8/11
     */
    @Override
    public void createCjTask(CfCertificateCollectionApplyList cfCertificateCollectionApplyList) {
        List<CertificateCollectionApply> certificateCollectionApplyList = cfCertificateCollectionApplyList.getCertificateCollectionApplyList();
        if(certificateCollectionApplyList.size()==0)
            throw  new CustomMessageException("请求参数不能为空，请核实！");
        List<CfCertificateCollection> cfCertificateCollectionList=new ArrayList<>();
        for (CertificateCollectionApply certificateCollectionApply : certificateCollectionApplyList) {
            CfCertificateCollection cfCertificateCollection=new CfCertificateCollection();
            BeanUtils.copyProperties(certificateCollectionApply,cfCertificateCollection);
            //数据来源
            cfCertificateCollection.setDataSource(CjDataSourceEnum.DJBA.getCode());
            cfCertificateCollectionList.add(cfCertificateCollection);
        }
        cfCertificateCollectionService.createCjTask(cfCertificateCollectionList);
    }


    /**
     * <b>功能描述: 查询证照状态</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    public List<SysDictItem> getCfCertificateStatus() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("dictCode","zjzt");
        List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
        return list;
    }


    /**
     * <b>功能描述: 查询证照类型</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    public List<SysDictItem> getCfCertificateType() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("dictCode","zjlx");
        List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
        return list;
    }


    /**
     * <b>功能描述: 查询证照形式</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    public List<SysDictItem> getCfCertificateForm() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("dictCode","zjxs");
        List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
        return list;
    }


    /**
     * <b>功能描述: 查询证照保管状态</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    public List<SysDictItem> getCfCertificateSaveStatus() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("dictCode","zzbgzt");
        List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
        return list;
    }


    /**
     * <b>功能描述: 查询证照保管单位</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    public List<SysDictItem> getCfCertificateSaveCompany() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("dictCode","zzbgdw");
        List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
        return list;
    }


    /**
     * <b>功能描述: 查询证照保管方式</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    public List<SysDictItem> getCfCertificateSaveWay() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("dictCode","zzbgfs");
        List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
        return list;
    }

    @Override
    public PageBean queryCertificateByOmsId(PageBean pageBean, String b0100) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<ImportInterface> pageInfo= new PageInfo<ImportInterface>(cfCertificateMapper.queryCertificateByOmsId(b0100));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 公安已注销证照，更新状态
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: void
     * @Date: 2020/8/10
     */
    @Override
    public void updateCerForCerIsCancel(String id) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        CfCertificate cfCertificate=new CfCertificate();
        cfCertificate.setId(id);
        cfCertificate.setSaveStatus(SaveStatusEnum.YQC.getCode());
        cfCertificate.setCardStatus(CardStatusEnum.ZX.getCode());
        cfCertificate.setUpdater(userInfo.getId());
        cfCertificate.setUpdateTime(new Date());
        int result = cfCertificateMapper.updateById(cfCertificate);
        if(result==0)
            throw new CustomMessageException("处理失败！");
    }

    /**
     * @Desc: 存疑处理，以证照信息为准
     * @Author: wangyunquan
     * @Param: [qureyDealRequestInfoEx]
     * @Return: void
     * @Date: 2020/8/10
     */
    @Override
    public void updateCerForCerIsRight(QureyDealRequestInfoEx qureyDealRequestInfoEx) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("获取登陆用户信息失败");
        CfCertificate cfCertificate=new CfCertificate();
        Date currDate = new Date();
        BeanUtils.copyProperties(qureyDealRequestInfoEx,cfCertificate);
        cfCertificate.setSaveStatus(SaveStatusEnum.YQC.getCode());
        cfCertificate.setCardStatus(CardStatusEnum.YYZ.getCode());
        cfCertificate.setExceptionSolvedate(currDate);
        cfCertificate.setUpdater(userInfo.getId());
        cfCertificate.setUpdateTime(currDate);
        cfCertificate.setZjxs(cfCertificateMapper.selectById(cfCertificate.getId()).getZjxs());
        //通过接口获取证照存储位置，优先选择证照机存储，否则柜台存储。
        setStoreMode(cfCertificate,userInfo);
        int result = cfCertificateMapper.updateById(cfCertificate);
        if(result==0)
            throw new CustomMessageException("处理失败！");
    }

    /**
     * @Desc: 存疑处理，以公安信息为准
     * @Author: wangyunquan
     * @Param: [qureyDealRequestInfoEx]
     * @Return: void
     * @Date: 2020/8/10
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateCerForGaInfoIsRight(QureyDealRequestInfoEx qureyDealRequestInfoEx) {
        CfCertificate cfCertificate=new CfCertificate();
        Date currDate = new Date();
        BeanUtils.copyProperties(qureyDealRequestInfoEx,cfCertificate);
        cfCertificate.setSaveStatus(SaveStatusEnum.WSQ.getCode());
        cfCertificate.setCardStatus(CardStatusEnum.DYZ.getCode());
        cfCertificate.setExceptionSolvedate(currDate);
        cfCertificate.setUpdater(cfCertificate.getExceptionHandler());
        cfCertificate.setUpdateTime(currDate);
        int result = cfCertificateMapper.updateById(cfCertificate);
        if(result==0)
            throw new CustomMessageException("处理失败！");
        //产生催缴任务
        CfCertificateInfo cfCertificateInfo=cfCertificateMapper.selectCjNeedInfo(cfCertificate.getId());
        CfCertificateCollection cfCertificateCollection=new CfCertificateCollection();
        cfCertificateCollection.setOmsId(cfCertificateInfo.getOmsId());
        cfCertificateCollection.setCerId(cfCertificateInfo.getId());
        cfCertificateCollection.setName(cfCertificateInfo.getName());
        cfCertificateCollection.setRfB0000(cfCertificateInfo.getRfB0000());
        cfCertificateCollection.setWorkUnit(cfCertificateInfo.getWorkUnit());
        cfCertificateCollection.setZjlx(cfCertificateInfo.getZjlx());
        cfCertificateCollection.setZjhm(cfCertificateInfo.getZjhm());
        //0:登记备案,1:因私出国(境),2:证照借出,3:撤销出国申请
        cfCertificateCollection.setDataSource(CjDataSourceEnum.DJBA.getCode());
        List<CfCertificateCollection> cfCertificateCollectionList=new ArrayList<>();
        cfCertificateCollectionList.add(cfCertificateCollection);
        cfCertificateCollectionService.createCjTask(cfCertificateCollectionList);
    }

    /**
     * @Desc: 证件信息对比
     * @Author: wangyunquan
     * @Param: [certificateGa, cfCertificate,flag(true:代表数据库数据为公安，flase代表数据库数据为证照)]
     * @Param: [数据库数据, 对比数据]
     * @Return: void
     * @Date: 2020/8/6
     */
    public void validateCerInfo(CfCertificate certificateExist, CfCertificate cfCertificate,boolean flag){
        //验证姓名、性别、国籍、出生日期、签发单位、签发日期、出生地、证件有效期至
        StringBuffer stringBuffer=new StringBuffer();
        String[] valiUint={"姓名","性别","国籍","出生日期","签发单位","签发日期","出生地","证件有效期至"};
        List<String> certificateExistList=new LinkedList<>();
        List<String> certificateList=new LinkedList<>();
        SimpleDateFormat sF=new SimpleDateFormat("yyyy.MM.dd");
        certificateExistList.add(certificateExist.getName());certificateList.add(cfCertificate.getName());
        certificateExistList.add(certificateExist.getSex());certificateList.add(cfCertificate.getSex());
        certificateExistList.add(certificateExist.getGj());certificateList.add(cfCertificate.getGj());
        certificateExistList.add(sF.format(certificateExist.getCsrq()));certificateList.add(sF.format(cfCertificate.getCsrq()));
        certificateExistList.add(certificateExist.getQfjg());certificateList.add(cfCertificate.getQfjg());
        certificateExistList.add(sF.format(certificateExist.getQfrq()));certificateList.add(sF.format(cfCertificate.getQfrq()));
        certificateExistList.add(certificateExist.getCsdd());certificateList.add(cfCertificate.getCsdd());
        certificateExistList.add(sF.format(certificateExist.getYxqz()));certificateList.add(sF.format(cfCertificate.getYxqz()));
        for (int i = 0; i < valiUint.length; i++) {
            String unit = valiUint[i];
            String valueExist = certificateExistList.get(i);
            String value = certificateList.get(i);
            if(StringUtils.isBlank(valueExist)||StringUtils.isBlank(value))
                throw new CustomMessageException("公安或证照的"+unit+"为空，验证失败，请核实！");
            if(!valueExist.equals(value)){
                //格式不能修改，耶稣说的。
                stringBuffer.append("公安"+unit+"：").append(flag?valueExist:value).append("or").append("证照"+unit+"：").append(flag?value:valueExist);
                stringBuffer.append("and");
            }
        }
        if(StringUtils.isBlank(stringBuffer.toString())){
            //验证通过
            certificateExist.setCardStatus(CardStatusEnum.YYZ.getCode());
        }else{
            //验证失败
            certificateExist.setCardStatus(CardStatusEnum.YZSB.getCode());
            certificateExist.setExceptionMessage(stringBuffer.substring(0,stringBuffer.toString().lastIndexOf("and")));
        }
    }
    /**
     * @Desc: 读取Excel数据
     * @Author: wangyunquan
     * @Param: [inputStream, fileName, year]
     * @Return: java.lang.String
     * @Date: 2020/7/24
     * 处理业务：1、导入（证照管理导入、正常证照导入）2、与数据库数据对比去重 3、证照验证 (已取出，待验证。若验证通过获取存储位置) 4、无证照修改证照持有情况 5、无证照解除催缴任务
     */
    public void readExcel(InputStream inputStream, String fileName,String year) throws IOException {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        boolean isE2007 = false;
        //判断是否是excel2007格式
        if(fileName.endsWith("xlsx")){
            isE2007 = true;
        }
        Workbook wb=null;
        try {
            //根据文件格式(2003或者2007)来初始化
            if(isE2007){
                wb = new XSSFWorkbook(inputStream);
            }else{
                wb = new HSSFWorkbook(inputStream);
            }
            int numberOfSheets = wb.getNumberOfSheets();
            for (int k = 0; k < numberOfSheets; k++) {
                Sheet sheet = wb.getSheetAt(k);
                int sheetMergeCount = sheet.getNumMergedRegions();
                List<CellRangeAddress> cellRangeAddressList=new LinkedList<>();
                //获取人员
                for (int i = 0; i < sheetMergeCount; i++) {
                    CellRangeAddress range = sheet.getMergedRegion(i);
                    int firstColumn = range.getFirstColumn();
                    int firstRow = range.getFirstRow();
                    if(0 == firstColumn&&firstRow>0){
                        cellRangeAddressList.add(range);
                    }
                }
                //保存导入批次
                OmsCerImportBatch omsCerImportBatch=new OmsCerImportBatch();
                SimpleDateFormat dateBatchNo=new SimpleDateFormat("yyyyMMddHHmmss");
                String BatchNo=dateBatchNo.format(new Date());
                omsCerImportBatch.setBatchNo(BatchNo);
                omsCerImportBatch.setPeopleNum(cellRangeAddressList.size());
                omsCerImportBatch.setImportPerson(userInfo.getId());
                omsCerImportBatch.setImportTime(new Date());
                if(omsCerImportBatchMapper.insert(omsCerImportBatch)==0)
                    throw new CustomMessageException("导入批次保存失败！");

                SimpleDateFormat dateParse=new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat timeParse=new SimpleDateFormat("HHmmss");
                SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
                Date importDate = new Date();
                CfCertificateExport cfCertificateExport=new CfCertificateExport();
                //获取人员证照信息及对应出入境记录
                for (int j =0;j<cellRangeAddressList.size();j++) {
                    CellRangeAddress ca=cellRangeAddressList.get(j);
                    int firstRow = ca.getFirstRow();
                    int lastRow = ca.getLastRow();
                    //无证件标记
                    boolean noZj=false;
                    if((lastRow-firstRow+1)<=2)
                        noZj=true;
                    Row firstRowData = sheet.getRow(firstRow);
                    //登记备案人员集合
                    List<OmsRegProcpersoninfo> omsRegProcpersoninfoList=null;
                    //证照导入集合
                    List<CfCertificate> cfCertificateExistList=null;
                    //出入境记录导入集合
                    List<OmsEntryexitRecord> omsEntryexitRecordExistList=null;

                    //匹配到人标记
                    boolean mathPeople=true;
                    if("查询条件".equals(firstRowData.getCell(1).toString())){
                        String idNo = firstRowData.getCell(2).toString();
                        String name = firstRowData.getCell(3).toString();
                        omsRegProcpersoninfoList=cfCertificateMapper.selectA0100ByQua(idNo,name);
                        int size = omsRegProcpersoninfoList.size();
                        //未匹配到人员
                        if(size==0){
                            mathPeople=false;
                        }
                    }
                    //登记备案人员
                    OmsRegProcpersoninfo omsRegProcpersoninfo =new OmsRegProcpersoninfo();

                    if(mathPeople){
                        omsRegProcpersoninfo = omsRegProcpersoninfoList.get(0);
                        //获取已存在证件信息
                        cfCertificateExistList=cfCertificateMapper.selectZJExisByQua(omsRegProcpersoninfo.getId());
                        //获取已存在出入境记录数据
                        omsEntryexitRecordExistList=cfCertificateMapper.selectRecordExisByQua(omsRegProcpersoninfo.getId());
                    }
                    //查询无证照信息时处理
                    if(noZj&&mathPeople){
                        List<CfCertificateCollection> cfCertificateCollectionList = cfCertificateMapper.selectCjTask(omsRegProcpersoninfo.getId());
                       //自动解除催缴任务
                        for (CfCertificateCollection cfCertificateCollection : cfCertificateCollectionList) {
                            //0:手动解除,1;已上缴,2:未上缴,3:自动解除
                            cfCertificateCollection.setCjStatus(CjStatusEnum.ZDJC.getCode());
                            cfCertificateCollection.setRemoveCjDesc("出入境管理局系统未查询到证件");
                            cfCertificateCollection.setUpdator(userInfo.getId());
                            cfCertificateCollection.setUpdatetime(importDate);
                            cfCertificateExport.setCfCertificateCollection(cfCertificateCollection);
                        }
                        //将人员证照持有情况置为无证照
                        if(LicenceIdentityEnum.WZZ.getCode()!=omsRegProcpersoninfo.getLicenceIdentity()&&LicenceIdentityEnum.FSWGLZZ.getCode()!=omsRegProcpersoninfo.getLicenceIdentity()){
                            omsRegProcpersoninfo.setLicenceIdentity(LicenceIdentityEnum.WZZ.getCode());
                            cfCertificateExport.setOmsRegProcpersoninfo(omsRegProcpersoninfo);
                        }
                        continue;
                    }
                    //存储证照有效期集合
                    Map<String,Date> idTypeInfo= new HashedMap();
                    for (int i = firstRow+1; i <= lastRow; i++) {
                        //获取合并单元格值
                        String regionValue=null;
                        if(isMergedRegion(sheet,i,1)){
                            regionValue=getMergedRegionValue(sheet,i,1);
                        }else{
                            regionValue=sheet.getRow(i).getCell(1).toString();
                        }
                        if(!StringUtils.isBlank(regionValue)){
                            Row row =sheet.getRow(i);
                            int column=2;
                            //证件信息读取
                            if("证件信息".equals(regionValue)){
                                CfCertificate cfCertificate=new CfCertificate();
                                cfCertificate.setId(UUIDGenerator.getPrimaryKey());
                                cfCertificate.setImportPerson(userInfo.getId());
                                cfCertificate.setImportTime(importDate);
                                cfCertificate.setOmsId(omsRegProcpersoninfo.getId());
                                cfCertificate.setA0100(omsRegProcpersoninfo.getA0100());
                                cfCertificate.setA0184(row.getCell(column++).toString());
                                String name=row.getCell(column++).toString();
                                cfCertificate.setName(name);
                                cfCertificate.setPy(PingYinUtil.getFirstSpell(name));
                                cfCertificate.setSex(getSex(row.getCell(column++).toString()));
                                cfCertificate.setCsrq(dateParse.parse(getCellValue(row.getCell(column++))));
                                cfCertificate.setGj(row.getCell(column++).toString());
                                cfCertificate.setZjlx(getIdType(row.getCell(column++).toString()));
                                cfCertificate.setZjhm(row.getCell(column++).toString());
                                cfCertificate.setCsdd(row.getCell(column++).toString());
                                cfCertificate.setQfjg(row.getCell(column++).toString());
                                cfCertificate.setQfrq(dateParse.parse(getCellValue(row.getCell(column++))));
                                cfCertificate.setYxqz(dateParse.parse(getCellValue(row.getCell(column++))));
                                //年度值
                                if(!StringUtils.isBlank(year))
                                    cfCertificate.setYear(year);
                                idTypeInfo.put(cfCertificate.getZjlx()+cfCertificate.getZjhm(),cfCertificate.getYxqz());
                                //数据拷贝到证照导入管理表
                                OmsCerImportManage omsCerImportManage=new OmsCerImportManage();
                                BeanUtils.copyProperties(cfCertificate,omsCerImportManage);
                                omsCerImportManage.setBatchNo(BatchNo);
                                if(!mathPeople){
                                    //0:成功导入,1:重复导入,2:匹配失败
                                    omsCerImportManage.setStatus(CerImportManageEnum.PPSB.getCode());
                                    cfCertificateExport.setOmsCerImportManages(omsCerImportManage);
                                }else{
                                    //判断是否过期
                                    if(cfCertificate.getYxqz().after(dateParse.parse(dateParse.format(new Date())))){
                                        //证照状态(0:正常,1:过期,2:注销,3:验证失败,4:已验证,5:待验证,6:借出,7:待领取,8:已领取)
                                        cfCertificate.setCardStatus(CardStatusEnum.DYZ.getCode());
                                        //保管状态(0:正常保管,1:已取出,2:未上缴)
                                        cfCertificate.setSaveStatus(SaveStatusEnum.WSQ.getCode());
                                    }else{
                                        cfCertificate.setCardStatus(CardStatusEnum.GQ.getCode());
                                        cfCertificate.setSaveStatus(SaveStatusEnum.YQC.getCode());
                                    }
                                    //去重
                                    if(cfCertificateExistList!=null&&cfCertificateExistList.size()>0){
                                        boolean flag=false;
                                        for (CfCertificate cfCertificateExist : cfCertificateExistList) {
                                            if(cfCertificateExist.getZjlx()==cfCertificate.getZjlx()
                                                    &&cfCertificateExist.getZjhm().equals(cfCertificate.getZjhm())){
                                                if(!StringUtils.isBlank(year)||(SaveStatusEnum.YQC.getCode().equals(cfCertificateExist.getSaveStatus())&&CardStatusEnum.DYZ.getCode().equals(cfCertificateExist.getCardStatus()))){
                                                    //重复，如果有年度值要更新年度
                                                    if(!StringUtils.isBlank(year)){
                                                        cfCertificateExist.setYear(year);
                                                    }
                                                    //已取出待验证证照，此处要验证证照
                                                    if(SaveStatusEnum.YQC.getCode().equals(cfCertificateExist.getSaveStatus())&&CardStatusEnum.DYZ.getCode().equals(cfCertificateExist.getCardStatus())){
                                                        validateCerInfo(cfCertificateExist,cfCertificate,false);
                                                        //验证通过，设置存储方式
                                                        setStoreMode(cfCertificateExist,userInfo);
                                                        //验证通过，修改人员证件持有情况
                                                        if(CardStatusEnum.YYZ.getCode().equals(cfCertificateExist.getCardStatus())){
                                                            omsRegProcpersoninfo.setLicenceIdentity(PubUtils.calLicenceIdentity(omsRegProcpersoninfo.getLicenceIdentity(),cfCertificateExist.getZjlx()));
                                                            cfCertificateExport.setOmsRegProcpersoninfo(omsRegProcpersoninfo);
                                                        }
                                                    }
                                                    cfCertificateExist.setUpdater(userInfo.getId());
                                                    cfCertificateExist.setUpdateTime(importDate);
                                                    cfCertificateExport.setCfCertificateUpdate(cfCertificateExist);
                                                }
                                                flag=true;
                                                break;
                                            }
                                        }
                                        if(flag){
                                            //0:成功导入,1:重复导入,2:匹配失败
                                            omsCerImportManage.setStatus(CerImportManageEnum.ZFDR.getCode());
                                            cfCertificateExport.setOmsCerImportManages(omsCerImportManage);
                                            continue;
                                        }
                                    }
                                    //0:成功导入,1:重复导入,2:匹配失败
                                    omsCerImportManage.setStatus(CerImportManageEnum.CGDR.getCode());
                                    cfCertificateExport.setOmsCerImportManages(omsCerImportManage);
                                    cfCertificateExport.setCfCertificate(cfCertificate);
                                }
                            }else if("出入境记录".equals(regionValue)){
                                //出入境记录读取
                                OmsEntryexitRecord omsEntryexitRecord=new OmsEntryexitRecord();
                                omsEntryexitRecord.setId(UUIDGenerator.getPrimaryKey());
                                omsEntryexitRecord.setPriapplyId("");
                                omsEntryexitRecord.setImportPerson(userInfo.getId());
                                omsEntryexitRecord.setImportTime(importDate);
                                //数据来源(1:手工,2:导入)
                                omsEntryexitRecord.setDataSource("2");
                                omsEntryexitRecord.setOmsId(omsRegProcpersoninfo.getId());
                                omsEntryexitRecord.setA0100(omsRegProcpersoninfo.getA0100());
                                omsEntryexitRecord.setOgeStatus(getOgeStatus(row.getCell(column++).toString()));
                                omsEntryexitRecord.setName(row.getCell(column++).toString());
                                omsEntryexitRecord.setSex(getSex(row.getCell(column++).toString()));
                                omsEntryexitRecord.setBirthDate(dateParse.parse(getCellValue(row.getCell(column++))));
                                omsEntryexitRecord.setNationality(row.getCell(column++).toString());
                                omsEntryexitRecord.setIdType(getIdType(row.getCell(column++).toString()));
                                omsEntryexitRecord.setIdNumber(row.getCell(column++).toString());
                                omsEntryexitRecord.setDestination(row.getCell(column++).toString());
                                omsEntryexitRecord.setEntraceExit(row.getCell(column++).toString());
                                omsEntryexitRecord.setOgeDate(dateParse.parse(getCellValue(row.getCell(column++))));
                                omsEntryexitRecord.setOgeTime(timeFormat.format(timeParse.parse(row.getCell(column++).toString())));
                                omsEntryexitRecord.setValidUntil(idTypeInfo.get(omsEntryexitRecord.getIdType()+omsEntryexitRecord.getIdNumber()));

                                //数据拷贝到证照出入境导入管理表
                                OmsCerExitEntryImportManage omsCerExitEntryImportManage=new OmsCerExitEntryImportManage();
                                BeanUtils.copyProperties(omsEntryexitRecord,omsCerExitEntryImportManage);
                                omsCerExitEntryImportManage.setBatchNo(BatchNo);
                                if(!mathPeople){
                                    //0:成功导入,1:重复导入,2:匹配失败
                                    omsCerExitEntryImportManage.setStatus(CerExitEntryImportManageEnum.PPSB.getCode());
                                    cfCertificateExport.setOmsCerExitEntryImportManage(omsCerExitEntryImportManage);
                                }else{
                                    //去重
                                    if(omsEntryexitRecordExistList!=null&&omsEntryexitRecordExistList.size()>0){
                                        boolean flag=false;
                                        for (OmsEntryexitRecord omsEntryexitRecordExist : omsEntryexitRecordExistList) {
                                            if(omsEntryexitRecordExist.getIdType()==omsEntryexitRecord.getIdType()
                                                    &&omsEntryexitRecordExist.getIdNumber().equals(omsEntryexitRecord.getIdNumber())
                                                    &&omsEntryexitRecordExist.getOgeDate().getTime()==omsEntryexitRecord.getOgeDate().getTime()
                                                    &&omsEntryexitRecordExist.getOgeTime().equals(omsEntryexitRecord.getOgeTime())){
                                                flag=true;
                                                break;
                                            }
                                        }
                                        if(flag){
                                            omsCerExitEntryImportManage.setStatus(CerExitEntryImportManageEnum.ZFDR.getCode());
                                            cfCertificateExport.setOmsCerExitEntryImportManage(omsCerExitEntryImportManage);
                                            continue;
                                        }
                                    }
                                    omsCerExitEntryImportManage.setStatus(CerExitEntryImportManageEnum.CGDR.getCode());
                                    cfCertificateExport.setOmsCerExitEntryImportManage(omsCerExitEntryImportManage);
                                    cfCertificateExport.setOmsEntryexitRecord(omsEntryexitRecord);
                                }
                            }
                        }
                    }
                    //批量提交
                    if(j+1%50==0){
                        batchSaveEntity(cfCertificateExport);
                    }
                }
                batchSaveEntity(cfCertificateExport);
            }
        } catch (Exception ex) {
            if(wb!=null){
                wb.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
            ex.printStackTrace();
            throw  new CustomMessageException(ex.getMessage());
        }
    }
    @Transactional(rollbackFor=Exception.class)
    public void batchSaveEntity(CfCertificateExport cfCertificateExport){
        //证件信息
        List<CfCertificate> cfCertificateList = cfCertificateExport.getCfCertificateList();
        if(cfCertificateList.size()>0){
            if(!saveBatch(cfCertificateList,cfCertificateList.size()))
                throw  new CustomMessageException("证照信息插入失败！");
        }
        //出入境记录
        List<OmsEntryexitRecord> omsEntryexitRecordList = cfCertificateExport.getOmsEntryexitRecordList();
        if(omsEntryexitRecordList.size()>0){
            if(!omsEntryexitRecordService.saveBatch(omsEntryexitRecordList,omsEntryexitRecordList.size()))
                throw  new CustomMessageException("证照出入境插入失败！");
        }
        //证件信息管理表
        List<OmsCerImportManage> omsCerImportManagesList = cfCertificateExport.getOmsCerImportManagesList();
        if(omsCerImportManagesList.size()>0){
            if(!omsCerImportManageService.saveBatch(omsCerImportManagesList,omsCerImportManagesList.size()))
                throw  new CustomMessageException("证件信息管理插入失败！");
        }
        //证照出入境记录管理表
        List<OmsCerExitEntryImportManage> omsCerExitEntryImportManageList = cfCertificateExport.getOmsCerExitEntryImportManageList();
        if(omsCerExitEntryImportManageList.size()>0){
            if(!omsCerExitEntryImportManageService.saveBatch(omsCerExitEntryImportManageList,omsCerExitEntryImportManageList.size()))
                throw  new CustomMessageException("证照出入境记录管理插入失败！");
        }
        //证照信息更新
        List<CfCertificate> cfCertificateUpdateList = cfCertificateExport.getCfCertificateUpdateList();
        if(cfCertificateUpdateList.size()>0){
            if(!updateBatchById(cfCertificateUpdateList,omsCerExitEntryImportManageList.size()))
                throw  new CustomMessageException("证照信息更新失败！");
        }
        //登记备案人员
        List<OmsRegProcpersoninfo> omsRegProcpersoninfoList = cfCertificateExport.getOmsRegProcpersoninfoList();
        if(omsRegProcpersoninfoList.size()>0){
            if(!omsRegProcpersonInfoService.updateBatchById(omsRegProcpersoninfoList,omsRegProcpersoninfoList.size()))
                throw  new CustomMessageException("登记备案更新失败！");
        }
        //解除催缴任务
        List<CfCertificateCollection> cfCertificateCollectionList = cfCertificateExport.getCfCertificateCollectionList();
        if(cfCertificateCollectionList.size()>0){
            if(!cfCertificateCollectionService.updateBatchById(cfCertificateCollectionList,cfCertificateCollectionList.size()))
                throw  new CustomMessageException("解除催缴任务失败！");
        }
        cfCertificateExport.clear();
    }

    /**
     * @Desc: 获取存储方式
     * @Author: wangyunquan
     * @Param: [certificateGa, userInfo]
     * @Return: void
     * @Date: 2020/9/18
     */
    private void setStoreMode(CfCertificate certificateGa,UserInfo userInfo) {
        //根据登陆用户设置保管单位
        //0:干部监督处,1:省委统战部(台办)
        certificateGa.setSurelyUnit(cfCertificateMapper.selectUserType(userInfo.getId()));
        //验证通过，通过接口获取证照存储位置
        if(CardStatusEnum.YYZ.getCode().equals(certificateGa.getCardStatus())){
            //通过接口查询证照机是否可以存，否则柜台存。
            if(omsDeviceInteractionService.isStoreDevice(certificateGa.getSurelyUnit(),certificateGa.getZjxs())){
                //保管方式(0:证照机,1:柜台)
                certificateGa.setSurelyWay(SurelyWayEnum.CABINET.getCode());
            }else{
                //获取位置
                OmsCerCounterNumber omsCerCounterNumber=omsCerConuterNumberMapper.selectCounterNum(certificateGa.getSurelyUnit(),certificateGa.getZjlx(),certificateGa.getZjxs());
                //将位置置为已使用
                omsCerCounterNumber.setStatus(UseStatusEnum.ON_USE.getCode());
                omsCerCounterNumber.setIsLock(LockEnum.LOCK.getCode());
                omsCerCounterNumber.setUpdater(userInfo.getId());
                omsCerCounterNumber.setUpdateTime(new Date());
                if(omsCerConuterNumberMapper.updateById(omsCerCounterNumber)==0)
                    throw new CustomMessageException("柜台号置为已使用失败！");
                //保管方式
                certificateGa.setSurelyWay(SurelyWayEnum.COUNTER.getCode());
                //保管位置
                certificateGa.setCounterNum(omsCerCounterNumber.getCounterNum());
            }
        }
    }
    /**
     *
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell){
        if(cell == null)
            return "";
        if(cell.getCellType() == CellType.STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellType() == CellType.BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if(cell.getCellType() == CellType.FORMULA){
            return cell.getCellFormula() ;
        }else if(cell.getCellType() == CellType.NUMERIC){
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                return DateFormatUtils.format(date, "yyyyMMdd");
            } else {
                return String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue());
            }
        }
        return "";
    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public  String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }
        return null ;
    }
    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private  boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Desc: 获取性别,1：男，2：女
     * @Author: wangyunquan
     * @Param: [sex]
     * @Return: java.lang.String
     * @Date: 2020/7/27
     */
    private String getSex(String sex){
        return "男".equals(sex)?"1":"女".equals(sex)?"2":null;
    }
    /**
     * @Desc: 获取证件类型，1：护照，2：港澳通行证，4：台湾通行证
     * @Author: wangyunquan
     * @Param: [idType]
     * @Return: java.lang.Integer
     * @Date: 2020/7/27
     */
    private Integer getIdType(String idType){
        if(StringUtils.isBlank(idType))
            return null;
        return idType.contains("护照")?1:idType.contains("港澳")?2:idType.contains("台湾")?4:null;
    }

    /**
     * @Desc: 获取出入境状态，出境：1，入境：2
     * @Author: wangyunquan
     * @Param: [OgeStatus]
     * @Return: java.lang.Integer
     * @Date: 2020/7/27
     */
    private Integer getOgeStatus(String OgeStatus){
        return "出境".equals(OgeStatus)?1:"入境".equals(OgeStatus)?2:null;
    }

}
