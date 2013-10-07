package com.weixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.UserDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_User;
import com.weixin.utility.Hint;

/** 
 * @author wjh 
 * @version 创建时间：2013年9月19日 下午2:23:55 
 * 
 * 类说明 
 * 1.添加酒店(action=addUnit appid secret unitName)
 * 2.添加用户(action=addUser account password unitID role)
 */
public class Registe extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	private UserDaoImpl userDao = UserDaoImpl.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if(action.equals("addUser")){
			//添加用户
			addUser(request, response);
		}
		else if(action.equals("addUnit")){
			//添加单位
			addUnit(request,response);
		}
	}
	
	private void addUnit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		try{
			String appid = request.getParameter("appid");
			String secret = request.getParameter("secret");
			String unitName = request.getParameter("unitName");
			if(appid==null||secret==null){
				return;
			}
			TB_Unit unit = new TB_Unit();
			unit.setAppid(appid);
			unit.setSecret(secret);
			unit.setUnitName(unitName);
			unitDao.saveOrUpdate(unit);
			Hint.hint("添加成功", "", request, response);
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().println("add unit error");
		}
	}
	
	private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		try{
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			Integer unitID = Integer.parseInt(request.getParameter("unitID"));
			Integer role = Integer.parseInt(request.getParameter("role"));
			if(account==null||password==null){
				return;
			}
			TB_Unit unit = unitDao.findByUnitID(unitID);
			if(unit==null){
				Hint.hint("请先添加所属", "", request, response);
				return;
			}
			TB_User user = new TB_User();
			user.setPassword(password);
			user.setUserAccount(account);
			user.setRole(role);
			user.setUnit(unit);
			userDao.saveOrUpdate(user);
			Hint.hint("添加成功", "", request, response);
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().println("add user error");
		}
	}
}
