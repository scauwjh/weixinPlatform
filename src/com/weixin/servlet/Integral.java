package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.weixin.service.UnitService;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月15日 下午3:08:07 
 * 
 * 积分
 */
@SuppressWarnings("serial")
public class Integral extends HttpServlet {
	
	private UnitService unitService = new UnitService();
	private PrintWriter out = null;
	private Integer unitID = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		unitID = (Integer)request.getSession().getAttribute("unitID");
		String action = request.getParameter("action");
		if(action.equals("get")){
			//获取积分信息
			getIntegral(request,response);
		}
		else if(action.equals("update")){
			//更新积分信息
			updateIntegral(request,response);
		}
	}
	
	private void getIntegral(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject print = unitService.getIntegral(unitID);
		if(print!=null){
			out.println(print);
			return;
		}
		out.println("error");
	}
	
	private void updateIntegral(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject update = JSONObject.fromObject(request.getParameter("data"));
		boolean flag = unitService.updateIntegral(update, unitID);
		if(flag){
			out.println("ok");
		}
		out.println("error");
	}
}
