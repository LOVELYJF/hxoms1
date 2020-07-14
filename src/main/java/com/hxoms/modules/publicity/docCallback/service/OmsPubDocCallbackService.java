package com.hxoms.modules.publicity.docCallback.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.CallbackRegVo;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.ExportRequestPara;
import com.hxoms.modules.publicity.docCallback.entity.parameterEntity.SelCallbackRegByQuaVo;
import com.hxoms.support.user.entity.User;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/1
 */
public interface OmsPubDocCallbackService {
    /**
     * @Desc: 查询可回收登记备案申请
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/7/7
     */
    PageBean selectCanCallbApply(PageBean pageBean);
    /**
     * @Desc: 获取登陆用户信息
     * @Author: wangyunquan
     * @Param: []
     * @Return: com.hxoms.support.user.entity.User
     * @Date: 2020/7/7
     */
    User selectUserInfo();
    /**
     * @Desc: 批件回收登记
     * @Author: wangyunquan
     * @Param: [callbackRegVo]
     * @Return: void
     * @Date: 2020/7/7
     */
    void insertDocCallbackReg(CallbackRegVo callbackRegVo);

    /**
     * @Desc: 查询批件回收登记记录
     * @Author: wangyunquan
     * @Param: [pageBean, selCallbackRegByQuaVo]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/7/7
     */
    PageBean getDestroyRegByQuaVo(PageBean pageBean, SelCallbackRegByQuaVo selCallbackRegByQuaVo);

    /**
     * @Desc: 批件回收登记记录导出excel文件
     * @Author: wangyunquan
     * @Param: [exportRequestPara, outputStream]
     * @Return: void
     * @Date: 2020/7/7
     */
    void exportExcel(ExportRequestPara exportRequestPara, ServletOutputStream outputStream) throws IOException;
}
