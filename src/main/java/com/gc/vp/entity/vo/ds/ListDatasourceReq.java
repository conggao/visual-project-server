package com.gc.vp.entity.vo.ds;

import lombok.Data;

@Data
public class ListDatasourceReq {
    private Integer currentPage;
    private Integer pageSize;
    private String keyword;
}
