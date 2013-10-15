package com.weixin.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 登录过滤器
 */
public class LoginFilter implements Filter {
	protected FilterConfig config;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String userID = (String) req.getSession().getAttribute("userID");
		if (userID == null) {
			String error = "请先登录";
			request.setAttribute("error", error);
			request.getRequestDispatcher("home.jsp")
					.forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
	}
}