package com.gc.vp.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gc.vp.entity.po.DatasourcePo;
import com.gc.vp.entity.vo.TransDto;
import com.gc.vp.entity.vo.ds.ListDatasourceReq;
import com.gc.vp.service.IDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源配置
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
        LambdaQueryWrapper<DatasourcePo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(req.getKeyword())) {
            queryWrapper.like(DatasourcePo::getName, req.getKeyword());
        }
        return TransDto.success(dataSourceService.list(queryWrapper));
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
