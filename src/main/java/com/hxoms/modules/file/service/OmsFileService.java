package com.hxoms.modules.file.service;

import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.paramentity.AbroadAskFileParams;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OmsFileService {
    /**
     * 根据业务表编码查询文件
     *
     * @author sunqian
     * @date 2020/5/7 14:18
     */
    List<OmsFile> selectFileListByCode(String tableCode);

    /**
     * 文件上传
     *
     * @author sunqian
     * @date 2020/5/7 17:09
     */
    void uploadOmsFile(MultipartFile file, OmsFile omsFile) throws IOException;

    void uploadOmsFileList(List<MultipartFile> fileList, OmsFile omsFile) throws IOException;

    /**
     * 根据主键删除文件
     * 
     * @author sunqian
     * @date 2020/5/8 11:29
     */
    void deleteOmsFile(String id);

    void downloadOmsFile(String fileId, String applyId) throws Exception;

    /**
     * 查询请示文件
     *
     */
    Map<String, Object> selectAbroadAskFile(AbroadAskFileParams abroadAskFileParams);
}
