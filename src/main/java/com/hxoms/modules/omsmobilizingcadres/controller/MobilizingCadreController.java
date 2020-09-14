package com.hxoms.modules.omsmobilizingcadres.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre;
import com.hxoms.modules.omsmobilizingcadres.service.MobilizingcadreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈调整干部〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/5/29 9:01
 */
@RestController
@RequestMapping("/mobilizingCadre")
public class MobilizingCadreController {
    @Autowired
    MobilizingcadreService mobilizingcadreService;

    /**
     * 功能描述: <br>
     * 〈添加调整期干部〉
     * @Param: [mobilizingcadre, loginUser]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:10
     */
    @PostMapping("/insertMobilizingCadre")
    public Result insertMobilizingCadre(OmsMobilizingcadre mobilizingCadre ){
        mobilizingcadreService.insertMobilizingCadre(mobilizingCadre);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈根据ID删除调整期干部〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:15
     */
    @PostMapping("/deleteMobilizingCadre")
    public Result deleteMobilizingCadre(String id){
        mobilizingcadreService.deleteMobilizingCadre(id);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈通过人员主键查找调整期干部〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:21
     */
    @GetMapping("/getMobilizingCadreByID")
    public Result getMobilizingCadreByID(String id){
        OmsMobilizingcadre mobilizingCadre = mobilizingcadreService.getMobilizingCadreByID(id);
        return Result.success(mobilizingCadre);
    }

    /**
     * 功能描述: <br>
     * 〈通过机构或者条件查找全部调整期干部列表〉
     * @Param: [orgIds, name, status]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:46
     */
    @GetMapping("/getAllMobilizingCadre")
    public Result getAllMobilizingCadre(Integer pageNum, Integer pageSize,@RequestParam(value ="orgIds",required = false) List<String> orgIds, String name, String status){
        PageInfo info = mobilizingcadreService.getAllMobilizingCadre(pageNum,pageSize,orgIds, name, status);
        return Result.success(info.getList()).setTotal(info.getTotal());
    }

    /**
     * 功能描述: <br>
     * 〈通过条件导出调整期干部〉
     * @Param: [orgIds, name, status, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/9/14 10:02
     */
    @PostMapping("/exportMobilizingCadre")
    public void  exportMobilizingCadre(@RequestParam(value ="orgIds",required = false) List<String> orgIds, String name, String status, HttpServletResponse response){
        mobilizingcadreService.exportMobilizingCadre(orgIds,name,status,response);
    }
}
