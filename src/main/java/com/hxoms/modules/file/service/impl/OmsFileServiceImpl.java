package com.hxoms.modules.file.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.file.OmsFileUtils;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.file.service.OmsFileService;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class OmsFileServiceImpl implements OmsFileService {

    @Autowired
    private OmsFileUtils omsFileUtils;
    @Autowired
    private OmsFileMapper omsFileMapper;
    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;

    @Override
    public List<OmsFile> selectFileListByCode(String tableCode) {
        return omsFileMapper.selectFileListByCode(tableCode);
    }

    @Override
    public void uploadOmsFile(MultipartFile file, OmsFile omsFile) throws IOException {
        if (omsFile == null || StringUtils.isBlank(omsFile.getTableCode())) {
            throw new CustomMessageException("模块编码不能为空");
        }

        String primaryKey = UUIDGenerator.getPrimaryKey();
        omsFileUtils.saveFile(file, primaryKey);
        //数据库中插入记录
        omsFile.setId(primaryKey);
        omsFile.setFileName(file.getOriginalFilename());
        omsFile.setCreateTime(new Date());
        omsFile.setCreateUser(UserInfoUtil.getUserId());
        omsFileMapper.insert(omsFile);
    }

    @Override
    public void uploadOmsFileList(List<MultipartFile> fileList, OmsFile omsFile) throws IOException {
        if (fileList == null || fileList.isEmpty()) {
            throw new CustomMessageException("所上传的文件为空");
        }
        for (MultipartFile multipartFile : fileList) {
            uploadOmsFile(multipartFile, omsFile);
        }
    }

    @Override
    public void deleteOmsFile(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("文件id不能为空");
        }
        //查找文件的名称
        String fileName = getFileName(id);
        if (StringUtils.isBlank(fileName)) {
            throw new CustomMessageException("文件不存在");
        }
        //删除文件
        omsFileUtils.deleteFile(fileName);
        //删除数据库数据
        omsFileMapper.deleteById(id);
    }

    private String getFileName(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("文件id不能为空");
        }
        OmsFile omsFile = omsFileMapper.selectById(id);
        return id + omsFile.getFileName().substring(omsFile.getFileName().lastIndexOf("."));
    }

    @Override
    public void downloadOmsFile(String fileId, String applyId) throws Exception {
        if (StringUtils.isBlank(fileId)) {
            throw new CustomMessageException("文件id不能为空");
        }
        if (StringUtils.isBlank(applyId)) {
            throw new CustomMessageException("申请记录id不能为空");
        }
        //获取文件名称
        OmsFile omsFile = omsFileMapper.selectById(fileId);
        String fileName = fileId + omsFile.getFileName().substring(omsFile.getFileName().lastIndexOf("."));
        if (StringUtils.isBlank(fileName)) {
            throw new CustomMessageException("文件不存在");
        }
        File file = omsFileUtils.getFile(fileName);
        //复制文件
//        FileOutputStream outputStream  = new FileOutputStream();
        File tempFile = File.createTempFile(UUIDGenerator.getPrimaryKey(), file.getName().substring(file.getName().lastIndexOf(".")));
        FileUtils.copyFile(file, tempFile);
        //查询所需的信息
        Map<String, Object> map = omsPubApplyMapper.selectPersonInfoByApplyId(applyId);

        if ("台湾".contains((String) map.get("SDGJ"))) {
            map.put("TITLE", Constants.TITLES[2]);
        } else if ("香港".contains((String) map.get("SDGJ")) || "澳门".contains((String) map.get("SDGJ"))) {
            map.put("TITLE", Constants.TITLES[1]);
        } else {
            map.put("TITLE", Constants.TITLES[0]);
        }
        //设置出国审批单位
        map.put("CGSPDW","海南省人民政府");
        //处理文件中的内容
        XSSFWorkbook book = null;
        try {
            book = new XSSFWorkbook(tempFile);
            XSSFSheet sheet = book.getSheetAt(0);
            for (Row next : sheet) {
                Iterator<Cell> cellIterator = next.cellIterator();
                while (cellIterator.hasNext()) {
                    boolean flag = false;
                    Cell cell = cellIterator.next();
                    String cellValue = cell.getStringCellValue();
                    //判断你是占位符的则去查找替换值
                    if (cellValue.contains("${") && cellValue.contains("}")) {
                        for (String key : map.keySet()) {
                            if (cellValue.equals("${" + key.toUpperCase() + "}")) {
                                Object obj = map.get(key);
                                String value = "";
                                if (obj instanceof Date) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                                    value = sdf.format(obj);
                                } else if (obj instanceof String) {
                                    value = (String) obj;
                                }
                                if (StringUtils.isBlank(value)) {
                                    value = "";
                                }
                                cell.setCellValue(value);
                                flag = true;
                                break;
                            }
                        }
                        //判断时候替换，处理未找到key的情况
                        if (!flag) {
                            cell.setCellValue("");
                        }
                    }
                }
            }
            //传回前端的文件
            String originFileName = omsFile.getFileName();
            omsFileUtils.downloadFileByPoi(originFileName, book);
        } catch (Exception e) {
            throw e;
        } finally {
            if (book != null) {
                book.close();
            }
            tempFile.delete();
        }
    }
}
