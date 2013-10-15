package com.weixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.weixin.utility.Hint;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 登录servlet
 */
@SuppressWarnings("serial")
public class Login extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession sess = request.getSession();
		//roleID充当unitID
		Long roleID = (Long)sess.getAttribute("authorize.user.roleid");
		String userID = (String)sess.getAttribute("authorize.user.name");
		if(roleID==null){
			System.out.println("no");
			Hint.hint("帐号或密码不正确", "login.jsp", request, response);
		}
		Integer unitID = roleID.intValue();
		sess.setAttribute("unitID", unitID);
		sess.setAttribute("userID", userID);
		response.sendRedirect("unit.jsp");
	}
}