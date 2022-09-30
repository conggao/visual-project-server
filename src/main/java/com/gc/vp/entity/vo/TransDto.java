package com.gc.vp.entity.vo;

import lombok.Data;

@Data
public class TransDto<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    public static <T> TransDto<T> success(T data) {
        TransDto<T> res = new TransDto<>();
        res.setSuccess(true);
        res.setCode(0);
        res.setData(data);
        res.setMessage("ok");
        return res;
    }
}
