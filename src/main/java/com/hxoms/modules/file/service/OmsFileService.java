package com.hxoms.modules.file.service;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.OtherMaterial;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import java.util.List;
import java.util.Map;

public interface OmsFileService {
    /**
     * @description:返回需要生成的材料列表，自动根据模板设置返回通用或机构自定义模板，自动根据关键字替换生成材料，
     * 重新根据业务数据获取需要的材料，将业务数据变化后不再需要的已经生成过的材料删除
     * @author:杨波
     * @date:2020-10-15
     *  * @param tableCode 业务类型
     * @param procpersonId 备案人员id
     * @param applyId 业务主键
     * @return:java.util.List<com.hxoms.modules.file.entity.OmsCreateFile>
     **/
    List<OmsCreateFile> selectFileListByCode(String tableCode, String procpersonId, String applyId);

    /**
     * 根据材料类别、模板ID返回材料模板
     *
     */
    Map<String, Object> selectFileTemplate(String fileTemplateId);

    /**
     * 文件类型下载
     * @param abroadFileDestailParams
     */
    void downloadOmsFile(AbroadFileDestailParams abroadFileDestailParams);

    /**
     * 保存富文本文件
     * @param omsFile
     * @return
     */
    String saveTextOmsFile(OmsFile omsFile);
    /**
    * @description: 保存其它材料设置
    * @author:杨波
    * @date:2020-10-15
    *  * @param id omsFile的主键
     *  applyId 业务主键
     *  isRequired 是否勾选（1勾选，0未勾选）
    * @return:
    **/
    Result saveOtherFile(String id,String applyId,Integer isRequired);
    /**
     * 重新生成内容
     * @param fileId omsCreateFile的id
     */
    OmsCreateFile selectFileDestailNew(String fileId);

    /**
     * 功能描述: <br>
     * 〈通用模板查询〉
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/10/12 19:33
     */
    Map<String, Object> selectFileList();
    /**
     * @description:返回其它材料列表，其它材料也允许按非涉密、涉密、核心涉密、挂职来设置在哪种条件下需要
     * @author:杨波
     * @date:2020-10-15
     *  * @param tableCode 业务类型（因公、因私等）
     * @param procpersonId 备案人员
     * @param applyId 业务主主键
     * @return:java.util.List<com.hxoms.modules.file.entity.OtherMaterial>
     **/
    List<OtherMaterial> selectOtherMaterial(String tableCode, String procpersonId, String applyId);
    /**
     * @description:根据模板文件创建用户文件，不自动生成模板内容
     * @author:杨波
     * @date:2020-10-15
     *  * @param omsFile 模板文件
     * @param applyId 业务主键
     * @return:com.hxoms.modules.file.entity.OmsCreateFile
     **/
    OmsCreateFile createFile(OmsFile omsFile,String applyId);
}
