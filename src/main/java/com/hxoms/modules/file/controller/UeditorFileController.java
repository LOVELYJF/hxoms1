package com.hxoms.modules.file.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.hxoms.common.utils.DomainObjectUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.baidu.ueditor.ActionEnter;
import com.hxoms.modules.file.entity.UeditorResponseVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @desc: Ueditor上传文件管理
 * @author: lijing
 * @date: 2020-07-13
 */
@RestController
@RequestMapping("/ueditorFile")
public class UeditorFileController {

    @Value(value = "${file.ueditorRealImgUrl}")
    private String ueditorRealImgUrl;
    @Value(value = "${file.ueditorImgUrl}")
    private String ueditorImgUrl;

    /**
     * 读取配置文件
     * @return
     */
    @RequestMapping("/config")
    public Object selectFileListByCode(String action, MultipartFile upfile) throws JSONException {
        try {
            HttpServletResponse response = DomainObjectUtil.getResponse();
            HttpServletRequest request = DomainObjectUtil.getRequest();
            //读取配置文件
            if (action.equals("config")) {
                request.setCharacterEncoding("utf-8");
                String exec = new ActionEnter(request, "").exec();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
            }else if("uploadimage".equals(action)){
                //上传图片
                String originalFilename = upfile.getOriginalFilename();
                String fileName = UUIDGenerator.getPrimaryKey() + originalFilename.substring(upfile.getOriginalFilename().lastIndexOf("."), upfile.getOriginalFilename().length()).toLowerCase();
                UeditorResponseVO ur = new UeditorResponseVO();
                ur.setType(fileName.split("\\.")[1]);
                ur.setOriginal(fileName);
                try {
                    File dest = new File(ueditorRealImgUrl + fileName);
                    if (!dest.getParentFile().exists()){
                        dest.getParentFile().mkdirs();
                    }
                    upfile.transferTo(dest);
                    ur.setUrl("/oms" + ueditorImgUrl + fileName);
                    ur.setState("SUCCESS");
                    return JSONArray.toJSON(ur).toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    ur.setState("图片上传失败");
                    return JSONArray.toJSON(ur).toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
