package com.gc.vp.web;

import com.gc.vp.entity.po.DataSourcePo;
import com.gc.vp.utils.JsonUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataSourceControllerTest {

    @Test
    public void list() {
    }

    @Test
    public void add() {
        DataSourcePo dataSourcePo = new DataSourcePo("测试数据源1", "gc", "gc");
        System.out.println(JsonUtils.stringify(dataSourcePo));
    }
}