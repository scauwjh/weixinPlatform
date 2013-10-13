package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.weixin.service.UnitService;

/** 
 * @author wjh 
 * @version 创建时间：2013年9月25日 下午18:04:27 
 *  
 * 优惠券
 */
public class Unit extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UnitService unitService = new UnitService();
	private PrintWriter out = null;
	private Integer unitID = null;
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request,response);
	}
	
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//初始化out和unitID
		out = response.getWriter();
		unitID = (Integer)request.getSession().getAttribute("unitID");
		String action = request.getParameter("action");
		//单位管理
		if(action.equals("get")){
			unitMessage(request,response);
		}
		//更新（添加）单位信息
		else if(action.equals("updateUM")){
			updateUnit(request,response);
		}
		//更新自动回复
		else if(action.equals("updateAR")){
			updateAutoReply(request,response);
		}
	}
	
	private void updateUnit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject data = JSONObject.fromObject(request.getParameter("data"));
		boolean flag = unitService.saveOrUpdate(data);
		if(flag){
			out.println("ok");
			return;
		}
		out.println("error");
	}
	
	private void unitMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer unitID = (Integer) request.getSession().getAttribute("unitID");
		JSONObject print = unitService.get(unitID);
		if(print!=null){
			out.println(print);
			return;
		}
		out.println("error");
	}
	
	private void updateAutoReply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String autoReply = request.getParameter("data");
		boolean ret = unitService.updateAutoReply(unitID, autoReply);
		if(ret){
			out.println("ok");
			return;
		}
		out.println("error");
	}

}
