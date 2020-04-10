package com.hxoms.support.ETLDataException.mapper;

import com.hxoms.support.ETLDataException.entity.Wrongrecord;
import com.hxoms.support.ETLDataException.entity.WrongrecordExample;
import java.util.List;

public interface WrongrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Wrongrecord record);

    int insertSelective(Wrongrecord record);

    List<Wrongrecord> selectByExampleWithBLOBs(WrongrecordExample example);

    List<Wrongrecord> selectByExample(WrongrecordExample example);

    Wrongrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Wrongrecord record);

    int updateByPrimaryKeyWithBLOBs(Wrongrecord record);

    int updateByPrimaryKey(Wrongrecord record);
}