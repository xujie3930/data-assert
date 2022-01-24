package com.hashtech.feign.result;

/**
 * @author EDZ
 */
public enum ResultCode implements IErrorCode {
    /**
     * 操作状态码及提示信息
     */
    SUCCESS(200, "操作成功"),
    PATH_NOT_FUND(404, "路径不存在"),
    UNAUTHORIZED(401, "未登录/Token已过期"),
    FORBIDDEN(403, "暂无权限"),
    VALIDATE_FAILED(1003, "参数校验异常"),
    TOO_MANY_REQUESTS_ERROR(1004, "请求过多,服务流控触发"),
    FAILED(500, "操作失败,请稍后再试"),
    BAD_GATEWAY(1006, "网关服务异常"),
    GEN_TOKEN_ERROR(1007, "token生成异常"),
    AUTHORIZATION_HEADER_IS_EMPTY(1008, "请求头中token为空"),
    GET_TOKEN_KEY_ERROR(1009, "远程获取tokenKey异常"),
    GRN_PUBLIC_KEY_ERROR(1010, "公钥生成异常"),
    JWT_TOKEN_EXPIRE_ERROR(1011, "token校验异常"),
    BACKGROUND_DEGRADED_ERROR(1012, "请求过多,服务降级触发"),
    DUPLICATE_KEY_ERROR(1013, "数据重复，请检查后提交"),
    PARAM_FAIL_ERROR(1014, "参数异常，请检查后提交");

    private long code;

    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
