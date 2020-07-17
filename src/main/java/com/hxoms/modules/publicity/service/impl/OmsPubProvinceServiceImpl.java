package com.hxoms.modules.publicity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.mapper.OmsPubProvinceMapper;
import com.hxoms.modules.publicity.service.OmsPubProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OmsPubProvinceServiceImpl extends ServiceImpl<OmsPubProvinceMapper, OmsPubApply> implements OmsPubProvinceService {


    @Autowired
    private OmsPubProvinceMapper pubProvinceMapper;

    @Override
    public PageInfo<OmsPubApply> getPubProvinceList(Map<Object,String> param) throws ParseException {
        List<OmsPubApply> resultList = pubProvinceMapper.getPubProvinceList(param);
        PageUtil.pageHelp(Integer.parseInt(param.get("pageNum")), Integer.parseInt(param.get("pageSize")));
        PageInfo<OmsPubApply> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    @Override
    public Object insertPubProvince(OmsPubApply pubApply, List<OmsPubApply> list) {
        int num = list.size();
        if(num > 0){
            //登录用户信息
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            for(int i = 0; i < num; i++){
                list.get(i).setId(UUIDGenerator.getPrimaryKey());

                list.get(i).setCreateUser(userInfo.getId());
                list.get(i).setCreateTime(new Date());
            }
            return pubProvinceMapper.insertPubProvince(list);
        }else{
            return "未选择备案人员";
        }
    }
}
