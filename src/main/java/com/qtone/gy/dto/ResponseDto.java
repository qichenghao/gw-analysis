package com.qtone.gy.dto;

import java.io.Serializable;

/**
 * 返回响应结果
 */
public class ResponseDto implements Serializable {

    /**   
	 * serialVersionUID : TODO   
	 */  
	private static final long serialVersionUID = 1L;

	/*状态码*/
    private int code;

    /*响应信息*/
    private String msg;

    /*是否成功*/
    private boolean success;

    /*数据结果*/
    private Object data;

    public ResponseDto() {
    }

    public ResponseDto(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseDto(int code, String msg, boolean success, Object data) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
