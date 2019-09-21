package com.qtone.gy.dto;

import java.io.Serializable;

/**
 * 分页返回响应结果
 */
public class ResponsePageDto implements Serializable {

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
    
    /**分页信息*/
    private PageGridDto pageInfo;

    public ResponsePageDto() {
    }

    public ResponsePageDto(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponsePageDto(int code, String msg, boolean success, Object data) {
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

	public PageGridDto getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageGridDto pageInfo) {
		this.pageInfo = pageInfo;
	}
    
}
