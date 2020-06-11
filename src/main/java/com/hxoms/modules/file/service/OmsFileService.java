package com.hxoms.modules.file.service;

import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.OmsFileVO;
import com.hxoms.modules.file.entity.OmsSealhandleRecordsVO;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import java.util.List;
import java.util.Map;

public interface OmsFileService {
    /**
     * 文件列表
     * @param tableCode 类型（因公 因私 延期回国）
     * @param a0100 出国人
     * @return
     */
    List<OmsFile> selectFileListByCode(String tableCode, String a0100);

    /**
     * 查询富文本文件详情
     *
     */
    Map<String, Object> selectFileDestail(AbroadFileDestailParams abroadFileDestailParams);

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
     * 签字盖章列表
     * @param tableCode
     * @param applyId
     * @return
     */
    List<OmsFileVO> selectSealHandleList(String tableCode, String applyId);

    /**
     * 保存签字盖章
     * @param omsSealhandleRecordsVOS
     * @return
     * @throws Exception
     */
    String saveSealHandle(List<OmsSealhandleRecordsVO> omsSealhandleRecordsVOS);
}
