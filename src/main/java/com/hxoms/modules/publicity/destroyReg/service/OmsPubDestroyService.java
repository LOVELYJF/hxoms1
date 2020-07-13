package com.hxoms.modules.publicity.destroyReg.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.DestroyRegVo;
import com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.ExportRequestPara;
import com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.SelDestroyRegByQuaVo;
import com.hxoms.support.user.entity.User;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/1
 */
public interface OmsPubDestroyService {
    /**
     * @Desc: 查询提交撤销备案申请
     * @Author: wangyunquan
     * @Param: [pageVO]
     * @Return: com.hxoms.modules.publicity.destroyReg.entity.parameterEntity.PageVO
     * @Date: 2020/7/2
     */
    PageBean selectSubmitDesApply(PageBean pageBean);
    /**
     * @Desc: 获取登陆用户信息
     * @Author: wangyunquan
     * @Param: []
     * @Return: com.hxoms.support.user.entity.User
     * @Date: 2020/7/2
     */
    User selectUserInfo();
    /**
     * @Desc: 备案表销毁登记
     * @Author: wangyunquan
     * @Param: [destroyRegVo]
     * @Return: void
     * @Date: 2020/7/2
     */
    void babDestroyReg(DestroyRegVo destroyRegVo);

    /**
     * @Desc: 查询已销毁登记记录
     * @Author: wangyunquan
     * @Param: [pageBean, selDestroyRegByQuaVo]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/7/2
     */
    PageBean SelDestroyRegByQuaVo(PageBean pageBean,SelDestroyRegByQuaVo selDestroyRegByQuaVo);

    /**
     * @Desc: 销毁登记记录导出excel文件
     * @Author: wangyunquan
     * @Param: [exportRequestPara, outputStream]
     * @Return: void
     * @Date: 2020/7/3
     */
    void exportExcel(ExportRequestPara exportRequestPara, ServletOutputStream outputStream) throws IOException;
}
