package com.hxoms.modules.passportCard.printGetQrCode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.passportCard.printGetQrCode.entity.OmsCerPrintQrCode;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CanGetCerInfo;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CreateQrCodeApply;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.QrCode;
import com.hxoms.modules.passportCard.printGetQrCode.mapper.OmsCerPrintQrCodeMapper;
import com.hxoms.modules.passportCard.printGetQrCode.service.OmsPrintGetQrCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OmsPrintGetQrCodeServiceImpl extends ServiceImpl<OmsCerPrintQrCodeMapper, OmsCerPrintQrCode> implements OmsPrintGetQrCodeService {

    @Autowired
    private OmsCerPrintQrCodeMapper omsCerPrintQrCodeMapper;

    @Autowired
    private OmsPrintGetQrCodeService omsPrintGetQrCodeService;
    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, overFlag]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CanGetCerInfo>
     * @Date: 2020/8/20
     */
    @Override
    public PageBean<CanGetCerInfo> selectCanGetCer(PageBean pageBean, String overFlag) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        PageInfo<CanGetCerInfo> pageInfo=new PageInfo<CanGetCerInfo>(omsCerPrintQrCodeMapper.selectCanGetCer(userInfo.getId(),overFlag));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 生成打印二维码
     * @Author: wangyunquan
     * @Param: [list]
     * @Return: java.lang.String
     * @Date: 2020/8/21
     */
    @Override
    public QrCode createPrintQrCode(List<CreateQrCodeApply> createQrCodeApplyList) throws IOException {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        String enCodeStr=null;
        QrCode qrCode=new QrCode();
        String imgFormat="jpg";
        String base64Pre="data:image/"+imgFormat+";base64,";
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        if(createQrCodeApplyList.size()>0){
            HttpServletRequest request = DomainObjectUtil.getRequest();
            String qrCodeId= UUIDGenerator.getPrimaryKey();
            ByteArrayOutputStream bs=new ByteArrayOutputStream();
            try {
                QrCodeCreateUtil.createQrCode(bs,qrCodeId,190,imgFormat);
                enCodeStr = base64Pre+Base64.encode(bs.toByteArray());
            } catch (WriterException|IOException e) {
                e.printStackTrace();
                throw new CustomMessageException("二维码生成失败，原因："+e.getMessage());
            }finally {
                if(bs!=null)
                    bs.close();
            }
            List<OmsCerPrintQrCode> omsCerPrintQrCodeList=new ArrayList<>();
            for (CreateQrCodeApply createQrCodeApply : createQrCodeApplyList) {
                OmsCerPrintQrCode omsCerPrintQrCode=new OmsCerPrintQrCode();
                BeanUtils.copyProperties(createQrCodeApply,omsCerPrintQrCode);
                omsCerPrintQrCode.setId(UUIDGenerator.getPrimaryKey());
                omsCerPrintQrCode.setQrCodeId(qrCodeId);
                omsCerPrintQrCode.setQrCode(enCodeStr);
                omsCerPrintQrCode.setOperator(userInfo.getId());
                omsCerPrintQrCode.setOperateTime(new Date());
                omsCerPrintQrCodeList.add(omsCerPrintQrCode);
            }
            if(!omsPrintGetQrCodeService.saveBatch(omsCerPrintQrCodeList))
                throw new CustomMessageException("保存失败！");
        }
        qrCode.setQrCodeStr(enCodeStr);
        return qrCode;
    }
}
