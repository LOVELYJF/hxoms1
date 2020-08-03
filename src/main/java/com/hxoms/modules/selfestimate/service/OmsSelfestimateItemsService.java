package com.hxoms.modules.selfestimate.service;

import com.hxoms.modules.selfestimate.entity.OmsSelfFile;
import com.hxoms.modules.selfestimate.entity.OmsSelfFileVO;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateItems;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemVO;

import java.util.List;
import java.util.Map;

public interface OmsSelfestimateItemsService {
    /**
     * 自评信息维护列表
     * @return
     */
    List<OmsSelfFile> selectItemsList(String type);
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
     * 自评结果列表
     * @return
     * @param type 因公 因私  延期回国
     * @param applyId 申请id
     * @param personType 操作人类型（经办人  干部监督处）
     */
    List<OmsSelfFileVO> selectFileList(String type, String applyId, String personType);

    /**
     * 添加文件列表
     * @return
     * @param type 因公 因私  延期回国
     */
    List<Map<String, String>> selectOmsFileList(String type);

    /**
     * 自评项目维护列表
     * @return
     * @param selffileId 自评材料清单id
     */
    List<OmsSelfestimateItems> selectSelfItemList(String selffileId);

    /**
     * 自评结果项列表
     * @return
     * @param applyId 自评id
     * @param applyId 申请id
     * @param personType 操作人类型（经办人  干部监督处）
     */
    OmsSelfFileVO selectFileItemsList(String type, String selffileId, String applyId, String personType);
}
