package com.hashtech.feign.result;

/**
 * API错误码通用接口
 *
 * @author EDZ
 */
public interface IErrorCode {

    /**
     * 异常操作码
     *
     * @return
     */
    long getCode();

    /**
     * 异常信息
     *
     * @return
     */
    String getMessage();

}
