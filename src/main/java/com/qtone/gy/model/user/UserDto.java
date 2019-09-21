package com.qtone.gy.model.user;
/**
 * 用户登录dto
 * @author guohaibing
 *
 */
public class UserDto {

	/**用户名*/
	private String username;
	/**密码*/
	private String password;
	/**用户类型*/
	private Integer userType;
	/**验证码*/
	private String verificationCode;
	/**key时间戳*/
	private String key;
	/***登录方式*/
	private String loginChannel;
	/**openId*/
	private String openId;
	/**浏览器指纹*/
	private String browsersId;
	
	public UserDto() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLoginChannel() {
		return loginChannel;
	}

	public void setLoginChannel(String loginChannel) {
		this.loginChannel = loginChannel;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getBrowsersId() {
		return browsersId;
	}

	public void setBrowsersId(String browsersId) {
		this.browsersId = browsersId;
	}

	@Override
	public String toString() {
		return "UserDto [username=" + username + ", password=" + password + ", userType=" + userType
				+ ", verificationCode=" + verificationCode + ", key=" + key + ", loginChannel=" + loginChannel
				+ ", openId=" + openId +",browsersId="+browsersId+"]";
	}
	
}
