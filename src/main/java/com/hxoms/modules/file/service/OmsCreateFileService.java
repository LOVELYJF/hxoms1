package com.hxoms.modules.file.service;

import com.hxoms.modules.file.entity.OmsCreateFile;

import java.util.List;

/**
 * @desc: 生成材料
 * @author: lijing
 * @date: 2020-06-12
 */
public interface OmsCreateFileService {
    /**
     * 生成文件列表
     * @param tableCode 类型（因公 因私 延期回国）
     * @param applyId 申请Id
     * @return
     */
    List<OmsCreateFile> selectCreateFileList(String tableCode, String applyId);

    OmsCreateFile InsertOrUpdate(OmsCreateFile omsCreateFile);
    /**
     * 批量删除生成文件
     * @param tableCode
     * @param applyId
     * @return
     */
    String deleteCreateFile(String tableCode, String applyId);
}
