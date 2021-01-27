package com.huaxin.webchat.model;

import com.huaxin.webchat.unit.EnumsUtils;
import lombok.Data;

/**
 * 微信异常处理类
 */
@Data
public class BizException extends RuntimeException{
    private int code;

    private String msg;

    private EnumsUtils enumsUtils;

    public BizException(EnumsUtils enumsUtils) {
        this.enumsUtils = enumsUtils;
    }

    public BizException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
