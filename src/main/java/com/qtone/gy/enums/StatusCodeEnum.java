package com.qtone.gy.enums;


/**
 * 状态码enum类
 */
public enum StatusCodeEnum implements BaseResultEnum {
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "Bad Request!"),
    NOTAUTHORIZATION(401, "NotAuthorization"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    SERVER_ERROR(500, "服务器出错"),

    SERVER_RUN_EX(1000, "[服务器]运行时异常"),
    SERVER_NULL_EX(1001, "[服务器]空值异常"),
    PARSE_EX(1002, "[服务器]数据类型转换异常"),
    IO_EX(1003, "[服务器]IO异常"),
    UNKNOW_EX(1004, "[服务器]未知方法异常"),
    ARRAY_EX(1005, "[服务器]数组越界异常"),
    NET_EX(1006, "[服务器]网络异常") ;

    private int code;

    private String msg;

    StatusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
