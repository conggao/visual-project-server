package com.gc.vp.service;

import com.gc.vp.dao.DataSourceRepository;
import com.gc.vp.entity.po.DataSourcePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataSourceService {
    @Autowired
    private DataSourceRepository dataSourceRepository;

    public List<DataSourcePo> list() {
        return dataSourceRepository.findAll();
    }
    public DataSourcePo add(DataSourcePo dataSourceConfig){
        dataSourceConfig.setEnable(true);
        return dataSourceRepository.save(dataSourceConfig);
    }
}
