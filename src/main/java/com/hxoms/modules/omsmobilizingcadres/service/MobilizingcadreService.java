package com.hxoms.modules.omsmobilizingcadres.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre;
import com.hxoms.modules.sysUser.entity.CfUser;

import java.util.List;
import java.util.Map;

public interface MobilizingcadreService {
    /**
     * 功能描述: <br>
     * 〈添加调整期干部〉
     * @Param: [mobilizingcadre, loginUser]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:09
     */
    void insertMobilizingCadre(OmsMobilizingcadre mobilizingCadre);

    /**
     * 功能描述: <br>
     * 〈根据ID删除调整期干部〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:14
     */
    void deleteMobilizingCadre(String id);

    /**
     * 功能描述: <br>
     * 〈通过人员主键查找调整期干部〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:22
     */
    OmsMobilizingcadre getMobilizingCadreByID(String id);

    /**
     * 功能描述: <br>
     * 〈通过机构或者条件查找全部调整期干部列表〉
     * @Param: [orgIds, name, status]
     * @Return: java.util.List<com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre>
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:45
     */
    PageInfo getAllMobilizingCadre(List<String> orgIds, String name, String status);

    /**
     * 功能描述: <br>
     * 〈每天自动拉取干部信息库信息更改调整期状态〉
     * @Param: [a0100]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/23 9:43
     */
    void updateStatus(String a0100);
}
