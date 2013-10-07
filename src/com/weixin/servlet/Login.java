package com.weixin.servlet;

import com.weixin.daoimpl.UserDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_User;
import com.weixin.utility.Hint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 登录servlet
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDaoImpl userDao = UserDaoImpl.getInstance();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		//第一次运行执行，后期修改为一个servlet吧
//		if(userDao.getAllUser()==null){
//			addAdmin();
//		}
		TB_User user = userDao.findByUserAccountandPassword(account,password);
		if (user == null) {
			Hint.hint("帐号或者密码错误", "login.jsp", request, response);
			return;
		}
		//System.out.println(user.getUnit());
		TB_Unit unit = user.getUnit();
		Integer unitID = 0;
		if(unit!=null){
			unitID = unit.getUnitID();
		}
		HttpSession sess = request.getSession();
		sess.setAttribute("userID", account);
		sess.setAttribute("unitID", unitID);
		response.sendRedirect("unit.jsp");
	}
	
	@SuppressWarnings("unused")
	private void addAdmin(){
		TB_User user = new TB_User();
		user.setUserAccount("admin");
		user.setPassword("123");
		user.setRole(1);
		userDao.saveOrUpdate(user);
	}
}