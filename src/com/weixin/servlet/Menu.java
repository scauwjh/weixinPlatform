package com.weixin.servlet;

import com.weixin.service.MenuService;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 创建菜单servlet
 * 
 * TOKEN过时会重新获取
 * 从前端获取JSON数据
 */
public class Menu extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private MenuService menuService = new MenuService();
	private PrintWriter out = null;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		String action = request.getParameter("action");
		if(action.equals("post")){
			setMenu(request,response);
		}
		else{
			getMenuDatas(request,response);
		}
	}

	private void setMenu(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//单位id，从session获取
		Integer unitID = (Integer)request.getSession().getAttribute("unitID");
		JSONObject requestJson = JSONObject.fromObject(request.getParameter("data"));
		String ret = menuService.setMenu(requestJson,unitID);
		out.println(ret);
	}
	
	private void getMenuDatas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//单位id，从session获取
		Integer unitID = (Integer)request.getSession().getAttribute("unitID");
		String ret = menuService.getMenu(unitID);
		if(ret==null){
			out.println("error");
			return;
		}
		out.println(ret);
	}
}