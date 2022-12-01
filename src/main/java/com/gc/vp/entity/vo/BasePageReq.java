package com.gc.vp.entity.vo;

import lombok.Data;

@Data
public class BasePageReq {
    private Integer currentPage;
    private Integer pageSize;
}
