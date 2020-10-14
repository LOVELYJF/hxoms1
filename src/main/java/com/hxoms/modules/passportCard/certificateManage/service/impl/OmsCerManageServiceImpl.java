package com.hxoms.modules.passportCard.certificateManage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.ExportExcelUtil;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.enums.CjStatusEnum;
import com.hxoms.modules.passportCard.certificateCollect.mapper.CfCertificateCollectionMapper;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.certificateManage.mapper.OmsCerManageMapper;
import com.hxoms.modules.passportCard.certificateManage.service.OmsCerManageService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.enums.CardStatusEnum;
import com.hxoms.modules.passportCard.initialise.entity.enums.SaveStatusEnum;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
@Service
public class OmsCerManageServiceImpl implements OmsCerManageService {

    @Autowired
    private OmsCerManageMapper omsCerManageMapper;

    @Autowired
    private CfCertificateMapper cfCertificateMapper;

    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersoninfoMapper;

    @Autowired
    private CfCertificateCollectionMapper cfCertificateCollectionMapper;

    /**
     * @Desc: 证照管理-证照信息管理-导出
     * @Author: wuqingfan
     * @Param: ids
     * @Return: excel
     * @Date: 2020/9/11
     */
    @Override
    public  void exportSelectCerInfo(List<String> ids, HttpServletResponse response){
        CerManageQueryParam param=new CerManageQueryParam();
        param.setIds(ids);
        List<CerManageInfo> getinfos=omsCerManageMapper.selectCerInfo(param);
        List<ExportCerManageInfoVo> vos=new ArrayList<>();
        if (getinfos.size()>0){
            ExportCerManageInfoVo vo=null;
            for (CerManageInfo m:getinfos){
                vo=new ExportCerManageInfoVo();
                BeanUtils.copyProperties(m,vo);
                vos.add(vo);
            }
        }
        String[] headers=("序号,姓名,性别,单位,任职状态,职务,证照类型,芯片类型,证照样式,证照号码,有效期至,管理单位,证照状态,保管状态," +
                "人员状态,保管方式,机柜,位置,柜台编号,出生日期,签发单位,签发日期,出生地点,存疑信息").split(",");
        ExportExcelUtil.exportNotTitleExcel("证照信息管理",headers,(List)vos,response);

    }

    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, cerManageQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/13
     */
    @Override
    public PageBean<CerManageInfo> selectCerInfo(PageBean pageBean, CerManageQueryParam cerManageQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CerManageInfo> pageInfo=new PageInfo<CerManageInfo>(omsCerManageMapper.selectCerInfo(cerManageQueryParam));
        return PageUtil.packagePage(pageInfo);
    }


