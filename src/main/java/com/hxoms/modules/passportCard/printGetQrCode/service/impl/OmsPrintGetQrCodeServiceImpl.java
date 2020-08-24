package com.hxoms.modules.passportCard.printGetQrCode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.passportCard.printGetQrCode.entity.OmsCerPrintQrCode;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CanGetCerInfo;
import com.hxoms.modules.passportCard.printGetQrCode.mapper.OmsCerPrintQrCodeMapper;
import com.hxoms.modules.passportCard.printGetQrCode.service.OmsPrintGetQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/20
     */
    @Override
    public PageBean selectCanGetCer(PageBean pageBean, String overFlag) {
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
    @Transactional(rollbackFor = Exception.class)
    public String createPrintQrCode(List<OmsCerPrintQrCode> omsCerPrintQrCodeList) throws IOException {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        String enCodeStr=null;
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        if(omsCerPrintQrCodeList.size()>0){
            String qrCodeId= UUIDGenerator.getPrimaryKey();
            StringBuffer stringBuffer=new StringBuffer("获取请求路径");
            stringBuffer.append("?").append("operatId=").append(userInfo.getId()).append("&qrCodeId=").append(qrCodeId);
            ByteArrayOutputStream bs=new ByteArrayOutputStream();
            try {
                QrCodeCreateUtil.createQrCode(bs,stringBuffer.toString(),200,"JPEG");
                enCodeStr = Base64.encode(bs.toByteArray());
            } catch (WriterException|IOException e) {
                e.printStackTrace();
                throw new CustomMessageException("二维码生成失败，原因："+e.getMessage());
            }finally {
                if(bs!=null)
                    bs.close();
            }
            for (OmsCerPrintQrCode omsCerPrintQrCode : omsCerPrintQrCodeList) {
                omsCerPrintQrCode.setGetId(UUIDGenerator.getPrimaryKey());
                omsCerPrintQrCode.setQrCodeId(qrCodeId);
                omsCerPrintQrCode.setQrUrl(stringBuffer.toString());
                omsCerPrintQrCode.setQrCode(enCodeStr);
                omsCerPrintQrCode.setOperator(userInfo.getId());
                omsCerPrintQrCode.setOperateTime(new Date());
            }
            if(omsPrintGetQrCodeService.saveBatch(omsCerPrintQrCodeList))
                throw new CustomMessageException("保存失败！");
        }
        return enCodeStr;
    }
}
