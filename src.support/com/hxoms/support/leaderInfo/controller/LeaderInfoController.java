package com.hxoms.support.leaderInfo.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.support.leaderInfo.service.LeaderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leaderInfo")
public class LeaderInfoController {

    @Autowired
    private LeaderInfoService leaderInfoService;

    /**
     * @desc: 根据id查询返回数据
     * @author: sundeng
     * @date: 2019/7/26
     */
    @RequestMapping("/selectBasicInfo")
    public Result selectBasicInfo(Integer pageNum, Integer pageSize,String orgId) {
        Map<String,Object> map = leaderInfoService.selectBasicInfo(pageNum, pageSize, orgId);
        return Result.success(map);
    }
    /**
     * 功能描述: <br>
     * 〈根据关键词/姓氏模糊查询结果〉
     * @Param: [name]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/4/29 11:04
     */
    @RequestMapping("/selectBasicInfoByName")
    public Result selectBasicInfoByName(String name){
        Map<String,Object> map = leaderInfoService.selectBasicInfoByName(name);
        return Result.success(map);
    }

    /**
     * @desc: 查询数据信息集
     * @author: sundeng
     * @date: 2019/7/26
     */
    @RequestMapping("/selectLeaderInfoData")
    public Result getUiSetingWithData(String tablecode, String id) {
        List<Map<String, Object>> a31s = leaderInfoService.selectLeaderInfoData(tablecode, id);
        return Result.success(a31s);
    }

    /**
     * @desc:  修改干部信息
     *
     *
     */
    @RequestMapping("/updateLeaderInfoByTableCode")
    public Result updateLeaderInfoByTableCode(@RequestBody Map<String,String> tableData){

        System.out.println(tableData+"1111");
        leaderInfoService.updateLeaderInfoByTableCode(tableData);

        return Result.success();

    }
}
