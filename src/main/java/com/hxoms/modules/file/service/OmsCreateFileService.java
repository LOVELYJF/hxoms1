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
    /**
     * 保存或者更新
     *
     */
    OmsCreateFile insertOrUpdate(OmsCreateFile omsCreateFile);
}
