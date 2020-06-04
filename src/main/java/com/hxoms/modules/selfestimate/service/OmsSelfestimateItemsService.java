package com.hxoms.modules.selfestimate.service;

import com.hxoms.modules.selfestimate.entity.OmsSelfFile;
import com.hxoms.modules.selfestimate.entity.OmsSelfFileVO;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateItems;

import java.util.List;

public interface OmsSelfestimateItemsService {
    /**
     * 自评信息维护列表
     * @return
     */
    List<OmsSelfFileVO> selectItemsList(String type);
    /**
     * 自评项目添加修改
     * @return
     * @param omsSelfestimateItems
     */
    String insertItem(OmsSelfestimateItems omsSelfestimateItems);
    /**
     * 删除自评项目
     * @return
     * @param id
     */
    String deleteItem(String id);

    /**
     * 自评文件添加修改
     * @return
     * @param omsSelfFile
     */
    String insertSelfFile(OmsSelfFile omsSelfFile);

    /**
     * 自评文件删除
     * @return
     * @param id
     */
    String deleteSelfFile(String id);

    /**
     * 自评文件列表
     * @return
     * @param type 因公 因私  延期回国
     */
    List<OmsSelfFileVO> selectFileList(String type);
}
