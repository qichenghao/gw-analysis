package com.qtone.gy.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {
	
	/**
	 * 跨域支持过滤器
	 * @author huangguangxi
	 * @date:   2017年9月19日 下午7:13:24    
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException       
	 * @throws
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Token, system-code");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {

	}

	public void destroy() {

	}
}