    /**
     * @Desc: 新领证照录入
     * @Author: wangyunquan
     * @Param: [ReadCerInfo]
     * @Return: void
     * @Date: 2020/8/17
     */
    @Override
    public CerAndPerson insertCertificate(ReadCerInfo readCerInfo) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败，请核实！");
        String exist=omsCerManageMapper.selectIsExist(readCerInfo.getZjlx(),readCerInfo.getZjhm());
        if(exist!=null)
            throw new CustomMessageException("系统已存在此证照，不能执行新增操作！");
        CfCertificate cfCertificate=new CfCertificate();
        BeanUtils.copyProperties(readCerInfo,cfCertificate);
        //根据登陆用户设置保管单位
        //0:干部监督处,1:省委统战部(台办)
        cfCertificate.setSurelyUnit(cfCertificateMapper.selectUserType(userInfo.getId()));
        readCerInfo.setSurelyUnit(cfCertificate.getSurelyUnit());
        //获取备案人员信息
        List<RegProcpersoninfo> regProcpersoninfoList=omsCerManageMapper.selectRegPerson(cfCertificate.getName(),cfCertificate.getCsrq());
        if(regProcpersoninfoList.size()==1){
            RegProcpersoninfo regProcpersoninfo = regProcpersoninfoList.get(0);
            OmsRegProcpersoninfo omsRegProcpersoninfo=new OmsRegProcpersoninfo();
            omsRegProcpersoninfo.setId(regProcpersoninfo.getId());
            omsRegProcpersoninfo.setLicenceIdentity(regProcpersoninfo.getLicenceIdentity());

            cfCertificate.setOmsId(regProcpersoninfo.getId());
            cfCertificate.setA0100(regProcpersoninfo.getA0100());
            cfCertificate.setA0184(regProcpersoninfo.getIdnumberGb());
            //处理新证照
            dealNewCer(userInfo, cfCertificate, omsRegProcpersoninfo);
        }
        CerAndPerson cerAndPerson=new CerAndPerson();
        cerAndPerson.setReadCerInfo(readCerInfo);
        cerAndPerson.setRegProcpersoninfoList(regProcpersoninfoList);
        return cerAndPerson;
    }

    /**
     * @Desc: 保存证照
     * @Author: wangyunquan
     * @Param: [cerInfoSave]
     * @Return: void
     * @Date: 2020/9/9
     */
    @Override
    public void saveCertificate(CerInfoSave cerInfoSave) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败，请核实！");
        OmsRegProcpersoninfo regProcpersoninfo = omsRegProcpersoninfoMapper.selectById(cerInfoSave.getOmsId());
        CfCertificate cfCertificate=new CfCertificate();
        BeanUtils.copyProperties(cerInfoSave,cfCertificate);
        //根据登陆用户设置保管单位
        //0:干部监督处,1:省委统战部(台办)
        cfCertificate.setSurelyUnit(cfCertificateMapper.selectUserType(userInfo.getId()));

        dealNewCer(userInfo,cfCertificate,regProcpersoninfo);
    }

    /**
     * @Desc: 对新录入证件处理，1、保存  2、解除催缴任务
     * @Author: wangyunquan
     * @Param: [userInfo, cfCertificate, omsRegProcpersoninfo]
     * @Return: void
     * @Date: 2020/9/9
     */
    @Transactional(rollbackFor = Exception.class)
    public void dealNewCer(UserInfo userInfo, CfCertificate cfCertificate, OmsRegProcpersoninfo omsRegProcpersoninfo) {
        try {
            cfCertificate.setId(UUIDGenerator.getPrimaryKey());
            cfCertificate.setPy(PingYinUtil.getFirstSpell(cfCertificate.getName()));
            //已取出
            cfCertificate.setSaveStatus(SaveStatusEnum.YQC.getCode());
            //待验证
            cfCertificate.setCardStatus(CardStatusEnum.DYZ.getCode());
            cfCertificate.setUpdater(userInfo.getId());
            cfCertificate.setUpdateTime(new Date());
            if(cfCertificateMapper.insert(cfCertificate)==0)
                throw new CustomMessageException("保存失败！");
            //取消催缴任务,查询证件是否存在催缴，不存在则按人员解除催缴证件类型和证件号码为空的催缴任务
            List<CfCertificateCollection> cfCertificateCollectionList = cfCertificateMapper.selectCjTask(omsRegProcpersoninfo.getId());
            boolean isExist=false;
            Date date=new Date();
            CfCertificateCollection cfCerCollection=null;
            for (CfCertificateCollection cfCertificateCollection : cfCertificateCollectionList) {
                if(cfCertificate.getZjlx().equals(cfCertificateCollection.getZjlx())&&cfCertificate.getZjhm().equals(cfCertificateCollection.getZjhm())){
                    //按证件解除催缴
                    //0:手动解除,1;已上缴,2:未上缴,3:自动解除
                    cfCertificateCollection.setCjStatus(CjStatusEnum.YSJ.getCode());
                    cfCertificateCollection.setUpdator(userInfo.getId());
                    cfCertificateCollection.setUpdatetime(date);
                    cfCertificateCollectionMapper.updateById(cfCertificateCollection);
                    isExist=true;
                }
                if(cfCertificateCollection.getZjlx()==null&& StringUtils.isBlank(cfCertificateCollection.getZjhm()))
                    cfCerCollection=cfCertificateCollection;
            }
            //按人员解除催缴证件类型和证件号码为空的催缴任务
            if(!isExist&&cfCerCollection!=null){
                cfCerCollection.setCjStatus(CjStatusEnum.YSJ.getCode());
                cfCerCollection.setUpdator(userInfo.getId());
                cfCerCollection.setUpdatetime(date);
                cfCertificateCollectionMapper.updateById(cfCerCollection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomMessageException(e.getMessage());
        }
    }
}
