package com.gc.vp.entity.vo.code;

import lombok.Data;

import java.util.List;

@Data
public class ComTreeReq {
    private int id;
    private int parentId;
    private String type;
    private String comName;
    private List<ComTreeReq> childrens;
}
