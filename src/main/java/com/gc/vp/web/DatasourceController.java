package com.gc.vp.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gc.vp.entity.po.DatasourcePo;
import com.gc.vp.entity.vo.TransDto;
import com.gc.vp.entity.vo.ds.ListDatasourceReq;
import com.gc.vp.service.IDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author conggao
 * @since 2022-10-06
 */
@RestController
@RequestMapping("/api/datasource")
public class DatasourceController {
    @Autowired
    private IDatasourceService dataSourceService;

    /**
     * 查询数据源配置列表
     *
     * @return
     */
    @PostMapping("/list")
    public TransDto<List<DatasourcePo>> list(@RequestBody ListDatasourceReq req) {
        return TransDto.success(dataSourceService.list(
                new LambdaQueryWrapper<DatasourcePo>().like(DatasourcePo::getName, req.getKeyword())
        ));
    }

    /**
     * 新增数据源配置
     *
     * @return
     */
    @PostMapping("/add")
    public TransDto<Boolean> add(
            @RequestHeader("loginUser") String loginUser,
            @RequestBody DatasourcePo dataSourceConfig
    ) {
        dataSourceConfig.setCreateUser(loginUser);
        dataSourceConfig.setUpdateUser(loginUser);
        return TransDto.success(dataSourceService.save(dataSourceConfig));
    }
}
