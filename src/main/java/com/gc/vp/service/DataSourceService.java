package com.gc.vp.service;

import com.gc.vp.entity.po.DatasourcePo;
import com.gc.vp.entity.vo.TransDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataSourceService {
    @Autowired
    private IDatasourceService datasourceService;

    public TransDto<List<DatasourcePo>> list() {
        return TransDto.success(datasourceService.list());
    }
    public TransDto<Boolean> add(DatasourcePo dataSourceConfig){
        dataSourceConfig.setEnable(true);
        return TransDto.success(datasourceService.save(dataSourceConfig));
    }
}
