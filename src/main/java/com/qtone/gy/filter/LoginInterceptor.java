package com.qtone.gy.filter;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {
	
	//private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Value("${token.duration}")
	private long duration;
	
	/** 请求开始时间 */
	private long beginTime;
	/** 请求结束时间 */
	private long endTime;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		endTime = System.currentTimeMillis();

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		return true;
	}
	
	/*private void printRequestHeader(HttpServletRequest request) {
		Enumeration names = request.getHeaderNames();
		System.out.println("=======================打印请求头信息开始============================================");
		while(names.hasMoreElements()){
			String name = (String) names.nextElement();
			System.out.println(name + ":" + request.getHeader(name));
		}
		System.out.println("========================打印请求头信息结束===========================================");
	}*/
}
