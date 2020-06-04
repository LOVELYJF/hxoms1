

package com.hxoms.modules.dataCapture.log.service.impl;






import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.modules.dataCapture.log.mapper.SysLogMapper;
import com.hxoms.modules.dataCapture.log.service.SysLogService;
import com.hxoms.modules.dataCapture.entity.SysLogEntity;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
//@Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false)
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

    @Transactional
    @Override
    public void deleteAndsave() {
        baseMapper.deleteById("2da48869-e21c-1038-99c7-fe7a699dd96a");
        int a = 1/0;
    }
}
