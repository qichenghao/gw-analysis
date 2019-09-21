package com.qtone.gy.exception;


import com.qtone.gy.enums.BaseResultEnum;

/**
 * 业务异常处理类
 *  
 * @author huangguangxi
 * @date:   2017年9月29日 下午5:09:23   
 *
 */
public class BusinessException extends Exception{

	/**   
	 * serialVersionUID : TODO   
	 */  
	private static final long serialVersionUID = 1L;

	/**枚举*/
    private BaseResultEnum resultEnum;
    /**参数属性*/
    private String param;
    
	/**
	 * 统一异常枚举处理
	 * @param resultEnum
	 */
	public BusinessException(BaseResultEnum resultEnum) {
		super(resultEnum.getMsg());
		this.resultEnum = resultEnum;
	}
	
	public BusinessException(BaseResultEnum resultEnum,String param) {
		super(resultEnum.getMsg());
		this.resultEnum = resultEnum;
		this.param = param;
	}
	
	public BaseResultEnum getResultEnum() {
		return resultEnum;
	}

	public void setResultEnum(BaseResultEnum resultEnum) {
		this.resultEnum = resultEnum;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	

}
