package com.gc.vp.entity.vo.code;

import lombok.Data;

@Data
public class ListCodeCategoryReq {
    private Integer currentPage;
    private Integer pageSize;
    private Integer categoryId;
}
