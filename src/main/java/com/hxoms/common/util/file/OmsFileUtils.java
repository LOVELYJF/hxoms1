package com.hxoms.common.util.file;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.DomainObjectUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class OmsFileUtils {
    Logger logger = LoggerFactory.getLogger(OmsFileUtils.class);

//    @Value("${omsFile.baseDir}")
    private String baseDir;

    public String getBaseDir() {
        return baseDir;
    }

    public void saveFile(MultipartFile file, String primaryKey) throws IOException {
        if (file == null) {
            throw new CustomMessageException("上传的文件为空");
        }
        if (StringUtils.isBlank(primaryKey)) {
            throw new CustomMessageException("参数为空");
        }
        if (StringUtils.isBlank(baseDir)) {
            throw new CustomMessageException("文件存储根路径为空");
        }
        String dirPath = baseDir;
        File fileDir = new File(dirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String filename = file.getOriginalFilename();
        String filePath = baseDir + File.separator + primaryKey + filename.substring(filename.lastIndexOf("."));
        file.transferTo(new File(filePath));

    }

    public void deleteFile(String fileName) {
        File file = new File(baseDir + File.separator + fileName);
        logger.error(fileName);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        } else {
            throw new CustomMessageException("文件不存在");
        }
    }

    public File getFile(String fileName) {
        String filePath = baseDir + File.separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new CustomMessageException("文件不存在");
        }
        return file;
    }

    public void downloadFile(String fileName, File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        downloadFile(fileName, in);
    }

    public void downloadFile(String fileName, InputStream in) {
        OutputStream out = null;
        try {

            HttpServletResponse response = DomainObjectUtil.getResponse();
            HttpServletRequest request = DomainObjectUtil.getRequest();
            response.reset();
            //浏览器设置
            String userAgent = request.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                //IE浏览器处理
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //获取文件输入流
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                //将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadFileByPoi(String fileName, Workbook book) {
        OutputStream out = null;
        try {

            HttpServletResponse response = DomainObjectUtil.getResponse();
            HttpServletRequest request = DomainObjectUtil.getRequest();
            response.reset();
            //浏览器设置
            String userAgent = request.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                //IE浏览器处理
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //获取文件输入流
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            book.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
