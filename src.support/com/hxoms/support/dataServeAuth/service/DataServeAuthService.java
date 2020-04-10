package com.hxoms.support.dataServeAuth.service;

import com.hxoms.support.dataServeAuth.entity.DataServeAuth;
import com.hxoms.support.dataServeAuth.entity.DataServeAuthVO;

import java.util.List;

public interface DataServeAuthService {

    public abstract List<DataServeAuthVO> selectByServeIdAndInfoResourceId(String serveId);

    public abstract List<DataServeAuth> selectByDataInterfaceDefinitionId(String interfaceDefinitionId);

    public abstract DataServeAuth selectDataServeAuthById(String id);

    public abstract int insertDataServeAuth(DataServeAuth dataServeAuth);

    public abstract int deleteById(String id);

    public abstract void insertBatchDataServeAuth(String params);
}
