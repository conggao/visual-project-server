package com.gc.vp.web;

import com.gc.vp.entity.po.DataSourcePo;
import com.gc.vp.entity.vo.TransDto;
import com.gc.vp.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datasource")
public class DataSourceController {
    @Autowired
    private DataSourceService dataSourceService;

    /**
     * 查询数据源配置列表
     *
     * @return
     */
    @GetMapping("/list")
    public TransDto<List<DataSourcePo>> list() {
        return TransDto.success(dataSourceService.list());
    }

    /**
     * 新增数据源配置
     *
     * @return
     */
    @PostMapping("/add")
    public TransDto<DataSourcePo> add(
            @RequestHeader("header_name") String value,
            @RequestBody DataSourcePo dataSourceConfig
    ) {
        return TransDto.success(dataSourceService.add(dataSourceConfig));
    }
}
