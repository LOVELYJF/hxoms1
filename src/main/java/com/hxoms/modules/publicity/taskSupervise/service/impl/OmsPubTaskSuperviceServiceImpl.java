package com.hxoms.modules.publicity.taskSupervise.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.entity.paramentity.SendMessageParam;
import com.hxoms.message.message.service.MessageService;
import com.hxoms.message.msguser.entity.MsgUser;
import com.hxoms.modules.publicity.taskSupervise.entity.*;
import com.hxoms.modules.publicity.taskSupervise.mapper.OmsPubTaskSuperviseMapper;
import com.hxoms.modules.publicity.taskSupervise.service.OmsPubTaskSuperviseService;
import com.hxoms.support.user.entity.User;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class OmsPubTaskSuperviceServiceImpl implements OmsPubTaskSuperviseService {
    @Autowired
    private OmsPubTaskSuperviseMapper omsPubTaskSuperviseMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TaskSuperviseCfg taskSuperviseCfg;
    @Override
    public List<ZtDwTreeVO> selectZtDwApplyList() {
        //获取用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<ZtDwTreeVO> ztDwList = null;
        if (userInfo == null) {
            return ztDwList;
        }
        //获取经办人所在单位的组团集合
        ztDwList = omsPubTaskSuperviseMapper.selectZtDwApplyList(userInfo.getId());
        return ztDwList;
    }
    @Override
    public PageBean selectZtDwPerson(PageBean pageBean,String year, String ztDwName) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<ZtDwPersionVO> pageInfo= new PageInfo<ZtDwPersionVO>(selectZtDwPersonAll(year, ztDwName));
        return PageUtil.packagePage(pageInfo);
    }

    public List<ZtDwPersionVO> selectZtDwPersonAll(String year, String ztDwName) {
        //获取用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<ZtDwPersionVO> ztDwPersionVOList = null;
        if (userInfo == null) {
            return ztDwPersionVOList;
        }
        if (StringUtils.isBlank(year) || StringUtils.isBlank(ztDwName)) {
            return ztDwPersionVOList;
        }
        ztDwPersionVOList = omsPubTaskSuperviseMapper.selectZtDwPersonAll(userInfo.getId(), year, ztDwName);
        return ztDwPersionVOList;
    }


    @Override
    public PageBean selectZtDwPersonByQua(PageBean pageBean,ZtDwPersionQuery ztDwPersionQuery) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<ZtDwPersionVO> pageInfo= new PageInfo<ZtDwPersionVO>(selectZtDwPersonByQuaAll(ztDwPersionQuery));
        return PageUtil.packagePage(pageInfo);
    }

    public List<ZtDwPersionVO> selectZtDwPersonByQuaAll(ZtDwPersionQuery ztDwPersionQuery) {
        //获取用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<ZtDwPersionVO> ztDwPersionVOList = null;
        if (userInfo == null ) {
            return ztDwPersionVOList;
        }
        ztDwPersionQuery.setId(userInfo.getId());
        ztDwPersionVOList = omsPubTaskSuperviseMapper.selectZtDwPersonByQuaAll(ztDwPersionQuery);
        return ztDwPersionVOList;
    }
    @Override
    public  FileInfo batchDownloadBab(DownloadBabParam downloadBabParam) throws Exception {
        List<String> list = downloadBabParam.getList();
        ZtDwPersionQuery ztDwPersionQuery = downloadBabParam.getZtDwPersionQuery();
        FileInfo  fileInfo = new FileInfo();
        byte[] fileDateByte=null;
        if(list.size()==1){
            //pdf文件下载
            String fileName=list.get(0)+ taskSuperviseCfg.getBabDownSuffix();
            fileDateByte= downloadFile(taskSuperviseCfg.getBabDownPath()+fileName);
            fileInfo.setFileName(fileName);
            fileInfo.setFileDataByte(fileDateByte);
        }else{
            //获取生成zip文件数据
            if(list.size()==0){
                //未选中，则通过条件查询下载
                List<ZtDwPersionVO> ztDwPersionVOList = selectZtDwPersonByQuaAll(ztDwPersionQuery);
                for (ZtDwPersionVO ztDwPersionVO:ztDwPersionVOList) {
                    if(!list.contains(ztDwPersionVO.getId())){
                        list.add(ztDwPersionVO.getId());
                    }
                }
            }
            //生成zip文件
            String zipFileName=UUIDGenerator.getPrimaryKey()+taskSuperviseCfg.getCompressDownSuffix();
            String zipFilePullPath=taskSuperviseCfg.getCompressDownPath()+zipFileName;
            List<File> fileList=new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String fileName = list.get(i) + taskSuperviseCfg.getBabDownSuffix();
                String filePath=taskSuperviseCfg.getBabDownPath() + fileName;
                File inputFile=new File(FilenameUtils.normalize(filePath));
                //判断文件是否存在
                if (inputFile.exists()&&inputFile.isFile()) {
                    fileList.add(inputFile);
                }
            }
            ZIPUtils.zipFiles(fileList, new File(zipFilePullPath));
            //zip文件下载
            fileDateByte=downloadFile(zipFilePullPath);
            //删除zip文件
            FileUtil.deleteFile(zipFilePullPath);
            fileInfo.setFileName(zipFileName);
            fileInfo.setFileDataByte(fileDateByte);
        }
        return fileInfo;
    }

    /**
     * @Desc: 文件下载
     * @Author: wangyunquan
     * @Param: [filedDownPath]
     * @Return: byte[]
     * @Date: 2020/7/7
     */
    public byte[] downloadFile(String filedDownPath) throws IOException {
        byte[] fileDateByte=null;
        File file=new File(FilenameUtils.normalize(filedDownPath));
        if(!file.exists()||!file.isFile()){
            throw new CustomMessageException("文件未生成！");
        }
        ByteArrayOutputStream outStream=null;
        FileInputStream in=null;
        try {
            outStream = new ByteArrayOutputStream();
            in = new FileInputStream(file);
            int len;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }
            fileDateByte=outStream.toByteArray();
        } catch (IOException e) {
            throw new CustomMessageException(e);
        } finally {
            //关闭流
            if(in!=null){
                in.close();
            }
            if(outStream!=null){
                outStream.close();
            }
        }
        return fileDateByte;
    }
    @Override
    public void urgeBusiness(String id) throws Exception {
        UrgeParameterVO urgeParameterVO = omsPubTaskSuperviseMapper.selectById(id);
        if (urgeParameterVO == null)
            throw new CustomMessageException("业务数据为空，无法办理催办业务！");
        int sqzt=Integer.parseInt(urgeParameterVO.getSqzt());
        //申请状态为已办结、待领证、已领证、撤销情况，不能办理催办业务。
        if(sqzt>=28)
            throw new CustomMessageException("备案申请状态为："+Constants.leader_business[sqzt]+"，无法办理催办业务！");
        if (Arrays.asList(Constants.leader_business).contains(sqzt)) {
            //干部监督处处理
            preAndRecMessage(urgeParameterVO, taskSuperviseCfg.getJdcMsgTemplate(),"5","1");
        } else {
            //经办人处理
            preAndRecMessage(urgeParameterVO, taskSuperviseCfg.getJbrMsgTemplate(),"6","1");
        }
    }
    /**
     * @Desc: 准备参数和发送线上消息
     * @Author: wangyunquan
     * @Param: [urgeParameterVO, msgTemplate, userType, receiveUserType]
     * //用户类型：5监督处工作人员、6经办人
     * //接收用户 key：类型 1个人 2处室 3机构 4讨论组
     * @Return: void
     * @Date: 2020/6/29
     */
    public void preAndRecMessage(UrgeParameterVO urgeParameterVO,String msgTemplate,String userType,String receiveUserType) throws Exception{
        //用户类型为监督处工作人员时，查询条件剔除机构单位。
        List<User> userList=omsPubTaskSuperviseMapper.selectUserByQua(userType.equals("5")?null:urgeParameterVO.getOrgId(),userType);
        if(userList==null||userList.size()==0)
            throw new CustomMessageException("无接收者，无法办理催办业务！");
        SendMessageParam sendMessageParam=new SendMessageParam();
        //设置参数
        Field fields[]=urgeParameterVO.getClass().getDeclaredFields();
        for (int i=0;i<fields.length;i++){
            Field field=fields[i];
            field.setAccessible(true);
            String fieldName="${"+field.getName()+"}";
            if(msgTemplate.contains(fieldName)){
                msgTemplate=msgTemplate.replace(fieldName,(String)field.get(urgeParameterVO));
            }
        }
        //设置信息内容
        Message message=new Message();
        message.setContent(String.format(msgTemplate,urgeParameterVO.getName(),urgeParameterVO.getDays()));
        sendMessageParam.setMessage(message);
        //设置接收人
        List<MsgUser> msgUserList=new ArrayList<>();
        for (User user:userList) {
            MsgUser msgUser=new MsgUser();
            msgUser.setReceiveUserId(user.getId());
            msgUser.setReceiveUsername(user.getUserName());
            msgUserList.add(msgUser);
        }
        Map<String,List<MsgUser>> msgUserMap=new HashedMap();
        msgUserMap.put(receiveUserType,msgUserList);
        sendMessageParam.setMsgUserMap(msgUserMap);
        //发送信息
        messageService.sendMessage(sendMessageParam);
    }
}
